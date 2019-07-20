package io.pburakov.gol.automaton;

import io.pburakov.gol.automaton.util.CellUpdateListener;
import java.util.ArrayList;

public class Cell {

  private boolean alive = false;
  private boolean survivesNextGen;
  private ArrayList<CellUpdateListener> listeners = new ArrayList<>();

  void setAlive(boolean alive) {
    this.alive = alive;
    notifyListeners();
  }

  public boolean isAlive() {
    return alive;
  }

  void setSurvivesNextGen(boolean survives) {
    this.survivesNextGen = survives;
  }

  private boolean survivesNextGen() {
    return this.survivesNextGen;
  }

  void kill() {
    setSurvivesNextGen(false);
    setAlive(false);
  }

  public void toggle() {
    setAlive(!isAlive());
  }

  void update() {
    setAlive(survivesNextGen());
  }

  public void attachListener(CellUpdateListener listener) {
    this.listeners.add(listener);
  }

  private void notifyListeners() {
    for (CellUpdateListener listener : listeners) {
      listener.onUpdate(this);
    }
  }
}
