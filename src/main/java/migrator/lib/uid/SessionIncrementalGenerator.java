package migrator.lib.uid;

public class SessionIncrementalGenerator implements Generator {
    protected String sessionId;
    protected Long nextId;

    public SessionIncrementalGenerator(String sessionId) {
        this(sessionId, Long.valueOf(1));
    }

    public SessionIncrementalGenerator(String sessionId, Long nextId) {
        this.sessionId = sessionId;
        this.nextId = nextId;
    }
    
    @Override
    public String next() {
        Long currentId = this.nextId;
        this.nextId++;
        return this.sessionId + "-" + currentId.toString();
    }
}