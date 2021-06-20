package com.example.myapplication;

import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.util.AES;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestAesMp3Activity extends FragmentActivity {
    private byte[] key = "11111111111111111111111111111111".getBytes();
    private String localPath;
    private static final String TAG = "TestAesMp3Activity";

    private String fileName = "周杰伦-一路向北.mp3";
    private AudioTrack audioTrack;

    final int sampleRate = 44100;
    final int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,
            //MI3：CHANNEL_OUT_STEREO //[]AudioFormat.CHANNEL_OUT_STEREO
            //CHANNEL_OUT_MONO影响不大，只要是new AudioTrack构建时选择AudioFormat.CHANNEL_OUT_STEREO即可
            AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_aes_mp3);
        Log.i(TAG, "->" + getCacheDir().getAbsolutePath());
        Log.i(TAG, "->" + getExternalCacheDir().getAbsolutePath());
        localPath = getExternalCacheDir().getAbsolutePath() + File.separator + fileName;
    }

    private void initPlayer(byte[] result) {
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                2 * minBufferSize,
                AudioTrack.MODE_STATIC);
    }

    public void encode(View view) {
        try {
            try {
                AssetManager assetManager = getAssets();
                InputStream inputStream = assetManager.open(fileName);
                byte[] data = toByteArray(inputStream);
                byte[] result = AES.Aes256Encode(data, key);
                File file = new File(localPath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                OutputStream outputStream = new FileOutputStream(localPath);
                outputStream.write(result);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public short[] byteArray2ShortArray(byte[] data, int items) {
        short[] retVal = new short[items];
        for (int i = 0; i < retVal.length; i++)
            retVal[i] = (short) ((data[i * 2] & 0xff) | (data[i * 2 + 1] & 0xff) << 8);
        return retVal;
    }

    public void play(View view) {
        try {
            File file = new File(localPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = toByteArray(fileInputStream);
            byte[] result = AES.Aes256Decode(data, key);
            initPlayer(result);
            int writeResult = audioTrack.write(result, 0, result.length);
            if (writeResult == AudioTrack.ERROR_INVALID_OPERATION || writeResult == AudioTrack.ERROR_BAD_VALUE
                    || writeResult == AudioTrack.ERROR_DEAD_OBJECT || writeResult == AudioTrack.ERROR) {
                //出异常情况
                audioTrack.release();
            } else {
                audioTrack.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
