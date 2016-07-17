package cc.haoduoyu.demoapp.audiorecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 录音器
 * <p/>
 * Created by XP on 2016/7/17.
 */
public class VoiceRecorder {
    MediaRecorder recorder;
    static final String PREFIX = "voice";
    static final String EXTENSION = ".amr";
    private boolean isRecording = false;
    private long startTime;
    private String voiceFilePath = null;
    private String voiceFileName = null;
    private File file;
    private Handler handler;

    public VoiceRecorder(Handler var1) {
        this.handler = var1;
    }

    public String startRecording(String fileName) {
        this.file = null;

        try {
            if (this.recorder != null) {
                this.recorder.release();
                this.recorder = null;
            }

            this.recorder = new MediaRecorder();
            this.recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            this.recorder.setAudioChannels(1);
            this.recorder.setAudioSamplingRate(8000);
            this.recorder.setAudioEncodingBitRate(64);
            this.voiceFileName = this.getVoiceFileName(fileName);
            this.voiceFilePath = this.getVoiceFilePath();
            this.file = new File(this.voiceFilePath);
            this.recorder.setOutputFile(this.file.getAbsolutePath());
            this.recorder.prepare();
            this.isRecording = true;
            this.recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("voice", "prepare() failed");
        }

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        if (VoiceRecorder.this.isRecording) {
                            Message message = new Message();
                            message.what = VoiceRecorder.this.recorder.getMaxAmplitude() * 13 / 32767;
                            VoiceRecorder.this.handler.sendMessage(message);
                            SystemClock.sleep(100L);
                            continue;
                        }
                    } catch (Exception var2) {
                        Log.e("voice", var2.toString());
                    }

                    return;
                }
            }
        }).start();

        this.startTime = new Date().getTime();
        Log.d("voice", "start voice recording to file:" + this.file.getAbsolutePath());
        return this.file == null ? null : this.file.getAbsolutePath();
    }

    public void discardRecording() {
        if (this.recorder != null) {
            try {
                this.recorder.stop();
                this.recorder.release();
                this.recorder = null;
                if (this.file != null && this.file.exists() && !this.file.isDirectory()) {
                    this.file.delete();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            this.isRecording = false;
        }

    }

    public int stopRecoding() {
        if (this.recorder != null) {
            this.isRecording = false;
            this.recorder.stop();
            this.recorder.release();
            this.recorder = null;
            if (this.file != null && this.file.exists() && this.file.isFile()) {
                if (this.file.length() == 0L) {
                    this.file.delete();
                    return -1011;
                } else {
                    int var1 = (int) ((new Date()).getTime() - this.startTime) / 1000;
                    Log.d("voice", "voice recording finished. seconds:" + var1 + " file length:" + this.file.length());
                    return var1;
                }
            } else {
                return -1011;
            }
        } else {
            return 0;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.recorder != null) {
            this.recorder.release();
        }

    }

    public String getVoiceFileName(String fileName) {
        Time time = new Time();
        time.setToNow();
        return PREFIX + fileName + time.toString().substring(0, 15) + EXTENSION;
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public String getVoiceFilePath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "demoapp" + File.separator + "voice" + File.separator);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file + File.separator + voiceFileName;
    }


}

