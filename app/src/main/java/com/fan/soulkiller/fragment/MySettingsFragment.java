package com.fan.soulkiller.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.fan.soulkiller.R;

/**
 * Created by Chengming Fan on 2023/2/26 12:16 AM
 */
public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.perferences, rootKey);
    }
}
