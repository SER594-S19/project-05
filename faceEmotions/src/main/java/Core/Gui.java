package Core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;


/* This is the Main Server class which loads the fxml file "FaceEmotions.fxml" into a new scene. Extends Application.*/

public class Gui extends Application {
  private final int PORT = 1595;
  @Override


  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/fxml/faceEmotions.fxml"));
    primaryStage.setTitle("Simulator");
    primaryStage.setScene(new Scene(root, 592, 374
    ));
    primaryStage.show();
    primaryStage.setResizable(false);

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        Platform.exit();
        System.exit(0);
      }
    });

  }

  public static void main(String[] args) {
    launch();
    }

}