package in.aerogram.eziosense.tone.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import in.aerogram.eziosense.tone.ui.home.HomeActivity;

import static in.aerogram.eziosense.tone.MainApplication.getMainApplication;

/**
 * @author rishabh-goel on 04-05-2020
 * @project Eziosense
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(HomeActivity.start(getMainApplication(), null, null));
        finish();
    }

}
