package edu.uic.hcilab.emoca;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.uic.hcilab.emoca.TestFragments.Test_start;
import edu.uic.hcilab.emoca.TestFragments.Test_v1;

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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_test);
        final Button nextBtn = (Button) findViewById(R.id.test_next_btn);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.test_progressBar); // initiate the progress bar
        setSupportActionBar(myToolbar);

        fragmentList.add(new Test_start());
        fragmentList.add(new Test_v1());

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


}
