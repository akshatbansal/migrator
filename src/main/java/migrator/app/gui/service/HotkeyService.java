package migrator.app.gui.service;

import javafx.stage.Stage;
import migrator.app.gui.GuiContainer;
import migrator.app.service.Service;
import migrator.lib.hotkyes.HotkeyFactory;
import migrator.lib.hotkyes.HotkeysService;

public class HotkeyService implements Service {
    private Stage stage;
    private HotkeysService libHotkeysService;
    private HotkeyFactory hotkeyFactory;

    public HotkeyService(
        GuiContainer container,
        Stage primaryStage
    ) {
        this.stage = primaryStage;
        this.libHotkeysService = container.hotkeyContainer().hotkeysService();
        this.hotkeyFactory = container.hotkeyContainer().hotkeyFactory();
    }

    @Override
    public void start() {
        this.stage.getScene()
            .getRoot().setOnKeyPressed((e) -> {
                this.libHotkeysService
                    .keyPressed(
                        this.hotkeyFactory.create(e.getCode().getCode(), e.isControlDown(), e.isShiftDown())
                    );
            });
    }

    @Override
    public void stop() {
        
    }
}