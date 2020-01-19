package migrator.lib.version;

public class Version implements Comparable<Version> {
    private Integer major;
    private Integer minor;
    private Integer patch;

    public Version(Integer major, Integer minor, Integer patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(String version) {
        String[] parts = version.split("\\.");
        this.major = Integer.parseInt(parts[0]);
        this.minor = Integer.parseInt(parts[1]);
        this.patch = Integer.parseInt(parts[2]);
    }

    public Integer getMajor() {
        return this.major;
    }

    public Integer getMinor() {
        return this.minor;
    }

    public Integer getPatch() {
        return this.patch;
    }

    @Override
    public int compareTo(Version version) {
        if (this.getMajor() > version.getMajor()) {
            return 1;
        } else if (this.getMajor() < version.getMajor()) {
            return -1;
        }

        if (this.getMinor() > version.getMinor()) {
            return 1;
        } else if (this.getMinor() < version.getMinor()) {
            return -1;
        }

        if (this.getPatch() > version.getPatch()) {
            return 1;
        } else if (this.getPatch() < version.getPatch()) {
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return this.getMajor() + "." + this.getMinor() + "." + this.getPatch();
    }
}