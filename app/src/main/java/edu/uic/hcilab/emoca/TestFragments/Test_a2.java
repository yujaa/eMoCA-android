package edu.uic.hcilab.emoca.TestFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;

public class Test_a2 extends android.app.Fragment {

    AudioPlayer ap = new AudioPlayer();
    public Test_a2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ap.audioPlayer(this.getActivity().getApplicationContext(), R.raw.n);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_a2, container, false);
    }
}
