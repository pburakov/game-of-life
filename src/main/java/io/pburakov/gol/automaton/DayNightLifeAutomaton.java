package io.pburakov.gol.automaton;

public class DayNightLifeAutomaton extends BaseLifeAutomaton {

  private static final String NAME = "Day & Night";

  public DayNightLifeAutomaton(Grid grid) {
    super(grid);
  }

  /**
   * Day and Night is defined by rule notation B3678/S34678, meaning that a dead cell becomes live
   * (is born) if it has 3, 6, 7, or 8 live neighbors, and a live cell remains alive (survives) if
   * it has 3, 4, 6, 7, or 8 live neighbors.
   *
   * <p>The rule is given the name "Day & Night" because its on and off states are symmetric: if all
   * the cells in the Universe are inverted, the future states are the inversions of the future
   * states of the original pattern. A pattern in which the entire universe consists of off cells
   * except for finitely many on cells can equivalently be represented by a pattern in which the
   * whole universe is covered in on cells except for finitely many off cells in congruent
   * locations.
   */
  @Override
  void prepareNextGeneration() {
    for (int y = 0; y < grid.getHeight(); y++) {
      for (int x = 0; x < grid.getWidth(); x++) {
        int aliveCount = grid.countAliveNeighborsAt(y, x);
        final Cell cell = grid.getCellAt(y, x);
        if (cell.isAlive()) {
          if (aliveCount == 3
              || aliveCount == 4
              || aliveCount == 6
              || aliveCount == 7
              || aliveCount == 8) {
            cell.setSurvivesNextGen(true);
          } else {
            cell.setSurvivesNextGen(false);
          }
        } else {
          if (aliveCount == 3 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8) {
            cell.setSurvivesNextGen(true);
          }
        }
      }
    }
  }

  @Override
  public String name() {
    return NAME;
  }
}
