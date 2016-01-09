package im.dacer.mrghllghghllghg;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvMurkEye;
    private TextView tvBluegill;
    private TextView tvWarleader;
    private TextView tvGrimscale;
    private TextView tvBabyMurloc;
    private TextView tvDamage;
    private TextView tvMinions;
    private VerticalSeekBar seekBarMinions;

    private int numMurkEye;
    private int numBluegill;
    private int numWarleader;
    private int numGrimscale;
    private int numBabyMurloc;
    private int numDamage;
    private int numAvailableMinions;

    private ArrayList<Integer> murlocList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tvDamage = (TextView) findViewById(R.id.tv_damage);
        tvMurkEye = (TextView) findViewById(R.id.tv_murkeye);
        tvBluegill = (TextView) findViewById(R.id.tv_bluegill);
        tvWarleader = (TextView) findViewById(R.id.tv_warleader);
        tvGrimscale = (TextView) findViewById(R.id.tv_grimscale);
        tvBabyMurloc = (TextView) findViewById(R.id.tv_baby_murloc);
        seekBarMinions = (VerticalSeekBar) findViewById(R.id.mySeekBar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        tvMinions = (TextView) findViewById(R.id.tv_minions);

        tvMurkEye.setTag(0);
        tvBluegill.setTag(1);
        tvWarleader.setTag(2);
        tvGrimscale.setTag(3);
        tvBabyMurloc.setTag(4);

        tvMurkEye.setOnClickListener(this);
        tvBluegill.setOnClickListener(this);
        tvWarleader.setOnClickListener(this);
        tvGrimscale.setOnClickListener(this);
        tvBabyMurloc.setOnClickListener(this);

        setSupportActionBar(toolbar);
        init();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });
        seekBarMinions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numAvailableMinions = progress;
                tvMinions.setText(String.valueOf(progress));
                calculateDamageAndShowNumbers();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void init() {
        murlocList = new ArrayList<>();
        numMurkEye = numBluegill = numWarleader = numGrimscale = numBabyMurloc = 0;
        numAvailableMinions = 7;
        seekBarMinions.setProgress(numAvailableMinions);
        tvMinions.setText(String.valueOf(numAvailableMinions));
        calculateDamageAndShowNumbers();
    }

    @Override
    public void onClick(View v) {
        int tag = (int)v.getTag();
        switch (tag){
            case 0:
                numMurkEye++;
                break;
            case 1:
                numBluegill++;
                break;
            case 2:
                numWarleader++;
                break;
            case 3:
                numGrimscale++;
                break;
            case 4:
                numBabyMurloc++;
                break;
        }
        murlocList.add(tag);
        calculateDamageAndShowNumbers();
    }

    private void calculateDamageAndShowNumbers() {
        calculateDamage();
        showNumbers();
    }

    private void calculateDamage(){
        int mNumMurkEye = 0;
        int mNumBluegill = 0;
        int mNumWarleader = 0;
        int mNumGrimscale = 0;
        int mNumBabyMurloc = 0;
        int numMinion = Math.min(murlocList.size(), numAvailableMinions);
        for (int i=0; i<numMinion; i++) {
            int murloc = murlocList.get(i);
            switch (murloc){
                case 0:
                    mNumMurkEye++;
                    break;
                case 1:
                    mNumBluegill++;
                    break;
                case 2:
                    mNumWarleader++;
                    break;
                case 3:
                    mNumGrimscale++;
                    break;
                case 4:
                    mNumBabyMurloc++;
                    break;
            }
        }

        int bluegillAttack = 2 + 2 * mNumWarleader + mNumGrimscale;
        int murkEyeAttack = 2 + 2 * mNumWarleader + mNumGrimscale +
                (mNumMurkEye + mNumBluegill + mNumWarleader + mNumGrimscale + mNumBabyMurloc - 1);
        numDamage = bluegillAttack * mNumBluegill + murkEyeAttack * mNumMurkEye;
    }

    private void showNumbers() {
        tvMurkEye.setText(String.valueOf(numMurkEye));
        tvBluegill.setText(String.valueOf(numBluegill));
        tvWarleader.setText(String.valueOf(numWarleader));
        tvGrimscale.setText(String.valueOf(numGrimscale));
        tvBabyMurloc.setText(String.valueOf(numBabyMurloc));
        tvDamage.setText(String.valueOf(numDamage));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
