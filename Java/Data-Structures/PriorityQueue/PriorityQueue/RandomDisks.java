 
import java.util.Random;
import java.io.*;   
import java.lang.Math;

public class RandomDisks {
	
	public static void DiskComparison(int number_of_folders,int number_of_files) {
		createFolders(number_of_folders,number_of_files);
		int greedy_sum = 0;
		int sorted_greedy_sum = 0;
		for (int i = 0; i < number_of_files;i++) {
			int[] folders = Greedy.read("data\\test_for_N_"
							+number_of_folders+"\\test" +"_"+ (i+1) + ".txt");
			greedy_sum += Greedy.GreedyAlgorithm(folders).getMaxPQ().getSize();
			sorted_greedy_sum += Greedy.SortedGreedyAlgorithm(folders).getMaxPQ().getSize();
		}
		System.out.println("Greedy_Average_Disks:" + Math.round((float)greedy_sum/number_of_files));
		System.out.println("Sorted_Greedy_Average_Disks:" + Math.round((float)sorted_greedy_sum/number_of_files));
	}
	
	public static void createFolders(int number_of_folders,int number_of_files) {
		try {
			Random random = new Random();
			File f = new File("data\\test_for_N_" + number_of_folders);				//create a subdirectory for each
			f.mkdir();																//value of number_of_folders
			for (int i = 0; i < number_of_files ; i++) {
				FileWriter myWriter = new FileWriter("data\\test_for_N_"			//create a txt file for each				
						+number_of_folders+"\\test" +"_"+ (i+1) + ".txt");			//value of number_of_files
				for (int j = 0; j < number_of_folders ; j++) {
					myWriter.write(Integer.toString(random.nextInt(1000000))+"\n");	//write a random number 
				}																	//from [0,1000000] to txt
				myWriter.close();
			}
	    } catch (IOException e) {
	    	System.out.println("Error!!!Cannot create file."
					+ "\nDue to error/s the programm will shut down...");
	    	System.exit(0);
		}
	}
	
	public static void main(String[] args) {  
		System.out.println("\nFOR N = 100");
		DiskComparison(100,10);
		System.out.println("\nFOR N = 500");
		DiskComparison(500,10); 
		System.out.println("\nFOR N = 1000");
		DiskComparison(1000,10);
	}
}
