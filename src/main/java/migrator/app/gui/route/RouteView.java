package migrator.app.gui.route;

import migrator.app.gui.view.View;

public interface RouteView {
    public View getView();
    public void activate();
    public void suspend();
}