package migrator.gui.javafx;

import migrator.gui.Line;

public class JavafxLine implements Line {
    protected javafx.scene.shape.Line line;

    public JavafxLine(double startX, double startY, double endX, double endY) {
        this.line = new javafx.scene.shape.Line(startX, startY, endX, endY);
    }

    @Override
    public Object getContent() {
        return this.line;
    }
}