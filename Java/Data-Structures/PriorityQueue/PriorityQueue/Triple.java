
public class Triple {
	
	MaxPQ<Disk> pq;
	int[] folders;
	int sum;
	
	Triple(MaxPQ<Disk> pq,int[] folders,int sum){
		this.pq = pq;
		this.folders= folders;
		this.sum = sum;
	}
	
	public MaxPQ<Disk> getMaxPQ() {
		return this.pq ;
	}
	
	public int[] getFolders() {
		return this.folders ;
	}
	
	public int getSum() {
		return this.sum;
	}

}
