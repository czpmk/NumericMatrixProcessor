# Numeric Matrix Processor
Project created with the support of JetBrains Academy as an introduction to Kotlin.
- created by: Michał Czapiewski
- email: czapiewskimk@gmail.com
- Github: https://github.com/czpmk
# Purpose
The program returns results of basic operations on matrices: Matrix Addition, Multiplication
by constant and more. Supported input types: Integer and Double.
# Available operations:
1. Add matrices
2. Multiply matrix to a constant
3. Multiply matrices
4. Transpose matrix by:
    - Main diagonal
    - Side diagonal
    - Vertical line
    - Horizontal line
5. Calculate a determinant
6. Inverse matrix
# Limitations
In case of incorrect input (e.g. different sizes of matrices to sum), the program displays
a suitable message to the user. 
List of limitations:
- Addition - matrices must be of the same size
- Matrix multiplication - number of columns of the first matrix has to be equal to the number of rows 
    of the second matrix
- Determinant - input matrix has to be square
- Inverse matrix - input matrix has to be square and its determinant different than zero