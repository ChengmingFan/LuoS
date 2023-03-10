package com.fan.soulkiller.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.fan.soulkiller.R;

import java.util.Objects;

/**
 * Created by Chengming Fan on 2023/2/26 12:16 AM
 */
public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.perferences, rootKey);
        Preference preferenceSourcecode = this.getPreferenceScreen().findPreference("preference_sourcecode");
        Objects.requireNonNull(preferenceSourcecode).setOnPreferenceClickListener(it -> {
            Uri uri_sourcecode = Uri.parse("https://github.com/ChengmingFan/LuoS");
            Intent intent_sourcecode = new Intent(Intent.ACTION_VIEW, uri_sourcecode);
            startActivity(intent_sourcecode);
            return false;
        });
        Preference preferenceDonate = this.getPreferenceScreen().findPreference("preference_donate");
        Objects.requireNonNull(preferenceDonate).setOnPreferenceClickListener(it -> {
            Intent intent = new Intent("donate_action");
            intent.addCategory("donate_category");
            startActivity(intent);
            return false;
        });
    }
}
