package br.com.mylocationclient.views;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocationclient.R;
import br.com.mylocationclient.app.Client;

public class GpsActivity extends Activity implements LocationListener {

	private TextView latitudeText;
	private TextView longitudeText;
	private LocationManager locationManager;
	private Client client; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		client = Client.getInstance();

		Bundle extras = getIntent().getExtras();
		String key = extras.getString("key");
		TextView textKey = (TextView) findViewById(R.id.text_key);
		textKey.setText("Code: "+key);
		
		latitudeText = (TextView) findViewById(R.id.labelLatitude);
		longitudeText = (TextView) findViewById(R.id.labelLongitude);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		latitudeText.setText("Latitude: "+String.valueOf(location.getLatitude()));
		longitudeText.setText("Longitude: "+String.valueOf(location.getLongitude()));
		
		if(client != null) {
			Position position = new Position(location.getLatitude(),
											 location.getLongitude(), 
											 location.getSpeed(), 
											 location.getAccuracy(), 
											 location.getAltitude(), 
											 System.currentTimeMillis());
			client.sendPosition(position);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, provider+" desabilitado", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, provider+" habilitado", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
