package com.example.trailer;

import service.MyService;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private boolean checkService;
	private Button btnstart, btnstop, btnclose;
	private TextView tvstatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Trailer");
		checkService = isServicerunning();
		btnstart = (Button) findViewById(R.id.btnStart);
		btnstop = (Button) findViewById(R.id.btnStop);
		btnclose = (Button) findViewById(R.id.btnClose);
		tvstatus = (TextView) findViewById(R.id.statusservice);
		if (checkService) {
			tvstatus.setText("Service is running ...");
		} else {
			tvstatus.setText("Service is closed ...");
		}
		btnstart.setOnClickListener(this);
		btnstop.setOnClickListener(this);
		btnclose.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.About:
			Intent about = new Intent("com.example.trailer.aboutus");
			startActivity(about);
			break;

		case R.id.Setting:
			Intent setting = new Intent("com.example.trailer.setup");
			startActivity(setting);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// check service exist
	public boolean isServicerunning() {
		ActivityManager acm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : acm
				.getRunningServices(Integer.MAX_VALUE)) {
			if (MyService.class.getName()
					.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	//event click button
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnStart:
			if (!checkService) {
				startService(new Intent(this, service.MyService.class));
				checkService = true;
				tvstatus.setText("Service is running ...");
			} else {
				Toast.makeText(MainActivity.this, "Service is running ...",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnStop:
			if (checkService) {
				stopService(new Intent(this, service.MyService.class));
				checkService = false;
				tvstatus.setText("Service is closed ...");
			} else {
				Toast.makeText(MainActivity.this, "Service is closed ...",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnClose:
			finish();
			break;
		}
	}

}
