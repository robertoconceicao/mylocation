package br.com.mylocationclient.views;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.ProtocolDefines;
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
		setContentView(R.layout.activity_gps);
		
		//pega a referencia do client 
		Bundle extras = getIntent().getExtras();
		
		client = (Client) extras.getSerializable("client");
		
		latitudeText = (TextView) findViewById(R.id.infoLatitude);
		longitudeText = (TextView) findViewById(R.id.infoLongitude);
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
		latitudeText.setText(String.valueOf(location.getLatitude()));
		longitudeText.setText(String.valueOf(location.getLongitude()));
		
		if(client != null) {
			Position position = new Position(location.getLatitude(),
											 location.getLongitude(), 
											 location.getSpeed(), 
											 location.getAccuracy(), 
											 location.getAltitude(), 
											 System.currentTimeMillis());
			Event eventPosition = new Event(ProtocolDefines.OPERATION_POSITION, position);
//			try {
//				client.sendMessage(eventPosition);
//				Toast.makeText(this, "Enviando Position para servidor ", Toast.LENGTH_SHORT).show();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
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
