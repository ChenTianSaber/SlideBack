package com.example.saber.slideback;

import android.os.Bundle;

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
