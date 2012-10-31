package com.laaficionmanda.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * BaseActivity
 * 
 * @author Luis Aguilar
 * 
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public abstract void initComponents();

    /**
     * Inicia una actividad usando el Class de la misma
     * @param clazz
     */
    public void startActivityByClass(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * Inicia una actividad usando el Class de la misma y enviando un bundle de extras
     * @param clazz
     * @param extras
     */
    public void startActivityByClass(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

}
