package migrator.app.modification;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.Modification;

public class SimpleModification<T> implements Modification<T> {
    protected ChangeCommand changeCommand;
    protected T original;
    protected T modification;

    public SimpleModification(
        T original,
        T modification,
        ChangeCommand changeCommand
    ) {
        this.original = original;
        this.modification = modification;
        this.changeCommand = changeCommand;
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.changeCommand;
    }

    @Override
    public T getModification() {
        return this.modification;
    }

    @Override
    public T getOriginal() {
        return this.original;
    }

    @Override
    public void updateOriginal(T original) {
        
    }
}