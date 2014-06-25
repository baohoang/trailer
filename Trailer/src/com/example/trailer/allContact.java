package com.example.trailer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class allContact extends Activity implements OnItemClickListener {

	public static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
	public static final Uri CONTACTS_URI_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	public static final int REQUEST_CODE = 1510;
	public static final int RESULT_OK = 1;
	public static final int RESULT_CANCEL = 0;

	private ArrayList<String> list;
	private ListView lv;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.listcontact);
		setTitle("Contact");
		lv = (ListView) findViewById(R.id.listview);
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(CONTACTS_URI, null, null, null, null);
		ArrayList<String> list_name = new ArrayList<String>();
		list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String number = "";
			String id = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor cur = getContentResolver().query(CONTACTS_URI_PHONE, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[] { id }, null);
			if (cur.moveToFirst()) {
				number = cur
						.getString(cur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				list_name.add(name);
				list.add(number);
			}
			cur.close();
		}
		cursor.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.row, R.id.textname, list_name);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		i.putExtra("phone", list.get(arg2));
		setResult(RESULT_OK, i);
		finish();
	}
}
