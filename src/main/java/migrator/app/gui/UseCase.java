package migrator.app.gui;

public class UseCase {
    public static void runOnThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}