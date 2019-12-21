# Game of Life

Implementations of various cellular automatons, including Conway's Game of Life and a some of its variations, powered by JavaFX GUI.

![Screenshot](docs/screenshot.png)

Includes implementations of following [automatons](https://en.wikipedia.org/wiki/Life-like_cellular_automaton):

- [Game of Life (standard)](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
- [Seeds](https://en.wikipedia.org/wiki/Seeds_(cellular_automaton))
- [Inkspot or Life without Death](https://en.wikipedia.org/wiki/Life_without_Death) 
- [Day & Night](https://en.wikipedia.org/wiki/Day_and_Night_(cellular_automaton))

Simulation can be paused, resumed and reset at any time. Step-by-step simulation is also included. The grid can be interacted with using mouse, allowing activation of arbitrary cells, including random grid-fill. Automatons can be switched at any time while the simulation is paused.

## Prerequisites

- Java 11+
- JavaFX SDK 11+
- Maven

The project was tested on Ubuntu 18 and macOS 10.14 Mojave installation.

## Running

Project compilation lifecycle is managed by Maven. To compile the project and execute it, run:

```bash
mvn javafx:run
```

## Development 

### Packaging

```bash
mvn clean javafx:jlink
```

Distributable package will available in `target/dist` directory. Run (macOS) executable with:

```bash
sh target/dist/bin/gol
```

### Further Development

- Dynamic grid and responsive layout (support resizable window)
- Add more automatons
- Various runtime optimizations

## Credits

This project was originally developed as an OOP/Java exercise for NYU Fundamentals of Computing course by Pavel Burakov.
