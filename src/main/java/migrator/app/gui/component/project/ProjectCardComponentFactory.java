package migrator.app.gui.component.project;

import javafx.beans.property.SimpleObjectProperty;
import migrator.app.domain.project.model.Project;
import migrator.app.gui.component.card.CardComponent;
import migrator.app.gui.component.card.CardComponentFactory;

public class ProjectCardComponentFactory implements CardComponentFactory<Project> {
    public ProjectCardComponentFactory() {}

    @Override
    public CardComponent<Project> create(Project item) {
        CardComponent<Project> cardComponent = new ProjectCardComponent();
        cardComponent.bind(new SimpleObjectProperty<>(item));
        return cardComponent;
    }
}