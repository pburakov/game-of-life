package io.pburakov.gol;

public class Cell {

  private boolean alive = false;
  private boolean survivesNextGen;

  public Cell() {}

  public Cell(boolean alive) {
    this.alive = alive;
  }

  public boolean isAlive() {
    return alive;
  }

  public void toggle() {
    this.alive = !this.alive;
  }

  public void setToSurvive() {
    this.survivesNextGen = true;
  }

  public void setToDie() {
    this.survivesNextGen = false;
  }

  public void update() {
    this.alive = this.survivesNextGen;
  }
}
