package io.pburakov.gol;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid {

  private Cell[][] cells; // Population
  private int width;
  private int height;

  public Grid(int height, int width) {
    this(height, width, false);
  }

  public Grid(int height, int width, boolean activateAll) {
    this.height = height;
    this.width = width;
    this.cells = new Cell[width][height];
    initCells(activateAll);
  }

  private void initCells(boolean activateAll) {
    for (int y = 0; y < this.cells.length; y++) {
      for (int x = 0; x < this.cells[y].length; x++) {
        this.cells[y][x] = new Cell(activateAll);
      }
    }
  }

  public Cell getCellAt(int y, int x) {
    return this.cells[y][x];
  }

  public void toggleCellAt(int y, int x) {
    this.getCellAt(y, x).toggle();
  }

  public void nextStep() {
    prepareNextGeneration();
    commit();
  }

  /**
   * Every cell interacts with its eight neighbours, which are the cells that are horizontally,
   * vertically, or diagonally adjacent. At each step in time, the following transitions occur:
   *
   * <ol>
   *   <li>Any live cell with fewer than two live neighbours dies, as if by underpopulation.
   *   <li>Any live cell with two or three live neighbours lives on to the next generation.
   *   <li>Any live cell with more than three live neighbours dies, as if by overpopulation.
   *   <li>Any dead cell with three live neighbours becomes a live cell, as if by reproduction.
   * </ol>
   */
  private void prepareNextGeneration() {
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        int aliveCount = countAdjacentAlive(y, x);
        final Cell curCell = getCellAt(y, x);
        if (curCell.isAlive()) {
          if (aliveCount == 2 || aliveCount == 3) {
            curCell.setToSurvive();
          } else {
            curCell.setToDie();
          }
        } else if (!curCell.isAlive() && aliveCount == 3) {
          curCell.setToSurvive();
        }
      }
    }
  }

  private void commit() {
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        getCellAt(y, x).update();
      }
    }
  }

  int countAdjacentAlive(int y, int x) {
    final Iterator<Cell> iterator = new AdjacentIterator(y, x);
    int count = 0;
    while (iterator.hasNext()) {
      final Cell adjCell = iterator.next();
      if (adjCell.isAlive()) count++;
    }
    return count;
  }

  /** Allows to iterate over available adjacent cells */
  private class AdjacentIterator implements Iterator<Cell> {

    private final ArrayList<Cell> iterations = new ArrayList<>();
    private int i = 0; // Current iteration

    AdjacentIterator(int y, int x) {
      // available adjacent directions as offsets from y and x
      int[] offsetsVertical = {1, 1, 1, 0, -1, -1, -1, 0};
      int[] offsetsLateral = {-1, 0, 1, 1, 1, 0, -1, -1};
      for (int k = 0; k < 8; k++) {
        int nextRow = y + offsetsVertical[k];
        int nextCol = x + offsetsLateral[k];
        if (nextRow >= 0 && nextRow < height && nextCol >= 0 && nextCol < width) {
          this.iterations.add(getCellAt(nextRow, nextCol));
        }
      }
    }

    @Override
    public boolean hasNext() {
      return this.i < this.iterations.size();
    }

    @Override
    public Cell next() {
      return this.iterations.get(this.i++);
    }
  }
}
