package com.juanma.greennews;


import android.os.Bundle;
import android.util.Log;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.juanma.greennews.models.Settings;
import java.util.Objects;


public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = SettingsFragment.class.getName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {



        setPreferencesFromResource(R.xml.settings_fragment, rootKey);
        //((PreferenceCategory)findPreference("PreferenceKey")).addPreference(new LoadingPreference(getActivity()));

        final ListPreference languagePreference = (ListPreference) findPreference(getString(R.string.language_preference_key));
        final ListPreference sortByPreference = (ListPreference) findPreference(getString(R.string.SortBy_preference_key));
        final ListPreference datePreference = (ListPreference) findPreference(getString(R.string.date_preference_key));



        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                Settings.getInstance().language = value;
                Log.d(TAG, "onPreferenceChange: " + Settings.getInstance().sortBy + " "+ Settings.getInstance().language);
                return true;
            }
        });

        sortByPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                Settings.getInstance().sortBy = value;
                Log.d(TAG, "onPreferenceChange: " + Settings.getInstance().sortBy + " "+ Settings.getInstance().language);
                return true;
            }
        });

        datePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = newValue.toString();
                int wedate = Integer.parseInt(value);
                Settings.getInstance().date = wedate;
                Log.d(TAG, "onPreferenceChange: " + wedate);
                return true;
            }
        });



    }







}
