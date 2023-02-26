package com.fan.soulkiller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fan.soulkiller.fragment.MySettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new MySettingsFragment())
                    .commit();
        }
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        Toast.makeText(this, "voice" + sharedPreferences.getBoolean("switch_voice", false), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "video" + sharedPreferences.getBoolean("switch_video", false), Toast.LENGTH_SHORT).show();
    }
}