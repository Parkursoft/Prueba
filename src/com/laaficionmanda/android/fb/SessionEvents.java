package com.laaficionmanda.android.fb;

import java.util.LinkedList;


public class SessionEvents {

    private static LinkedList<AuthListener> mAuthListeners = new LinkedList<AuthListener>();
    private static LinkedList<LogoutListener> mLogoutListeners = new LinkedList<LogoutListener>();
  
    /**
     * Asocia el listener dado con este objecto de Facebook. El callback de la interfaz
     * del listener ser� invocado cuando el evento de autenticaci�n ocurra.
     * 
     * @param listener
     * 				El objecto callback que notifica a la aplicaci�n cuando
     * 				la autenticaci�n ocurra.
     */
    public static void addAuthListener(AuthListener listener) {
        mAuthListeners.add(listener);
    }
    
    /**
     * Remueve el listener dado de la lista de aquellos que ser�n notificados
     * cuando el evento de autenticaci�n ocurra.
     * 
     * @param listener
     * 			El objecto callback que notifica a la aplicaci�n cuando
     * 			events happen.
     */
    public static void removeAuthListener(AuthListener listener) {
        mAuthListeners.remove(listener);
    }

    /**
     * Asocia el listener dado con este objecto de Facebook. El callback de la interfaz
     * del listener ser� invocado cuando el evento de logout ocurra.
     * 
     * @param listener
     *            The callback object for notifying the application when log out
     *            starts and finishes.
     */
    public static void addLogoutListener(LogoutListener listener) {
        mLogoutListeners.add(listener);
    }

    /**
     * Remove the given listener from the list of those that will be notified
     * when logout occurs.
     * 
     * Remueve el listener dado de la lista de aquellos que ser�n notificados
     * cuando el logout ocurra.
     * 
     * @param listener
     *            The callback object for notifying the application when log out
     *            starts and finishes.
     *            El objecto callback por notificar a la aplicaci�n 
     *            cuando el logout inicie y finalice.
     */
    public static void removeLogoutListener(LogoutListener listener) {
        mLogoutListeners.remove(listener);
    }

    public static void onLoginSuccess() {
        for (AuthListener listener : mAuthListeners) {
            listener.onAuthSucceed();
        }
    }

    public static void onLoginError(String error) {
        for (AuthListener listener : mAuthListeners) {
            listener.onAuthFail(error);
        }
    }

    public static void onLogoutBegin() {
        for (LogoutListener l : mLogoutListeners) {
            l.onLogoutBegin();
        }
    }

    public static void onLogoutFinish() {
        for (LogoutListener l : mLogoutListeners) {
            l.onLogoutFinish();
        }
    }

}