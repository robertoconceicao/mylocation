package br.com.olholho.gps;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import br.com.olholho.R;
import br.com.olholho.gps.io.FileGps;

public class DumpFileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump_file);
        
        EditText fieldDump = (EditText) findViewById(R.id.fieldDump);
        fieldDump.setText(dump());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dump_file, menu);
        return true;
    }
    
    public String dump(){
    	StringBuilder builder = new StringBuilder();
    	
    	FileGps fileGps = new FileGps("testegps");
    	
    	List<Location> list = fileGps.read();
    	
    	for(Location location : list){
    		builder.append(location.getLatitude()+";");
    		builder.append(location.getLongitude()+";");
    		builder.append(location.getSpeed()+";");
    		builder.append(location.getAltitude()+"\n");
    	}
    	return builder.toString();
    }
}
