package com.jacob.bt.spp.utils;

/**
 * Package : com.jacob.bt.spp.utils
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是描述所有命令
 */
public class CommandUtils {

    //COMMAND {
    private static final String COMMAND_GET_IMEI = "Get IMEI";
    private static final String COMMAND_GET_IMEI_DATA = "gmi:";
    private static final String COMMAND_GET_IMSI = "Get IMSI";
    private static final String COMMAND_GET_IMSI_DATA = "gii:";
    private static final String COMMAND_GET_BATTERY = "Get Battery Status";
    private static final String COMMAND_GET_BATTERY_DATA = "gbi:";
    private static final String COMMAND_GET_GSM = "Get GSM Status";
    private static final String COMMAND_GET_GSM_DATA = "ggi:";
    private static final String COMMAND_GET_WIFI_MAC = "Get WIFI MAC";
    private static final String COMMAND_GET_WIFI_MAC_DATA = "gwm:";
    private static final String COMMAND_GET_BT_MAC = "Get BT MAC";
    private static final String COMMAND_GET_BT_MAC_DATA = "gbm:";
    private static final String COMMAND_REBOOT = "Reboot";
    private static final String COMMAND_REBOOT_DATA = "rb:";
    private static final String COMMAND_POWER_OFF = "Power Off";
    private static final String COMMAND_POWER_OFF_DATA = "pof:";

    private static final String COMMAND_PULL_FILE = "Pull File";
    private static final String COMMAND_PUSH_FILE = "Push File";
    private static final String COMMAND_OPEN_FILE_DATA = "fo:";
    private static final String COMMAND_SET_FILE_PATH_DATA = "fsp:";
    private static final String COMMAND_CLOSE_FILE_DATA = "fc:";
    private static final String COMMAND_FILE_READ_DATA = "fr:";
    private static final String COMMAND_FILE_WRITE_DATA = "fw:";


    private static final String[] mCommandString = {
            COMMAND_GET_IMEI,
            COMMAND_GET_IMSI,
            COMMAND_GET_BATTERY,
            COMMAND_GET_GSM,
            COMMAND_GET_WIFI_MAC,
            COMMAND_GET_BT_MAC,
            COMMAND_REBOOT,
            COMMAND_POWER_OFF,
            COMMAND_PULL_FILE,
            COMMAND_PUSH_FILE,
    };
}
