package edu.uic.hcilab.emoca.TestFragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;
import edu.uic.hcilab.emoca.SpeechRecognition;

import static android.app.Activity.RESULT_OK;

public class Test_l2 extends android.app.Fragment {
    AudioPlayer ap;
    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {3000};

    SpeechRecognition sr;
    private Button toggleReco;

    public Test_l2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.l2_inst, mCallback);

        sr = new SpeechRecognition(1);
        sr.sessionInit(this.getActivity().getApplicationContext());
        sr.loadEarcons(this.getActivity().getApplicationContext());

        sr.setState(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_l2, container, false);
        viewArr[0] = view.findViewById(R.id.l2_alert);
        viewArr[1] = view.findViewById(R.id.l2_ans);


        sr.setTextViews((TextView)view.findViewById(R.id.l2_ans), (TextView)view.findViewById(R.id.l2_log));
        toggleReco = (Button)view.findViewById(R.id.l2_toggle_reco);
        toggleReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == toggleReco) {
                    sr.toggleReco();
                }
            }
        });
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
                    ((TextView)viewArr[0]).setText("Listen Carefully.");
                    ap.audioAnimationPlayer(R.raw.l2, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 1;
                    break;

                case 1:
                    ((TextView)viewArr[0]).setText("Press the Button when you are ready to answer.");
                    ((TextView)viewArr[0]).setAlpha(1.0f);
                    ((Button)toggleReco).setBackgroundResource(R.drawable.recording);

                    audioNum = 2;
                    break;

            }
        }

    };

}