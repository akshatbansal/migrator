package migrator.ext.javafx.component.button;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import migrator.ext.javafx.UseCase;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class AsyncButtonComponent extends ButtonComponent {
    protected UseCase useCase;

    public AsyncButtonComponent(Button button, UseCase useCase) {
        super(button);
        this.useCase = useCase;

        this.emitter.on("taskFailed", (e) -> {
            this.emitter.emit("taskEnded");
        });
        this.emitter.on("taskSucceeded", (e) -> {
            this.emitter.emit("taskEnded");
        });

        this.onAction((e) -> {
            AsyncButtonComponent self = this;
            this.disable();
            Task<Void> task = new Task<Void>() {
                protected Void call() {
                    self.useCase.run();
                    return null;
                }
            };

            task.setOnFailed((eFailed) -> {
                this.enable();
                this.emitter.emit("taskFailed");
            });
            task.setOnSucceeded((eSucceeded) -> {
                this.enable();
                this.emitter.emit("taskSucceeded");
            });

            new Thread(task).start();
        });
    }

    public Subscription<Void> onTaskEnded(Subscriber<Void> subscriber) {
        return this.emitter.on("taskEnded", subscriber);
    }
}