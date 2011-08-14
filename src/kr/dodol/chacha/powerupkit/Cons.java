package kr.dodol.chacha.powerupkit;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Cons
{
    
    public static void setFnKey(Context context, int keyCode, String symbol) {
        SharedPreferences pref = getSharedPreference( context );
        Editor edit = pref.edit();
        edit.putString( getFnKeyStringFromKeyCode( keyCode ), symbol);
        
        String listString = pref.getString( getFnKeyListString(), "" );
        if(!listString.contains( keyCode + "," )) {
            listString += keyCode + ",";
            edit.putString( getFnKeyListString(), listString );
        }
        
        edit.commit();
    }
    
    public static void removeFnKey(Context context, int keyCode) {
        SharedPreferences pref = getSharedPreference( context );
        Editor edit = pref.edit();
        edit.remove( getFnKeyStringFromKeyCode( keyCode ) );
        
        String listString = pref.getString( getFnKeyListString(), "" );
        listString = listString.replace( keyCode + ",", "" );
        edit.putString( getFnKeyListString(), listString );
        edit.commit();
        
    }
    
    public static String[] getFnKeyList(Context context) {
        SharedPreferences pref = getSharedPreference( context );
        String listString = pref.getString( getFnKeyListString(), "" );
        String[] array = listString.split( "," );
        return array;
    }
    
    public static String getFnKey(Context context, int keyCode) {
        SharedPreferences pref = getSharedPreference( context );
        return  pref.getString( getFnKeyStringFromKeyCode( keyCode ), "" );
    }

    private static String getFnKeyStringFromKeyCode(int keyCode) {
        return "fn_key_" + keyCode;
    }
    private static String getFnKeyListString() {
        return "fn_key_list";
    }

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences( "pref", 0 );
    }
}
