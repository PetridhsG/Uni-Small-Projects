
# Pizza Delivery System

This project implements a **multi-threaded pizza delivery system** using **POSIX threads (pthreads)**. The system processes customer orders, prepares, bakes, packages, and delivers pizzas while ensuring proper **synchronization** using **mutexes and condition variables**.

## Project Structure

The project consists of the following files:

- **`config.h`**: Defines constants for the simulation.
- **`pizzeria.c`**: Implements the main logic of the pizza delivery system.
- **`test_res.sh`**: A shell script to compile and run the project.
- **`README.md`**: Documentation for the project.

##  How It Works

1. **Initialization**
   - Reads `Ncust` (number of customers) and a **random seed** from the command line.
   - Initializes mutexes, condition variables, and necessary resources.
   - Creates **Ncust threads**, one for each customer.

2. **Order Processing**
   - Each thread represents a customer placing an order.
   - The number of pizzas (plain/special) is determined randomly.
   - The order is either **approved** or **rejected** (based on a probability of failure).
   - If successful, the order proceeds through **preparation, baking, packaging, and delivery**.

3. **Synchronization**
   - **Mutexes** lock critical sections where resources (cooks, ovens, packers, delivery personnel) are allocated.
   - **Condition variables** ensure proper **waiting and signaling** when resources become available.

4. **Order Stages & Timers**
   - **Preparation (`Tprep`)**
   - **Baking (`Tbake`)**
   - **Packaging (`Tpack`)**
   - **Delivery (`Tdellow` - `Tdelhigh`)**
   - Each stage is **simulated with sleep()** and tracked using **timers**.

5. **Statistics**
   - Total revenue, order success/failure counts.
   - Average & maximum **service time** (from order to delivery).
   - Average & maximum **cooling time** (baking to delivery).

## Running the Project

### Prerequisites
- A **Unix-based system** with **pthreads** installed.

### Compilation & Execution
To compile and run the project, use the provided shell script:

```sh
./test_res.sh
