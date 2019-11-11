package migrator.ext.javafx.component.button;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import migrator.ext.javafx.UseCase;

public class ButtonBuilder {
    protected Button button;

    public ButtonBuilder() {
        this.button = new Button();
    }

    public ButtonBuilder secondary() {
        this.button.getStyleClass().add("btn-secondary");
        return this;
    }

    public ButtonBuilder primary() {
        this.button.getStyleClass().add("btn-primary");
        return this;
    }

    public ButtonBuilder withText(String text) {
        this.button.setText(text);
        return this;
    }

    public ButtonBuilder withIcon(String icon) {
        this.button.setGraphic(
            new ImageView(icon)
        );
        return this;
    }

    public ButtonBuilder withTooltip(String tooltip) {
        this.button.setTooltip(new Tooltip(tooltip));
        return this;
    }

    public ButtonComponent build() {
        return new ButtonComponent(this.button);
    }

    public AsyncButtonComponent buildAsync(UseCase useCase) {
        return new AsyncButtonComponent(
            this.button,
            useCase
        );
    }
}