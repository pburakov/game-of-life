package io.pburakov.gol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  void testToggle() {
    Grid grid = new Grid(3, 3);
    assertFalse(grid.getCellAt(1, 1).isAlive());

    grid.toggleCellAt(1, 1);
    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testCountAllAdjacent() {
    Grid grid = new Grid(3, 3, true);

    assertEquals(8, grid.countAdjacentAlive(1, 1));
  }

  @Test
  void testLivesOn2() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(2, 2);
    grid.toggleCellAt(1, 1);

    grid.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testLivesOn3() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 1);
    grid.toggleCellAt(1, 1);

    grid.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testUnderpopulation() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 1);

    grid.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testUnderpopulationEmpty() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(1, 1);

    grid.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testOverpopulation() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(0, 1);
    grid.toggleCellAt(2, 1);
    grid.toggleCellAt(2, 2);
    grid.toggleCellAt(1, 1);

    grid.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testReproduction() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(0, 0);
    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 2);

    grid.nextStep();

    assertTrue(grid.getCellAt(1, 1).isAlive());
  }

  @Test
  void testStale() {
    Grid grid = new Grid(3, 3);

    grid.toggleCellAt(1, 0);
    grid.toggleCellAt(2, 2);

    grid.nextStep();

    assertFalse(grid.getCellAt(1, 1).isAlive());
  }
}
