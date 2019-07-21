package io.pburakov.gol.automaton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GridTest {

  @Test
  void testToggle() {
    Grid grid = new Grid(1, 2);
    assertFalse(grid.getCellAt(0, 0).isAlive());

    grid.toggleCellAt(0, 0);
    assertTrue(grid.getCellAt(0, 0).isAlive());
    assertFalse(grid.getCellAt(0, 1).isAlive());
  }

  @Test
  void testWidthHeight() {
    Grid grid = new Grid(42, 41);
    assertEquals(42, grid.getHeight());
    assertEquals(41, grid.getWidth());
  }

  @Test
  void testActivateAllAndReset() {
    Grid grid = new Grid(1, 2);

    grid.activateAll();
    for (int i = 0; i < 2; i++) {
      assertTrue(grid.getCellAt(0, i).isAlive());
    }

    grid.reset();
    for (int i = 0; i < 2; i++) {
      assertFalse(grid.getCellAt(0, i).isAlive());
    }
  }

  @Test
  void testCountAllAdjacent() {
    Grid grid = new Grid(3, 3);
    grid.activateAll();

    assertEquals(8, grid.countAliveNeighborsAt(1, 1));
  }
}
