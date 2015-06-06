package com.jacob.bt.spp.utils;

/**
 * Package : com.jacob.bt.spp.utils
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是描述所有命令
 */
public class CommandUtils {

    //COMMAND {
    public static final String COMMAND_GET_IMEI = "Get IMEI";
    public static final String COMMAND_GET_IMEI_DATA = "gmi:";
    public static final String COMMAND_GET_IMSI = "Get IMSI";
    public static final String COMMAND_GET_IMSI_DATA = "gii:";
    public static final String COMMAND_GET_BATTERY = "Get Battery Status";
    public static final String COMMAND_GET_BATTERY_DATA = "gbi:";
    public static final String COMMAND_GET_GSM = "Get GSM Status";
    public static final String COMMAND_GET_GSM_DATA = "ggi:";
    public static final String COMMAND_GET_WIFI_MAC = "Get WIFI MAC";
    public static final String COMMAND_GET_WIFI_MAC_DATA = "gwm:";
    public static final String COMMAND_GET_BT_MAC = "Get BT MAC";
    public static final String COMMAND_GET_BT_MAC_DATA = "gbm:";
    public static final String COMMAND_REBOOT = "Reboot";
    public static final String COMMAND_REBOOT_DATA = "rb:";
    public static final String COMMAND_POWER_OFF = "Power Off";
    public static final String COMMAND_POWER_OFF_DATA = "pof:";

    public static final String COMMAND_PULL_FILE = "Pull File";
    public static final String COMMAND_PUSH_FILE = "Push File";
    public static final String COMMAND_OPEN_FILE_DATA = "fo:";
    public static final String COMMAND_SET_FILE_PATH_DATA = "fsp:";
    public static final String COMMAND_CLOSE_FILE_DATA = "fc:";
    public static final String COMMAND_FILE_READ_DATA = "fr:";
    public static final String COMMAND_FILE_WRITE_DATA = "fw:";


    public static final String[] sCommandString = {
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

    public static String parseSendCommand(String command) {
        switch (command) {
            case CommandUtils.COMMAND_GET_IMEI:
                return CommandUtils.COMMAND_GET_IMEI_DATA;

            case CommandUtils.COMMAND_GET_IMSI:
                return CommandUtils.COMMAND_GET_IMSI_DATA;

            case CommandUtils.COMMAND_GET_BATTERY:
                return CommandUtils.COMMAND_GET_BATTERY_DATA;

            case CommandUtils.COMMAND_GET_GSM:
                return CommandUtils.COMMAND_GET_GSM_DATA;

            case CommandUtils.COMMAND_GET_WIFI_MAC:
                return CommandUtils.COMMAND_GET_WIFI_MAC_DATA;

            case CommandUtils.COMMAND_GET_BT_MAC:
                return CommandUtils.COMMAND_GET_BT_MAC_DATA;

            case CommandUtils.COMMAND_REBOOT:
                return CommandUtils.COMMAND_REBOOT_DATA;

            case CommandUtils.COMMAND_POWER_OFF:
                return CommandUtils.COMMAND_POWER_OFF_DATA;

            case CommandUtils.COMMAND_PULL_FILE:
                return "";

            case CommandUtils.COMMAND_PUSH_FILE:
                return "";
            default:
                return "";
        }
    }
}
