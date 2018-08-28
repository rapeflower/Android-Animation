package com.lily.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lily.anim.view.PathView;

public class MainActivity extends AppCompatActivity {

    PathView pvAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pvAnim = findViewById(R.id.pv_anim);
        pvAnim.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pvAnim.pauseAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pvAnim.cancelAnim();
    }
}