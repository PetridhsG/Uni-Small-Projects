
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner ;


public class Main{

    // this method prompts the user to enter only integer positive values
    public static int integerInput(){
        Scanner in = new Scanner(System.in);
        int x = 0;
        String input = in.nextLine();
        boolean ok = false;
        while (!ok) {
            if (input.matches("^\\d+$")) {      // check if input is integer
                x = Integer.parseInt(input);
                if (x > 0) {
                    ok = true;
                } else {
                    System.out.print("Please enter a positive integer: ");
                    input = in.nextLine();
                }
            } else {
                System.out.print("Please enter a positive integer: ");
                input = in.nextLine();
            }
        }
        return x ;
    }

    // this method create and initialize the initial state of the game
    public static State initializeState(String[] args){

        Scanner in = new Scanner(System.in);
        ArrayList<Integer> family = new ArrayList<>();
        StringBuilder timeString = new StringBuilder();
        for(int i = 0 ; i < args.length ; i++){    // default values given as command line arguments
            family.add(Integer.parseInt(args[i]));
            if (i != args.length - 1){
                timeString.append(args[i]).append(",");
            }
        }

        timeString = new StringBuilder(timeString.substring(0, timeString.length() - 1));

        int totalTime = family.remove(family.size()-1); // the last command line argument
        int N = family.size();               // the size of the family

        System.out.print("Do you want the default settings;\n" + N + " people with the following times:" + timeString +
                " and with total time of " + totalTime  + " minutes.\n" +
                "Answer with Y(yes) or N(no):");
        String answer = in.next();
        while (!(answer.equals("Y") || answer.equals("N"))) {
            System.out.print("Wrong input!Please type Y/N:");
            answer = in.next();
        }

        if (answer.equals("N")) {

            System.out.print("Give me the number of the family members(Positive Integer):");
            N = integerInput();

            family.clear();
            for (int i = 0; i < N; i++) {
                System.out.print("Give the time for family member " + (i + 1) + "(Positive Integer):");
                int t = integerInput();
                family.add(t);
            }
            System.out.print("Give the total time of the game(Positive Integer):");
            totalTime = integerInput();
        }

        State initialState = new State(family);
        State.setTotalTime(totalTime);
        return initialState;
    }

    // this method solves the game and prints the solving path
    public static void solve(State initialState){

        long start = System.currentTimeMillis();
        State terminalState = MoveSearcher.AStar(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null) {
            System.out.println("Could not find a solution.");
        }
        else {
            // print the path from beginning to start.
            System.out.println("Typing the solution...");
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            System.out.print("\n");
            for(State item: path)
            {
                System.out.println("F = " + item.getF() + " G = " + item.getG() + " H = " + item.getH());
                item.print();
            }
            System.out.println("Finished in "+path.size()+" steps!");
            System.out.println("Total Game time:" + terminalState.getG());
            System.out.println("Total Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
        }
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        State initialState = initializeState(args);
        solve(initialState);
        System.out.print("To shutdown the program type any key:");
        in.next();
        in.close();
        System.exit(0);
    }
}
