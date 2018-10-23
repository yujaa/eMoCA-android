package edu.uic.hcilab.emoca.TestFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;

public class Test_a3 extends android.app.Fragment {
    AudioPlayer ap;

    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {4000};

    public Test_a3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);


        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.a3_inst, mCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_a3, container, false);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ap.stop();
    }

    AudioPlayer.AudioCallback mCallback = new AudioPlayer.AudioCallback(){
        @Override
        public void onAudioCallback() {
            switch(audioNum){
                case 0:
                    ap.audioAnimationPlayer(R.raw.a3, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 1;
                    break;

            }
        }

    };

}