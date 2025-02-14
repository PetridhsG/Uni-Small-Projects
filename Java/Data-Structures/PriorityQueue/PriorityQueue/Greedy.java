
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class Greedy {
	
	public static int[] read(String name) {		//returns an array of the folders it read in the archive
		try {
			int folder;
			int n_folders = 0;					
			FileReader filename = new FileReader(name);			
			BufferedReader buff = new BufferedReader(filename);	 
			String st = buff.readLine();
			while (st!=null) {					//calculate the number of folders given
				try {
					folder = Integer.parseInt(st);
				}
				catch( NumberFormatException e) {		//if there is character or an empty line on the archive
					System.out.println("Error!!!A non valid value detected."
							+ "\nDue to error/s the programm will shut down...");
					int[] x = {};
					System.exit(0);
					return x;
				}
				if (folder < 0 || folder > 1000000) {	//if a folder size is not valid
					System.out.println("Error in file!!!The size of a folder is not valid."
							+ "\nDue to error/s the programm will shut down...");
					System.exit(0);
				}
				n_folders++;
				st = buff.readLine();
			}
			buff.close();
			int[] folders = new int[n_folders];
			filename = new FileReader(name);		
			buff = new BufferedReader(filename);	 
			st = buff.readLine();
			for(int i = 0;i<n_folders;i++) {	//save the folders to an array
				folder = Integer.parseInt(st);
				folders[i] = folder;
				st = buff.readLine();
			}
			buff.close();
			return folders;
		}
		catch (IOException e) {														
			System.out.println("Error!!!File name not found."
					+ "\nDue to error/s the programm will shut down...");
			int[] x = {};
			System.exit(0);
			return x;
		}
	}
	
	public static Triple GreedyAlgorithm(int[] folders) {
		Disk.resetKeys();		//reset keys in case this algorithm runs more than one time 
		int sum = 0;			//Sum of folders
		MaxPQ<Disk> pq = new MaxPQ<Disk>(10,new DiskComparator());	//create a MaxPQ that handles Disk instances
		pq.insert(new Disk());
		for(int i =0 ;i<folders.length;i++) {	//puts the disks and at the same time maintains the nature of heap
			sum+=folders[i];					//at every time the Disk with the maximum available space is at the root of heap
			pq.recoverHeap();
			pq.peek().calculate();
			if (pq.peek().getFreeSpace() >= folders[i]) {
				pq.peek().insertFolder(folders[i]);
			}
			else {							//if the current Disk is full create another one
				Disk x = new Disk();			
				x.insertFolder(folders[i]);
				x.calculate();
				pq.insert(x);
			}
		}
		return new Triple(pq,folders,sum);
	}
	
	public static Triple SortedGreedyAlgorithm(int[] folders) {
		Sort.quicksort(folders, 0, folders.length-1);		//sort the folders
		return GreedyAlgorithm(folders);					//apply GreedyAlgorithm
	}
	
	public static void printGreedyAlgorithm(Triple tr) {
		MaxPQ<Disk> pq = tr.getMaxPQ();
		int [] folders = tr.getFolders();
		int sum = tr.getSum();
		System.out.println("Sum of all folders = " + (float) sum/1000000 + "TB");
		System.out.println("Total number of disks used = " + pq.getSize());
		int M = pq.getSize();
		if (folders.length <=100) {		
			pq.recoverHeap();				//recover for the last time the nature of heap
			for(int i =1 ;i <= M;i++) {		//print all the Disks and their contents
				Disk x =  pq.getMax();
				x.calculate();
				System.out.print("id " + x.getKey() + " " + x.getFreeSpace()+":");
				List<Integer> ls = x.getFolders();
				int N = x.getNumberOfFolders();
				for(int j = 0 ; j < N;j++) {
					int y = ls.removeFromFront();
					System.out.print(" " + y );
				}
				System.out.print("\n");
			}
		}
	}
	
	public static void main(String[] args) {
		int[] folders = read(args[0]);
		System.out.println("WITHOUT SORTING");
		printGreedyAlgorithm(GreedyAlgorithm(folders));
		System.out.println("\nWITH SORTING");
		printGreedyAlgorithm(SortedGreedyAlgorithm(folders));		
	}
				
}
