
## Rectangle
contains: The coordinates of the given point are checked against all dimensions of the parallelogram. The x-coordinate of the point is checked with the two vertical sides of the parallelogram, meaning if x <= max and x >= xmin. Similarly, the y-coordinate is checked with the two horizontal sides, meaning if y <= ymax and y >= ymin. Finally, if all four of these conditions are met, it means that the given point is inside the parallelogram.

intersects: The opposite condition is checked, meaning if the parallelograms do not intersect. This occurs when one of the following conditions is met: this.xmin >= that.xmax (it is to the right), this.xmax <= that.xmin (it is to the left), this.ymin >= that.ymax (it is above), this.ymax <= that.ymin (it is below). It also checks the case when they share a common side.

distanceTo: Initially, it finds the maximum distance from the point to one side of the parallelogram (first the x-distance and then the y-distance) and then returns the distance based on the formula for the distance between two points in space.

## TwoDTree
nearestNeighbor: The nearestNeighbor method calls nearestNeighborR with arguments of the given point and the root of the tree. In nearestNeighborR, it first checks if the root has a null value, and if it does, it returns an empty list. Then, if it's the first call (recursion hasn't started yet), it clears the list of points (it might contain some elements from a previous call), sets the level at which the tree is being examined to 0, and puts the parallelogram corresponding to the root (which is always [0,100]x[0,100]) on the stack. If it's at a level with an odd number, it checks the x variable to determine which sub-trees to explore; otherwise, it checks the y variable. Then, it creates the parallelograms corresponding to the left and right of the root and starts the search. If the current distance is smaller than the previous one, and the previous distance is greater than the distance of the current parallelogram to the given point, it continues as follows: it removes the old distance and adds the new one to the stack, then checks if the point exists in either of the two parallelograms. If it does, it adds it to the queue and continues the recursion. Finally, at the end of each recursion, it returns the oldest point found through the queue, ensuring that when the recursion finishes, it returns the closest point found.

rangeSearch: Similar to nearestNeighbor, it also calls its recursive version with the given point and the root. The same checks are made to determine if the root has a null value, if it's the first call, and at which level it is. Then, it checks if the current parallelogram intersects with the left parallelogram of the current node, and if so, it checks if the point is contained in the given parallelogram. If it is, it adds the point to the list. Now, if the left sub-tree of the current node is not null and the point corresponding to that node is in the parallelogram, it adds the left parallelogram to the stack and continues the recursion. The same checks are made for the right sub-tree. Finally, it returns the list of points found to be contained in the parallelogram.

## Run The Program
To compile the files, use ```javac *.java```. 

To run Thiseas.java, use ```java TwoDTree sample.txt```

The lab.txt represents the nodes of the TwoDTree.
First line is size of the tree and the other ones represent each Point of the tree.