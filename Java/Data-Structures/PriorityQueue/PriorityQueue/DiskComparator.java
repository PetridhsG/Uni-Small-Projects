import java.util.Comparator;

//This class is used to compare Disk based on available capacity
public class DiskComparator implements Comparator<Disk> {	
    @Override
    public int compare(Disk t1, Disk t2) {
        return t1.compareTo(t2);
    }
}
