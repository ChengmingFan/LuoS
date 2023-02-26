package com.fan.soulkiller;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fan.soulkiller.fragment.MySettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySettingsFragment mySettingsFragment = new MySettingsFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, mySettingsFragment)
                    .commit();
        }
        getSharedPreferences("com.fan.soulkiller_preferences", Context.MODE_WORLD_READABLE);
    }
}