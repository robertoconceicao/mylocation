package br.com.mylocationclient.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;

public class FileGps {

	private static final int INDEX_LATITUDE = 0;
	private static final int INDEX_LONGITUDE = 1;
	private static final int INDEX_SPEED = 2;
	private static final int INDEX_ALTITUDE = 3;
	
	private String filePath;
	private static final String DIRECTORY = "RecordGps";
	private File file;
	
	public FileGps(String fileName){
		this.filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + DIRECTORY;
		
		// cria o diretorio
		file = new File ( filePath) ;
		if (!file.exists()) {
			file.mkdirs();
		}
		// cria o arquivo
		file = new File(filePath + File.separator + fileName + ".txt");
	}

	/**
	 * Metodo que escreve os dados do Location no arquivo.
	 * Os dados serão armazenados no formato String seguindo a seguinte sintaxe:
	 * 
	 * latitude;longitude;speed;altitude
	 * 
	 * @param Location a ser persistido no arquivo
	 */
	public void write(Location location) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(file, true));
			br.append(location.getLatitude()+";");
			br.append(location.getLongitude()+";");
			br.append(location.getSpeed()+";");
			br.append(location.getAltitude()+"");
			
			br.newLine();			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( br != null ){
				try {
					br.close(); // o close ja da o flush
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * Metodo que lê os dados armazenados no arquivo e retorna uma lista de
	 * {@code} Location
	 * 
	 * @return List<Location> com os dados do arquivo
	 */
	public List<Location> read() {
		List<Location> locations = new ArrayList<Location>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader (new FileReader (file));
			String line;
			
			while ((line = br.readLine()) != null) {  
			    locations.add(convertStringToLocation(line));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( br != null ){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return locations;
	}
		
	public String dump() {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader (new FileReader (file));
			String line;
			
			while ((line = br.readLine()) != null) {  
			    stringBuilder.append(line+"\n");			    
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( br != null ){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Metodo que escreve os dados do Location no arquivo.
	 * Os dados serão armazenados no formato String seguindo a seguinte sintaxe:
	 * 
	 * latitude;longitude;speed;altitude
	 * 
	 * @param Location a ser persistido no arquivo
	 */
	public void writeTrace(String string) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(file, true));
			br.append(string);		
			br.newLine();	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( br != null ){
				try {
					br.close(); // o close ja da o flush
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * Metodo que converte uma string no formato: latitude;longitude;speed;altitude
	 * {@code write}
	 * 
	 * @param buffer
	 * @return Objeto do tipo Location com os dados extraidos da string buffer
	 */
	public Location convertStringToLocation(String buffer){
		Location location = null;
		if(buffer != null && !buffer.isEmpty()){
			String []split = buffer.split(";");
			location = new Location(LocationManager.GPS_PROVIDER);
			location.setLatitude(Double.parseDouble(split[INDEX_LATITUDE]));
			location.setLongitude(Double.parseDouble(split[INDEX_LONGITUDE]));
			location.setSpeed(Float.parseFloat(split[INDEX_SPEED]));
			location.setAltitude(Double.parseDouble(split[INDEX_ALTITUDE]));
		}
		return location;
	}
}
