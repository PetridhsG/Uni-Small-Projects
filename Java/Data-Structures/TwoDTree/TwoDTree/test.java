public class test {

    public static void main(String[] args){
        Point p1 = new Point(5,10);
        Point p2 = new Point(10,30);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p1.distanceTo(p2));
        System.out.println(p1.squareDistanceTo(p2));
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle(10,20,30,40);
        System.out.println(r1.contains(p1));
        System.out.println(r1.contains(p2));
        System.out.println(r2.contains(p1));
        System.out.println(r2.contains(p2));
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r1.intersects(r2));
        System.out.println(r1.squareDistanceTo(p1));
        System.out.println(r1.distanceTo(p1));
    }
}
