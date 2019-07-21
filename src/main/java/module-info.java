module gol {
  requires javafx.controls;
  requires javafx.fxml;

  opens io.pburakov.gol to
      javafx.fxml;

  exports io.pburakov.gol;
}
