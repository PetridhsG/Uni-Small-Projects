
import java.lang.Math;
public class Rectangle {
    private int xmin,xmax,ymin,ymax;
    public Rectangle(){
        xmin = ymin = 0;
        xmax = ymax = 100;
    }

    public Rectangle(int xmin,int xmax,int ymin,int ymax){
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }
    public int xmin(){
        return xmin;
    }

    public int xmax(){
        return xmax;
    }

    public int ymin(){
        return ymin;
    }

    public int ymax(){
        return ymax;
    }

    public boolean contains(Point p){
        return ((p.x() <= xmax)&&(p.x() >= xmin)&&(p.y() <= ymax)&&(p.y() >= ymin));
    }

    public boolean intersects(Rectangle that){
        return !((xmin >= that.xmax|| xmax <= that.xmin) || (ymin >= that.ymax || ymax <= that.ymin))   //if not intersects
                || (xmax - xmin == that.xmax - that.xmin || ymax - ymin == that.ymax - that.ymin);  //if rectangles have a same side
    }

    public double distanceTo(Point z){
        return Math.sqrt(squareDistanceTo(z));
    }

    public int squareDistanceTo(Point z){
        int x = Math.max(xmin - z.x(),z.x() - xmax);
        int y = Math.max(ymin - z.y(),z.y() - ymax);
        return x*x + y*y;
    }
    public String toString(){
        return "[" + xmin + "," + xmax + "]" + "x" + "[" + ymin + "," + ymax + "]" ;
    }
}
