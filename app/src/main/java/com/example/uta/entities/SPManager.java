package com.example.uta.entities;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager{
    private static SharedPreferences mInstance;

    public static SharedPreferences getInstance(Context context){
        if(mInstance==null) {
            mInstance = context.getSharedPreferences(GenConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return mInstance;
    }
}
