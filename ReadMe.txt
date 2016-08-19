The dv103 package version 2011-03-15
=====================================

This is the dv103 package documentation for final competition PA4. 

Changes since PA3
=================
1. Test Suite is updated:
In order to make sure that the teams taking part in the competition has a generic
implementation of the RMatrix interface, i.e. SquareMatrix, we have added few 
more tests in the test suite. In addition to the matrices of element type "Int" we
now test that implementation also works with any other element type that implements
Ring interface.  See dv103.test.TestSquareMatrix for more the updates.

2. A new implementation of the Ring interface, Real.java" has been added to the dv103 package

Changes since PA2
=================
* We have removed the methods multRecursive() and multStrassen()
  from the RMatrix interface. We want you to put all your focus on a 
  single multiplication implementation mult(). This is the one that 
  will be tested in competition benchmark. We have also made corresponding
  changes in the JUnit test TestSquareMatrix.
  
* We have included a new package dv103.benchmark containing the benchmark
  to be used in the competition. 
  
The Matrix Benchmark
======================
* The matrix benchmark contains three classes (BenchmarkMain, 
  MatrixBenchmark and SquareMatrixGenerator). BenchmarkMain is 
  the program entry point.
  
* To run the benchmark on your matrix implementation you must
  instruct the matrix generator SquareMatrixGenerator to start
  using your matrix implementation (rather than ours). Thus,
  in the file SquareMatrixGenerator.java, replace
      import teachers_default.SquareMatrix;
  with a new import statement pointing to you implementation.
  That's it (hopefully).
  
* In the file BenchmarkMain.java there is a parameter SIZE which
  determines the size of the matrices used in benchmark. SIZE = 100
  will be used in the competition. If SIZE = 100 causes problem when 
  you first run the benchmark, try a lower value like 20 or 50.

Further
========
* Methods toString() and hashCode() should take entire matrix into account, and not
  just few of the entries there. 
  
  
  =====
  Brief Description
Int in multiplication are treated as simple int. 
Strassen algorithm is combined with JAMA algorithm and it works very 25% faster comparing with Naïve multiplication. 
Recursive algorithm used in the third assignment is not implemented at all in this version.
 Actually there are two versions of Strassen one with simple ints and one with E generic. 
 During building of the matrices we define if the matrix is Sparse or is Symetric  with two methods and continue with operation.  
 Also if is one or if is null is defined in the constructor.
After we know what a matrix is we used to have very fast addition of matrices combined with threads. 
When side is bigger than 1000 we divide the matrix and one is done by the main program and one by the threads. 
The thread start first and add the last part of matrix while the main part continue with the first part. If thread is alive we wait until it finish the job and return the result. 
Actually isSparse method we define the opposite of 1-0.988 and if the first row continue more non null value than side*side we don’t continue and return false. If matrix is not sparse then it is not null. But if it is sparse we check also if it is null. Else we check it is one and we this was done to create a fast creation.

The columns in Matrix are treated with singe array and we just know that it is a column and check every method. 
This was done to save memory and time. Also some if checks where we check for the side to get element we check if the position in that array is not null so with negation we save a lot of time. 
Also clone() method in the array is used to clone the Qring Values. We also used many checks if it is an instance of Int in order to construct a faster. 
We don’t create many new Object except when it is necessary. 
The time in my computer is around 10-11 and it works fast. 
It does the multiplication of matrices for side n=4000 for 10 seconds.
I improve a lot on code quality and make fast code and don’t  do sth if it is not nessecary.
Threads also are supposed to be used in toString method when matrices are large. 
We had problems with negative method which take us a lot time .8 for a random matrix. 
We use some final values which used to be faster than normal values.
The program works fast and it passes the test.
  
