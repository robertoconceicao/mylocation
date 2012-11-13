package br.com.olholho.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import br.com.olholho.gps.io.FileGps;

public class Gps extends Activity implements LocationListener {

	private TextView latitudeText;
	private TextView longitudeText;
	private TextView velocidadeText;
	private LocationManager locationManager;

	private FileGps fileGps;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);

		latitudeText = (TextView) findViewById(R.id.latitudeText);
		longitudeText = (TextView) findViewById(R.id.longitudeText);
		velocidadeText = (TextView) findViewById(R.id.velocidadeText);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		fileGps = new FileGps("testegps");
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
		velocidadeText.setText(String.valueOf(location.getSpeed()));
		
		fileGps.write(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
