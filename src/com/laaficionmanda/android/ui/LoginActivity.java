package com.laaficionmanda.android.ui;

import org.json.JSONException;

import org.json.JSONObject;


import com.laaficionmanda.android.R;
import com.laaficionmanda.android.service.LaAficionMandaSyncService;
import com.laaficionmanda.android.util.BackgroundTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.*;
import com.laaficionmanda.android.fb.*;

public class LoginActivity extends BaseActivity {

	private TextView label;
	private ImageView mUserPic;
	private Handler mHandler;
	private FacebookLoginButton mLoginButton;
	final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	final static int PICK_EXISTING_PHOTO_RESULT_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initComponents();
		// getHello();
	}

	@Override
	public void initComponents() {
		label = (TextView) findViewById(R.id.activity_login_label);

		// Se inicializa las componentes de Facebook.
		this.inicializarFacebook();
	}

	/**
	 * Inicializa los componentes de facebook.
	 */
	private void inicializarFacebook() {
		
		//Manejo del hilo para obtener los datos
		//del usuario
		this.mHandler = new Handler();
		
		// Se crea la instancia de facebook
		Utility.getFacebook();

		// se crea la instancia del AsyncFacebookRunner
		Utility.getAsyncFacebookRunner();

		// Se obtiene el botón de facebook para iniciarlo.
		mLoginButton = (FacebookLoginButton) findViewById(R.id.login);
		mUserPic = (ImageView) findViewById(R.id.imgUsuario);

		// Se agregan los listener para el login.
		SessionEvents.addAuthListener(new FbAPIsAuthListener());
		SessionEvents.addLogoutListener(new FbAPIsLogoutListener());

		mLoginButton.init(this, AUTHORIZE_ACTIVITY_RESULT_CODE,
				Utility.getFacebook(), Utility.getPermissions());

		if (Utility.getFacebook().isSessionValid()) {
			requestUserData();
			login(null);
		}

	}

	public void requestUserData() {
		label.setText("Obteniendo información del usuario...");
		Bundle params = new Bundle();
		params.putString("fields", "name, picture");
		Utility.getAsyncFacebookRunner().request("me", params,
				new UserRequestListener());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Utility.getFacebook().authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Utility.getFacebook() != null) {
			if (!Utility.getFacebook().isSessionValid()) {
				label.setText("Usted esta deslogeado! ");
				mUserPic.setImageBitmap(null);
			} else {
				Utility.getFacebook().extendAccessTokenIfNeeded(this, null);
				this.login(null);
			}
		}
	}

	public void login(View view) {
		startActivityByClass(CountrySelectionActivity.class);
	}

	private void getHello() {

		new BackgroundTask() {
			String hello = null;

			@Override
			public void work() {
				LaAficionMandaSyncService service = new LaAficionMandaSyncService();
				hello = service.helloto("alfonso");

			}

			@Override
			public void done() {
				label.setText(hello);
			}
		};
	}

	/**
	 * 
	 * Callback para obtener el nombre de usuario, la imagen y el user id.
	 */
	public class UserRequestListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {

			JSONObject jsonObject;
			JSONObject jsonImage;
			try {
				jsonObject = new JSONObject(response);

				String picJason = jsonObject.getString("picture");

				jsonImage = new JSONObject(picJason);

				picJason = jsonImage.getString("data");

				jsonImage = new JSONObject(picJason);

				final String picURL = jsonImage.getString("url");

				final String name = jsonObject.getString("name");

				Utility.setUserId(jsonObject.getString("id")); 

				mHandler.post(new Runnable() {
					public void run() {
						label.setText("Bienvenido " + name + "!");
						mUserPic.setImageBitmap(Utility.getBitmap(picURL));
					}
				});

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * EL callback para notificar a la aplicación cuando la autorización se
	 * realiza correctamente o falla.
	 * 
	 * @author Esteban
	 * 
	 */
	public class FbAPIsAuthListener implements AuthListener {

		public void onAuthSucceed() {
			requestUserData();
			/*Se inicia el siguiente activity*/
			login(null);
		}

		public void onAuthFail(String error) {
			label.setText("Fallo al ingresar: " + error);
		}
	}

	/*
	 * The Callback for notifying the application when log out starts and
	 * finishes.
	 */

	/**
	 * El callback para notificar a la aplicación cuando el log out inicia o
	 * finaliza.
	 * 
	 * @author Esteban
	 * 
	 */
	public class FbAPIsLogoutListener implements LogoutListener {
		public void onLogoutBegin() {
			label.setText("Deslogeandose...");
		}

		public void onLogoutFinish() {

			label.setText("Usted ha sido deslogeado! ");
			mUserPic.setImageBitmap(null);

		}
	}
}
