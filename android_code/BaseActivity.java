package kr.co.sora.sbapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by SORA on 2017-06-03.
 */

public class BaseActivity extends AppCompatActivity {
    TextView name[] = new TextView[9];
    TextView state[] = new TextView[9];
    Button bin[] = new Button[9];
    double data[][] = new double[9][5];
    String bin_name[]=new String[9];
    String add[]=new String[9];
    private static Typeface typeface;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        for (int i=0;i<9;i++){
            for (int j=0;j<5;j++){
                data[i][j]=0;
            }}
        Arrays.fill(add, null);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(this.getAssets(), "SDSwaggerTTF.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int vgCnt = viewGroup.getChildCount();
                for (int i = 0; i < vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if (v instanceof Button) {
                        ((Button) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}


