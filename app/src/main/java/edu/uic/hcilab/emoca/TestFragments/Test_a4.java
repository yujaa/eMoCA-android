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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;
import edu.uic.hcilab.emoca.SpeechRecognition;

import static android.app.Activity.RESULT_OK;

public class Test_a4 extends android.app.Fragment {
    AudioPlayer ap;
    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {3000};

    SpeechRecognition sr;
    private Button toggleReco;

    public Test_a4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);

        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.a4_inst, mCallback);

        sr = new SpeechRecognition(2);
        sr.sessionInit(this.getActivity().getApplicationContext());
        sr.loadEarcons(this.getActivity().getApplicationContext());

        sr.setState(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_a4, container, false);

        TextView ansArr[] ={(TextView)view.findViewById(R.id.a4_ans1), (TextView)view.findViewById(R.id.a4_ans2),
                (TextView)view.findViewById(R.id.a4_ans3), (TextView)view.findViewById(R.id.a4_ans4),
                (TextView)view.findViewById(R.id.a4_ans5), (TextView)view.findViewById(R.id.a4_ans6)};

        sr.setTextViews(ansArr, (TextView)view.findViewById(R.id.a4_log), getContext());
        toggleReco = (Button)view.findViewById(R.id.a4_toggle_reco);
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
                    audioNum = 2;
                    break;

            }
        }

    };
}