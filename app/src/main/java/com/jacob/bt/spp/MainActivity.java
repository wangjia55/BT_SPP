package com.jacob.bt.spp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jacob.bt.spp.core.BtManager;
import com.jacob.bt.spp.core.ConnectState;
import com.jacob.bt.spp.impl.BtConnectCallBack;
import com.jacob.bt.spp.impl.BtPullFileCallBack;
import com.jacob.bt.spp.impl.BtTransferDataCallBack;
import com.jacob.bt.spp.utils.CommandUtils;
import com.jacob.bt.spp.utils.LogUtils;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private static final int REQUEST_START_BLE = 10;
    private TextView mTextViewData;
    private TextView mTextViewDataFilter;
    private Spinner mSpinnerCommand;
    private Button mButtonConnect;
    private Button mButtonSendData;
    private Button mButtonPullFile;
    private EditText mEditTextBTMac;
    private EditText mEditTextOutputData;
    private ScrollView mScrollViewData = null;
    private ScrollView mScrollViewDataFliter = null;


    private String mSendData;
    private String mCommand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonConnect = (Button) findViewById(R.id.button_connect_spp);
        mButtonConnect.setOnClickListener(this);
        mScrollViewData = (ScrollView) findViewById(R.id.scrollView_data);
        mScrollViewDataFliter = (ScrollView) findViewById(R.id.scrollView_data_fliter);

        mTextViewData = (TextView) findViewById(R.id.textView_data);
        mTextViewDataFilter = (TextView) findViewById(R.id.textView_data_fliter);
        mSpinnerCommand = (Spinner) findViewById(R.id.spinner_command);
        mButtonSendData = (Button) findViewById(R.id.button_send_data);
        mButtonSendData.setOnClickListener(this);

        mButtonPullFile = (Button) findViewById(R.id.button_pull_file);
        mButtonPullFile.setOnClickListener(this);


        mEditTextBTMac = (EditText) findViewById(R.id.editText_BT_MAC);
        mEditTextOutputData = (EditText) findViewById(R.id.editText_output_data);

        mEditTextOutputData.setEnabled(false);
        mButtonSendData.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommandUtils.sCommandString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCommand.setAdapter(adapter);
        mSpinnerCommand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCommand = CommandUtils.sCommandString[position];
                mEditTextOutputData.setText(CommandUtils.parseSendCommand(mCommand));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (BtManager.getInstance().getBluetoothState() == BluetoothAdapter.STATE_OFF) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_START_BLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_connect_spp:
                if (BtManager.getInstance().getCurrentState() == ConnectState.STATE_DISCONNECTED) {
                    String mac = mEditTextBTMac.getText().toString();
                    BtManager.getInstance().connect(mac, connectCallBack);
                } else {
                    BtManager.getInstance().disconnect();
                }
                break;
            case R.id.button_send_data:
                String text = mTextViewData.getText().toString();
                String command = CommandUtils.COMMAND_PREFIX + mEditTextOutputData.getText().toString();

                if (mSendData != null) {
                    command = CommandUtils.COMMAND_PREFIX + mSendData;
                    mSendData = null;
                }
                BtManager.getInstance().writeData(command.getBytes(), transferDataCallBack);
                mTextViewData.setText(text + "SEND -- " + command + "\r\n");
                break;
            case R.id.button_pull_file:
                BtManager.getInstance().pullFile("c:\\autostart.txt",pullFileCallBack);
                break;
        }
    }

    private BtPullFileCallBack pullFileCallBack = new BtPullFileCallBack() {
        @Override
        public void readData(final String data) {
            LogUtils.LOGE(TAG, "readData:" + data);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextViewDataFilter.setText(data + "\r\n");
                    mScrollViewData.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

        @Override
        public void readFail(String reason) {
            LogUtils.LOGE(TAG, "readFail:" + reason);
        }
    };


    private BtTransferDataCallBack transferDataCallBack = new BtTransferDataCallBack() {
        @Override
        public void sendData(byte[] data) {
            LogUtils.LOGE(TAG, "sendData:" + new String(data));
        }

        @Override
        public void readData(final byte[] data) {
            LogUtils.LOGE(TAG, "readData:" + new String(data));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String text = mTextViewData.getText().toString();
                    String data_str = new String(data);
                    mTextViewData.setText(text + "RECV -- " + data_str + "\r\n");
                    mScrollViewData.fullScroll(ScrollView.FOCUS_DOWN);

                    mTextViewDataFilter.setText(mTextViewDataFilter.getText().toString()
                            + CommandUtils.parseReadData(mCommand, data));
                }
            });

        }

        @Override
        public void transDataError(String reason) {
            LogUtils.LOGE(TAG, "transDataError:" + reason);
        }
    };

    private BtConnectCallBack connectCallBack = new BtConnectCallBack() {
        @Override
        public void deviceConnected() {
            LogUtils.LOGE(TAG, "deviceConnected");

            mButtonSendData.setEnabled(true);
            mButtonConnect.setText("DisConnect");
            mTextViewData.setText(mTextViewData.getText().toString() + "New connection!!!\r\n");
        }

        @Override
        public void deviceDisconnected(String reason) {
            LogUtils.LOGE(TAG, "device Disconnected-->" + reason);
            mButtonConnect.setText("Connect");
            mButtonSendData.setEnabled(false);
        }
    };

    /**
     * 开启ble的回执
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_START_BLE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "同意开启 BLE", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "拒绝开启 BLE", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BtManager.getInstance().disconnect();
        BtManager.getInstance().closeBluetooth();
    }
}
