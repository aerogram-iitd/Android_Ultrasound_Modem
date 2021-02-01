package in.aerogram.eziosense.tone.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import in.aerogram.eziosense.tone.R;
import in.aerogram.eziosense.tone.databinding.ActivityHomeBinding;
import in.aerogram.eziosense.tone.lib.Encoder;
import in.aerogram.eziosense.tone.lib.MicrophoneListener;
import in.aerogram.eziosense.tone.lib.PlayThread;
import in.aerogram.eziosense.tone.lib.StreamDecoder;
import in.aerogram.eziosense.tone.ui.BaseActivity;
import in.aerogram.eziosense.tone.ui.config.ConfigActivity;
import in.aerogram.eziosense.tone.ui.helpers.PermissionsHelper;
import timber.log.Timber;

import static in.aerogram.eziosense.tone.ui.helpers.PermissionsHelper.PERMISSIONS_REQUEST_ACCESS_MIC;
import static in.aerogram.eziosense.tone.ui.helpers.PermissionsHelper.askPermission;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    public static final String ACTIVE_TAG = TAG + "active";

    private MicrophoneListener microphoneListener = null;
    private PlayThread playThread;
    private StreamDecoder sDecoder = null;
    private ByteArrayOutputStream decodedStream = new ByteArrayOutputStream();
    private Timer refreshTimer = null;

    private Handler mHandler = new Handler();

    private Uri mCreateDataUri = null;
    private String mCreateDataType = null;
    private String mCreateDataExtraText = null;

    private ActivityHomeBinding mBinding;

    public static Intent start(final Context context, final String action, final Bundle extras) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (action != null && !"".equals(action)) intent.setAction(action);
        if (extras != null) intent.putExtras(extras);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        askRecordPermission();

        // TODO
        final Intent intent = getIntent();
        final String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            mCreateDataUri = intent.getData();
            mCreateDataType = intent.getType();
            if (mCreateDataUri == null) {
                mCreateDataUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            }
            mCreateDataExtraText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (mCreateDataUri == null)
                mCreateDataType = null;
            // The new entry was created, so assume all will end well and
            // set the result to be returned.
            setResult(RESULT_OK, (new Intent()).setAction(null));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sent = null;
        if (mCreateDataExtraText != null) {
            sent = mCreateDataExtraText;
        } else if (mCreateDataType != null && mCreateDataType.startsWith("text/")) {
            //read the URI into a string
            byte[] b = readDataFromUri(this.mCreateDataUri);
            if (b != null)
                sent = new String(b);
        }
        if (sent != null) mBinding.ahDataEt.setText(sent);
        refreshTimer = new Timer();
        refreshTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // have to do this on the UI thread
                        mHandler.post(() -> updateResults());
                    }
                }, 500, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (refreshTimer != null) {
            refreshTimer.cancel();
            refreshTimer = null;
        }
        stopListening();
    }

    private void updateResults() {
        if (microphoneListener != null) {
            mBinding.ahDataViewTv.setText(decodedStream.toString());
            mBinding.ahDataStatusViewTv.setText(sDecoder.getStatusString());
        } else mBinding.ahDataStatusViewTv.setText("");
    }

    private void listen() {
        stopListening();
        decodedStream.reset();
        //the StreamDecoder uses the Decoder to decode samples put in its AudioBuffer
        // StreamDecoder starts a thread
        sDecoder = new StreamDecoder(decodedStream);
        //the MicrophoneListener feeds the microphone samples into the AudioBuffer
        // MicrophoneListener starts a thread
        microphoneListener = new MicrophoneListener(sDecoder.getAudioBuffer());
        System.out.println("Listening");
    }

    private void stopListening() {
        if (microphoneListener != null)
            microphoneListener.quit();
        microphoneListener = null;
        if (sDecoder != null)
            sDecoder.quit();
        sDecoder = null;
        if (playThread != null)
            playThread.stopLoop();
        playThread = null;
    }

    private void perform(String input) {
        try {
            //try to play the file
            Timber.d("Performing " + input);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Encoder.encodeStream(new ByteArrayInputStream(input.getBytes()), baos);
            playThread = new PlayThread(baos.toByteArray());
        } catch (Exception e) {
            Timber.e("Could not encode " + input + " because of " + e);
        }
    }

    private byte[] readDataFromUri(Uri uri) {
        byte[] buffer = null;
        try {
            InputStream stream = getContentResolver().openInputStream(uri);

            int bytesAvailable = stream.available();
            //int maxBufferSize = 1024;
            int bufferSize = bytesAvailable; //Math.min(bytesAvailable, maxBufferSize);
            int totalRead = 0;
            buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = stream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                bytesRead = stream.read(buffer, totalRead, bufferSize);
                totalRead += bytesRead;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return buffer;
    }

    public void handleConfigFabClick(View view) {
        startActivity(ConfigActivity.start(getApplicationContext(), null, null));
    }

    public void handleSendClick(View view) {
        final String data = mBinding.ahDataEt.getText().toString();
        if (!TextUtils.isEmpty(data)) perform(data);
        else Toast.makeText(this, "Please enter a valid data!", Toast.LENGTH_SHORT).show();
    }

    public void handleRecvClick(View view) {
        if (isPermissionGranted()) {
            final String tag = (String) mBinding.ahRecvMtrBtn.getTag();
            if (ACTIVE_TAG.equals(tag)) {
                stopListening();
                mBinding.ahRecvMtrBtn.setText("Receive");
                mBinding.ahRecvMtrBtn.setTag(null);
                return;
            }
            listen();
            mBinding.ahRecvMtrBtn.setText("Receiving...");
            mBinding.ahRecvMtrBtn.setTag(ACTIVE_TAG);
        } else askRecordPermission();
    }

    // region Permissions

    private boolean isRecordPermissionGranted = false;
    private boolean isRecordPermissionDisabled = false;


    // endregion

    private boolean isPermissionGranted() {
        isRecordPermissionGranted = PermissionsHelper.isPermissionGranted(this, Manifest.permission.RECORD_AUDIO);
        return isRecordPermissionGranted;
    }

    private void askRecordPermission() {
        PermissionsHelper.checkPermission(this, Manifest.permission.RECORD_AUDIO, new PermissionsHelper.PermissionAskListener() {
            @Override
            public void onNeedPermission() {
                Timber.e("onNeedPermission");
                isRecordPermissionGranted = false;
                isRecordPermissionDisabled = false;
            }

            @Override
            public void onPermissionPreviouslyDenied() {
                Timber.e("onPermissionPreviouslyDenied");
                isRecordPermissionGranted = false;
                isRecordPermissionDisabled = false;
            }

            @Override
            public void onPermissionDisabled() {
                Timber.e("onPermissionDisabled");
                isRecordPermissionGranted = false;
                isRecordPermissionDisabled = true;
            }

            @Override
            public void onPermissionGranted() {
                Timber.e("onPermissionGranted");
                isRecordPermissionGranted = true;
                isRecordPermissionDisabled = false;
            }
        });
        if (!isRecordPermissionGranted)
            askPermission(this, PERMISSIONS_REQUEST_ACCESS_MIC, Manifest.permission.RECORD_AUDIO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_MIC:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted by User
                    isRecordPermissionDisabled = false;
                    isRecordPermissionGranted = true;
                } else {
                    // Permission Denied or Disabled by User
                    if (isRecordPermissionDisabled) {
                        Toast.makeText(this, "~ Permission Disabled by you! ~\n\nPlease Enable it under the App Settings first.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "~ Permission Denied ~", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

}