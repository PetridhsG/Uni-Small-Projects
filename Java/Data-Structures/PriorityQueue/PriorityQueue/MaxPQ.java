
import java.util.Comparator;


public class MaxPQ<T> {

	private T[] heap;
	private int size;
	private Comparator<T> comparator;		//comparisons are made on the basis of this object
	
	@SuppressWarnings("unchecked")
	public MaxPQ(int N,Comparator<T> comparator){	//MaxPQ constructor
		 this.heap = (T[]) new Object[N];
		 this.size = 0;
		 this.comparator = comparator;
	}
	
	public int getSize() {
		return size;
	}
	
	public T peek() {			//this method returns the root of heap(the maximum value)
        if (size == 0) {
            return null;
        }
        return heap[1];
    }
	
	public void insert(T item) {
		if(size == heap.length -1) {	//if there is not available space grow
			grow();
		}
		heap[++size] = item;
		swim(size);
	}
	
	public T getMax() {				//this method remove and then returns the root of heap(the maximum value)
	    if (size == 0) {
	        return null;
	    }
	    T root = heap[1];
	    heap[1] = heap[size];
	    size--;
	    sink(1);
	    return root;
	}
	
	public void recoverHeap() {		//this method recovers the heap
		swim(size);
		sink(1);
	}
	
	private void swap(int i, int j) {
	    T temp = heap[i];
	    heap[i] = heap[j];
	    heap[j] = temp;
	}
	
	private void swim(int i) {
        if (i == 1) {
            return;
        }
        int parent = i / 2;
        while (i != 1 && comparator.compare(heap[i], heap[parent]) > 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
	}     
	 
    private void sink(int i) {
        int left = 2 * i;
        int right = left + 1;
        if (left > size) {
            return;
        }
        while (left <= size) {
            int max = left;
            if (right <= size) {
                if (comparator.compare(heap[left], heap[right]) < 0) {
                    max = right;
                }
            }
            if (comparator.compare(heap[i], heap[max]) >= 0) {
                return;
            }
            else {
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }
    
	private void grow() {
        T[] newHeap = (T[]) new Object[heap.length + 10];    
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
	
}
