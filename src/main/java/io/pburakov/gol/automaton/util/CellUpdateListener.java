package io.pburakov.gol.automaton.util;

import io.pburakov.gol.automaton.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** A bridge between the cell state and displayed rectangle state. */
public class CellUpdateListener {

  private Rectangle rectangle;

  public CellUpdateListener(Rectangle rectangle) {
    this.rectangle = rectangle;
  }

  /** Updates rectangle on cell state change. */
  public void onUpdate(Cell cell) {
    this.rectangle.setFill(cell.isAlive() ? Color.BLACK : Color.WHITE);
  }
}
