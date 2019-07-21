package io.pburakov.gol.automaton;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InkspotAutomatonTest {

  private Grid grid;

  @BeforeEach
  void setup() {
    grid = new Grid(3, 3);
  }

  @Test
  void testLivesOnEvenWhenAlone() {
    LifeAutomaton lifeAutomaton = new InkspotAutomaton(grid);

    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testInactiveRemains() {
    LifeAutomaton lifeAutomaton = new InkspotAutomaton(grid);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testLivesOn3() {
    LifeAutomaton lifeAutomaton = new InkspotAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(0, 2);
    grid.toggleCellAt(2, 1);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }
}
