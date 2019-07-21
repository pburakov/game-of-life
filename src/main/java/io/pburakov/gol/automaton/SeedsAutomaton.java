package io.pburakov.gol.automaton;

/** Seeds B2/S cellular state automaton implementation */
public class SeedsAutomaton extends BaseLifeAutomaton {

  private static final String NAME = "Seeds";

  public SeedsAutomaton(Grid grid) {
    super(grid);
  }

  /**
   * In Seeds cellular automaton, in each time step, a cell turns on or is "born" if it was off or
   * "dead" but had exactly two neighbors that were on; all other cells turn off. Thus, in the
   * notation describing the family of cellular automata containing Life, it is described by the
   * rule B2/S.
   */
  @Override
  void prepareNextGeneration() {
    for (int y = 0; y < grid.getHeight(); y++) {
      for (int x = 0; x < grid.getWidth(); x++) {
        int aliveCount = grid.countAliveNeighborsAt(y, x);
        final Cell cell = grid.getCellAt(y, x);
        if (!cell.isAlive() && aliveCount == 2) {
          cell.setSurvivesNextGen(true);
        } else {
          cell.setSurvivesNextGen(false);
        }
      }
    }
  }

  @Override
  public String name() {
    return NAME;
  }
}
