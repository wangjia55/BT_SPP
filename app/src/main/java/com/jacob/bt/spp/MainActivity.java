package com.jacob.bt.spp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jacob.bt.spp.core.BtManager;
import com.jacob.bt.spp.impl.BtConnectCallBack;
import com.jacob.bt.spp.utils.CommandUtils;
import com.jacob.bt.spp.utils.LogUtils;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private TextView mTextViewData;
    private TextView mTextViewDataFliter;
    private Spinner mSpinnerCommand;
    private Button mButtonConnect;
    private Button mButtonSendData;

    private EditText mEditTextBTMac;
    private EditText mEditTextOutputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonConnect = (Button) findViewById(R.id.button_connect_spp);
        mButtonConnect.setOnClickListener(this);

        mTextViewData = (TextView) findViewById(R.id.textView_data);
        mTextViewDataFliter = (TextView) findViewById(R.id.textView_data_fliter);
        mSpinnerCommand = (Spinner) findViewById(R.id.spinner_command);
        mButtonSendData = (Button) findViewById(R.id.button_send_data);
        mButtonSendData.setOnClickListener(this);

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
                mEditTextOutputData.setText(CommandUtils.parseSendCommand(CommandUtils.sCommandString[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_connect_spp:
                String mac = mEditTextBTMac.getText().toString();
                BtManager.getInstance().connect(mac, connectCallBack);

                break;
            case R.id.button_send_data:


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BtManager.getInstance().disconnect();
    }

    private BtConnectCallBack connectCallBack = new BtConnectCallBack() {
        @Override
        public void deviceConnected() {
            LogUtils.LOGE(TAG, "deviceConnected");
        }

        @Override
        public void deviceDisconnected(String reason) {
            LogUtils.LOGE(TAG, "deviceDisconnected-->" + reason);
        }
    };
}
