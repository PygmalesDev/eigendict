package com.pygmales.component;

import com.pygmales.controller.CommonController;
import com.pygmales.utils.AppContext;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.pygmales.constant.Constants.LANGUAGES;
import static com.pygmales.constant.Constants.LANGUAGE_MAP;

public class SetupDictionaryComponent extends CommonController<Pane> {
    @FXML
    ChoiceBox<String> localeChoiceBox;
    @FXML
    Button createDictionaryButton;
    @FXML
    TextField dictionaryNameInputField;
    @FXML
    TextField dictionaryPathField;
    @FXML
    Button openDirectoryChooserButton;
    @FXML
    Button returnButton;
    @FXML
    Button creationFinishedButton;

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

        this.openDirectoryChooserButton.setOnAction(this::onDirectoryChoosing);
        this.creationFinishedButton.disableProperty().bind(Bindings.or(
                this.dictionaryNameInputField.textProperty().isEmpty(),
                Bindings.createBooleanBinding(() -> {
                    String path = this.dictionaryPathField.getText();
                    File file = new File(path);
                    return !file.exists() || !file.isDirectory();
                }, this.dictionaryPathField.textProperty())
        ));
    }

    private void onDirectoryChoosing(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(ResourceBundle.getBundle("localization", this.context.locale).getString("startMenu.directoryChooser"));

        File directory = chooser.showDialog(this.context.stage);
        if (Objects.nonNull(directory)) {
            this.dictionaryPathField.setText(directory.getAbsolutePath());
        }
    }

    public void setOnLocaleChanged(Consumer<Locale> consumer) {
        this.onLocaleChanged = consumer;
    }

    public void setOnCreateDictionaryButtonPressed(Consumer<ActionEvent> consumer) {
        this.createDictionaryButton.setOnAction(event -> {
            this.returnButton.setDefaultButton(true);
            consumer.accept(event);
        });
    }

    public void setOnReturnButtonPressed(Consumer<ActionEvent> consumer) {
        this.returnButton.setOnAction(event -> {
            this.createDictionaryButton.setDefaultButton(true);
            this.dictionaryNameInputField.clear();
            this.dictionaryPathField.clear();
            consumer.accept(event);
        });
    }

}
