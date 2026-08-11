package com.pygmales.component;

import com.pygmales.controller.CommonController;
import com.pygmales.utils.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static com.pygmales.constant.Constants.LANGUAGES;
import static com.pygmales.constant.Constants.LANGUAGE_MAP;

public class SetupDictionaryComponent extends CommonController<Pane> {
    @FXML
    ChoiceBox<String> localeChoiceBox;
    @FXML
    Button createDictionaryButton;

    private Consumer<Locale> onLocaleChanged;

    public SetupDictionaryComponent(AppContext context) {
        super("setupDictionary", Pane::new, context);

        this.localeChoiceBox.setItems(FXCollections.observableArrayList(LANGUAGES));
        this.localeChoiceBox.setValue(LANGUAGE_MAP.getOrDefault(this.context.locale, LANGUAGES.getFirst()));

        this.localeChoiceBox.valueProperty().addListener(((_, oldValue, newValue) -> {
            if (Objects.equals(oldValue, newValue)) return;
            LANGUAGE_MAP.entrySet().stream()
                    .filter(entry -> Objects.equals(entry.getValue(), newValue))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .ifPresent(this.onLocaleChanged);
        }));
    }

    public void setOnLocaleChanged(Consumer<Locale> consumer) {
        this.onLocaleChanged = consumer;
    }

    public void setOnCreateDictionaryButtonPressed(Consumer<ActionEvent> consumer) {
        this.createDictionaryButton.setOnAction(consumer::accept);
    }

}
