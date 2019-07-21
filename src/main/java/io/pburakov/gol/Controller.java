package io.pburakov.gol;

import io.pburakov.gol.automaton.Cell;
import io.pburakov.gol.automaton.GameOfLifeAutomaton;
import io.pburakov.gol.automaton.Grid;
import io.pburakov.gol.automaton.InkspotAutomaton;
import io.pburakov.gol.automaton.LifeAutomaton;
import io.pburakov.gol.automaton.SeedsAutomaton;
import io.pburakov.gol.automaton.util.CellUpdateListener;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class Controller implements Initializable {

  private static final int GRID_WIDTH = 96;
  private static final int GRID_HEIGHT = 64;
  private static final int RECTANGLE_SIZE = 9;
  private static final double RECTANGLE_STROKE_WIDTH = 0.1;
  private static final Duration FRAME_DURATION = Duration.millis(1000 / 15); // 15 FPS

  private final Timeline timeline; // animation controller
  private final Random random;

  private Grid grid; // In-memory population representation

  private LifeAutomaton selectedAutomaton; // Selected automaton
  private GameOfLifeAutomaton gofAutomaton;
  private SeedsAutomaton seedsAutomaton;
  private InkspotAutomaton inkspotAutomaton;

  @FXML private Button randomizeButton;
  @FXML private Button playButton;
  @FXML private Button pauseButton;

  @FXML private ToggleGroup automatonSelector;
  @FXML private RadioButton gofButton;
  @FXML private RadioButton seedsButton;
  @FXML private RadioButton inkspotButton;

  @FXML private TilePane tilePane;

  public Controller() {
    this.random = new Random();
    this.timeline = new Timeline(new KeyFrame(FRAME_DURATION, e -> selectedAutomaton.nextStep()));
    this.timeline.setCycleCount(Animation.INDEFINITE);

    this.grid = new Grid(GRID_HEIGHT, GRID_WIDTH);
    this.gofAutomaton = new GameOfLifeAutomaton(this.grid);
    this.seedsAutomaton = new SeedsAutomaton(this.grid);
    this.inkspotAutomaton = new InkspotAutomaton(this.grid);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resizeTilePane(tilePane);

    this.selectedAutomaton = gofAutomaton; // start default
    gofButton.setSelected(true);

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
    for (int i = 0; i < GRID_HEIGHT; i++) {
      for (int k = 0; k < GRID_WIDTH; k++) {
        if (random.nextBoolean()) {
          grid.toggleCellAt(i, k);
        }
      }
    }
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
  public void gofSelected() {
    selectedAutomaton = gofAutomaton;
  }

  @FXML
  public void seedsSelected() {
    selectedAutomaton = seedsAutomaton;
  }

  @FXML
  public void inkspotSelected() {
    selectedAutomaton = inkspotAutomaton;
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

      for (Toggle toggle : automatonSelector.getToggles()) {
        ((Node) toggle).setDisable(true);
      }
    } else {
      playButton.setDisable(false);
      pauseButton.setDisable(true);
      randomizeButton.setDisable(false);
      for (Toggle toggle : automatonSelector.getToggles()) {
        ((Node) toggle).setDisable(false);
      }
    }
  }
}
