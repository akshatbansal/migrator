package migrator.app.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import migrator.lib.version.Version;

public class VersionContainerTest {
    @Test
    public void addVersionMigration_newVersionNumber_addsToNewList() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(1, versionContainer.getMigrationsFor(new Version("0.2.0")).size());
    }

    @Test
    public void addVersionMigration_toExistingVersionNumber_addsToExistingList() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.0"), null);
        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(2, versionContainer.getMigrationsFor(new Version("0.2.0")).size());
    }

    @Test
    public void addVersionMigration_toOtherVersionNumber_addsToOtherList() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.0"), null);
        versionContainer.addVersionMigration(new Version("0.2.1"), null);

        assertEquals(1, versionContainer.getMigrationsFor(new Version("0.2.1")).size());
    }

    @Test
    public void getMigrationsGreaterThen__returnsAllInList() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.1"), null);
        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(2, versionContainer.getMigrationsGreaterThen(new Version("0.1.0")).size());
    }

    @Test
    public void getMigrationsGreaterThen_insertInReverseOrder_returnsListInOrder() {
        VersionContainer versionContainer = new VersionContainer();

        VersionMigration v020 = new MockVersionMigration();
        VersionMigration v021 = new MockVersionMigration();
        versionContainer.addVersionMigration(new Version("0.2.1"), v021);
        versionContainer.addVersionMigration(new Version("0.2.0"), v020);

        assertEquals(v020, versionContainer.getMigrationsGreaterThen(new Version("0.1.0")).get(0));
        assertEquals(v021, versionContainer.getMigrationsGreaterThen(new Version("0.1.0")).get(1));
    }

    @Test
    public void getMigrationsGreaterThen_smallestVersionInList_returnsListWithSizeOneLess() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.1"), null);
        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(1, versionContainer.getMigrationsGreaterThen(new Version("0.2.0")).size());
    }

    @Test
    public void getMigrationsGreaterThen_biggestVersionInList_returnsEmptyList() {
        VersionContainer versionContainer = new VersionContainer();

        versionContainer.addVersionMigration(new Version("0.2.1"), null);
        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(0, versionContainer.getMigrationsGreaterThen(new Version("0.2.1")).size());
    }

    @Test
    public void getMigrationsGreaterThen_smallestVersionInList_returnsListWithoutSmallestMigrator() {
        VersionContainer versionContainer = new VersionContainer();

        VersionMigration versionMigration = new MockVersionMigration();
        versionContainer.addVersionMigration(new Version("0.2.1"), versionMigration);
        versionContainer.addVersionMigration(new Version("0.2.0"), null);

        assertEquals(versionMigration, versionContainer.getMigrationsGreaterThen(new Version("0.2.0")).get(0));
    }

    public class MockVersionMigration implements VersionMigration {
        @Override
        public void migrate() {}
    }
}