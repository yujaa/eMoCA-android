package edu.uic.hcilab.emoca.TestFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;

public class Test_m extends android.app.Fragment implements AudioPlayer.AudioCallback {
    AudioPlayer ap = new AudioPlayer();
    int audioNum = 0;
    View[] viewArr = new View[5];
    public Test_m() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);

        int[] startTimeArr = {0};
        int[] endTimeArr = {1000};

        ap.audioPlayer(this.getActivity().getApplicationContext(),this.getContext(), R.raw.m_inst);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_m, container, false);
        viewArr[0] = view.findViewById(R.id.m_alert);

        return view;
    }

    public void onAudioCallback(){
        if(audioNum == 0)
            ap.audioPlayer(this.getActivity().getApplicationContext(), R.raw.m);

    }

}
