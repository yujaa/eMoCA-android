package edu.uic.hcilab.emoca;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import edu.uic.hcilab.emoca.TestFragments.Test_a1;
import edu.uic.hcilab.emoca.TestFragments.Test_a2;
import edu.uic.hcilab.emoca.TestFragments.Test_a3;
import edu.uic.hcilab.emoca.TestFragments.Test_a4;
import edu.uic.hcilab.emoca.TestFragments.Test_abs1;
import edu.uic.hcilab.emoca.TestFragments.Test_abs2;
import edu.uic.hcilab.emoca.TestFragments.Test_abs3;
import edu.uic.hcilab.emoca.TestFragments.Test_abs4;
import edu.uic.hcilab.emoca.TestFragments.Test_l1;
import edu.uic.hcilab.emoca.TestFragments.Test_l2;
import edu.uic.hcilab.emoca.TestFragments.Test_l3;
import edu.uic.hcilab.emoca.TestFragments.Test_m;
import edu.uic.hcilab.emoca.TestFragments.Test_n1;
import edu.uic.hcilab.emoca.TestFragments.Test_n2;
import edu.uic.hcilab.emoca.TestFragments.Test_n3;
import edu.uic.hcilab.emoca.TestFragments.Test_start;
import edu.uic.hcilab.emoca.TestFragments.Test_v1;
import edu.uic.hcilab.emoca.TestFragments.Test_v2;
import edu.uic.hcilab.emoca.TestFragments.Test_v3;

public class MocaActivity extends TabHost {


    private List<Fragment> fragmentList = new ArrayList<>();
    int fIndex = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_moca;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_moca;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Request user permissions in runtime */
        ActivityCompat.requestPermissions(MocaActivity.this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                100);
        /* Request user permissions in runtime */

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_test);
        final Button nextBtn = (Button) findViewById(R.id.test_next_btn);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.test_progressBar); // initiate the progress bar
        setSupportActionBar(myToolbar);

        fragmentList.add(new Test_start());
        fragmentList.add(new Test_v1());
        fragmentList.add(new Test_v2());
        fragmentList.add(new Test_v3());
        fragmentList.add(new Test_n1());
        fragmentList.add(new Test_n2());
        fragmentList.add(new Test_n3());
        fragmentList.add(new Test_m());
        fragmentList.add(new Test_a1());
        fragmentList.add(new Test_a2());
        fragmentList.add(new Test_a3());
        fragmentList.add(new Test_a4());
        fragmentList.add(new Test_l1());
        fragmentList.add(new Test_l2());
        fragmentList.add(new Test_l3());
        fragmentList.add(new Test_abs1());
        fragmentList.add(new Test_abs2());
        fragmentList.add(new Test_abs3());
        fragmentList.add(new Test_abs4());

        pb.setMax(fragmentList.size());

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.test_frame, fragmentList.get(fIndex)).commit();
            fIndex++;

        }

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (fIndex == 0)
                    nextBtn.setText("Next");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.test_frame, fragmentList.get(fIndex));
                ft.commit();
                pb.setProgress(fIndex);
                fIndex++;

                if (fIndex == 16) {
                    nextBtn.setText("Finish");
                    fIndex = 0;
                }
            }
        });

    }

    public void writeToSDFile(String str){

        // Find the root of the external storage.
        File root = Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/FLM");
        File file = new File(dir,"Moca.txt");

        try {

            int permissionCheck = ContextCompat.checkSelfPermission(MocaActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                if (!file.createNewFile()) {
                    Log.i("Test", "This file is already exist: " + file.getAbsolutePath());
                }

            }
            FileOutputStream f = new FileOutputStream(file, true);
            Long tsLong = System.currentTimeMillis()/10;
            String ts = tsLong.toString();
            PrintWriter pw = new PrintWriter(f);
            pw.println(str+":"+ts);
            pw.flush();
            pw.close();
            f.close();
            Log.i("my",str+":"+ts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("myTag", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0
                        || grantResults[0] == PackageManager.PERMISSION_GRANTED
                        || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    /* User checks permission. */

                } else {
                    //Toast.makeText(MainActivity.this, "Permission is denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                //return; // delete.
        }
    }



}
