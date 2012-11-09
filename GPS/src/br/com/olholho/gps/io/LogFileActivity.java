package br.com.olholho.gps.io;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.olholho.R;
import br.com.olholho.gps.Gps;

public class LogFileActivity extends Activity {
	private static final String TAG = "MainActivity";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_file);

		final EditText fileName = ( EditText ) findViewById (R.id.textFileName);

		Button buttonOk = (Button) findViewById(R.id.buttonOk);
		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = fileName.getEditableText().toString();
				if(name != null){
					Intent intent = new Intent(LogFileActivity.this, Gps.class);
					startActivity(intent);
				}else{
					Toast.makeText(LogFileActivity.this,"Nome do arquivo é obrigatório",Toast.LENGTH_LONG).show();
				}				
			}
		});
		
		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(LogFileActivity.this,"Click button cancelar",Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
