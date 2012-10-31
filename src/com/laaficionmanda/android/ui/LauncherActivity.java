package com.laaficionmanda.android.ui;

import com.laaficionmanda.android.R;
import com.laaficionmanda.android.R.layout;
import com.laaficionmanda.android.R.menu;
import com.laaficionmanda.android.util.BackgroundTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class LauncherActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
        

        new BackgroundTask() {

            @Override
            public void work() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

            @Override
            public void done() {
               startNextActivity();
            }
        };
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_launcher, menu);
        return true;
    }
    
    public void startNextActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub
		
	}
}
