package com.pygmales.controller;

import com.pygmales.utils.AppContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class CommonController<T extends Pane> {
    protected final String sceneName;
    protected final AppContext context;
    public final T root;

    public CommonController(String sceneName, Supplier<T> rootFactory, AppContext context) {
        this.context = context;
        this.sceneName = sceneName;
        this.root = rootFactory.get();
    }

    public void init() {
        this.root.getChildren().clear();
        try {
            URL data = getClass().getResource(String.format("/controller/%1s.fxml", this.sceneName));
            ResourceBundle resources = ResourceBundle.getBundle("localization", this.context.locale);
            FXMLLoader loader = new FXMLLoader(data, resources);

            loader.setRoot(this.root);
            loader.setController(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
