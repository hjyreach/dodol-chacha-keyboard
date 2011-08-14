package kr.dodol.chacha.powerupkit;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ChaChaSetting extends Activity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.chacha_setting_activity );

        findViewById( R.id.keyboard_setting ).setOnClickListener( new OnClickListener()
        {
            
            @Override
            public void onClick( View v )
            {

                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
                startActivity( intent );                
            }
        });
        ((ToggleButton) findViewById(R.id.keyboard_toast_button)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor edit = Cons.getSharedPreference(ChaChaSetting.this).edit();
				edit.putBoolean("keyboard_toast", isChecked);
				edit.commit();
			}
		});
        findViewById( R.id.locale_setting ).setOnClickListener( new OnClickListener()
            {
                
                @Override
                public void onClick( View v )
                {
                    
                    IActivityManager am = ActivityManagerNative.getDefault();
                    Configuration config;
                    try
                    {
                        config = am.getConfiguration();
                        config.locale = Locale.KOREAN;
                        am.updateConfiguration( config );
                        findViewById( R.id.locale_setting ).setEnabled( false );
                        Toast.makeText( ChaChaSetting.this, "한국어로 변경 되었습니다.", Toast.LENGTH_LONG ).show();
                    }
                    catch( RemoteException e )
                    {
                        e.printStackTrace();
                    }
                }
        });
        findViewById(R.id.keyboard_input_method).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		        im.showInputMethodPicker();				
			}
		});
        

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        ((ToggleButton) findViewById(R.id.keyboard_toast_button)).setChecked(Cons.getSharedPreference(ChaChaSetting.this).getBoolean("keyboard_toast", false));
        
        if(Locale.KOREAN.equals( getResources().getConfiguration().locale)) {
            findViewById( R.id.locale_setting ).setEnabled( false );    
            ((TextView)findViewById( R.id.locale_setting_description)).setText( "이미 한글로 설정 되어 있습니다" );
        } 
        if(isEnabledChaChaKeyboard( this )) {
            findViewById( R.id.keyboard_setting ).setEnabled( false );
            ((TextView)findViewById( R.id.keyboard_setting_description)).setText( "도돌 차차 키보드로 설정 되어 있습니다" );
        }
    }
    
    private boolean isEnabledChaChaKeyboard(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = imm.getEnabledInputMethodList();
        if(list.size() > 2) {
            return false;
        }
        for( InputMethodInfo info : list) {
            if(info.getComponent().getPackageName().equals( getPackageName() )) {
                return true;
            }
        }
        return false;
    }
}
