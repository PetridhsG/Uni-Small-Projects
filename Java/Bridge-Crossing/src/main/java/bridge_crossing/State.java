
import java.util.ArrayList;
import java.util.Collections;

public class State implements Comparable<State>
{
	private int f, h, g;	      // f = g + h , where h is the heuristic function and
							      // g the actual cost from the root to the current node
	private State father ;
	private static int totalTime; // maximum total time of the game
	private boolean lamp;		  // when the lamp is on the right side,lamp = true
	private final ArrayList<Integer> rightPersons;	    // family members that are on the right side
	private final ArrayList<Integer> leftPersons;		// family members that are on the left side
	
	// default constructor
	public State(){
		this.f = 0;
		this.h = 0;
		this.g = 0;
		this.father = null;
		this.lamp = true;
		this.rightPersons = new ArrayList<>();
		this.leftPersons = new ArrayList<>();
	}

	// this constructor is used to initialize the root
	public State(ArrayList<Integer> persons){
		this.f = 0;
		this.h = 0;
		this.g = 0;
		this.father = null;
		this.lamp = true;
		this.rightPersons = new ArrayList<>();
		this.rightPersons.addAll(persons);  	// in the beginning all the family members will be on the right side
		this.leftPersons = new ArrayList<>();
		this.evaluate();		// evaluate the root
	}
	
	// copy constructor
	public State(State s){
		this.rightPersons = new ArrayList<>();
		this.leftPersons = new ArrayList<>();
		this.f = s.f ;
		this.h = s.h ;
		this.g = s.g ;
		this.lamp = s.lamp;
		this.father = s.father;
		this.rightPersons.addAll(s.rightPersons);
		this.leftPersons.addAll(s.leftPersons);
	}
	
	public int getF(){
		return this.f;
	}

	public void setF(int f){
		this.f = f;
	}

	public int getG(){
		return this.g;
	}

	public void setG(int g){
		this.g = g;
	}

	public int getH(){
		return this.h;
	}

	public void setH(int h){
		this.h = h;
	}

	public boolean getLamp(){
		return lamp;
	}

	public void setLamp(boolean lamp){
		this.lamp = lamp;
	}

	public State getFather(){
		return this.father;
	}

	public void setFather(State father){
		this.father = father;
	}

	public static void setTotalTime(int totalTime){
		State.totalTime = totalTime;
	}

	public int getTotalTime(){
		return State.totalTime ;
	}

	public ArrayList<Integer> getRightPersons(){
		return this.rightPersons;
	}

	public ArrayList<Integer> getLeftPersons(){
		return this.leftPersons;
	}

	public void setRightPersons(ArrayList<Integer> rightPersons){
		this.rightPersons.addAll(rightPersons);
	}

	public void setLeftPersons(ArrayList<Integer> leftPersons){
		this.leftPersons.addAll(leftPersons);
	}

	public void evaluate() {
		// sort by descending order and then choose
		this.rightPersons.sort(Collections.reverseOrder());
		this.h = 0;
		for (int i = 0; i < this.rightPersons.size(); i += 3) {		// we assume that three persons can cross the bridge
			this.h += rightPersons.get(i);							// together,so the heuristic function calculate
		}															// the maximum time to pass for every three persons

		State father = this.getFather();

		if (this.father != null) {	// if this is not the root
			this.g = father.getG();
			if (!this.lamp) {    // if the lamp is left,that means we are heading right, so the cost is the cost
				this.g += Collections.min(father.leftPersons);  // of the person with the minimum crossing cost
			}
			else {    			// if the lamp is right,that means we are heading left,so the cost is the cost of
				int max = -1;	// the cost of the person in the pair we choose with the maximum crossing cost
				for (Integer i : father.rightPersons) {
					if (!this.rightPersons.contains(i)) {
						max = Math.max(i, max);
					}
				}
				this.g += max;
			}
		}
		else{
			this.g = 0;
		}
		this.f = this.g + this.h;	// calculate the F score
	}

