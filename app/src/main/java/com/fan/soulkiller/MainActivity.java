package com.fan.soulkiller;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import com.fan.soulkiller.domain.LatestReleaseResponse;
import com.fan.soulkiller.fragment.MySettingsFragment;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        assembleLatestVersion(mySettingsFragment);
    }

    private void assembleLatestVersion(MySettingsFragment mySettingsFragment) {
        new Thread(() -> {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(getResources().getString(R.string.latest_version)).build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                LatestReleaseResponse latestReleaseResponse = new Gson().fromJson(response.body().string(), LatestReleaseResponse.class);
                String name = latestReleaseResponse.getName();
                if (name == null) {
                    return;
                }
                String latestVersion = name.split(" ")[1].substring(1);
                new Handler(Looper.getMainLooper()).post(() -> {
                    Preference preferenceVersion = mySettingsFragment.findPreference("preference_version");
                    assert preferenceVersion != null;
                    if (!latestVersion.equals(getString(R.string.app_version_str))) {
                        preferenceVersion.setSummary(String.format("%s (最新版: %s)", getString(R.string.app_version_str), latestVersion));
                    }
                });
                Log.d("LuoS", "assembleLatestVersion: " + latestVersion);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to fetch latest version");
            }
        }).start();

    }


}