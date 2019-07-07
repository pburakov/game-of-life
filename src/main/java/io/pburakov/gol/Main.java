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
    Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));
    stage.setTitle("Hello World");
    stage.setScene(new Scene(root, 300, 275));
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
