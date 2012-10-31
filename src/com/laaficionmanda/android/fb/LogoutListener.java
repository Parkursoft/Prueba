package com.laaficionmanda.android.fb;

/**
 * Interface de callback para eventos de logout.
 * @author Esteban
 *
 */
public interface LogoutListener {

	/**
	 * Llamado cuando el logout inicia, antes de que la sesión es invalidada.
	 * La última oportunidad de hacer una llamada al API. Ejectado por el 
	 * hilo que inicializa el logout.
	 */
    public void onLogoutBegin();

    /**
     * Called when the session information has been cleared. UI should be
     * updated to reflect logged-out state.
     * 
     * Executed by the thread that initiated the logout.
     */
    
    /**
     * Llamado cuando la información de la sesión ha sido limpiada. La IU 
     * dedería de actualizarse para refliejar el estado de deslogeo.
     * 
     * Inicializado por el hilo que inicializa el logout.
     */
    public void onLogoutFinish();
	
}

