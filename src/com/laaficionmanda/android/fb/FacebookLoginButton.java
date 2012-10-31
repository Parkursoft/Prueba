package com.laaficionmanda.android.fb;

import com.laaficionmanda.android.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;


/**
 * Botón que hace los llamados
 * al API de facebook para inicializar la sesión de 
 * facebook.
 * @author Esteban
 *
 */
public class FacebookLoginButton extends ImageButton {
	
	
	//Atributos
	
	//Objecto del API de facebook.
	private Facebook mFb = null;
	
	//Código de la actividad.
	private int mActivity;
	
	//Actividad a la que pertence el botón.
	private Activity mMyActivity;
	
	//Permisos para el api de facebook.
	private String[] mPermissions;
	
	//Handler
	private Handler mHandler;
	
	//Listener de la sesión del usuario.
	private SessionListener mSessionListener = new SessionListener();
	
	//Constructores deñ botón
	
	public FacebookLoginButton(Context context) {
		super(context);
	}

    public FacebookLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FacebookLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(final Activity activity, final int activityCode, final Facebook fb) {
        init(activity, activityCode, fb, new String[] {});
    }

    
    /**
     * Método de inicialización del botón
     * @param activity
     * @param activityCode
     * @param fb
     * @param permissions
     */
	public void init(Activity activity, int activityCode, Facebook fb,
			String[] permissions) {
		
		this.mFb = fb;
		this.mMyActivity = activity;
		this.mActivity = activityCode;
		this.mPermissions = permissions;
		this.mHandler = new Handler();
		
		setBackgroundColor(Color.TRANSPARENT);
		setImageResource(fb.isSessionValid() ? R.drawable.logout : R.drawable.login);
		drawableStateChanged();
		
		SessionEvents.addAuthListener(mSessionListener);
        SessionEvents.addLogoutListener(mSessionListener);
        
        SessionStore.restore(fb, getContext());
        
        if(fb.isSessionValid()){
        	setImageResource(R.drawable.logout);
        }
        else{
        	setImageResource(R.drawable.login);
        }
        
		setOnClickListener(new ButtonOnClickListener());
		
	}
    
	/**
	 * Clase que maneja el evento de click del botón.
	 * @author Esteban
	 *
	 */
	private final class ButtonOnClickListener implements OnClickListener {
        
        public void onClick(View arg0) {
            if (mFb.isSessionValid()) {
                SessionEvents.onLogoutBegin();
                AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFb);
                asyncRunner.logout(getContext(), new LogoutRequestListener());
            } else {
            	//Inicializa el dialogo para la sesión de facebook
                mFb.authorize(mMyActivity, mPermissions, mActivity, new LoginDialogListener());
            }
        }
    }
	
	/**
	 * Clase que implementa el DialogListener de Facebook.
	 * @author Esteban
	 *
	 */
	 private final class LoginDialogListener implements DialogListener {
	        public void onComplete(Bundle values) {
	            SessionEvents.onLoginSuccess();
	        }

	        public void onFacebookError(FacebookError error) {
	            SessionEvents.onLoginError(error.getMessage());
	        }

	        public void onError(DialogError error) {
	            SessionEvents.onLoginError(error.getMessage());
	        }

	        public void onCancel() {
	            SessionEvents.onLoginError("Action Canceled");
	        }
	    }
	 
	 	/**
	 	 * Request Listener que maneja 
	 	 * el proceso deslogeo.
	 	 * @author Esteban
	 	 *
	 	 */
	    private class LogoutRequestListener extends BaseRequestListener {
	        public void onComplete(String response, final Object state) {
	          
	        	/*
	        	 * El Callback debe ser ejecutado en el hilo original, no el hilo 
	        	 * en el background
	        	 */
	            mHandler.post(new Runnable() {
	                public void run() {
	                    SessionEvents.onLogoutFinish();
	                }
	            });
	        }
	    }

	    /**
	     * Clase que maneja los callback de login y logout.
	     * @author Esteban
	     *
	     */
	    private class SessionListener implements AuthListener, LogoutListener {

	        public void onAuthSucceed() {
	            setImageResource(R.drawable.logout);
	            SessionStore.save(mFb, getContext());
	        }

	        public void onAuthFail(String error) {
	        }

	        public void onLogoutBegin() {
	        }

	        public void onLogoutFinish() {
	            SessionStore.clear(getContext());
	            setImageResource(R.drawable.login);
	        }
	    }

}

