package Algorithm;

public class DistanceFunction {
	public static double calcDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2)+Math.pow(p1.getZ()-p2.getZ(),2));
    }
}
