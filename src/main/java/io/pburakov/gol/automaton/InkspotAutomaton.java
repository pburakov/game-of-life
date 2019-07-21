package io.pburakov.gol.automaton;

/** Inkspot "Life Without Death" B3/S012345678 cellular state automaton implementation */
public class InkspotAutomaton extends BaseLifeAutomaton {

  public InkspotAutomaton(Grid grid) {
    super(grid);
  }

  /**
   * In this cellular automaton, an initial seed pattern grows according to the same rule as in
   * Conway's Game of Life; however, unlike Life, patterns never shrink.
   *
   * <p>Every cell that was alive in the previous pattern remains alive, every dead cell that has
   * exactly 3 live neighbors becomes alive itself, and every other dead cell remains dead. That is,
   * in the notation describing Life-like cellular automaton rules, it is rule B3/S012345678: a live
   * cell is born when there are 3 live neighbors, and a live cell survives with any number of
   * neighbors.
   */
  @Override
  void prepareNextGeneration() {
    for (int y = 0; y < grid.getHeight(); y++) {
      for (int x = 0; x < grid.getWidth(); x++) {
        int aliveCount = grid.countAdjacentAliveAt(y, x);
        final Cell cell = grid.getCellAt(y, x);
        if (cell.isAlive()) {
          cell.setSurvivesNextGen(true);
        } else if (!cell.isAlive() && aliveCount == 3) {
          cell.setSurvivesNextGen(true);
        }
      }
    }
  }
}
