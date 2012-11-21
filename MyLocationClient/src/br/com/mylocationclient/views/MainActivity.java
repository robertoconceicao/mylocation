package br.com.mylocationclient.views;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.mylocation.bean.message.Command;
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
		addListenerOnButtonTrack();
	}

	private void addListenerOnButtonConnect() {
		final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					client.connect(ProtocolDefines.HOST_NAME, ProtocolDefines.PORT);
					buttonConnect.setText("Conectado");
					client.sendMessage(new Command(1, ProtocolDefines.OPERATION_LOGIN));
					client.onMessage();
					
					Toast.makeText(v.getContext(), "Conectado", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}				
			}
		});
	}
	
	private void addListenerOnButtonTrack() {
		final Button buttonTrack = (Button) findViewById(R.id.buttonTrack);
		buttonTrack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GpsActivity.class);
				intent.putExtra("client", client);
				startActivity(intent);
			}
		});
	}
}
