package com.pygmales;

import com.pygmales.controller.StartMenuController;
import com.pygmales.utils.AppContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        AppContext context = new AppContext();
        context.stage = primaryStage;

        StartMenuController appStart = new StartMenuController(context);

        Scene scene = new Scene(appStart.root);

        primaryStage.setTitle("HelloWorld");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
