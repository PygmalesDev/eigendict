package com.pygmales.controller;

import com.pygmales.utils.AppContext;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.util.Locale;

import static com.pygmales.constant.Constants.LANGUAGE_MAP;

public class StartMenuController extends CommonController<AnchorPane> {
    @FXML
    public ChoiceBox<String> localeChoiceBox;

    private final ObservableList<String> localeValues = FXCollections.observableList(LANGUAGE_MAP.keySet().stream().toList());
    private String currentSelectedLocale = localeValues.getFirst();

    public StartMenuController(AppContext context) {
        super("startMenu", AnchorPane::new, context);
        this.init();
    }

    private void onLocaleChanged(Observable obs, String oldValue, String newValue) {
        String languageTag = LANGUAGE_MAP.getOrDefault(newValue, LANGUAGE_MAP.get(localeValues.getFirst()));
        this.context.locale = Locale.forLanguageTag(languageTag);
        this.currentSelectedLocale = newValue;
        this.init();
    }

    @Override
    public void init() {
        super.init();

        this.localeChoiceBox.setItems(this.localeValues);
        this.localeChoiceBox.setValue(this.currentSelectedLocale);
        this.localeChoiceBox.valueProperty().addListener(this::onLocaleChanged);
    }
}
