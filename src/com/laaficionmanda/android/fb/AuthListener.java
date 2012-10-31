package com.laaficionmanda.android.fb;

/**
 * Callback interface for authorization events.
 * @author Esteban
 *
 */
public interface AuthListener {

	/**
	 * Llamado cuando el flujo de autorización se completa correctamente y un
	 * token de OAuth (autenticación) fue recibido. Ejecutado por el hili que inicializó
	 * la autenticación. Ahora las peticiones al API pueden ser hechas.
	 */
    public void onAuthSucceed();

    /**
     * Llamado cuando el login se completa incorrectamente con un error.
     * Ejecutado por el hilo que inicializa la autenticación
     */
    public void onAuthFail(String error);
	
}
