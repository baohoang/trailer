package Algorithm;

import java.util.ArrayList;

public class Example {
	ArrayList<listPoint> example;

	public Example() {
		example = new ArrayList<listPoint>();
	}

	public void add(listPoint p) {
		example.add(p);
	}

	public int size() {
		return example.size();
	}

	public listPoint get(int index) {
		return example.get(index);
	}

	public void delete(int index) {
		example.remove(index);
	}

	public ArrayList<listPoint> getList() {
		return example;
	}
	public void clear(){
		example.clear();
	}
}
