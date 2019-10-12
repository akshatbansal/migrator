package migrator.ext.javafx.project.component;

import migrator.app.domain.project.model.Project;
import migrator.ext.javafx.component.card.Card;
import migrator.ext.javafx.component.card.CardFactory;

public class ProjectCardFactory implements CardFactory<Project> {
    @Override
    public migrator.ext.javafx.component.card.Card<Project> create(Project value) {
        return new Card<>(
            value,
            value.nameProperty(),
            "open",
            "edit",
            null
        );
    }
}