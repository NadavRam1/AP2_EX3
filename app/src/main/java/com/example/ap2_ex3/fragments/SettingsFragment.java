package com.example.ap2_ex3.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.ap2_ex3.R;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private EditTextPreference serverAddressPreference;
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Get a reference to the EditTextPreference
        serverAddressPreference = findPreference("server_address");
//        serverAddressPreference.setDefaultValue("http://192.168.2.107:5000");

        // Set the listener for the preference
        serverAddressPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Update the UI element with the new value
                updateServerAddressSummary(newValue.toString());
                return true; // Return true to save the new value
            }
        });

        final SwitchPreferenceCompat activateDarkMode = findPreference("darkMode");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        Boolean bool = sharedPreferences.getBoolean("dark_mode", true);

        if (bool) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            activateDarkMode.setChecked(true);
        }



        activateDarkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Cast the new value to boolean
                boolean isDarkModeEnabled = (boolean) newValue;

                // Handle the dark mode change
                if (isDarkModeEnabled) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    activateDarkMode.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dark_mode", true);
                    editor.commit();
                    reset();

                    // Return true to save the new value
                    return true;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    activateDarkMode.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dark_mode", false);
                    editor.commit();
                    reset();
                    return false;
                }
            }
        });

    }

    private void reset() {

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("base_url");
        editor.putString("base_url", serverAddress);
        editor.commit();
        // Update the summary of the EditTextPreference
        if (!Objects.equals(serverAddress, "")) {
            serverAddressPreference.setSummary("Currently at use: " + serverAddress);
        }
        else {
            serverAddressPreference.setSummary(serverAddress);
            serverAddressPreference.setSummary("No server currently at use");
        }

    }
}
