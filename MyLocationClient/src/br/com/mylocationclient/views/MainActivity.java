package br.com.mylocationclient.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.command.Login;
import br.com.mylocation.bean.message.commandresponse.LoginResponse;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocationclient.R;
import br.com.mylocationclient.app.Client;
import br.com.mylocationclient.core.RequestInstance;

public class MainActivity extends Activity {

	private Client client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		client = Client.getInstance();
		client.setMainActivity(this);

		addListenerOnButtonConnect();
		addListenerOnButtonTrack();
	}

	private void addListenerOnButtonConnect() {
		final Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendLogin();
			}
		});
	}

	private void addListenerOnButtonTrack() {
		final Button buttonTrack = (Button) findViewById(R.id.buttonTrack);
		buttonTrack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GpsActivity.class);
				startActivity(intent);
			}
		});
	}

	public void sendLogin() {
		/*
		 * class interna que encapsula o command de login
		 */
		class RequestLogin extends RequestInstance {
			public RequestLogin(Command command) {
				super(command);
			}

			@Override
			public void onRequestInstance(CommandResponse response) {
				onLogin(response);
			}
		}
		try {
			client.connect();
			Command command = new Command(ProtocolDefines.OPERATION_LOGIN);
			Login login = new Login("Teste");
			command.setData(login);
			/*
			 * encapsulou o command numa Instance e envia para o server. A
			 * resposta vai cair no metodo onRequestInstance dessa instancia
			 */
			client.sendRequestInstance(new RequestLogin(command));
		} catch (Exception e) {
			dialog(e.getMessage());
		}
	}

	public void onLogin(CommandResponse response) {
		if (response.getStatus() == ProtocolDefines.STATUS_SUCCESS 
			&& response.getData() != null) {
			final LoginResponse loginResponse = (LoginResponse) response.getData();
			client.setKey(loginResponse.getKey());
			dialog("Conectado key: " + loginResponse.getKey());
		}else{
			dialog("Erro no commando de login");
		}
	}

	public void dialog(final String message) {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				TextView textKey = (TextView) findViewById(R.id.text_key);
				textKey.setText(message);
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();		
			}
		};		
		runOnUiThread(run);		
	}
}
