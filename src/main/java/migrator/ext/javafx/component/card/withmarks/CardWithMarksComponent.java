package migrator.ext.javafx.component.card.withmarks;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.ext.javafx.component.MarkComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.Card;
import migrator.ext.javafx.component.card.CardComponent;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class CardWithMarksComponent extends ViewComponent implements CardComponent<Table> {
    protected static final Integer MARKS_LINE_LIMIT = 8;

    protected Card<Table> card;
    protected Emitter<Card<Table>> emitter;

    @FXML protected Text name;
    @FXML protected HBox buttonBox;
    @FXML protected VBox pane;
    @FXML protected HBox marksTop;
    @FXML protected HBox marksBottom;

    public CardWithMarksComponent(Card<Table> card, ViewLoader viewLoader) {
        super(viewLoader);
        this.card = card;
        this.emitter = new EventEmitter<>();        

        this.loadView("/layout/component/cardwithchanges.fxml");

        for (ColumnChange columnChange : this.card.getValue().getColumnsChanges()) {
            if (columnChange.getCommand().isType(ChangeCommand.NONE)) {
                continue;
            }
            this.addMark(
                new MarkComponent(viewLoader, columnChange.getCommand().getType())
            );
        }
        for (IndexChange IndexChange : this.card.getValue().getIndexesChanges()) {
            if (IndexChange.getCommand().isType(ChangeCommand.NONE)) {
                continue;
            }
            this.addMark(
                new MarkComponent(viewLoader, IndexChange.getCommand().getType())
            );
        }
    }

    protected void addMark(GuiNode node) {
        if (this.marksTop.getChildren().size() >= MARKS_LINE_LIMIT) {
            return;
        }
        if (this.marksBottom.getChildren().size() == this.marksTop.getChildren().size()) {
            this.marksBottom.getChildren().add(
                (Node) node.getContent()
            );
        } else {
            this.marksTop.getChildren().add(
                (Node) node.getContent()
            );
        }
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

    @Override
    public Subscription<Card<Table>> onPrimary(Subscriber<Card<Table>> subscriber) {
        return this.emitter.on("primary", subscriber);
    }

    @Override
    public Subscription<Card<Table>> onSecondary(Subscriber<Card<Table>> subscriber) {
        return this.emitter.on("secondary", subscriber);
    }

    @Override
    public void focus() {
        this.pane.getStyleClass().add("card--active");
    }

    @Override
    public void blur() {
        this.pane.getStyleClass().remove("card--active");
    }

    @Override
    public void changeTo(String changeType) {
        this.pane.getStyleClass().remove("card--create");
        this.pane.getStyleClass().remove("card--update");
        this.pane.getStyleClass().remove("card--delete");
        if (changeType != null) {
            this.pane.getStyleClass().add("card--" + changeType);
        }
    }
}