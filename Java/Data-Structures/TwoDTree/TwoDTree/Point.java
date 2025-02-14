
import java.lang.Math;
public class Point {
    private int x,y;

    Point(){
        x = y = 0 ;
    }

    Point(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public double distanceTo(Point z){
        return Math.sqrt(squareDistanceTo(z));
    }

    public int squareDistanceTo(Point z){
        return (z.x - x)*(z.x - x) + (z.y - y)*(z.y - y);
    }

    public String toString(){
        return "(" + x + "," + y + ")" ;
    }
}
