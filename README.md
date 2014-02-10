Maze-Solver
===========

A robust algorithm that solves almost every maze problem.
##Sample Run
####Maze File:
```
10
x x x x x x x x x x
x p x x x x p p g x
x p p p x x p x x x
x x x p p x p p p x
x p p p x x x x p x
x x x p p p p p p x
x p p p x x x x x x
x x x p x x x p p x
x x x p p p p p x x
x x x x x x x x x x
```
where `x` represents wall, `p` represents available movement space to build paths, and `g` is the goal position
####Complilation
```
javac Maze.java
```
####Running
```
java Maze mazefile.txt 2 2
```
where `2 2`is the starting position
####Output
```
X X X X X X X X X X 
X S X X X X P P G X 
X P P P X X P X X X 
X X X P X X P P P X 
X X X P X X X X P X 
X X X P P P P P P X 
X X X X X X X X X X 
X X X X X X X X X X 
X X X X X X X X X X 
X X X X X X X X X X
```
Therefore, the resulting path would be:
```
  S         P P G   
  P P P     P       
      P     P P P   
      P         P   
      P P P P P P   
```
and the positions of path in order are:
```
(2,2)
(3,2)
(3,3)
(3,4)
(4,4)
(5,4)
(6,4)
(6,5)
(6,6)
(6,7)
(6,8)
(6,9)
(5,9)
(4,9)
(4,8)
(4,7)
(3,7)
(2,7)
(2,8)
(2,9)
```
