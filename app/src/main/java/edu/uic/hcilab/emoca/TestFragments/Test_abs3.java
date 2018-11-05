package edu.uic.hcilab.emoca.TestFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;

import static android.app.Activity.RESULT_OK;

public class Test_abs3 extends android.app.Fragment {
    AudioPlayer ap;
    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {3000};

    public Test_abs3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.abs3, mCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_abs3, container, false);
        viewArr[0] = view.findViewById(R.id.abs3_inst);
        viewArr[1] = view.findViewById(R.id.abs3_button);
        viewArr[2] = view.findViewById(R.id.abs3_text);

        viewArr[1].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
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

        }
    };

    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say the answer");
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
                    ((TextView)viewArr[2]).setText(result.get(0));
                }
                break;
            }

        }
    }

}