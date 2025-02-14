

public class Sort {
	
	public static void quicksort(int[] arr,int left,int right ) {
		if(right<=left)return;
		int i = partition(arr,left,right);
		quicksort(arr,left,i-1);
		quicksort(arr,i+1,right);
	}
	
	private static int partition(int arr[], int left, int right) {
		int i = left - 1;
		int j = right;
		int k = arr[right];	//pivot
		for(;;) {
			while(less(arr[++i],k));
			while(less(k,arr[--j])) 
				if (j == left) break;
			if (i >= j) break;
			exchange(arr, i,j);
		}
		exchange(arr, i,right);
		return i;
	}
	
	private static void exchange(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;	
	}
	
	private static boolean less(int x,int y){	//descending order
		return x > y;
	}
	
}
