package com.pygmales.controller;

import com.pygmales.component.SetupDictionaryComponent;
import com.pygmales.utils.AppContext;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Locale;
import java.util.Objects;

public class StartMenuController extends CommonController<AnchorPane> {
    @FXML
    AnchorPane setupDictionaryPane;
    @FXML
    ScrollPane setupDictionaryScrollPane;

    private Timeline scrollAnimation;

    public StartMenuController(AppContext context) {
        super("startMenu", AnchorPane::new, context);
        this.init();
    }

    public void init() {
        super.loadFXML();

        SetupDictionaryComponent setupDictionary = new SetupDictionaryComponent(this.context);
        setupDictionary.setOnLocaleChanged(this::updateLocalization);
        setupDictionary.setOnCreateDictionaryButtonPressed(this::scrollSetupPane);

        this.setupDictionaryPane.getChildren().add(setupDictionary.root);
    }

    private void updateLocalization(Locale locale) {
        this.context.locale = locale;
        this.init();
    }

    private void scrollSetupPane(ActionEvent actionEvent) {
        if (Objects.nonNull(this.scrollAnimation)) {
            this.scrollAnimation.stop();
        }
        this.scrollAnimation = new Timeline(
                new KeyFrame(
                        Duration.millis(300),
                        new KeyValue(
                                this.setupDictionaryScrollPane.hvalueProperty(),
                                1.0,
                                Interpolator.EASE_BOTH
                        )
                )
        );
        this.scrollAnimation.play();
    }
}
