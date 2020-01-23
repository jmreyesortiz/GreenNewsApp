package com.juanma.greennews;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.juanma.greennews.models.Settings;
import java.util.Objects;


public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = SettingsFragment.class.getName();
    private Toolbar mToolbar;
    private ListPreference languagePreference;
    private ListPreference sortByPreference;
    private ListPreference datePreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        
        setPreferencesFromResource(R.xml.settings_fragment, rootKey);
        languagePreference = (ListPreference) findPreference(getString(R.string.language_preference_key));
        sortByPreference = (ListPreference) findPreference(getString(R.string.SortBy_preference_key));
        datePreference = (ListPreference) findPreference(getString(R.string.Date_preference_key));
        DefaultSettings();

        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                Settings.getInstance().setLanguage(value);
                return true;
            }

        });

        sortByPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                Settings.getInstance().setSortBy(value);
                return true;
            }
        });

        datePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                int wedate = Integer.parseInt(value);
                Settings.getInstance().setDate(wedate);
                return true;
            }
        });

    }

    private void DefaultSettings() {
        if (Settings.getInstance().getLanguage().equals("en")
                && Settings.getInstance().getSortBy().equals("relevancy")
                && Settings.getInstance().getDate() == 1) {
            languagePreference.setValue(getString(R.string.en));
            sortByPreference.setValue(getString(R.string.relevancy));
            datePreference.setValue(getString(R.string.today));
        }


    }
}
