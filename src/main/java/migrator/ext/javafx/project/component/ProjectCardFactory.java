package migrator.ext.javafx.project.component;

import migrator.app.domain.project.model.Project;
import migrator.ext.javafx.component.Card;
import migrator.ext.javafx.component.CardFactory;

public class ProjectCardFactory implements CardFactory<Project> {
    @Override
    public Card<Project> create(Project value) {
        return new Card<>(
            value,
            value.nameProperty(),
            "open",
            "edit",
            null
        );
    }
}