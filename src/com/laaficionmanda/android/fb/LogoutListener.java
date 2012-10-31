package com.laaficionmanda.android.fb;

/**
 * Interface de callback para eventos de logout.
 * @author Esteban
 *
 */
public interface LogoutListener {

	/**
	 * Llamado cuando el logout inicia, antes de que la sesi�n es invalidada.
	 * La �ltima oportunidad de hacer una llamada al API. Ejectado por el 
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
     * Llamado cuando la informaci�n de la sesi�n ha sido limpiada. La IU 
     * deder�a de actualizarse para refliejar el estado de deslogeo.
     * 
     * Inicializado por el hilo que inicializa el logout.
     */
    public void onLogoutFinish();
	
}

