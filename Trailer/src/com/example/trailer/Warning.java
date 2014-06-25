package com.example.trailer;

import Const.Const;
import SMS.sendSMS;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class Warning extends Activity implements OnClickListener {
	private Button btnyes;
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window win=getWindow();
		win.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		setContentView(R.layout.warning);
		setTitle("Warning");
		flag = true;
		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(Const.timeSleep);
					if (flag) {
						SharedPreferences preference = getSharedPreferences(
								"setting", MODE_PRIVATE);
						String msg = preference.getString("name", "aa");
						String num = preference.getString("contact", "bb");
						new sendSMS(num, msg);
						Warning.this.finish();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
		btnyes = (Button) findViewById(R.id.btnyes);
		btnyes.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnyes:
			flag = false;
			finish();
			break;
		}
	}
}
