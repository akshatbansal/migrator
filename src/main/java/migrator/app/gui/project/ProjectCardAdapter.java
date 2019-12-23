package migrator.app.gui.project;

import migrator.app.gui.component.card.CardGuiModel;
import migrator.lib.adapter.Adapter;

public class ProjectCardAdapter implements Adapter<ProjectGuiModel, CardGuiModel<ProjectGuiModel>> {
    @Override
    public ProjectGuiModel concretize(CardGuiModel<ProjectGuiModel> item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CardGuiModel<ProjectGuiModel> generalize(ProjectGuiModel item) {
        CardGuiModel<ProjectGuiModel> card = new CardGuiModel<>(
            item,
            item.getProjectProperty().nameProperty()
        );
        item.attribute("selected").addListener((observable, oldValue, newValue) -> {
            card.attribute("selected").set(newValue);
        });
        return card;
    }
}