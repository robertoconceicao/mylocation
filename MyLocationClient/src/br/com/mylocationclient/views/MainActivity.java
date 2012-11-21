package br.com.mylocationclient.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.mylocationclient.R;
import br.com.mylocationclient.app.Client;

public class MainActivity extends Activity {
	
	private Client client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		client = new Client(this);
		
		addListenerOnButtonConnect();
		addListenerOnButtonTrack();
	}

	private void addListenerOnButtonConnect() {
		final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				client.connect();
			}
		});
	}
	
	private void addListenerOnButtonTrack() {
		final Button buttonTrack = (Button) findViewById(R.id.buttonTrack);
		buttonTrack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GpsActivity.class);
//				intent.putExtra("client", client);
				
				startActivity(intent);
			}
		});
	}
	
	public void dialog(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
