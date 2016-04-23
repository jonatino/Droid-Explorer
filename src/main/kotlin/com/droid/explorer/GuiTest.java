package com.droid.explorer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Jonathan on 4/20/2016.
 */
public class GuiTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("droidexplorer.fxml"));

        primaryStage.setTitle("Test FXML");
/*        primaryStage.setWidth(320);
        primaryStage.setHeight(200);*/

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}


