import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class TwoDTree {

    private static class Treenode{     //node class
        Point item;             //item
        Treenode left;          //pointer to left subtree
        Treenode right;         //pointer to right subtree
        Treenode(Point item){   //constructor
            this.item = item;
            left = right = null;
        }
    }

    private StringStackImpl<Rectangle> rects_stack = new StringStackImpl<Rectangle>();
    private List<Point> points = new List<Point>();
    private StringStackImpl<Integer> distances = new StringStackImpl<Integer>();
    private StringQueueImpl<Point> spoints = new StringQueueImpl<Point>();
    private Treenode head;
    private int size = 0;
    private int level = 0;

    public TwoDTree(){}

    public boolean isEmpty() {return head==null;}

    public int size(){
        return size;
    }

    private Treenode insertR(Treenode h,Point p){
        if (h==null){
            size++;
            level = 0;
            return new Treenode(p);
        }
        if ((p.x() == h.item.x()) &&(p.y() == h.item.y())){
            System.out.println("Point is already on tree!");
            return h;
        }
        level++;
        if (level % 2 == 1){                    //if level is odd check x coordinates
            if (p.x() < h.item.x()){
                h.left = insertR(h.left,p);
            }
            else{
                h.right = insertR(h.right,p);
            }
        }
        else{                                   //if level is even check y coordinates
            if (p.y() < h.item.y()){
                h.left = insertR(h.left,p);
            }
            else{
                h.right = insertR(h.right,p);
            }
        }
        return h;
    }

    public void insert(Point p) {
        head = insertR(head,p);
    }

    private void inorderR(Treenode h){
        if(h !=null){
            inorderR(h.left);               //first left subtree
            System.out.println(h.item);
            inorderR(h.right);              //last right subtree
        }
    }

    public void inorder(){
        inorderR(head);
    }

    private Point searchR(Treenode h,Point p){
        if (h == null){
            level = 0;
            return null;
        }
        if ((p.x() == h.item.x()) &&(p.y() == h.item.y())){
            return h.item;
        }
        level++;
        if (level % 2 == 1){                 //if level is odd check x coordinates
            if (p.x() < h.item.x()){
                return searchR(h.left,p);
            }
            else{
                return searchR(h.right,p);
            }
        }
        else{                               //if level is even check y coordinates
            if (p.y() < h.item.y()){
                return searchR(h.left,p);
            }
            else{
                return searchR(h.right,p);
            }
        }
    }

    public boolean search(Point p) {
        Point p1 = searchR(head,p);
        if (p1 !=null) {
            return ((p1.x() == p.x()) && (p1.y() == p.y()));
        }
        return false;
    }

    private Point nearestNeighborR(Point p,Treenode h) {
        if (h == null){
            return null;
        }
        final Treenode hd = head;
        Rectangle rt,left,right ;
        if (p.squareDistanceTo(h.item) == 0){   //if the point tha given is a point of the tree
            if (!spoints.isEmpty()){
                spoints.get();
                spoints.put(p);
            }
            return p;
        }
        if (hd.item.x() == h.item.x() && hd.item.y() == h.item.y()){  //first call
            rt = new Rectangle();
            rects_stack.push(rt);                           //push current rectangle to rectangles stack
            distances.push(p.squareDistanceTo(h.item));     //push current distance to distances stack
            level = 0;
        }
        boolean trigger = true;
        if (p.squareDistanceTo(h.item) < distances.peek()){         //if current distances is less than previous distance
            distances.pop();                                        //then pop previous and push current distance
            distances.push(p.squareDistanceTo(h.item));             //and keep looking for points that have less distance
        }
        else{                                                       //if not stop the search
            trigger = false;
        }
        level++;
        if(level % 2 == 1){                         //if level is even check x coordinates
            rt = rects_stack.peek();
            left = new Rectangle(rt.xmin(),h.item.x(),rt.ymin(),rt.ymax());     //the rectangle that corresponds to the left subtree of current point
            right = new Rectangle(h.item.x(),rt.xmax(),rt.ymin(),rt.ymax());    //the rectangle that corresponds to the right subtree of current point
        }
        else{                                       //if level is even check x coordinates
            rt = rects_stack.peek();
            left = new Rectangle(rt.xmin(),rt.xmax(),rt.ymin(),h.item.y());     //the rectangle that corresponds to the left subtree of current point
            right = new Rectangle(rt.xmin(),rt.xmax(),h.item.y(),rt.ymax());    //the rectangle that corresponds to the right subtree of current point
        }
        if(trigger) {
            if (distances.peek() > rects_stack.pop().distanceTo(p)) {   //if previous distance is more than previous rectangle distance to requested point
                distances.pop();                                        //pop previous distance
                distances.push(p.squareDistanceTo(h.item));             //push current distance
                if (left.contains(p)) {                                 //if requested point contained in left rectangle
                    rects_stack.push(left);                             //push left rectangle
                    spoints.put(h.item);                                //put current point to the queue
                    nearestNeighborR(p, h.left);                        //continue searching in the left subtree
                } else if (right.contains(p)) {                         //if requested point contained in right rectangle
                    rects_stack.push(right);                            //push right rectangle
                    spoints.put(h.item);                                //put current point to the queue
                    nearestNeighborR(p, h.right);                       //continue searching in the right subtree
                }
            }
        }
        else {
            spoints.put(h.item);
        }
        return spoints.get();                                           //return the least recent point
    }

    public Point nearestNeighbor(Point p){
        return nearestNeighborR(p,head);
    }

    private List<Point> rangeSearchR(Rectangle rect,Treenode h){
        if (h == null){
            return new List<>();
        }
        final Treenode hd = head;
        Rectangle rt,left,right ;
        if (hd.item.x() == h.item.x() && hd.item.y() == h.item.y()){  //first call
            while(!points.isEmpty()){           //clear points list (from previous call)
                points.removeFromBack();
            }
            rt = new Rectangle();
            rects_stack.push(rt);
            level = 0;
        }
        level++;
        if(level % 2 == 1){                         //if level is even check x coordinates
            rt = rects_stack.peek();
            left = new Rectangle(rt.xmin(),h.item.x(),rt.ymin(),rt.ymax());     //the rectangle that corresponds to the left subtree of current point
            right = new Rectangle(h.item.x(),rt.xmax(),rt.ymin(),rt.ymax());    //the rectangle that corresponds to the right subtree of current point
        }
        else{                                       //if level is even check x coordinates
            rt = rects_stack.peek();
            left = new Rectangle(rt.xmin(),rt.xmax(),rt.ymin(),h.item.y());     //the rectangle that corresponds to the left subtree of current point
            right = new Rectangle(rt.xmin(),rt.xmax(),h.item.y(),rt.ymax());    //the rectangle that corresponds to the right subtree of current point
        }
        if (rect.intersects(left)){  //if previous rect intersects with left rectangle
            if (rect.contains(h.item)){         //if current point contained in rectangle
                points.insertAtBack(h.item);
            }
            if (h.left !=null) {
                if (rect.contains(h.left.item)) {       //if rectangle contains next left point
                    rects_stack.push(left);
                    rangeSearchR(rect, h.left);         //continue searching in left subtree
                    rangeSearchR(rect, h.right);
                } else {                                //if rectangle contains next right point
                    rects_stack.push(right);
                    rangeSearchR(rect, h.right);         //continue searching in right subtree
                    rangeSearchR(rect, h.left);
                }
            }
            else if (h.right !=null){                   //the same check for right
                if (rect.contains(h.right.item)) {
                    rects_stack.push(right);
                    rangeSearchR(rect, h.right);
                    rangeSearchR(rect, h.left);
                } else {
                    rects_stack.push(left);
                    rangeSearchR(rect, h.left);
                    rangeSearchR(rect, h.right);
                }
            }
        }
        else if(rect.intersects(right)){             //the same check for right subtree
            if (rect.contains(h.item)){
                points.insertAtBack(h.item);
            }
            if (h.right!=null) {
                if (rect.contains(h.right.item)) {
                    rects_stack.push(right);
                    rangeSearchR(rect, h.right);
                    rangeSearchR(rect, h.left);
                } else {
                    rects_stack.push(left);
                    rangeSearchR(rect, h.left);
                    rangeSearchR(rect, h.right);
                }
            }
            else if (h.left !=null){
                if (rect.contains(h.left.item)) {
                    rects_stack.push(left);
                    rangeSearchR(rect, h.left);
                    rangeSearchR(rect, h.right);
                } else {
                    rects_stack.push(right);
                    rangeSearchR(rect, h.right);
                    rangeSearchR(rect, h.left);
                }
            }
        }
        return points;
    }

    public List<Point> rangeSearch(Rectangle rt){
        return rangeSearchR(rt,head);
    }


    private static TwoDTree read(String name){      //this method read a TwoDTree from a txt input
        try{
            try {
                TwoDTree tree = new TwoDTree();
                FileReader filename = new FileReader(name);
                BufferedReader buff = new BufferedReader(filename);
                int x = 0;
                int y = 0;
                final int size = Integer.parseInt(buff.readLine());
                int n = 0;
                String st;
                String[] str ;
                do {
                    st = buff.readLine();
                    if (st == null) {
                        break;
                    }
                    n++;
                    str = st.split(" ");
                    if (str.length > 2){
                        System.out.println("Error in file!Wrong number of coordinates given." +
                                "\nDue to error/s the program will shut down...");
                        System.exit(0);
                    }
                    try{
                        x = Integer.parseInt(str[0]);
                        y = Integer.parseInt(str[1]);
                    }
                    catch(NumberFormatException e){
                        System.out.println("Error in file!Wrong arguments given as coordinates.\nDue to error/s the program will shut down...");
                        System.exit(0);
                    }
                    if (x < 0 || x > 100 || y < 0 || y > 100) {
                        System.out.println("Error in file!One of the points have invalid coordinates.\nDue to error/s the program will shut down...");
                        System.exit(0);
                    }
                    tree.insert(new Point(x,y));    //passed all the tests
                } while (st != null);
                if (n != size){
                    System.out.println("Error!Expecting " + size + "." + "Given " + n + " points.\nDue to error/s the program will shut down...");
                    System.exit(0);
                }
                return tree;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Error in file!Wrong number of coordinates given." +
                        "\nDue to error/s the program will shut down...");
                System.exit(0);
                return new TwoDTree();
            }
        }
        catch(IOException e){
            System.out.println("Error!File name not found."
                    + "\nDue to error/s the program will shut down...");
            System.exit(0);
            return new TwoDTree();
        }
    }

    private static Point read_point(){      //this method reads a point
        Scanner in = new Scanner(System.in);
        System.out.print("Please give the X coordinate of the point:");
        int x = in.nextInt();
        System.out.print("Please give the Y coordinate of the point:");
        int y = in.nextInt();
        return new Point(x,y);
    }

    private static Rectangle read_rectangle(){      //this method reads a rectangle
        Scanner in = new Scanner(System.in);
        System.out.print("Please give xmin of rectangle:");
        int xmin = in.nextInt();
        System.out.print("Please give xmax of rectangle:");
        int xmax = in.nextInt();
        System.out.print("Please give ymin of rectangle:");
        int ymin = in.nextInt();
        System.out.print("Please give ymax of rectangle:");
        int ymax = in.nextInt();
        return new Rectangle(xmin,xmax,ymin,ymax);
    }

    public static void main(String[] args){
        TwoDTree tree = read(args[0]);
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to 2d-tree simulator!");
        while(true){
            System.out.println("1.Compute the size of the tree.");
            System.out.println("2.Insert a new point.");
            System.out.println("3.Search if a given point exists in the tree.");
            System.out.println("4.Provide a query rectangle.");
            System.out.println("5.Provide a query point.");
            System.out.println("6.Inorder traversal.");
            System.out.println("0.Exit.");
            System.out.print("Please,select an operation:");
            int ans = in.nextInt();
            switch (ans) {
                case 0 -> System.exit(0);
                case 1 -> System.out.println("Tree size:" + tree.size());
                case 2 -> tree.insert(read_point());
                case 3 -> System.out.println(tree.search(read_point()));
                case 4 -> System.out.println(tree.rangeSearch(read_rectangle()));
                case 5 -> System.out.println(tree.nearestNeighbor(read_point()));
                case 6 -> tree.inorder();
                default -> System.out.println("Wrong operation code!Please enter a number between 0-6.");
            }
        }
    }
}
