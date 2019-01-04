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
import edu.uic.hcilab.emoca.WriteSDcard;

import static android.app.Activity.RESULT_OK;

public class Test_a1 extends android.app.Fragment {
    AudioPlayer ap;
    WriteSDcard wr;
    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {5000};

    SpeechRecognition sr;
    private Button toggleReco;

    public Test_a1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);

        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.a1_inst, mCallback);
        wr = new WriteSDcard();

        sr = new SpeechRecognition(2);
        sr.sessionInit(this.getActivity().getApplicationContext());
        sr.loadEarcons(this.getActivity().getApplicationContext());
        sr.setState(1);

        wr.writeToSDFile(this.getContext(), "Start A1 introduction");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_a1, container, false);
        viewArr[0] = view.findViewById(R.id.a1_alert);
        viewArr[1] = view.findViewById(R.id.a1_ans);

        sr.setTextViews((TextView)view.findViewById(R.id.a1_ans), (TextView)view.findViewById(R.id.a1_log), getContext());
        toggleReco = (Button)view.findViewById(R.id.a1_toggle_reco);
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
                    ap.audioAnimationPlayer(R.raw.a1, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 1;
                    break;

                case 1:
                    ((TextView)viewArr[0]).setText("Press Button when you are ready to answer.");
                    ((TextView)viewArr[0]).setAlpha(1.0f);
                    ((Button)toggleReco).setBackgroundResource(R.drawable.recording);

                    audioNum = 2;
                    break;

            }
        }

    };

    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say numbers");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ((TextView)viewArr[1]).setText(result.get(0));
                }
                break;
            }

        }
    }

}