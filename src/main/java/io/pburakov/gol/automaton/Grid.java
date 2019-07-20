package io.pburakov.gol.automaton;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid {

  private int width;
  private int height;

  private Cell[][] cells;

  public Grid(int height, int width) {
    this.width = width;
    this.height = height;
    this.cells = new Cell[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        cells[y][x] = new Cell();
      }
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public Cell getCellAt(int y, int x) {
    return cells[y][x];
  }

  public void toggleCellAt(int y, int x) {
    this.getCellAt(y, x).toggle();
  }

  public void activateAll() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.getCellAt(y, x).setAlive(true);
      }
    }
  }

  public void reset() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.getCellAt(y, x).kill();
      }
    }
  }

  public int countAdjacentAliveAt(int y, int x) {
    final Iterator<Cell> iterator = new AdjacentIterator(y, x);
    int count = 0;
    while (iterator.hasNext()) {
      final Cell adjCell = iterator.next();
      if (adjCell.isAlive()) count++;
    }
    return count;
  }

  /** Allows to iterate over available adjacent cells */
  class AdjacentIterator implements Iterator<Cell> {

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
      return this.i < iterations.size();
    }

    @Override
    public Cell next() {
      return iterations.get(i++);
    }
  }
}
