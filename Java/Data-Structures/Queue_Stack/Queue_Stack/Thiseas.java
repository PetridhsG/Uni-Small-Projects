
import java.io.*;

class Point {
	int x,y;
	Point(int x ,int y){
		this.x = x;
		this.y = y;
	}
	int getx() {
		return x;
	}
	int gety() {
		return y;
	}
	public String toString() {
		return  ("(" + x + "," + y + ")"); 
	}
   
}
		 
public class Thiseas {

	public static void main(String[] args) {	
		try {								//validity checks
			int i = 0;
			int errcounter = 0;
			FileReader filename = new FileReader(args[0]);		//open file 
			BufferedReader buff = new BufferedReader(filename);	 
			String[] str = buff.readLine().split(" ");			
			int size[] = new int[2];							//first text line (size of labyrinth)
			try {
				for (i = 0; i < str.length; i++) {
		            size[i] = Integer.parseInt(str[i]);
		        }			
				str = buff.readLine().split(" ");					//second text line (entrance of labyrinth)
				int entrance[] = new int[2];
				for (i = 0; i < str.length; i++) {
		            entrance[i] = Integer.parseInt(str[i]);
		        }
				if (((size[0] < 1) || (size[1] < 1 ))) {							//check if column or line size is less-equals than zero
					System.out.println("Error in file!!!There is a negative or zero value.\nDue to error/s the programm will shut down...");
					System.exit(0);
				}
				if (((entrance[0] < 0) || (entrance[1] < 0 ))) {					//check if column or line size is less than zero
					System.out.println("Error in file!!!There is a negative value.\nDue to error/s the programm will shut down...");
					System.exit(0);
				}
				String[][] lab = new String[size[0]][size[1]];		//determinate the labyrinth table size (lines and columns)
				String[] line = new String[size[0]];				//determinate the labyrinth lines
				String st = buff.readLine();
				i = 0;
				try {													
					while (st != null) {							//create the labyrinth table
						line[i] = st ;
						lab[i] = line[i].split(" ");
						i++;
						st = buff.readLine();
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {							//check if the number of lines is not compatible with the predetermined size
					System.out.println("Error in file!!!Wrong number of lines.");
					errcounter ++;
				}
				buff.close();
				if (i != size[0]) {													//check if the number of lines is not compatible with the predetermined size
					System.out.println("Error in file!!!Wrong number of lines.\nDue to error/s the programm will shut down...");
					System.exit(0);
				}
				for (i = 0; i < line.length;i++) {									//check if the number of columns is not compatible with the predetermined size
					int colsize = 0;
					for (int j = 0 ;j < lab[i].length;j++) {	
							colsize ++;
					}
					if (colsize != size[1]) {										//check if the number of columns is not compatible with the predetermined size
						System.out.println("Error in file!!!Wrong number of columns.");
						errcounter ++;
						break;
					}
				}
				boolean econt = false ;												//check if the number of columns is not compatible with the predetermined size
				boolean elcont = true ;
				int enti = -1 ;
				int entj = -1 ;
				int entcounter = 0;
				for (i = 0 ;i < line.length;i++) {
					for (int j = 0 ;j < lab[i].length;j++) {		
						if(!((lab[i][j].equals("E")) || (lab[i][j].equals("1")) || (lab[i][j].equals("0")))) { //check if characters are none of the above : 0,1,E
							elcont = false;
						}	
						if(lab[i][j].equals("E")) {									//check if the labyrinth contains an entrance
							econt = true;
							enti = i;
							entj = j;
							entcounter++;
						}
					}
				}
				if (econt == false) {												//check if there is a entrance E
					System.out.println("Error in file!!!Cannot find E.");
					errcounter ++;
				}
				if (entcounter > 1 ) {												//check if there is more than one entrance E
					System.out.println("Error in file!!!There is more than one E.");
					errcounter ++;
				}
				if (!((enti == entrance[0]) && (entj == entrance[1]))) {			//check if the coordinates of E match with the predetermined entrance E
					System.out.println("Error in file!!!The coordinates of E does not match.");
					errcounter ++;
				}
				if (elcont == false) {												//check if characters are none of the above : 0,1,E
					System.out.println("Error in file!!!There is a unknown character.");
					errcounter ++;
				}
				if (errcounter > 0 ) {												//if an error occurred then end the program
					System.out.println("Due to error/s the programm will shut down...");
					System.exit(0);
				}																	//end of validity checks
				Stack<Point> stack = new Stack<Point>();		//create a new stack
				stack.push(new Point(entrance[0],entrance[1])); 					//push the entrance to stack
				boolean end = false;
				boolean up,down,left,right,upmove,downmove,leftmove,rightmove;
				while(!end) {
					int avmoves = 0;
					up =down = left = right = true;
					upmove = downmove = leftmove = rightmove = false;
					if(stack.peek().getx() == 0) { 				//look for move up (bounds of array)
						up = false;
					}
					if(stack.peek().getx() == size[0] - 1) { 	//look for move down (bounds of array)
						down = false;
					}
					if(stack.peek().gety() == 0) { 				//look for move left (bounds of array)
						left = false;
					}
					if(stack.peek().gety() == size[1] -1) { 	//look for move right (bounds of array)
						right = false;
					}
					if (up) {									//look for move up (if element is zero)
						if (lab[stack.peek().getx()-1][stack.peek().gety()].equals("0")) {	
							avmoves++;
							upmove = true;
						}
					}
					if (down) {									//look for move down (if element is zero)
						if (lab[stack.peek().getx()+1][stack.peek().gety()].equals("0")) {	
							avmoves++;
							downmove = true;
						}
					}
					if (left) {									//look for move left (if element is zero)
						if (lab[stack.peek().getx()][stack.peek().gety()-1].equals("0")) {	
							avmoves++;
							leftmove = true;
						}
					}
					if (right) {								//look for move right (if element is zero)
						if (lab[stack.peek().getx()][stack.peek().gety()+1].equals("0")) {
							avmoves++;
							rightmove = true;
						}
					}
					lab[stack.peek().getx()][stack.peek().gety()] = "X";		//set element from zero to "X"
					if(avmoves == 0) {											//check if there are available moves 
						stack.pop();
					}
					if (upmove) {															//move up
						stack.push(new Point(stack.peek().getx()-1,stack.peek().gety()));
					}
					else if (leftmove) {													//move left
						stack.push(new Point(stack.peek().getx(),stack.peek().gety()-1));
					}
					else if (downmove) {													//move down
						stack.push(new Point(stack.peek().getx()+1,stack.peek().gety()));
					}
					else if (rightmove) {													//move right
						stack.push(new Point(stack.peek().getx(),stack.peek().gety()+1));
					}
					if (!stack.isEmpty()) {
						if (((stack.peek().getx() == 0 )&& lab[stack.peek().getx()][stack.peek().gety()].equals("0"))||
						((stack.peek().getx() == size[0] - 1 )&& lab[stack.peek().getx()][stack.peek().gety()].equals("0"))||
						((stack.peek().gety() == 0 )&& lab[stack.peek().getx()][stack.peek().gety()].equals("0"))||
						((stack.peek().gety() == size[1] - 1)&& lab[stack.peek().getx()][stack.peek().gety()].equals("0"))){	//check if it reached an exit
							System.out.println("Found an exit at:" + stack.peek() );
							end = true;
						}
					}
					if (stack.isEmpty()) {
						System.out.println("Cannot find an exit!!!");
						end = true;
					}	
				}
			}
			catch (NumberFormatException e) {										//shut the program if wrong number of arguments given
				System.out.println("Error in file!!!No or wrong number of arguments given.\nDue to error/s the programm will shut down...");
				System.exit(0);
			}
		} 
		catch (IOException e) {														//shut the program if file name does not exist 
			System.out.println("Error!!!File name not found.\nDue to error/s the programm will shut down...");
			System.exit(0);
		}											//end of program
		System.out.println("End of program...");
	}		
}

	
