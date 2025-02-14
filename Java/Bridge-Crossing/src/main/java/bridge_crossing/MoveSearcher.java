import java.util.PriorityQueue;


public class MoveSearcher {

    // this method implements the A* algorithm
    public static State AStar(State initialState){

        PriorityQueue<State> states = new PriorityQueue<>();
        states.add(initialState);   // add the initial state to the search front

        if(initialState.isFinal()){     // if the state is final return the state
            return initialState;
        }

        while(!states.isEmpty()){                // while there is a state in search front
            State state = states.remove();       // remove from the front the state with the least F score
            if (state.isFinal()){                // if the state is final return the state
                return state;
            }
            states.addAll(state.getChildren());  // add all children of the current state in the search front
        }
        return null;
    }

}