package migrator.app.version;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.lib.version.Version;

public class VersionContainer {
    private Map<String, List<VersionMigration>> migrations;
    private List<Version> versions;

    public VersionContainer() {
        this.migrations = new Hashtable<>();
        this.versions = new LinkedList<>();
    }

    public void addVersionMigration(Version version, VersionMigration migration) {
        if (!this.migrations.containsKey(version.toString())) {
            this.migrations.put(version.toString(), new LinkedList<>());
        }
        this.migrations.get(version.toString()).add(migration);
        this.versions.add(version);
        Collections.sort(this.versions);
    }

    public List<VersionMigration> getMigrationsFor(Version version) {
        if (!this.migrations.containsKey(version.toString())) {
            return new LinkedList<>();
        }
        return this.migrations.get(version.toString());
    }

    public List<VersionMigration> getMigrationsGreaterThen(Version version) {
        List<VersionMigration> result = new LinkedList<>();
        for (Version v : this.versions) {
            if (v.compareTo(version) != 1) {
                continue;
            }
            result.addAll(this.getMigrationsFor(v));
        }
        return result;
    }

    public Version getLastVersion() {
        if (this.versions.size() == 0) {
            return null;
        }
        return this.versions.get(this.versions.size() - 1);
    }
}