package com.laaficionmanda.android.fb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.facebook.android.*;

/**
 * Clase que maneja el 
 * almacenamiento del token
 * de autenticación
 * @author Esteban
 *
 */
public class SessionStore {

    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-session";
    
    /*
     * Guarda el token de acceso y la fecha de expiración por lo que se evita
     * hacer un fetch cada vez que se necesita.
     */
    public static boolean save(Facebook session, Context context) {
        Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, session.getAccessToken());
        editor.putLong(EXPIRES, session.getAccessExpires());
        return editor.commit();
    }

    
    /*
     *Restora el token de acceso y la fecha de expiración de las preferencias compartidas 
     * 
     */
    public static boolean restore(Facebook session, Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        session.setAccessToken(savedSession.getString(TOKEN, null));
        session.setAccessExpires(savedSession.getLong(EXPIRES, 0));
        return session.isSessionValid();
    }
    
    /*
     * Limpia las preferencias compartidadas y el token de acceso.
     */
    public static void clear(Context context) {
        Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}