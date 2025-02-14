

public class Disk implements Comparable<Disk> {
	
	private static int key = 0;
	private int m_key = key; 				//each Disk  has an exclusive key
	private final int capacity = 1000000;		//each Disk has 1TB storage space
	private int available_capacity = capacity;
	private List<Integer> folders = new List<Integer>();	//each Disk has a list that keeps the folders size
	
	Disk(){
		key++;		//exclusive key
	}
	
	public static void resetKeys() {	
		key = 0;			
	}
	
	public int getKey() {
		return m_key;
	}
	
	public void insertFolder(int size) {
		folders.insertAtFront(size);
	}
	
	public void calculate() {				//this method calculate the available capacity on disk
		available_capacity = capacity;
		List<Integer> fl = folders.copy();
		int len = fl.getLength();
		for(int i = 0 ; i < len ;i++) {
			int x = fl.removeFromFront();
			if (available_capacity >=x) {
				available_capacity  -= x ;
			}
			else {
				System.out.println("The Disk " + m_key + " is full!!!");
				break;
			}
		}
	}
	
	public int getFreeSpace() {
		return available_capacity ;
	}

	@Override	
	public int compareTo(Disk o) {			//this method compare Disks based on the available capacity 
		calculate();
		o.calculate();
		if (available_capacity> o.getFreeSpace()) {
			return 1;
		}
		else if (available_capacity < o.getFreeSpace()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public int getNumberOfFolders() {
		return folders.getLength();
	}
	
	public List<Integer> getFolders() {
		return folders;
	}
	
}