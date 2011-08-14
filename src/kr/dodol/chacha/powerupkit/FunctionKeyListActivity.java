package kr.dodol.chacha.powerupkit;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class FunctionKeyListActivity extends Activity
{
    
    ListView mList;
    
    int from;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.function_key_list_activity);
        mList = ( ListView )findViewById( R.id.list );
        findViewById( R.id.button_mapping ).setOnClickListener( new OnClickListener()
        {
            
            @Override
            public void onClick( View v )
            {
                AlertDialog.Builder builder = new AlertDialog.Builder( FunctionKeyListActivity.this );
                
                final LinearLayout ll = ( LinearLayout )LayoutInflater.from( FunctionKeyListActivity.this ).inflate( R.layout.dialog_add_new_function, null );
                ll.findViewById(R.id.symbol).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						AlertDialog.Builder builder = new AlertDialog.Builder( FunctionKeyListActivity.this );
						
						LinearLayout symbolll = new LinearLayout(FunctionKeyListActivity.this);
						symbolll.setOrientation(LinearLayout.VERTICAL);
						
						LinearLayout[] row = new LinearLayout[] { new LinearLayout(FunctionKeyListActivity.this),new LinearLayout(FunctionKeyListActivity.this), new LinearLayout(FunctionKeyListActivity.this), new LinearLayout(FunctionKeyListActivity.this)};
						
						for(int i = 0; i < row.length; i++) {
							symbolll.addView(row[i]);
						}
						
						
						builder.setView(symbolll);
						final AlertDialog dialog = builder.create();
						
						String[] symbols = "~,^,＊,￦,＠,☆,★,○,◎,♡,♥,♧,♣,→,←,↑,↓,▨,⊙,◐,◑,♨,☏,☎,☜,☞,♩,♪,♬,™,㈜,℡".split(",");
						
						
						for(int i = 0; i < symbols.length; i++) {
							LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
							lp.setMargins(4, 4, 4, 4);
							
							Button button = new Button(FunctionKeyListActivity.this);
							button.setText(symbols[i]);
							button.setLayoutParams(lp);
							button.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
									lp.setMargins(4, 4, 4, 4);
									Button button = (Button) v;
									button.setLayoutParams(lp);
									String string = button.getText().toString();
									((EditText)ll.findViewById( R.id.to )).append(string);
									dialog.dismiss();
									
								}
							});
							row[i / 8].addView(button);
						}
						dialog.show();
					}
				});
                ll.findViewById( R.id.from ).setOnKeyListener( new OnKeyListener()
                {
                    
                    @Override
                    public boolean onKey( View v, int keyCode, KeyEvent event )
                    {
                        from = keyCode;
                        EditText et = ( EditText )v;
                        et.setText(String.valueOf((char)(keyCode +68) ));
                        return true;
                    }
                });
                
                builder.setView( ll );
                builder.setPositiveButton( "확인", new DialogInterface.OnClickListener()
                {
                    
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                        String symobel = ((EditText)ll.findViewById( R.id.to )).getText().toString();
                        Cons.setFnKey( FunctionKeyListActivity.this, from, symobel );
                        updateList();
                    }

                });
                
                builder.setNegativeButton( "취소", null );
                builder.setTitle("새 단축키 추가");
                builder.show();
                
                updateList();
            }
        });
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        updateList();
    }
    
    private void updateList()
    {

        final String[] keyList = Cons.getFnKeyList(this);

        mList.setAdapter( new BaseAdapter()
        {
            
            @Override
            public View getView( final int position, View convertView, ViewGroup parent )
            {
            	if(convertView == null) {
            		convertView = LayoutInflater.from(FunctionKeyListActivity.this).inflate(R.layout.function_key_item, null);
            	}
            	
                String keyEvent = Cons.getFnKey( FunctionKeyListActivity.this, Integer.valueOf( keyList[position] ));
                String character = (new Character((char)(Integer.valueOf(keyList[position]) + 68)).toString() + " = " + keyEvent );
                
                TextView tv = (TextView) convertView.findViewById(R.id.title);
                tv.setText("FN + " + character );
                
                Button button = (Button) convertView.findViewById(R.id.delete_button);
                button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Cons.removeFnKey(FunctionKeyListActivity.this, Integer.valueOf(keyList[position]));
						updateList();
					}
				});
                
                return convertView;
            }
            
            @Override
            public long getItemId( int position )
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Object getItem( int position )
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int getCount()
            {
                // TODO Auto-generated method stub
                return keyList.length;
            }
        });
    }
}
