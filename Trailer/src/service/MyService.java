package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import Algorithm.DTW;
import Algorithm.Example;
import Algorithm.Point;
import Algorithm.listPoint;
import Const.Const;
import Database.MyDatabase;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

public class MyService extends Service implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor sensorAccelerometer;
	private listPoint tmp;
	private ArrayList<Example> listExample;
	private static boolean flag;

	// private int count;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// count = 0;
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorAccelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		tmp = new listPoint();
		flag = false;
		// thiet lap csdl
		MyDatabase.copydatabase(this);
		listExample = new ArrayList<Example>();
		for (int i = 0; i < Const.exam.length; i++) {
			String tableName = Const.exam[i];
			Example e = MyDatabase.getData(MyService.this, tableName);
			listExample.add(e);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flag, int startId) {
		super.onStartCommand(intent, flag, startId);
		Log.v("service", "Starting");
		sensorManager.registerListener(this, sensorAccelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this, sensorAccelerometer);
		Log.v("service", "stoped");
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/*
	 * if (count == 0) { GPSTracker gps = new
	 * GPSTracker(getApplicationContext()); if (gps.canGetLocation()) {
	 * 
	 * double latitude = gps.getLatitude(); double longitude =
	 * gps.getLongitude();
	 * 
	 * // \n is for new line Toast.makeText( getApplicationContext(),
	 * "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
	 * Toast.LENGTH_LONG).show(); } else { // can't get location // GPS or
	 * Network is not enabled // Ask user to enable GPS/network in settings
	 * gps.showSettingsAlert(); } count++; }
	 */
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		float x = arg0.values[0], y = arg0.values[1], z = arg0.values[2];
		Point point = new Point(x, y, z);
		if (flag) {
			// xay ra nguong
			tmp.add(point);
			if (tmp.size() == 201) {
				// sap xep theo DTW tang dan
				Vector<Pair<Double, Integer>> v = new Vector<Pair<Double, Integer>>();
				for (int i = 0; i < listExample.size(); i++) {
					Example ex = listExample.get(i);
					double val = DTW.DTW_betwween(ex.get(0), tmp);
					if (val < Const.thresholdEnergy) {
						// Warning
						Intent intent = new Intent(getBaseContext(),
								com.example.trailer.Warning.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						break;
					} else
						v.add(new Pair<Double, Integer>(val, i));
				}
				Collections.sort(v, new Comparator<Pair<Double, Integer>>() {
					public int compare(Pair<Double, Integer> strings,
							Pair<Double, Integer> otherStrings) {
						if (strings.first > otherStrings.first) {
							return 1;
						} else {
							if (strings.first < otherStrings.first)
								return -1;
						}
						return 0;
					}
				});
				Vector<Integer> order = new Vector<Integer>();
				for (Pair<Double, Integer> sa : v) {
					order.add(sa.second);
				}
				for (int i = 0; i < order.size(); i++) {
					Log.v("order", String.valueOf(order.get(i)));
					Example ex = listExample.get(order.get(i));
					for (int j = 0; j < ex.size(); j++) {
						listPoint t = ex.get(j);
						double dtw = DTW.DTW_betwween(tmp, t);
						if (dtw < Const.thresholdEnergy) {
							// Warning
							Intent intent1 = new Intent(getBaseContext(),
									com.example.trailer.Warning.class);
							intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent1);
							break;
						}
					}
				}
				tmp.clear();
				flag = false;
			}
		} else {
			tmp.add(point);
			if (tmp.size() > 30) {
				tmp.delete(0);
			}
			if ((Math.abs(x) > Const.threshold && Math.abs(y) > Const.threshold)
					|| (Math.abs(x) > Const.threshold && Math.abs(z) > Const.threshold)
					|| (Math.abs(y) > Const.threshold && Math.abs(z) > Const.threshold)) {
				flag = true;
			}
		}
	}
}
