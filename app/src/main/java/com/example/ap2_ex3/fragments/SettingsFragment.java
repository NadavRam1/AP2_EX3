package com.example.ap2_ex3.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.ap2_ex3.R;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private EditTextPreference serverAddressPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Get a reference to the EditTextPreference
        serverAddressPreference = findPreference("server_address");

        // Set the listener for the preference
        serverAddressPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Update the UI element with the new value
                updateServerAddressSummary(newValue.toString());
                return true; // Return true to save the new value
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the shared preference change listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the shared preference change listener
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Handle changes to other preferences if needed
    }

    private void updateServerAddressSummary(String serverAddress) {
        // Update the summary of the EditTextPreference
        if (!Objects.equals(serverAddress, "")) {
            serverAddressPreference.setSummary("Currently at use: " + serverAddress);
        }
        else {
            serverAddressPreference.setSummary(serverAddress);
        }

    }
}
