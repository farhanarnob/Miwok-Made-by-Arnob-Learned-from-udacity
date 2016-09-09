package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //creating up navigation action
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ColorsFragment()).commit();
        //Array list for color word
    }

    @Override
    protected void onStop() {
        super.onStop();
        //

    }
}
