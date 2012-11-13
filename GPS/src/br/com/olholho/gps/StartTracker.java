package br.com.olholho.gps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.olholho.gps.map.MapActivity;

public class StartTracker extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tracker);
        
        Button buttonStartTracker = (Button) findViewById(R.id.buttonStartTracker);
        buttonStartTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartTracker.this, Gps.class);
				startActivity(intent);
			}
		});
        
        
        Button buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartTracker.this, MapActivity.class);
				startActivity(intent);
			}
		});
        
        Button buttonDumpFile = (Button) findViewById(R.id.buttonDumpFile);
        buttonDumpFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartTracker.this, DumpFileActivity.class);
				startActivity(intent);
			}
		});
        
        
        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDestroy();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start_tracker, menu);
        return true;
    }
}
