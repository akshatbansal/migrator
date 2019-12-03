package migrator.ext.phinx;

import migrator.app.code.CodeManager;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.MigrationGeneratorFactory;
import migrator.app.migration.SimpleFileStorageFactory;
import migrator.app.toast.ToastService;

public class PhinxMigrationGeneratorFactory implements MigrationGeneratorFactory {
    protected CodeManager codeManager;
    protected ToastService toastService;

    public PhinxMigrationGeneratorFactory(CodeManager codeManager, ToastService toastService) {
        this.codeManager = codeManager;
        this.toastService = toastService;
    }

    @Override
    public MigrationGenerator create() {
        return new PhinxMigrationGenerator(
            new SimpleFileStorageFactory(),
            this.codeManager.getCommandFactory("php"),
            this.toastService
        );
    }
}