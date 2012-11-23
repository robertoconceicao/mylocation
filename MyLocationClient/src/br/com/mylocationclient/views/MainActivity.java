package br.com.mylocationclient.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.ProtocolDefines;
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
//				Intent intent = new Intent(MainActivity.this, GpsActivity.class);
//				intent.putExtra("client", client);
//
//				startActivity(intent);
				SendGpsEventTask task = new SendGpsEventTask();
				task.execute((Void)null);
			}
		});
	}

	public void dialog(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	
	class SendGpsEventTask extends AsyncTask<Void, Void, Void> implements
			LocationListener {

		private LocationManager locationManager;

		// private Client client;

		@Override
		protected Void doInBackground(Void... params) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);

			while(client != null && client.isConnected()) { }
			
			return null;
		}

		@Override
		public void onLocationChanged(Location location) {
			if (client != null) {
				Position position = new Position(location.getLatitude(),
						location.getLongitude(), location.getSpeed(),
						location.getAccuracy(), location.getAltitude(),
						System.currentTimeMillis());
				client.sendPosition(position);
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
//			Toast.makeText(MainActivity.this, provider + " desabilitado",
//					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
//			Toast.makeText(MainActivity.this, provider + " habilitado",
//					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
