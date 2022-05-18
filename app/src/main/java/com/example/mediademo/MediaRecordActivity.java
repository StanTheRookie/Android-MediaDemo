package com.example.mediademo;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MediaRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextureView textureView;
    private Button startStopButton;
    private MediaRecorder mediaRecorder;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);

        textureView = findViewById(R.id.texureViewVideo);
        startStopButton = findViewById(R.id.buttonStartStop);
        startStopButton.setOnClickListener(this); //注意这是一种新的监听按钮方法，让Activity实现 onClick 方法

    }

    @Override
    public void onClick(View view) {
        CharSequence text = startStopButton.getText(); //CharSequence 数据类型：A CharSequence is a readable sequence of char values.
        if(TextUtils.equals(text,"开始录制")){ //用系统的Text指令集方法判断text 内容
            startStopButton.setText("结束录制");

            //先打开Camera,否则将遇到 MediaRecorder 报错，代码 -19
            camera = android.hardware.Camera.open();
            camera.setDisplayOrientation(90); //设置摄像头显示的时候倒转90度，否则预览的时候将会是画面倒转的情况。
            camera.unlock();

            //创建一个MediaRecorder对象
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setCamera(camera);

            //查阅状态图，setAudioSource 和setVideoSource
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置音频源
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); //安卓的Camera API有两个版本，CAMERA 以及Camera Version2 （SURFACE）
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //指定录制的视频格式，参考MediaRecorder的状态图步骤指引
            //设置音视频编码格式， 必须设置，否则会报错。
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setOrientationHint(90); //将录制的视频调整90度，否则录制的是颠倒的视频。
            //设置音视频文件输出路径
            mediaRecorder.setOutputFile(new File(getExternalFilesDir(""),"a.mp4").getAbsolutePath()); //将音视频文件保存在APP的私有目录下。
            mediaRecorder.setVideoSize(480,640);   // 华为的手机对录制的VideoSize比较挑剔，宽高比要设置好，否则报错  Mediarecorder start failed -19
            mediaRecorder.setVideoFrameRate(20);

            //设置预览
            mediaRecorder.setPreviewDisplay(new Surface(textureView.getSurfaceTexture())); //设置预览，需要传入一个Surface对象作为参数, 指定由textureView 生成的Surface作为画布。

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();


        }else{
            startStopButton.setText("开始录制");
            mediaRecorder.stop();
            mediaRecorder.release();
            camera.stopPreview();
            camera.release();
        }
    }
}