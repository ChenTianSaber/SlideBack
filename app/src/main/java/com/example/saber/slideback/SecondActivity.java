package com.example.saber.slideback;

import android.os.Bundle;

import com.saber.chentianslideback.SlideBackActivity;

public class SecondActivity extends SlideBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void slideBackSuccess() {
        finish();
    }
}
