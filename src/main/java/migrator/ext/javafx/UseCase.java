package migrator.ext.javafx;

public class UseCase {
    public static void runOnThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}