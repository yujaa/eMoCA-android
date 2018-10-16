package edu.uic.hcilab.emoca;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import java.io.File;

public class AudioPlayer {

    public int stop = 0;
    public void audioPlayer(final Context context, int rawId){
        //set up MediaPlayer
        MediaPlayer mp = MediaPlayer.create(context, rawId);

        try {
            //mp.setDataSource(path + File.separator + fileName);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
            }

        });

    }

    public void audioAnimationPlayer(Context context, int rawId, final View[] viewArr, int[] startTimeArr, int[] endTimeArr){
        //set up MediaPlayer
        MediaPlayer mp = MediaPlayer.create(context, rawId);

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
    }

}

