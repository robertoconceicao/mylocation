package br.com.mylocationclient.views;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocationclient.R;
import br.com.mylocationclient.io.Client;

public class MainActivity extends Activity {
	private ProgressDialog progDialog;
	private Client client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		client = new Client();
		addListenerOnButtonConnect();
	}

	private void addListenerOnButtonConnect() {
		final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
				try {
					client.connect(ProtocolDefines.HOST_NAME, ProtocolDefines.PORT);
					dismissDialog(0);
					Toast.makeText(v.getContext(), "Conectado", Toast.LENGTH_SHORT).show();
					buttonConnect.setText("Conectado");
					client.sendMessage(new Message(ProtocolDefines.OPERATION_LOGIN, ProtocolDefines.TYPE_COMMAND));
				} catch (IOException e) {
					dismissDialog(0);
					Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}				
			}
		});
	}
	
	// Method to create a progress bar dialog of either spinner or horizontal type
    @Override
    protected Dialog onCreateDialog(int id) {
        progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setMessage(R.string.connect+"...");
        return progDialog;
    }
}
