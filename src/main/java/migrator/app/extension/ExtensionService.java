package migrator.app.extension;

import java.util.List;

import migrator.app.service.Service;

public class ExtensionService implements Service {
    private List<Service> extensions;

    public ExtensionService(List<Service> extensions) {
        this.extensions = extensions;
    }

    @Override
    public void start() {
        for (Service extension : this.extensions) {
            extension.start();
        }
    }

    @Override
    public void stop() {
        for (Service extension : this.extensions) {
            extension.stop();
        }
    }
}