	public void moveLeft(ArrayList<State> children){

		if(rightPersons.isEmpty()){ // if there is not a person on the right side
			return ;
		}

		for(int i=0 ; i < this.rightPersons.size();i++){		// create every possible pair between
			for(int j =i+1;j < this.rightPersons.size();j++){	// the persons on the right side
				State temp = new State(this);	// create a state same with its father
				temp.setFather(this);				// set the father of this state

				temp.leftPersons.add(rightPersons.get(i));	// move the pair from the right to the left side
				temp.leftPersons.add(rightPersons.get(j));	// add the pair to the left side

				temp.rightPersons.remove(j);	// remove the pair from the right side
				temp.rightPersons.remove(i);

				temp.evaluate();			// evaluate the score
				temp.lamp = !this.lamp;		// change the value of lamp

				if (temp.getG() > this.getTotalTime()){
					break;				// if the cost exceeded the maximum cost,it means that this state
				}				// can't be included at a possible solution, so don't include it to the children
				else{
					children.add(temp);
				}
			}
		}
	}

	public void moveRight(ArrayList<State> children){

		if(leftPersons.isEmpty()){	// if there is not a person on the left side
			return;
		}

		State temp = new State(this);	// create a state same with its father
		temp.setFather(this);				// set the father of this state

		int min = leftPersons.indexOf(Collections.min(leftPersons));	// get the index of the person with the minimum crossing time
		temp.rightPersons.add(leftPersons.get(min));	// move this person from the left to the right side
		temp.leftPersons.remove(min);					// remove the person from the left side

		temp.evaluate();				// evaluate the score
		temp.lamp = !this.lamp;			// change the value of lamp
		if (temp.getG() > this.getTotalTime()){		// if the cost exceeded the maximum cost,it means that this state
			return ;					// can't be included at a possible solution, so don't include it to the children
		}
		children.add(temp);		// add the state to the children

	}

	public ArrayList<State> getChildren(){

		ArrayList<State> children = new ArrayList<>();

		if(this.lamp){					// if the lamp is right,move left
			this.moveLeft(children);
		}
		else{							// if the lamp is left,move right
			this.moveRight(children);
		}
		return children;
	}
	
	public boolean isFinal() {
		return this.rightPersons.isEmpty();    // if rightPersons is empty that means that
	}										   // all person have crossed the bridge

	// override this for proper hash set comparisons.
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass() ){
			return false;
		}

		State state = (State) obj ;

		if (this.lamp != state.lamp){
			return false;
		}
		if (this.f != ((State) obj).getF() || this.g != ((State) obj).getG() || this.h != ((State) obj).getH()){
			return false;
		}
		if(!this.rightPersons.equals(state.rightPersons)){
			return false;
		}
		if(!this.leftPersons.equals(state.leftPersons)){
			return false;
		}
		if (this.father == null){
			return state.father == null ;
		}
		return this.father.equals(state.getFather());

	}

	// override this for proper hash set comparisons.
	@Override
    public int hashCode(){
		int hash = this.rightPersons.hashCode();
		hash += this.leftPersons.hashCode();
		hash += this.f ;
		return hash;
	}

	@Override
    public int compareTo(State s){
        return Integer.compare(this.f, s.getF()); // compare based on the heuristic score.
    }

	public void print(){

		Collections.sort(this.rightPersons);
		Collections.sort(this.leftPersons);
		int N = leftPersons.size() + rightPersons.size();
		for(int i = 0; i < N ; i++){
			System.out.print("----");
		}
		System.out.println("\n");

		for(Integer i:leftPersons){
			if (i != null)
				System.out.print(i + " ");
		}
		System.out.print(" ---- ");
		for(Integer i: rightPersons){
			if (i != null)
				System.out.print(i + " ");
		}
		System.out.println("\n");
		for(int i = 0; i < N ; i++){
			System.out.print("----");
		}
		System.out.print("\n");
	}
}