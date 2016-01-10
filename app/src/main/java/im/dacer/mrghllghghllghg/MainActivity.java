package im.dacer.mrghllghghllghg;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private int numDamage;
    private int numAvailableMinions;

    private ArrayList<Integer> murlocList;
    private ArrayList<ArrayList<Integer>> murlocHistoryList; //For undo

    public final static int MURLOC_MURK_EYE = 0;
    public final static int MURLOC_BLUEGILL = 1;
    public final static int MURLOC_WARLEADER = 2;
    public final static int MURLOC_GRIMSCALE = 3;
    public final static int MURLOC_BABYMURLOC = 4;

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
        murlocList = new ArrayList<>();
        murlocHistoryList = new ArrayList<>();
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
        if (!murlocList.isEmpty()) {
            murlocHistoryList.add(new ArrayList<>(murlocList));
        }
        murlocList.clear();
        numAvailableMinions = 7;
        seekBarMinions.setProgress(numAvailableMinions);
        tvMinions.setText(String.valueOf(numAvailableMinions));
        calculateDamageAndShowNumbers();
    }

    @Override
    public void onClick(View v) {
        int tag = (int)v.getTag();
        murlocList.add(tag);
        calculateDamageAndShowNumbers();
    }

    private void calculateDamageAndShowNumbers() {
        calculateDamage();
        showNumbers();
    }

    private void calculateDamage(){
        int[] murlocNumList = getMurlocNumList(murlocList, numAvailableMinions);
        int mNumMurkEye = murlocNumList[0];
        int mNumBluegill = murlocNumList[1];
        int mNumWarleader = murlocNumList[2];
        int mNumGrimscale = murlocNumList[3];
        int mNumBabyMurloc = murlocNumList[4];

        int bluegillAttack = 2 + 2 * mNumWarleader + mNumGrimscale;
        int murkEyeAttack = 2 + 2 * mNumWarleader + mNumGrimscale +
                (mNumMurkEye + mNumBluegill + mNumWarleader + mNumGrimscale + mNumBabyMurloc - 1);
        numDamage = bluegillAttack * mNumBluegill + murkEyeAttack * mNumMurkEye;
    }

    private void showNumbers() {
        int[] murlocNumList = getMurlocNumList(murlocList);
        tvMurkEye.setText(String.valueOf(murlocNumList[0]));
        tvBluegill.setText(String.valueOf(murlocNumList[1]));
        tvWarleader.setText(String.valueOf(murlocNumList[2]));
        tvGrimscale.setText(String.valueOf(murlocNumList[3]));
        tvBabyMurloc.setText(String.valueOf(murlocNumList[4]));
        tvDamage.setText(String.valueOf(numDamage));
    }


    private int[] getMurlocNumList(ArrayList<Integer> list) {
        return getMurlocNumList(list, -1);
    }

    private int[] getMurlocNumList(ArrayList<Integer> list, int limit) {
        int numMurkEye = 0;
        int numBluegill = 0;
        int numWarleader = 0;
        int numGrimscale = 0;
        int numBabyMurloc = 0;
        int numMinion = list.size();
        if(limit != -1){
            numMinion = Math.min(list.size(), limit);
        }
        for (int i=0; i<numMinion; i++) {
            int murloc = list.get(i);
            switch (murloc){
                case MURLOC_MURK_EYE:
                    numMurkEye++;
                    break;
                case MURLOC_BLUEGILL:
                    numBluegill++;
                    break;
                case MURLOC_WARLEADER:
                    numWarleader++;
                    break;
                case MURLOC_GRIMSCALE:
                    numGrimscale++;
                    break;
                case MURLOC_BABYMURLOC:
                    numBabyMurloc++;
                    break;
            }
        }
        return new int[]{numMurkEye, numBluegill, numWarleader, numGrimscale, numBabyMurloc};
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
        }else if (id == R.id.action_undo) {
            if (!murlocList.isEmpty()) {
                murlocList.remove(murlocList.size() - 1);
            }else if(!murlocHistoryList.isEmpty()) {
                murlocList = new ArrayList<>(murlocHistoryList.get(murlocHistoryList.size()-1));
                murlocHistoryList.remove(murlocHistoryList.size() - 1);
            }else {
                Toast.makeText(this, R.string.nothing_undo, Toast.LENGTH_SHORT).show();
            }


            calculateDamageAndShowNumbers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
