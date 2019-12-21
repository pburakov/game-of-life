package io.pburakov.gol.automaton;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameOfLifeAutomatonTest {

  private Grid grid;

  @BeforeEach
  public void setup() {
    grid = new Grid(3, 3);
  }

  @Test
  public void testLivesOn2() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(2, 2);
    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testLivesOn3() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 1);
    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testUnderpopulation() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testUnderpopulationEmpty() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testOverpopulation() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(0, 1);
    grid.toggleCellAt(2, 1);
    grid.toggleCellAt(2, 2);
    grid.toggleCellAt(1, 1);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testReproduction() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 2);

    lifeAutomaton.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  public void testStale() {
    LifeAutomaton lifeAutomaton = new GameOfLifeAutomaton(grid);

    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 2);

    lifeAutomaton.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }
}
