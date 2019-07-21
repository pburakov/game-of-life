package io.pburakov.gol.automaton;

/** Game of Life B3/S23 cellular state automaton implementation */
public class GameOfLifeAutomaton extends BaseLifeAutomaton {

  public GameOfLifeAutomaton(Grid grid) {
    super(grid);
  }

  /**
   * In B3/S23 Game of Life automaton, every cell interacts with its eight neighbours, which are the
   * cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the
   * following transitions occur:
   *
   * <ol>
   *   <li>Any live cell with fewer than two live neighbours dies, as if by underpopulation.
   *   <li>Any live cell with two or three live neighbours lives on to the next generation.
   *   <li>Any live cell with more than three live neighbours dies, as if by overpopulation.
   *   <li>Any dead cell with three live neighbours becomes a live cell, as if by reproduction.
   * </ol>
   *
   * <p>Game of Life is symbolized as B3/S23, because a cell is Born if it has exactly three
   * neighbours, and a cell survives if it has two or three living neighbours, and dies otherwise.
   */
  void prepareNextGeneration() {
    for (int y = 0; y < grid.getHeight(); y++) {
      for (int x = 0; x < grid.getWidth(); x++) {
        int aliveCount = grid.countAliveNeighborsAt(y, x);
        final Cell cell = grid.getCellAt(y, x);
        if (cell.isAlive()) {
          if (aliveCount == 2 || aliveCount == 3) {
            cell.setSurvivesNextGen(true);
          } else {
            cell.setSurvivesNextGen(false);
          }
        } else if (!cell.isAlive() && aliveCount == 3) {
          cell.setSurvivesNextGen(true);
        }
      }
    }
  }
}
