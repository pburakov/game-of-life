package io.pburakov.gol.automaton;

import java.util.Random;

/** In memory representation of a two-dimensional grid of cells */
public class Grid {

  private int width;
  private int height;
  private final Random random;
  private int[] offsetsVertical = {1, 1, 1, 0, -1, -1, -1, 0};
  private int[] offsetsLateral = {-1, 0, 1, 1, 1, 0, -1, -1};

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
    this.random = new Random();
  }

  int getHeight() {
    return height;
  }

  int getWidth() {
    return width;
  }

  public Cell getCellAt(int y, int x) {
    return cells[y][x];
  }

  /** Toggles cell's state */
  public void toggleCellAt(int y, int x) {
    this.getCellAt(y, x).toggle();
  }

  /** Activates all cells in the grid */
  void activateAll() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.getCellAt(y, x).setAlive(true);
      }
    }
  }

  /** Kills all cells in the grid */
  public void reset() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.getCellAt(y, x).kill();
      }
    }
  }

  /** Randomly toggles cells in the grid */
  public void randomize() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (random.nextBoolean()) {
          this.toggleCellAt(y, x);
        }
      }
    }
  }

  /** Returns number of activated adjacent cells */
  int countAliveNeighborsAt(int y, int x) {
    int count = 0;
    for (int k = 0; k < 8; k++) {
      int nextRow = y + offsetsVertical[k];
      int nextCol = x + offsetsLateral[k];
      if (nextRow >= 0 && nextRow < height && nextCol >= 0 && nextCol < width) {
        Cell adjCell = getCellAt(nextRow, nextCol);
        if (adjCell.isAlive()) count++;
      }
    }
    return count;
  }
}
