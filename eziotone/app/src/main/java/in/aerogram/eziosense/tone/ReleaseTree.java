package in.aerogram.eziosense.tone;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * @author rishabh-goel on 06-10-2020
 * @project Eziosense
 */
public class ReleaseTree extends Timber.Tree {

    public static final String ERROR = "Error - ";
    public static final String WARN = "Warning - ";

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (priority == Log.ERROR) {
//            FirebaseCrashlytics.getInstance().log(ERROR + tag + "::" + message);
//            FirebaseCrashlytics.getInstance().recordException(new Exception("Report"));
        }
    }

}
