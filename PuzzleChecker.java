/*************************************************************************
 *  Compilation:  javac PuzzleChecker.java
 *  Execution:    java PuzzleChecker filename.txt
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from a filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state. The program also reports the boards of each step.
 *  
 *  http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *
 *************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class PuzzleChecker {

 public static void main(String[] args) {
  // read in the board specified in the filename
  Scanner sc = null;
  try {
   sc = new Scanner(new File(args[0]));
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  }
  int N = sc.nextInt();
  int[][] tiles = new int[N][N];
  for (int i = 0; i < N; i++) {
   for (int j = 0; j < N; j++) {
    tiles[i][j] = sc.nextInt();
   }
  }

  // solve the slider puzzle
  Board initial = new Board(tiles);
  Solver solver = new Solver(initial);

  // output the number of steps
  System.out.println(args[0] + ": " + solver.moves());

  // output the steps
  Stack<Board> stack = new Stack<Board>();
  Iterator<Board> it = solver.solution().iterator();
  while (it.hasNext()) {
   stack.push(it.next());
  }
  while (!stack.isEmpty()) {
   System.out.println(stack.pop());
  }
 }

}
