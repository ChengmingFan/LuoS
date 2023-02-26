package com.fan.soulkiller.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
        addPreferencesFromResource(R.xml.perferences);
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(this.getContext().getPackageName() + "_preferences", MODE_PRIVATE);
        Preference preferenceSourcecode = this.getPreferenceScreen().findPreference("preference_sourcecode");
        Objects.requireNonNull(preferenceSourcecode).setOnPreferenceClickListener(it -> {
            Uri uri_sourcecode = Uri.parse("https://github.com/ChengmingFan/LuoS");
            Intent intent_sourcecode = new Intent(Intent.ACTION_VIEW, uri_sourcecode);
            startActivity(intent_sourcecode);
            return false;
        });
    }
}
