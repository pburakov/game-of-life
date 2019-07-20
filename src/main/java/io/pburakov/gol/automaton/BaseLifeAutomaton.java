package io.pburakov.gol.automaton;

/** Base cellular automaton with common methods and utility functions */
abstract class BaseLifeAutomaton implements LifeAutomaton {

  Grid grid;

  BaseLifeAutomaton(Grid grid) {
    this.grid = grid;
  }

  /** Prepare next generation according to automaton rules */
  abstract void prepareNextGeneration();

  /** Advances automaton to next generation */
  private void commit() {
    for (int y = 0; y < grid.getHeight(); y++) {
      for (int x = 0; x < grid.getWidth(); x++) {
        grid.getCellAt(y, x).update();
      }
    }
  }

  @Override
  public void nextStep() {
    prepareNextGeneration();
    commit();
  }
}
