package com.laaficionmanda.android;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.laaficionmanda.android.bo.*;

public class LaAficionMandaApp {

	/**
	 * Contructor de la clase.
	 */
	private void LaAficionMandaApp(){
	}

	/*Setters y Getters de la clase.*/
	
	public LaAficionMandaApp getInstance() {
		
		if(instance != null)
			instance = new LaAficionMandaApp();
		
		return instance;
	}

	public void setInstance(LaAficionMandaApp instance) {
		this.instance = instance;
	}
	
	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public AsyncFacebookRunner getAsyncRunner() {
		return asyncRunner;
	}

	public void setAsyncRunner(AsyncFacebookRunner asyncRunner) {
		this.asyncRunner = asyncRunner;
	}
	
	/*Atributos de la clase*/
	
	/*Instancia de la clase*/
	private LaAficionMandaApp instance;

	/*Instancia de facebook*/
	private Facebook facebook;
	
	/*Instancia de api asíncrono de facebook*/
	private AsyncFacebookRunner asyncRunner;
	
	/*Instancia del usuario de la aplicación*/
	private Usuario usuario;
}
