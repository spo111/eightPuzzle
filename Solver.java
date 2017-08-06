/*************************************************************************
 * Compilation:  javac Solver.java
 * Execution:    java Solver
 * Dependencies: Board.java
 *
 * Description:  Using two synchronized A* searches (priority queues) to
 *               solve a board and its twin for determining whether a board 
 *               is solvable.
 *               
 * http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *
 *************************************************************************/

import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {
 /*
  * stack stores a sequence of boards from initial board to goal board moves is
  * the number of moves required to solve the initial board
  */
 private Stack<Board> stack;
 private int moves;

 /**
  * helper linked list class that implements method compareTo. the priority of
  * Node is the sum of the Manhattan distances plus the number of moves made so
  * far to get to the search node.
  */
 private class Node implements Comparable<Node> {
  private Node previous;
  private Board board;
  private int priority;
  private int move;

  public Node(Board board, int move) {
   this.board = board;
   this.move = move;
   this.priority = board.manhattan() + move;
  }

  public int compareTo(Node that) {
   return this.priority - that.priority;
  }
 }

 public Solver(Board initial) {
  // find a solution to the initial board (using the A* algorithm)
  stack = new Stack<Board>();

  // if the initial board is the goal board
  if (initial.isGoal()) {
   stack.push(initial);
   return;
  }

  // if the twin board is the goal board
  moves = -1;
  Board twin = initial.twin();
  if (twin.isGoal())
   return;

  // insert the initial board and its neighbors onto a minimum priority queue
  // MinPQ<Node> pq = new MinPQ<Node>();
  PriorityQueue<Node> pq = new PriorityQueue();
  Node oldNd1 = new Node(initial, 0);
  for (Board b : oldNd1.board.neighbors()) {
   Node newNd1 = new Node(b, 1);
   pq.add(newNd1);
   newNd1.previous = oldNd1;
  }

  // insert the twin board and its neighbors onto a different minimum priority
  // queue
  PriorityQueue<Node> pqtwin = new PriorityQueue<Node>();
  Node oldNd2 = new Node(twin, 0);
  for (Board b : oldNd2.board.neighbors()) {
   Node newNd2 = new Node(b, 1);
   pqtwin.add(newNd2);
   newNd2.previous = oldNd2;
  }

  Node searchNd1, searchNd2;
  int move;

  // start search from the board with lowest priority until initial board or its
  // twin board is solved
  // avoid enqueuing a neighbor if its board is the same as the board of the
  // previous search node
  while (true) {
   searchNd1 = pq.poll();
   if (searchNd1.board.isGoal())
    break;
   move = searchNd1.move + 1;
   for (Board b : searchNd1.board.neighbors()) {
    if (!b.equals(searchNd1.previous.board)) {
     Node nd = new Node(b, move);
     nd.previous = searchNd1;
     pq.add(nd);
    }
   }
   searchNd2 = pqtwin.poll();
   if (searchNd2.board.isGoal())
    break;
   move = searchNd2.move + 1;
   for (Board b : searchNd2.board.neighbors()) {
    if (!b.equals(searchNd2.previous.board)) {
     Node nd = new Node(b, move);
     nd.previous = searchNd2;
     pqtwin.add(nd);
    }
   }
  }

  // if the initial board is unsolvable
  if (!searchNd1.board.isGoal()) {
   return;
  }

  // otherwise, reconstruct the solution onto stack
  moves = searchNd1.move;
  while (searchNd1 != null) {
   stack.push(searchNd1.board);
   searchNd1 = searchNd1.previous;
  }
 }

 // is the initial board solvable
 public boolean isSolvable() {
  return moves != -1;
 }

 public int moves() {
  return moves;
 } // min number of moves to solve initial board; -1 if unsolvable

 public Iterable<Board> solution() {
  // sequence of boards in a shortest solution; null if unsolvable
  if (moves == -1)
   return null;
  else
   return stack;
 }

}
