package in.aerogram.eziosense.tone.ui.config;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import in.aerogram.eziosense.tone.R;
import in.aerogram.eziosense.tone.databinding.ActivityConfigBinding;
import in.aerogram.eziosense.tone.db.PreferencesUtil;
import in.aerogram.eziosense.tone.ui.BaseActivity;

import static in.aerogram.eziosense.tone.Constants.DEFAULT_DURATION;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_HAIL_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_LOW_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_SAMPL_FREQ;
import static in.aerogram.eziosense.tone.Constants.DEFAULT_STEP_FREQ;

/**
 * @author rishabh-goel on 29-01-2021
 * @project eziotone
 */
public class ConfigActivity extends BaseActivity {

    private ActivityConfigBinding mBinding;

    private int lowFreq;
    private int stepFreq;
    private int samplFreq;
    private int hailFreq;
    private float duration;

    public static Intent start(final Context context, final String action, final Bundle extras) {
        Intent intent = new Intent(context, ConfigActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (action != null && !"".equals(action)) intent.setAction(action);
        if (extras != null) intent.putExtras(extras);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_config);
        setUpUI();
    }

    private void setUpUI() {
        lowFreq = PreferencesUtil.getLowFreq();
        stepFreq = PreferencesUtil.getStepFreq();
        samplFreq = PreferencesUtil.getSamplwFreq();
        hailFreq = PreferencesUtil.getHailFreq();
        duration = PreferencesUtil.getDuration();
        mBinding.acLowfTipet.setText(String.valueOf(lowFreq));
        mBinding.acStepfTipet.setText(String.valueOf(stepFreq));
        mBinding.acSampfTipet.setText(String.valueOf(samplFreq));
        mBinding.acHailfTipet.setText(String.valueOf(hailFreq));
        mBinding.acDurationTipet.setText(String.valueOf(duration));
    }

    public void handleResetClick(View view) {
        Toast.makeText(this, "Reset to default values!", Toast.LENGTH_SHORT).show();
        mBinding.acLowfTipet.setText(String.valueOf(DEFAULT_LOW_FREQ));
        mBinding.acStepfTipet.setText(String.valueOf(DEFAULT_STEP_FREQ));
        mBinding.acSampfTipet.setText(String.valueOf(DEFAULT_SAMPL_FREQ));
        mBinding.acHailfTipet.setText(String.valueOf(DEFAULT_HAIL_FREQ));
        mBinding.acDurationTipet.setText(String.valueOf(DEFAULT_DURATION));
    }

    public void handleDoneClick(View view) {
        final String lowFreq = mBinding.acLowfTipet.getText().toString();
        final String stepFreq = mBinding.acStepfTipet.getText().toString();
        final String samplingFreq = mBinding.acSampfTipet.getText().toString();
        final String hailFreq = mBinding.acHailfTipet.getText().toString();
        final String duration = mBinding.acDurationTipet.getText().toString();
        if (!TextUtils.isEmpty(lowFreq) && TextUtils.isDigitsOnly(lowFreq) &&
                !TextUtils.isEmpty(stepFreq) && TextUtils.isDigitsOnly(stepFreq) &&
                !TextUtils.isEmpty(samplingFreq) && TextUtils.isDigitsOnly(samplingFreq) &&
                !TextUtils.isEmpty(hailFreq) && TextUtils.isDigitsOnly(hailFreq) &&
                !TextUtils.isEmpty(duration)) {
            this.lowFreq = Integer.parseInt(lowFreq);
            this.stepFreq = Integer.parseInt(stepFreq);
            this.samplFreq = Integer.parseInt(samplingFreq);
            this.hailFreq = Integer.parseInt(hailFreq);
            this.duration = Float.parseFloat(duration);
            PreferencesUtil.setLowFreq(this.lowFreq);
            PreferencesUtil.setStepFreq(this.stepFreq);
            PreferencesUtil.setSamplFreq(this.samplFreq);
            PreferencesUtil.setHailFreq(this.hailFreq);
            PreferencesUtil.setDuration(this.duration);
//            Constants.setConstants(this.lowFreq, this.stepFreq, this.samplFreq, this.duration, this.hailFreq);
            Toast.makeText(this, "Values applied successfully! Please restart the application", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(this, "Please enter the correct values!", Toast.LENGTH_SHORT).show();
    }

}
