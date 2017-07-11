package com.example.admin.mpassbook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.mpassbook.global.BaseActivity;

public class SettingsActivity extends BaseActivity {
    ImageView imageViewfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        imageViewfinish=(ImageView) findViewById(R.id.img_drawer);
        imageViewfinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_drawer:
                finish();
                break;
        }
    }
}
