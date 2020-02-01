package migrator.lib.filesystem;

import java.io.File;
import java.nio.file.Path;

public interface Filesystem {
    public Boolean exists(String filePath);
    public Boolean exists(Path path);
    public Boolean exists(File file);
    public String read(String filePath) throws Exception;
    public String read(Path path) throws Exception;
    public String read(File file) throws Exception;
    public void write(String filePath, String content) throws Exception;
    public void write(Path path, String content) throws Exception;
    public void write(File file, String content) throws Exception;
}