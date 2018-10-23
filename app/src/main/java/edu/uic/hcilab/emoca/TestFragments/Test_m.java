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

public class Test_m extends android.app.Fragment {
    AudioPlayer ap;

    int audioNum = 0;
    View[] viewArr = new View[5];
    int[] startTimeArr = {0};
    int[] endTimeArr = {4000};

    public Test_m() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);


        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.m_inst, mCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_m, container, false);
        viewArr[0] = view.findViewById(R.id.m_alert);

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
                    ap.audioAnimationPlayer(R.raw.m, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 1;
                    break;

                case 1:
                    ((TextView)viewArr[0]).setText("Listen Again Carefully.");
                    ap.audioAnimationPlayer(R.raw.m, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 2;
                    break;

                case 2:
                    ((TextView)viewArr[0]).setText("I will ask you to recall those words again at the end of the test.");
                    ap.audioAnimationPlayer(R.raw.m_end, viewArr, startTimeArr, endTimeArr, mCallback);
                    audioNum = 3;
                    break;
            }
        }

    };

}