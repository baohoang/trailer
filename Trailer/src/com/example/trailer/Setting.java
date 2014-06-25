package com.example.trailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends Activity implements OnClickListener {

	public static final int REQUEST_CODE = 1510;
	public static final int RESULT_OK = 1;
	public static final int RESULT_CANCEL = 0;

	private EditText etname, etcontact;
	private Button btnok, btncancel;
	private Button btncontact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		setTitle("Setting");
		etname = (EditText) findViewById(R.id.etName);
		etcontact = (EditText) findViewById(R.id.etContact);
		btnok = (Button) findViewById(R.id.btnok);
		btncontact = (Button) findViewById(R.id.btncontact);
		btncancel = (Button) findViewById(R.id.btncancel);
		btnok.setOnClickListener(this);
		btncancel.setOnClickListener(this);
		btncontact.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String contact = data.getStringExtra("phone");
				etcontact.setText(contact);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnok:
			SharedPreferences preference = getSharedPreferences("setting",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = preference.edit();
			editor.putString("name", etname.getText().toString());
			editor.putString("contact", etcontact.getText().toString());
			editor.commit();
			finish();
			break;
		case R.id.btncancel:
			finish();
			break;
		case R.id.btncontact:
			Intent i = new Intent(Setting.this, allContact.class);
			startActivityForResult(i, REQUEST_CODE);
			break;
		}
	}
}
