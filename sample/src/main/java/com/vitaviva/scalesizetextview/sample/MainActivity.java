package com.vitaviva.scalesizetextview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vitaviva.scalesizetextview.ScaleTextSizeUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScaleTextSizeUtil.getInstance(this).setScale(ScaleTextSizeUtil.getInstance(this).getCurScale());
    }

    public void onClick(View view) {
        ScaleTextSizeUtil.SelectScaleDialog dialog = new ScaleTextSizeUtil.SelectScaleDialog();
        dialog.show(getSupportFragmentManager(), "ScaleSize");
    }
}
