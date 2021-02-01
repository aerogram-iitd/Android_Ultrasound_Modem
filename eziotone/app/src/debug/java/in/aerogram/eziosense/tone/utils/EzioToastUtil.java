package in.aerogram.eziosense.tone.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import timber.log.Timber;

import static in.aerogram.eziosense.tone.MainApplication.getMainApplication;

/**
 * @author rishabh-goel on 24-04-2020
 * @project EzioSchool
 */
public final class EzioToastUtil {

    private EzioToastUtil() {

    }

    // This method should run in main Thread
    public static void showToast(final Context context, final String message) {
//        runOnMainThread(() -> {
        Timber.i(Thread.currentThread().getName() + "::" + Thread.currentThread().getId());
        try {
            Toast toast = Toast.makeText(getMainApplication(), message, Toast.LENGTH_LONG);
            toast.setMargin(50, 50);
            toast.show();
        } catch (Throwable th) {
            // ignore
        }
//        });
    }

    public static void showToast(final String message) {
//        runOnMainThread(() -> {
        Timber.i(Thread.currentThread().getName() + "::" + Thread.currentThread().getId());
        try {
            Toast toast = Toast.makeText(getMainApplication(), message, Toast.LENGTH_LONG);
            toast.setMargin(50, 50);
            toast.show();
        } catch (Throwable th) {
            // ignore
        }
//        });
    }

    public static void showText(final TextView textView, final String message) {
        if (message != null) {
//            runOnMainThread(() -> {
            Timber.i(Thread.currentThread().getName() + "::" + Thread.currentThread().getId());
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
//            });
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

}
