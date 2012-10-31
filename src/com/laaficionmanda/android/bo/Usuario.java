package com.laaficionmanda.android.bo;


/**
 * Clase para el manejo de 
 * la información del usuario.
 * @author Esteban
 *
 */
public class Usuario {
	
	/**
	 * Constructor de la clase.
	 */
	public void Usuario(){
		
	}
	
	/*Setters y Getters*/
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	
	/*Atributos de la clase*/
	
	private String id;

	private String email;
	
	private String appID;
	
	private String urlImage;

}
