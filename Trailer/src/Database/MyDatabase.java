package Database;

import java.io.IOException;

import Algorithm.Example;
import Algorithm.Point;
import Algorithm.listPoint;
import Const.Const;
import android.content.Context;
import android.database.Cursor;

public class MyDatabase {

	public static Example getData(Context context, String tableName) {
		Database db = new Database(context);
		db.openDataBase();
		String selectQuery = "SELECT  * FROM " + tableName;
		Cursor c = db.getDb().rawQuery(selectQuery, null);
		Example result = new Example();
		listPoint lp = new listPoint();
		if (c.moveToFirst()) {
			while (c.moveToNext()) {
				float x = c.getFloat(c.getColumnIndex(Const.COL1));
				float y = c.getFloat(c.getColumnIndex(Const.COL2));
				float z = c.getFloat(c.getColumnIndex(Const.COL3));
				lp.add(new Point(x, y, z));
				if (lp.size() == 201) {
					result.add(lp);
					lp.clear();
				}
			}
		}
		c.close();
		db.close();
		return result;
	}

	public static void copydatabase(Context c) {
		Database myDbHelper = new Database(c);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

	}
}