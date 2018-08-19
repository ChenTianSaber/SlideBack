package com.example.saber.slideback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends SlideBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void slideBackSuccess() {
        finish();
    }

    public void StartSrcond(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }
}
