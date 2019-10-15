package migrator.lib.uid;

public class SessionIncrementalGenerator implements Generator {
    protected String sessionId;
    protected Integer nextId;

    public SessionIncrementalGenerator(String sessionId) {
        this(sessionId, 1);
    }

    public SessionIncrementalGenerator(String sessionId, Integer nextId) {
        this.sessionId = sessionId;
        this.nextId = nextId;
    }
    
    @Override
    public String next() {
        Integer currentId = this.nextId;
        this.nextId++;
        return this.sessionId + "-" + currentId.toString();
    }
}