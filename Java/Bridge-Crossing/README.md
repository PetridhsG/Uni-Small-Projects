# Bridge Crossing Problem

## Problem Overview

In this problem, a family needs to cross a river at night by walking on a log that connects the two riverbanks. The log can only hold a maximum of two people at a time. Additionally, the family has only one flashlight, which must be carried by one of the individuals walking on the log each time.

Each family member has a specific time it takes them to cross the river, which is constant for each individual, regardless of the direction. For example, the grandmother takes the same amount of time every time she crosses, but the grandfather takes a different time.

When two family members cross together, the time taken is determined by the slower individual.

### Key Constraints:
- A maximum of two people can cross the log at a time.
- One flashlight must be carried by someone crossing at all times.
- The crossing time is determined by the slower individual in a pair.

### Example:

Consider a family with the following members and crossing times:
- **Child 1**: 1 minute
- **Child 2**: 3 minutes
- **Mother**: 6 minutes
- **Father**: 8 minutes
- **Grandfather**: 12 minutes

The goal is to find the optimal sequence in which these family members should cross the river such that the total time for everyone to cross is minimized. The solution should not exceed a total crossing time of 30 minutes.

You can play the game here: [Bridge Crossing Game](https://www.mathgametime.com/games/bridge-crossing).

---

## Problem Solution

This project solves the bridge crossing problem using the **A* (A-star) algorithm**, an informed search algorithm that is commonly used in pathfinding and graph traversal problems. The goal is to determine the optimal sequence of crossings (and who crosses together) to minimize the total time taken.

### A* Algorithm Overview:

1. **State Representation**: 
   - The state of the system represents the positions of all family members (on the left or right side of the river) and the location of the flashlight.

2. **Heuristic Function**: 
   - The heuristic function estimates the cost (time) to reach the goal state from a given state. The heuristic is described in the `State.java` file, where we estimate the remaining crossing time based on the number of people left to cross and their crossing times.

3. **Search Process**:
   - We explore possible crossing sequences by simulating each possible crossing scenario, updating the state, and applying the heuristic to guide the search towards the optimal solution.

4. **Goal**: 
   - The goal is to find the sequence of crossings that allows all family members to cross the river within the given time limit (30 minutes in this case).

---

## Running the Project

To compile and run the project, follow these steps:

1. Clone the repository to your local machine.
2. Run the `compile_and_run.sh` script to compile and execute the program.

   ```bash
   ./compile_and_run.sh
