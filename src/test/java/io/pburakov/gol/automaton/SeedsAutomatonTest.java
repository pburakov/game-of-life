package io.pburakov.gol.automaton;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeedsAutomatonTest {
  private Grid grid;

  @BeforeEach
  void setup() {
    grid = new Grid(3, 3);
  }

  @Test
  void testLivesOn2() {
    LifeAutomaton lifeAutomaton = new SeedsAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(2, 2);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testOverpopulation() {
    LifeAutomaton lifeAutomaton = new SeedsAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(2, 2);
    grid.toggleCellAt(1, 0);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testUnderpopulation() {
    LifeAutomaton lifeAutomaton = new SeedsAutomaton(grid);

    grid.toggleCellAt(0, 0);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }
}
