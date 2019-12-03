package migrator.ext.phinx;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import migrator.lib.storage.Storage;
import migrator.lib.stringformatter.PascalCaseFormatter;
import migrator.lib.stringformatter.StringFormatter;
import migrator.lib.stringformatter.UnderscoreFormatter;
import migrator.app.code.CodeCommand;
import migrator.app.code.CodeCommandFactory;
import migrator.app.migration.FileStorageFactory;
import migrator.app.migration.MigrationGenerator;
import migrator.app.migration.model.TableChange;
import migrator.app.toast.ToastService;

public class PhinxMigrationGenerator implements MigrationGenerator {
    protected FileStorageFactory fileStorageFactory;
    protected CodeCommandFactory commandFactory;
    protected StringFormatter classNameFormatter;
    protected StringFormatter fileNameFormatter;
    protected StringFormatter timestampFormatter;
    protected ToastService toastService;

    public PhinxMigrationGenerator(
        FileStorageFactory fileStorageFactory,
        CodeCommandFactory commandFactory,
        ToastService toastService
    ) {
        this.fileStorageFactory = fileStorageFactory;
        this.commandFactory = commandFactory;
        this.classNameFormatter = new PascalCaseFormatter();
        this.fileNameFormatter = new UnderscoreFormatter();
        this.timestampFormatter = new TimestampFileNameFormatter();
        this.toastService = toastService;
    }

    public Boolean generateMigration(String projectFolder, String name, List<? extends TableChange> changes) {
        String formattedFileName = this.fileNameFormatter.format(name) + ".php";
        File projectFolderFile = new File(projectFolder);
        File[] sameNameFiles = projectFolderFile.listFiles((File dir, String fileInFolderName) -> {
            return fileInFolderName.endsWith(formattedFileName);
        });
        if (sameNameFiles != null && sameNameFiles.length > 0) {
            this.toastService.error("Commit name is the same as '" + sameNameFiles[0].getName() + "'. Change commit name.");
            return false;
        }
        String phinxContent = "";
        for (TableChange tableChange : changes) {
            phinxContent += this.toPhinxFormat(tableChange);
        }
        if (phinxContent.isEmpty()) {
            return true;
        }

        Storage<String> storage = this.fileStorageFactory.create(
            new File(projectFolder + System.getProperty("file.separator") + this.timestampFormatter.format(formattedFileName))
        );
        String className = this.classNameFormatter.format(name);
        storage.store(
            this.wrapToPhinxClass(className, phinxContent)
        );

        return true;
    }

    public Boolean generateMigration(String projectFolder, String name, TableChange ... changes) {
        return this.generateMigration(projectFolder, name, Arrays.asList(changes));
    }

    private String toPhinxFormat(TableChange tableChange) {
        CodeCommand codeCommand = this.commandFactory.table(tableChange);
        return codeCommand.toCode();
    }

    private String wrapToPhinxClass(String className, String changeContent) {
        String tabedContent = "";
        for (String line : changeContent.split("\n")) {
            tabedContent += "\t\t" + line + "\n";
        }
        return "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class " + className + " extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    tabedContent +
                "\t}\n" +
            "}\n";
    }
}