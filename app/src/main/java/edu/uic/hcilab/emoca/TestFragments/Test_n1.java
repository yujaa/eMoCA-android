package edu.uic.hcilab.emoca.TestFragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nuance.speechkit.Session;

import org.w3c.dom.Text;

import edu.uic.hcilab.emoca.AudioPlayer;
import edu.uic.hcilab.emoca.R;
import edu.uic.hcilab.emoca.SpeechRecognition;
import edu.uic.hcilab.emoca.WriteSDcard;

public class Test_n1 extends android.app.Fragment {

    AudioPlayer ap;
    WriteSDcard wr;
    private SpeechRecognition sr;
    private Button toggleReco;
    public Test_n1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wr =  new WriteSDcard();
        ap = new AudioPlayer(this.getActivity().getApplicationContext());
        ap.audioPlayer(R.raw.n);

        wr.writeToSDFile(this.getContext(), "N1:audio start");

        sr = new SpeechRecognition(1);
        sr.sessionInit(this.getActivity().getApplicationContext());
        sr.loadEarcons(this.getActivity().getApplicationContext());
        sr.setState(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_n1, container, false);
        sr.setTextViews((TextView)view.findViewById(R.id.n1_ans), (TextView)view.findViewById(R.id.n1_log));
        toggleReco = (Button)view.findViewById(R.id.n1_toggle_reco);
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

}
