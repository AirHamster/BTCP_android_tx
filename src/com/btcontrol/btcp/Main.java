package com.btcontrol.btcp;


import com.btcontrol.btcp.Joys;
import com.btcontrol.btcp.Settings;
import com.btcontrol.btcp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends Activity implements OnClickListener {
	Button joys, settings;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		 joys = (Button) findViewById(R.id.joys);
		    joys.setOnClickListener(this);
		    
		    settings = (Button) findViewById(R.id.settings);
		    settings.setOnClickListener(this);
		

				
				}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	    case R.id.joys:
	    	Intent intent_accel = new Intent(this, Joys.class);
	    	startActivity(intent_accel);
	    	break;
	    case R.id.settings:
	    	Intent intent_wheel = new Intent(this, Settings.class);
	    	startActivity(intent_wheel);
	    	break;	    	
		
	}
}
}
		

		


		


	
	

	
