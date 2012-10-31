package com.laaficionmanda.android.fb;

/**
 * Callback interface for authorization events.
 * @author Esteban
 *
 */
public interface AuthListener {

	/**
	 * Llamado cuando el flujo de autorizaci�n se completa correctamente y un
	 * token de OAuth (autenticaci�n) fue recibido. Ejecutado por el hili que inicializ�
	 * la autenticaci�n. Ahora las peticiones al API pueden ser hechas.
	 */
    public void onAuthSucceed();

    /**
     * Llamado cuando el login se completa incorrectamente con un error.
     * Ejecutado por el hilo que inicializa la autenticaci�n
     */
    public void onAuthFail(String error);
	
}
