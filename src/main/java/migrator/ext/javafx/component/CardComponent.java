package migrator.ext.javafx.component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class CardComponent<T> extends ViewComponent {
    protected Card<T> card;
    protected Emitter<Card<T>> emitter;

    @FXML protected Text name;
    @FXML protected HBox buttonBox;
    @FXML protected VBox pane;

    public CardComponent(Card<T> card, ViewLoader viewLoader) {
        super(viewLoader);
        this.card = card;
        this.emitter = new EventEmitter<>();        

        this.loadView("/layout/component/card.fxml");
    }

    @FXML public void initialize() {
        this.name.textProperty().bind(
            this.card.nameProperty()
        );

        if (this.card.secondaProperty().get() != null) {
            Button secondaryButton = new Button();
            secondaryButton.textProperty().bind(
                this.card.secondaProperty()
            );
            secondaryButton.getStyleClass().addAll("btn-secondary");
            secondaryButton.setOnAction((event) -> {
                this.emitter.emit("secondary", this.card);
            });
            this.buttonBox.getChildren().add(secondaryButton);
        }

        if (this.card.primaryProperty().get() != null) {
            Button primaryButton = new Button();
            primaryButton.textProperty().bind(
                this.card.primaryProperty()
            );
            primaryButton.getStyleClass().addAll("btn-primary");
            primaryButton.setOnAction((event) -> {
                this.emitter.emit("primary", this.card);
            });
            this.buttonBox.getChildren().add(primaryButton);
        }

        if (this.card.changeTypeProperty() != null) {
            this.card.changeTypeProperty()
                .addListener((obs, oldValue, newValue) -> {
                    this.changeTo(newValue);
                });
            this.changeTo(this.card.changeTypeProperty().get());
        }
    }

    public Subscription<Card<T>> onPrimary(Subscriber<Card<T>> subscriber) {
        return this.emitter.on("primary", subscriber);
    }

    public Subscription<Card<T>> onSecondary(Subscriber<Card<T>> subscriber) {
        return this.emitter.on("secondary", subscriber);
    }

    public void focus() {
        this.pane.getStyleClass().add("card--active");
    }

    public void blur() {
        this.pane.getStyleClass().remove("card--active");
    }

    public void changeTo(String changeType) {
        this.pane.getStyleClass().remove("card--create");
        this.pane.getStyleClass().remove("card--update");
        this.pane.getStyleClass().remove("card--delete");
        if (changeType != null) {
            this.pane.getStyleClass().add("card--" + changeType);
        }
    }
}