# Postfix Expression Evaluation in MIPS32 Assembly  

## Overview  
This project implements the evaluation of postfix expressions (Reverse Polish Notation - RPN) using MIPS32 Assembly. The program reads a postfix expression, processes it using a stack, and computes the final result.  

## Features  
- Reads a postfix expression from user input  
- Utilizes the MIPS stack (`$sp`) for expression evaluation  
- Supports basic arithmetic operations:  
  - Addition (`+`)  
  - Subtraction (`-`)  
  - Multiplication (`*`)  
  - Division (`/`)  
- Validates expressions to ensure correctness  

## Implementation Details  
- Operands (unsigned integers) are pushed onto the stack  
- When an operator is encountered, two operands are popped, the operation is performed, and the result is pushed back onto the stack  
- At the end of evaluation, the stack should contain a single value: the final result  

## Input Format  
- Numbers and operators are separated by at least one space  
- The expression ends with an equals sign (`=`)  

## Postfix Expression Evaluation  

### Postfix expression:  
```
5 1 2 + 4 * + 3 - =
```

### Step-by-step evaluation:  

#### Evaluate `1 2 +`  
```
1 + 2 = 3
```
→ Expression now:  
```
5 3 4 * + 3 -
```

#### Evaluate `3 4 *`  
```
3 × 4 = 12
```
→ Expression now:  
```
5 12 + 3 -
```

#### Evaluate `5 12 +`  
```
5 + 12 = 17
```
→ Expression now:  
```
17 3 -
```

#### Evaluate `17 3 -`  
```
17 − 3 = 14
```

### Final Result:  
```
14
```


## Setup & Execution  
1. Install a MIPS simulator (e.g., [SPIM](http://spimsimulator.sourceforge.net/))  
2. Load the assembly file (`.s`) into the simulator  
3. Run the program and enter a valid postfix expression  
