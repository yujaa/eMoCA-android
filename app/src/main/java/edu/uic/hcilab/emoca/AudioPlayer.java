package edu.uic.hcilab.emoca;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.io.File;

public class AudioPlayer{

    public AudioCallback mCallback;
    public Context mContext;
    MediaPlayer mp;
    WriteSDcard wr;
    public AudioPlayer(final Context context)
    {
        mContext = context;
    }

    public void stop(){
        mp.stop();
    }

    public void audioPlayer(int rawId){
        wr = new WriteSDcard();
        //set up MediaPlayer
        mp = MediaPlayer.create(mContext, rawId);
        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                wr.writeToSDFile(mContext, ":Audio stop");
            }
        });
    }

    public void audioPlayer(int rawId, final AudioCallback mCallback){
        //set up MediaPlayer
        mp = MediaPlayer.create(mContext, rawId);
        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCallback.onAudioCallback();
            }
        });
    }


    public void audioAnimationPlayer(int rawId, final View[] viewArr, int[] startTimeArr, int[] endTimeArr){
        wr = new WriteSDcard();
        //set up MediaPlayer
        mp = MediaPlayer.create(mContext, rawId);

        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.start();

            Animation show = new AlphaAnimation(0.0f, 1.0f);
            Animation hide = new AlphaAnimation(1.0f, 0.0f);


            for(int i  = 0; i< viewArr.length; i++) {
//                AnimationSet as =  new AnimationSet(true);
//                as.addAnimation(show);
//                as.setDuration(1000);
//
//
//                viewArr[i].startAnimation(as);
//            }
                final int final_i = i;
                viewArr[i].animate()
                        .setStartDelay(startTimeArr[i])
                        .alpha(1.0f)
                        .setDuration(endTimeArr[i] - startTimeArr[i])
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                viewArr[final_i].setAlpha(0.0f);
                            }
                        });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("my", "audio stop");
                wr.writeToSDFile(mContext, "Audio stop");
            }
        });
    }

    public void audioAnimationPlayer(int rawId, final View[] viewArr, int[] startTimeArr, int[] endTimeArr, final AudioCallback mCallback){
        //set up MediaPlayer
        mp = MediaPlayer.create(mContext, rawId);

        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.start();

            Animation show = new AlphaAnimation(0.0f, 1.0f);
            Animation hide = new AlphaAnimation(1.0f, 0.0f);


            for(int i  = 0; i< viewArr.length; i++) {
//                AnimationSet as =  new AnimationSet(true);
//                as.addAnimation(show);
//                as.setDuration(1000);
//
//
//                viewArr[i].startAnimation(as);
//            }
                final int final_i = i;
                viewArr[i].animate()
                        .setStartDelay(startTimeArr[i])
                        .alpha(1.0f)
                        .setDuration(endTimeArr[i] - startTimeArr[i])
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                viewArr[final_i].setAlpha(0.0f);
                            }
                        });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCallback.onAudioCallback();
            }
        });
    }



    public interface AudioCallback
    {
        public void onAudioCallback();
    }

}
