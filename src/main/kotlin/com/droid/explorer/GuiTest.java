package com.droid.explorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jonathan on 4/20/2016.
 */
public class GuiTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/droid/explorer/DroidExplorer.fxml"));

        primaryStage.setTitle("Test FXML");
/*        primaryStage.setWidth(320);
        primaryStage.setHeight(200);*/

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}


