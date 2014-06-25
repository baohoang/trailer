package Algorithm;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import android.util.Log;
import android.util.Pair;


public class test {
	public static void main(){
		Vector<Pair<Double, Integer>> v = new Vector<Pair<Double, Integer>>();
		v.addElement(new Pair<Double,Integer>(0.91,1));
		v.addElement(new Pair<Double, Integer>(0.81, 2));
		v.addElement(new Pair<Double, Integer>(7.81, 3));
		v.addElement(new Pair<Double, Integer>(0.01, 4));
		Collections.sort(v,
				new Comparator<Pair<Double, Integer>>() {
					public int compare(
							Pair<Double, Integer> strings,
							Pair<Double, Integer> otherStrings) {
						if (strings.first > otherStrings.first)
							return 1;
						else{
							if (strings.first < otherStrings.first)
								return -1;
						}
						return 0;
					}
				});
		Vector<Integer> order = new Vector<Integer>();
		for (Pair<Double, Integer> sa : v) {
			order.add(sa.second);
			Log.v("order",String.valueOf(sa.second));
		}
	}
}
