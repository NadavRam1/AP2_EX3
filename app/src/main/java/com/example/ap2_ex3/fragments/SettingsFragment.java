package com.example.ap2_ex3.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.ap2_ex3.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }
}