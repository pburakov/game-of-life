package io.pburakov.gol.automaton;

/**
 * Conway's Game of Life cellular automaton class A cellular automaton (CA) is Life-like (in the
 * sense of being similar to Conway's Game of Life) if it meets the following criteria:
 *
 * <ol>
 *   <li>The array of cells of the automaton has two dimensions.
 *   <li>Each cell of the automaton has two states (conventionally referred to as "alive" and
 *       "dead", or alternatively "on" and "off")
 *   <li>The neighborhood of each cell is the Moore neighborhood; it consists of the eight adjacent
 *       cells to the one under consideration and (possibly) the cell itself.
 *   <li>In each time step of the automaton, the new state of a cell can be expressed as a function
 *       of the number of adjacent cells that are in the alive state and of the cell's own state;
 *       that is, the rule is outer totalistic (sometimes called semitotalistic).
 * </ol>
 */
public interface LifeAutomaton {

  String name();

  void nextStep();

}
