package edu.uic.hcilab.emoca;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.File;

public class AudioPlayer {

    public void audioPlayer(Context context, int rawId){
        //set up MediaPlayer
        MediaPlayer mp = MediaPlayer.create(context, rawId);
        //\new MediaPlayer();

        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void audioAnimationPlayer(String path, String fileName, int startTime, int endTime){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(path + File.separator + fileName);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
