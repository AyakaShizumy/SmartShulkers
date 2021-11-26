package ee.whitenights.smartshulkers.shulker.util;

import ee.whitenights.smartshulkers.Main;
import ee.whitenights.smartshulkers.shulker.Shulker;

public class Message {

    public static String NO_PERMISSIONS = getMessage(".no-permissions");
    public static String PLAYER_NOT_FOUND = getMessage(".player-not-found");
    public static String SHULKER_GIVE_OK = getMessage(".shulker-give-ok");
    public static String SHULKER_CONVERT_OK = getMessage(".shulker-convert-ok");
    public static String SHULKER_CONVERT_ERROR = getMessage(".shulker-convert-error");
    public static String CMD_PLAYER_ONLY = getMessage(".cmd-player-only");
    public static String MATRIX_ERROR = getMessage(".marlix-error");

    public static String getMessage(String patch){
        return Main.config.getString(Shulker.MESSAGE_PATCH + patch);
    }
}
