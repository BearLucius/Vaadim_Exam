package com.example.application.views.admin;

import com.example.application.Repository.TeamRepository;
import com.example.application.data.TeamEntity;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "manage-team", layout = MainLayout.class)
@PageTitle("Управление участниками командой")
@CssImport("./styles/styles.css")
public class TeamManagementView extends VerticalLayout {

    private final TeamRepository teamRepository;
    private final Grid<TeamEntity> grid;
    private final Binder<TeamEntity> binder;

    @Autowired
    public TeamManagementView(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.grid = new Grid<>(TeamEntity.class, false);
        this.binder = new Binder<>(TeamEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }

    private void setupGrid() {
        grid.addColumn(TeamEntity::getLastname).setHeader("Фамилия").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(TeamEntity -> TeamEntity.getName()).setHeader("Имя").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(TeamEntity -> TeamEntity.getSurname()).setHeader("Отчество").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(TeamEntity -> TeamEntity.getExperience()).setHeader("Опыт работы").setClassNameGenerator(p -> "grid-header");
        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addLeader());
        Button editButton = new Button("Изменить", event -> {
            TeamEntity selectedLeaderEntity = grid.asSingleSelect().getValue();
            if (selectedLeaderEntity != null) {
                editProduct(selectedLeaderEntity);
            } else {
                Notification.show("Выберите руководителя для его редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            TeamEntity selectedTeamEntity = grid.asSingleSelect().getValue();
            if (selectedTeamEntity != null) {
                deleteProduct(selectedTeamEntity);
            } else {
                Notification.show("Выберите поле с руководителя для удаления.");
            }
        });

        return new HorizontalLayout(addButton, editButton, deleteButton);
    }

    private void setupForm() {
        // В данном примере метод setupForm остается пустым, так как вся форма находится в диалоговом окне
    }

    private void addLeader() {
        TeamEntity newTeamEntity = new TeamEntity();
        newTeamEntity.setLastname(String.valueOf(new TeamEntity()));
        newTeamEntity.setName(String.valueOf(new TeamEntity()));
        newTeamEntity.setSurname(String.valueOf(new TeamEntity()));
        newTeamEntity.setExperience(String.valueOf(new TeamEntity()));
        openEditor(new TeamEntity());
    }

    private void editProduct(TeamEntity teamEntity) {
        openEditor(teamEntity);
    }

    private void deleteProduct(TeamEntity leaderEntity) {
        teamRepository.delete(leaderEntity);
        refreshGrid();
        Notification.show("Руководитель удален.");
    }

    private void openEditor(TeamEntity teamEntity) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.addClassName("dialog-container");

        FormLayout formLayout = new FormLayout();
        TextField lastname = new TextField("Фамилия");
        TextField name = new TextField("Имя");
        TextField surname = new TextField("Отчество");
        TextField experience = new TextField("Опыт работы");



        binder.forField(lastname).bind(
                p -> p.getLastname(),
                (p, value) -> p.setLastname(value)
        );
        binder.forField(name).bind(
                p -> p.getName(),
                (p, value) -> p.setName(value)
        );
        binder.forField(surname).bind(
                p -> p.getSurname(),
                (p, value) -> p.setSurname(value)
        );
        binder.forField(experience).bind(
                p -> p.getExperience(),
                (p, value) -> p.setExperience(value)
        );

        formLayout.add(lastname, name, surname, experience);
        binder.setBean(teamEntity);

        Button saveButton = new Button("Сохранить", e -> {
            teamRepository.save(binder.getBean());
            refreshGrid();
            dialog.close();
        });
        saveButton.addClassName("dialog-button");

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.addClassName("dialog-button");

        dialog.add(formLayout, new HorizontalLayout(saveButton, cancelButton));
        dialog.open();
    }

    private void refreshGrid() {
        grid.setItems(teamRepository.findAll());
    }
}
