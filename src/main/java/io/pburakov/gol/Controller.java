package io.pburakov.gol;

import io.pburakov.gol.automaton.Cell;
import io.pburakov.gol.automaton.GameOfLifeAutomaton;
import io.pburakov.gol.automaton.Grid;
import io.pburakov.gol.automaton.InkspotAutomaton;
import io.pburakov.gol.automaton.LifeAutomaton;
import io.pburakov.gol.automaton.SeedsAutomaton;
import io.pburakov.gol.automaton.util.CellUpdateListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class Controller implements Initializable {

  private static final int GRID_WIDTH = 96;
  private static final int GRID_HEIGHT = 64;
  private static final int RECTANGLE_SIZE = 8;
  private static final double RECTANGLE_STROKE_WIDTH = 0.1;
  private static final Duration FRAME_DURATION = Duration.millis(1000 / 15); // 15 FPS

  private final Timeline timeline; // animation controller

  private Grid grid; // In-memory population representation
  private LifeAutomaton selectedAutomaton; // Selected automaton
  private List<LifeAutomaton> automatons = new ArrayList<>();

  @FXML private Button randomizeButton;
  @FXML private Button playButton;
  @FXML private Button pauseButton;
  @FXML private ComboBox<String> automatonSelector;
  @FXML private TilePane tilePane; // main grid display pane

  public Controller() {
    this.timeline = new Timeline(new KeyFrame(FRAME_DURATION, e -> selectedAutomaton.nextStep()));
    this.timeline.setCycleCount(Animation.INDEFINITE);

    this.grid = new Grid(GRID_HEIGHT, GRID_WIDTH);

    automatons.add(new GameOfLifeAutomaton(this.grid));
    automatons.add(new SeedsAutomaton(this.grid));
    automatons.add(new InkspotAutomaton(this.grid));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resizeTilePane(tilePane);

    // populate dropdown with selection of automatons
    automatons.forEach(automaton -> automatonSelector.getItems().add(automaton.name()));
    selectedAutomaton = automatons.get(0); // start with default
    automatonSelector.getSelectionModel().select(0);

    // fill tile pane with array of cell rectangles
    for (int i = 0; i < GRID_HEIGHT; i++) {
      for (int k = 0; k < GRID_WIDTH; k++) {
        final Rectangle rectangle = initRectangle(grid.getCellAt(i, k));
        tilePane.getChildren().add(rectangle);
      }
    }

    updateButtons();
  }

  @FXML
  private void onReset() {
    timeline.stop();
    grid.reset();

    updateButtons();
  }

  @FXML
  private void onRandomize() {
    grid.randomize();
  }

  @FXML
  private void onPlay() {
    timeline.play();

    updateButtons();
  }

  @FXML
  private void onPause() {
    timeline.stop();

    updateButtons();
  }

  @FXML
  private void onStep() {
    timeline.stop();
    selectedAutomaton.nextStep();

    updateButtons();
  }

  @FXML
  private void onAutomatonUpdate() {
    selectedAutomaton = automatons.get(automatonSelector.getSelectionModel().getSelectedIndex());
  }

  @FXML
  private void onExit() {
    System.exit(0);
  }

  /** Ensures tile pane can accommodate all cells on the grid */
  private static void resizeTilePane(TilePane tilePane) {
    tilePane.setMinHeight(GRID_HEIGHT * (tilePane.getVgap() + RECTANGLE_SIZE));
    tilePane.setMinWidth(GRID_WIDTH * (tilePane.getHgap() + RECTANGLE_SIZE));
    tilePane.setPrefColumns(GRID_WIDTH);
    tilePane.setPrefRows(GRID_HEIGHT);
  }

  /** Configures rectangle display properties and attaches state listeners */
  private static Rectangle initRectangle(Cell cell) {
    Rectangle rectangle = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE);

    // configure display properties
    rectangle.setFill(Color.WHITE);
    rectangle.setStroke(Color.SILVER);
    rectangle.setStrokeWidth(RECTANGLE_STROKE_WIDTH);
    rectangle.setStrokeType(StrokeType.INSIDE);

    // update rectangle state on cell state update
    cell.attachListener(new CellUpdateListener(rectangle));
    attachMouseEvents(rectangle, cell);

    return rectangle;
  }

  /** Updates cell state on mouse click */
  private static void attachMouseEvents(Rectangle rectangle, Cell cell) {
    rectangle.setOnMousePressed(
        event -> {
          cell.toggle();
          rectangle.setFill(cell.isAlive() ? Color.BLACK : Color.WHITE);
        });
  }

  /** Enables / disables buttons depending on current state of the machine */
  private void updateButtons() {
    if (timeline.getStatus() == Animation.Status.RUNNING) {
      playButton.setDisable(true);
      pauseButton.setDisable(false);
      randomizeButton.setDisable(true);
      automatonSelector.setDisable(true);
    } else {
      playButton.setDisable(false);
      pauseButton.setDisable(true);
      randomizeButton.setDisable(false);
      automatonSelector.setDisable(false);
    }
  }
}
