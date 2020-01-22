package com.juanma.greennews;

import android.content.Context;

import androidx.preference.Preference;

public class LoadingPreference extends Preference {

    public LoadingPreference(Context context){
        super(context);
        setLayoutResource(R.layout.preference_loading_placeholder);
    }

}
