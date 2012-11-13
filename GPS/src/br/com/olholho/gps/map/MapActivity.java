package br.com.olholho.gps.map;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import br.com.olholho.gps.CustomItemizedOverlay;
import br.com.olholho.gps.R;
import br.com.olholho.gps.io.FileGps;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class MapActivity extends com.google.android.maps.MapActivity {

	private FileGps fileGps;
	private CustomItemizedOverlay itemizedOverlay;
	private MyLocationOverlay myLocationOverlay;

	private MapController mapController;
	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		fileGps = new FileGps("testegps");

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(14);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);

		List<Overlay> mapOverlays = mapView.getOverlays();

		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
		
		itemizedOverlay = new CustomItemizedOverlay(this, drawable);
		
		List<Location> listLocation = fileGps.read();
		
		itemizedOverlay.addLocationList(listLocation);

		mapOverlays.add(itemizedOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
}
