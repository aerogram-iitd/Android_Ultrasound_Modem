package in.aerogram.eziosense.tone.db;

import android.content.Context;
import android.content.SharedPreferences;

import static in.aerogram.eziosense.tone.Constants.DEFAULT_DURATION;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_HAIL_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_LOW_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_SAMPL_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_STEP_FREQ;
import static in.aerogram.eziosense.tone.MainApplication.MODE_PRIVATE;
import static in.aerogram.eziosense.tone.MainApplication.getMainApplication;

/**
 * @author rishabh-goel on 30-04-2020
 * @project Eziosense
 */
public class PreferencesUtil {

    private static final String TAG = "PreferencesUtil";

    private static final String PREFS_FILE_NAME = "ezio_preference";

    public static final String PREF_LOW_FREQ = TAG + "lowfreq";

    public static final String PREF_STEP_FREQ = TAG + "stepfreq";

    public static final String PREF_SAMPL_FREQ = TAG + "samplfreq";

    public static final String PREF_HAIL_FREQ = TAG + "hailfreq";

    public static final String PREF_DURATION = TAG + "duration";

    public static void setLowFreq(final int value) {
        final SharedPreferences sharedPreferences = getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt(PREF_LOW_FREQ, value).apply();
    }

    public static int getLowFreq() {
        return getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getInt(PREF_LOW_FREQ, DEFAULT_LOW_FREQ);
    }

    public static void setStepFreq(final int value) {
        final SharedPreferences sharedPreferences = getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt(PREF_STEP_FREQ, value).apply();
    }

    public static int getStepFreq() {
        return getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getInt(PREF_STEP_FREQ, DEFAULT_STEP_FREQ);
    }

    public static void setSamplFreq(final int value) {
        final SharedPreferences sharedPreferences = getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt(PREF_SAMPL_FREQ, value).apply();
    }

    public static int getSamplwFreq() {
        return getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getInt(PREF_SAMPL_FREQ, DEFAULT_SAMPL_FREQ);
    }

    public static void setHailFreq(final int value) {
        final SharedPreferences sharedPreferences = getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt(PREF_HAIL_FREQ, value).apply();
    }

    public static int getHailFreq() {
        return getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getInt(PREF_HAIL_FREQ, DEFAULT_HAIL_FREQ);
    }

    public static void setDuration(final float value) {
        final SharedPreferences sharedPreferences = getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putFloat(PREF_DURATION, value).apply();
    }

    public static float getDuration() {
        return getMainApplication().getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getFloat(PREF_DURATION, DEFAULT_DURATION);
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }


}
