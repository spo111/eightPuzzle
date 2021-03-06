/*************************************************************************
 * Compilation:  javac Board.java
 * Execution:    java Board
 * Dependencies: 
 *
 * Description:  N * N array containing the integers between 0 and n2 − 1, 
 *               where 0 represents the blank square.
 *               
 * http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *
 *************************************************************************/

import java.util.Stack;

public class Board {
 private int[] board;
 private int n;
 private int x;
 private int y;

 public Board(int[][] blocks) {
  n = blocks.length;
  board = new int[n * n];
  for (int i = 0; i < n; i++) {
   for (int j = 0; j < n; j++) {
    board[i * n + j] = blocks[i][j];
    if (blocks[i][j] == 0) {
     x = i;
     y = j;
    }
   }
  }
 }

 public int dimension() {
  return n;
 } // board dimension N

 public int hamming() {
  // number of blocks out of place
  int count = 0;
  for (int i = 0; i < n * n - 1; i++) {
   if (board[i] != i + 1)
    count++;
  }
  return count;
 }

 public int manhattan() {
  // sum of Manhattan distances between blocks and goal
  int count = 0;
  for (int i = 0; i < n * n; i++) {
   int j = board[i] - 1;
   count += Math.abs(j / n - i / n) + Math.abs(j % n - i % n);
  }
  return count;
 }

 public boolean isGoal() {
  return this.hamming() == 0;
 }

 public Board twin() {
  // a board that is obtained by exchanging two adjacent blocks in the same row
  // exactly one of a board and its twin are solvable
  // a board may have several possible twins and only one of them is returned
  if (x == 0)
   return exch(copy(), 1, 0, 1, 1);
  else
   return exch(copy(), 0, 0, 0, 1);
 }

 public boolean equals(Object y) {
  // does this board equal y?

  if (this == y)
   return true;
  if (y == null)
   return false;
  if (this.getClass() != y.getClass())
   return false;
  Board that = (Board) y;
  if (this.n != that.n)
   return false;
  for (int i = 0; i < n * n; i++) {
   if (this.board[i] != that.board[i])
    return false;
  }
  return true;
 }

 public Iterable<Board> neighbors() {
  // all neighboring boards
  Stack<Board> stack = new Stack<Board>();
  Board b;
  if (x > 0) {
   b = exch(copy(), x, y, x - 1, y);
   stack.push(b);
  }
  if (x < n - 1) {
   b = exch(copy(), x, y, x + 1, y);
   stack.push(b);
  }
  if (y > 0) {
   b = exch(copy(), x, y, x, y - 1);
   stack.push(b);
  }
  if (y < n - 1) {
   b = exch(copy(), x, y, x, y + 1);
   stack.push(b);
  }
  return stack;
 }

 private int[][] copy() {
  // return a copy of array of blocks of board
  int[][] temp = new int[n][n];
  for (int i = 0; i < n; i++) {
   for (int j = 0; j < n; j++) {
    temp[i][j] = board[i * n + j];
   }
  }
  return temp;
 }

 private Board exch(int[][] temp, int i1, int j1, int i2, int j2) {
  // exchange block at (i1, j1) with block at (i2, j2) and return a new board
  int k = temp[i1][j1];
  temp[i1][j1] = temp[i2][j2];
  temp[i2][j2] = k;
  return new Board(temp);
 }

 public String toString() {
  // string representation of this board (in the output format specificed below)
  StringBuilder sb = new StringBuilder();
  sb.append(n + "\n");
  for (int i = 0; i < n; i++) {
   for (int j = 0; j < n; j++) {
    sb.append(String.format(" %2d", board[i * n + j]));
   }
   sb.append("\n");
  }
  return sb.toString();
 }
}
