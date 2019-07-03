package migrator.gui.javafx;

import javafx.geometry.Bounds;
import migrator.gui.Dimensions;

import javafx.scene.Node;

public class JavafxDimensions implements Dimensions {
    protected Bounds bounds;

    public JavafxDimensions(Node node) {
        this.bounds = node.getBoundsInLocal();
    }

    @Override
    public Double getX() {
        return this.bounds.getMinX();
    }

    @Override
    public Double getY() {
        return this.bounds.getMinY();
    }

    @Override
    public Double getWidth() {
        return this.bounds.getWidth();
    }

    @Override
    public Double getHeight() {
        return this.bounds.getHeight();
    }
}