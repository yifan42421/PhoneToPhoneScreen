package com.example.byc.phonesharescreen;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ScreenShareToActivity extends AppCompatActivity {

    private Button btn_startrecord;
    private Button btn_stoprecord;
    private SurfaceView surfaceView;
    private static final int REQUEST_MEDIA_PROJECTION=1000;
    private int mScreenDensity;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mMediaProjectionManager;
    private VirtualDisplay mVirtualDisplay;
    private MediaRecorder mediaRecorder = null;
    private boolean running = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_share_to);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        mediaRecorder = new MediaRecorder();
        mMediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        btn_startrecord = (Button)findViewById(R.id.btn_startrecord);
        btn_startrecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(running){
                    Toast.makeText(getApplicationContext(),"正在录制中...",Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(),REQUEST_MEDIA_PROJECTION);//android 21版本可用
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"截屏失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_stoprecord = (Button)findViewById(R.id.btn_stoprecord);
        btn_stoprecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                stopRecord();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_MEDIA_PROJECTION){

            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode,data);
            startRecord();
        }
    }

    private void createVirtualDisplay(Surface surface) {
        mVirtualDisplay = mMediaProjection.createVirtualDisplay(
                "MainScreen",
                surfaceView.getWidth(),
                surfaceView.getHeight(),
                mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface,
                null, null);
    }

    private void initRecorder() {
        File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".mp4");
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setVideoSize(surfaceView.getWidth(),surfaceView.getHeight());
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        mediaRecorder.setVideoFrameRate(30);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean startRecord() {
        if (mMediaProjection == null || running) {
            return false;
        }
        initRecorder();
        createVirtualDisplay(mediaRecorder.getSurface());
        createVirtualDisplay(surfaceView.getHolder().getSurface());
        mediaRecorder.start();
        running = true;
        return true;
    }

    private boolean stopRecord(){
        if(mMediaProjection == null || !running){
            return false;
        }
        mMediaProjection.stop();
        if(mediaRecorder != null){
            mediaRecorder.stop();
        }
        running = false;
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecord();
    }
}
