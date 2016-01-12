package im.dacer.mrghllghghllghg;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;

public class AboutActivity extends AppCompatActivity {
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final ViewRevealAnimator mViewAnimator = (ViewRevealAnimator) findViewById(R.id.animator);
        final ImageView ivTop = (ImageView) findViewById(R.id.iv_top);
        ivTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i == 6) {
//                    ivTop.setImageResource(R.drawable.s_2015_10_31_22_38_45);
                    mViewAnimator.showNext();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
