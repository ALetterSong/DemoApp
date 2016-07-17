package cc.haoduoyu.demoapp.audiorecorder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cc.haoduoyu.demoapp.R;
import cc.haoduoyu.demoapp.utils.SdCardUtil;

/**
 * Created by XP on 2016/7/17.
 */
public class VoiceRecordActivity extends AppCompatActivity {

    private Button recordBtn;
    private View recordingContainer;//录音containe
    private ImageView micImage;//mic图标
    private TextView recordingHint;   //取消发送hint
    private PowerManager.WakeLock wakeLock;
    private VoiceRecorder voiceRecorder;
    private Drawable[] micImages;


    private Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // 切换msg切换图片
            micImage.setImageDrawable(micImages[msg.what]);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicerecord);
        recordBtn = (Button) findViewById(R.id.btn_record);
        recordingContainer = findViewById(R.id.recording_container);
        micImage = (ImageView) findViewById(R.id.mic_image);   //mic图标
        recordingHint = (TextView) findViewById(R.id.recording_hint); //取消发送hint
        recordBtn.setOnTouchListener(new PressToSpeakListen());

        voiceRecorder = new VoiceRecorder(micImageHandler);
        wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "demo");
//        PowerManager.SCREEN_DIM_WAKE_LOCK


        // 动画资源文件,用于录制语音时
        micImages = new Drawable[]{getResources().getDrawable(R.drawable.record_animate_01),
                getResources().getDrawable(R.drawable.record_animate_02),
                getResources().getDrawable(R.drawable.record_animate_03),
                getResources().getDrawable(R.drawable.record_animate_04),
                getResources().getDrawable(R.drawable.record_animate_05),
                getResources().getDrawable(R.drawable.record_animate_06),
                getResources().getDrawable(R.drawable.record_animate_07),
                getResources().getDrawable(R.drawable.record_animate_08),
                getResources().getDrawable(R.drawable.record_animate_09),
                getResources().getDrawable(R.drawable.record_animate_10),
                getResources().getDrawable(R.drawable.record_animate_11),
                getResources().getDrawable(R.drawable.record_animate_12),
                getResources().getDrawable(R.drawable.record_animate_13),
                getResources().getDrawable(R.drawable.record_animate_14)};
    }


    class PressToSpeakListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!SdCardUtil.isSdCardAvailable()) {
                        String st4 = "Send_voice_need_sdcard_support";
                        Toast.makeText(VoiceRecordActivity.this, st4, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        wakeLock.acquire();
//                        if (VoicePlayClickListener.isPlaying)
//                            VoicePlayClickListener.currentPlayListener.stopPlayVoice();
                        recordingContainer.setVisibility(View.VISIBLE);
                        recordingHint.setText("move_up_to_cancel");
                        recordingHint.setBackgroundColor(Color.TRANSPARENT);
                        voiceRecorder.startRecording("name");
                    } catch (Exception e) {
                        e.printStackTrace();
                        v.setPressed(false);
                        if (wakeLock.isHeld())
                            wakeLock.release();
                        if (voiceRecorder != null)
                            voiceRecorder.discardRecording();
                        recordingContainer.setVisibility(View.INVISIBLE);
                        Toast.makeText(VoiceRecordActivity.this, "recoding_fail" + e.getMessage(), Toast
                                .LENGTH_SHORT).show();
                        return false;
                    }
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        recordingHint.setText("release_to_cancel");
                        recordingHint.setBackgroundResource(R.drawable.recording_text_hint_bg);
                    } else {
                        recordingHint.setText("move_up_to_cancel");
                        recordingHint.setBackgroundColor(Color.TRANSPARENT);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    recordingContainer.setVisibility(View.INVISIBLE);
                    if (wakeLock.isHeld())
                        wakeLock.release();
                    if (event.getY() < 0) {
                        // discard the recorded audio.
                        voiceRecorder.discardRecording();

                    } else {
                        // stop recording and send voice file
                        String st1 = "Recording_without_permission";
                        String st2 = "The_recording_time_is_too_short";
                        String st3 = "send_failure_please";
                        try {
                            int length = voiceRecorder.stopRecoding();
                            if (length > 0) {
                                Toast.makeText(getApplicationContext(), "save to " + "path: " +
                                        voiceRecorder.getVoiceFilePath()
                                        + "name: " + voiceRecorder.getVoiceFileName("name")
                                        + " length: " + Integer.toString(length), Toast
                                        .LENGTH_LONG).show();
                                Log.d("VoiceRecord", "path: " + voiceRecorder.getVoiceFilePath()
                                        + "name: " + voiceRecorder.getVoiceFileName("name")
                                        + " length: " + Integer.toString(length));
                            } else if (length == -1011) {
                                Toast.makeText(getApplicationContext(), st1, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "speak more than 1 seconds",
                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), st2, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(VoiceRecordActivity.this, st3, Toast.LENGTH_SHORT).show();
                        }

                    }
                    return true;
                default:
                    recordingContainer.setVisibility(View.INVISIBLE);
                    if (voiceRecorder != null)
                        voiceRecorder.discardRecording();
                    return false;
            }
        }
    }
}
