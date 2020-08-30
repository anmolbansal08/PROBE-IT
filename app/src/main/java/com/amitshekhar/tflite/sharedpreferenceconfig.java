package com.amitshekhar.tflite;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedpreferenceconfig {
    private SharedPreferences sf;
    private Context context;

    public sharedpreferenceconfig(Context context) {
        this.context = context;
       sf=context.getSharedPreferences(context.getResources().getString(R.string.shared_preferences),Context.MODE_PRIVATE) ;
    }
    public void writeloginstatus(Boolean a){
        SharedPreferences.Editor editor=sf.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status),a);
        editor.commit();
    }
    public boolean readloginstatus(){
        return sf.getBoolean(context.getResources().getString(R.string.login_status), false);
    }
}

