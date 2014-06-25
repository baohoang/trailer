package Algorithm;

import java.util.ArrayList;

public class listPoint{
	private ArrayList<Point> list;
	
	public listPoint(){
		list=new ArrayList<Point>();
	}
	
	public void add(Point p){
		list.add(p);
	}
	public int size(){
		return list.size();
	}
	public Point get(int index){
		return list.get(index);
	}
	public void delete(int index){
		list.remove(index);
	}
	public ArrayList<Point> getList(){
		return list;
	}
	public void clear(){
		list.clear();
	}
}
