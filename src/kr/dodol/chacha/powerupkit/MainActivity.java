package kr.dodol.chacha.powerupkit;

import kr.dodol.board.BoardListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		((TextView)findViewById(R.id.menu1)).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.process), null, null);
		((TextView)findViewById(R.id.menu1)).setText("º≥¡§");
		((TextView)findViewById(R.id.menu1)).setOnClickListener(this);
		((TextView)findViewById(R.id.menu2)).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.heart), null, null);
		((TextView)findViewById(R.id.menu2)).setText("≈∞ ∏ «Œ");
		((TextView)findViewById(R.id.menu2)).setOnClickListener(this);
		((TextView)findViewById(R.id.menu3)).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.comments), null, null);
		((TextView)findViewById(R.id.menu3)).setText("≥Ó¿Ã≈Õ");
		((TextView)findViewById(R.id.menu3)).setOnClickListener(this);
		 
		
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		switch(v.getId()) {
			case R.id.menu1:
				intent = new Intent(this, ChaChaSetting.class);
				break;
			case R.id.menu2:
				intent = new Intent(this, FunctionKeyListActivity.class);
				break;
			case R.id.menu3:
				intent = new Intent(this, BoardListActivity.class);
				intent.putExtra("parentKey", "ag1zfmRvZG9sLWJvYXJkchELEgtCb2FyZEVudGl0eRgBDA");
				break;
		}
		
		startActivity(intent);
		
	}
	
	
}
