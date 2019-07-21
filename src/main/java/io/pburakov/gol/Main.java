package io.pburakov.gol;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
    stage.setTitle("Game of Life");
    stage.setScene(new Scene(parent));
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
