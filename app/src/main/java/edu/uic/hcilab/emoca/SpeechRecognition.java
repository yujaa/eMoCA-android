package edu.uic.hcilab.emoca;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.widget.TextView;

import com.nuance.speechkit.Audio;
import com.nuance.speechkit.DetectionType;
import com.nuance.speechkit.Language;
import com.nuance.speechkit.Recognition;
import com.nuance.speechkit.ResultDeliveryType;
import com.nuance.speechkit.RecognitionType;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;
import com.nuance.speechkit.TransactionException;

public class SpeechRecognition {

    private Audio startEarcon;
    private Audio stopEarcon;
    private Audio errorEarcon;

    private TextView result;

    private TextView[] results={};
    private int next = 0;

    private TextView logs;

    private Session speechSession;
    private Transaction recoTransaction;
    private State state = State.IDLE;
    private int detectiontype;

    /* Reco transactions */
    public SpeechRecognition(int type)
    {
        detectiontype = type;
    }

    /**
     * Start listening to the user and streaming their voice to the server.
     */
    private void recognize() {
        //Setup our Reco transaction options.

        Transaction.Options options = new Transaction.Options();
        switch(detectiontype){
            case 1:
                options.setDetection(DetectionType.Short);
                break;
            case 2:
                options.setDetection(DetectionType.Long);
                break;
        }
        options.setRecognitionType(RecognitionType.DICTATION);
        options.setLanguage(new Language("eng-USA"));
        options.setEarcons(startEarcon, stopEarcon, errorEarcon, null);

        //options.setResultDeliveryType(ResultDeliveryType.PROGRESSIVE);

        //Start listening
        recoTransaction = speechSession.recognize(options, recoListener);
    }

    private Transaction.Listener recoListener = new Transaction.Listener() {
        @Override
        public void onStartedRecording(Transaction transaction) {
            logs.setText("Speak answer");

            //We have started recording the users voice.
            //We should update our state and start polling their volume.
            setState(State.LISTENING);
            startAudioLevelPoll();
        }

        @Override
        public void onFinishedRecording(Transaction transaction) {
            logs.setText("processing");

            //We have finished recording the users voice.
            //We should update our state and stop polling their volume.
            setState(State.PROCESSING);
            stopAudioLevelPoll();
        }

        @Override
        public void onRecognition(Transaction transaction, Recognition recognition) {
            //logs.append("\nonRecognition: " + recognition.getText());
            if(results.length !=0)
                results[next++].setBackgroundColor(0x1DDB1600);
            else
                result.setText(recognition.getText());
            //We have received a transcription of the users voice from the server.
        }

        @Override
        public void onSuccess(Transaction transaction, String s) {
            //logs.append("\nonSuccess");
            logs.setText("");
            //Notification of a successful transaction.
            setState(State.IDLE);
        }

        @Override
        public void onError(Transaction transaction, String s, TransactionException e) {
            //logs.append("\nonError: " + e.getMessage() + ". " + s);
            logs.setText("Click the button \nand speak again.");
            //Something went wrong. Check Configuration.java to ensure that your settings are correct.
            //The user could also be offline, so be sure to handle this case appropriately.
            //We will simply reset to the idle state.
            setState(State.IDLE);
        }
    };

    /**
     * Stop recording the user
     */
    private void stopRecording() {
        recoTransaction.stopRecording();
    }

    /**
     * Cancel the Reco transaction.
     * This will only cancel if we have not received a response from the server yet.
     */
    private void cancel() {
        recoTransaction.cancel();
        setState(State.IDLE);
    }

    /* Audio Level Polling */

    private Handler handler = new Handler();

    /**
     * Every 50 milliseconds we should update the volume meter in our UI.
     */
    private Runnable audioPoller = new Runnable() {
        @Override
        public void run() {
            float level = recoTransaction.getAudioLevel();
            handler.postDelayed(audioPoller, 50);
        }
    };

    /**
     * Start polling the users audio level.
     */
    private void startAudioLevelPoll() {
        audioPoller.run();
    }

    /**
     * Stop polling the users audio level.
     */
    private void stopAudioLevelPoll() {
        handler.removeCallbacks(audioPoller);

    }


    /* State Logic: IDLE -> LISTENING -> PROCESSING -> repeat */

    private enum State {
        IDLE,
        LISTENING,
        PROCESSING
    }


    public void toggleReco() {
        switch (state) {
            case IDLE:
                recognize();
                break;
            case LISTENING:
                stopRecording();
                break;
            case PROCESSING:
                cancel();
                break;
        }
    }

    public void setTextViews(TextView result, TextView logs)
    {
        this.logs = logs;
        this.result = result;
    }

    public void setTextViews(TextView[] result, TextView logs)
    {
        this.logs = logs;
        this.results = result;
    }

    /**
     * Set the state and update the button text.
     */
    private void setState(State newState) {
        state = newState;
        switch (newState) {
            case IDLE:
                //toggleReco.setText(getResources().getString(R.string.recognize));
                break;
            case LISTENING:
                //toggleReco.setText(getResources().getString(R.string.listening));
                break;
            case PROCESSING:
                //toggleReco.setText(getResources().getString(R.string.processing));
                break;
        }
    }
    public void setState(int newState){
        switch (newState)
        {
            case 1:
                setState(State.IDLE);
                break;
            case 2:
                setState(State.LISTENING);
                break;
            case 3:
                setState(State.PROCESSING);
                break;
        }
    }

    /* Earcons */

    public void loadEarcons(Context context) {
        //Load all the earcons from disk
        startEarcon = new Audio(context, R.raw.sk_start, Configuration.PCM_FORMAT);
        stopEarcon = new Audio(context, R.raw.sk_stop, Configuration.PCM_FORMAT);
        errorEarcon = new Audio(context, R.raw.sk_error, Configuration.PCM_FORMAT);
    }

    public void sessionInit(Context context)
    {
        speechSession = Session.Factory.session(context, Configuration.SERVER_URI, Configuration.APP_KEY);
    }

}
