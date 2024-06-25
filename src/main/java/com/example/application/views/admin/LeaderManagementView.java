package com.example.application.views.admin;

import com.example.application.Repository.LeaderRepository;
import com.example.application.data.*;
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

@Route(value = "manage-leader", layout = MainLayout.class)
@PageTitle("Управление руководством")
@CssImport("./styles/styles.css")
public class LeaderManagementView extends VerticalLayout {

    private final LeaderRepository leaderRepository;
    private final Grid<LeaderEntity> grid;
    private final Binder<LeaderEntity> binder;

    @Autowired
    public LeaderManagementView(LeaderRepository leaderRepository) {
        this.leaderRepository = leaderRepository;
        this.grid = new Grid<>(LeaderEntity.class, false);
        this.binder = new Binder<>(LeaderEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }

    private void setupGrid() {
        grid.addColumn(LeaderEntity::getLastname).setHeader("Фамилия").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(LeaderEntity -> LeaderEntity.getName()).setHeader("Имя").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(LeaderEntity -> LeaderEntity.getSurname()).setHeader("Отчество").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(LeaderEntity -> LeaderEntity.getExperience()).setHeader("Опыт работы").setClassNameGenerator(p -> "grid-header");
        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addLeader());
        Button editButton = new Button("Изменить", event -> {
            LeaderEntity selectedLeaderEntity = grid.asSingleSelect().getValue();
            if (selectedLeaderEntity != null) {
                editProduct(selectedLeaderEntity);
            } else {
                Notification.show("Выберите руководителя для его редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            LeaderEntity selectedLeaderEntity = grid.asSingleSelect().getValue();
            if (selectedLeaderEntity != null) {
                deleteProduct(selectedLeaderEntity);
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
        LeaderEntity newLeaderEntity = new LeaderEntity();
        newLeaderEntity.setLastname(String.valueOf(new LeaderEntity()));
        newLeaderEntity.setName(String.valueOf(new LeaderEntity()));
        newLeaderEntity.setSurname(String.valueOf(new LeaderEntity()));
        newLeaderEntity.setExperience(String.valueOf(new LeaderEntity()));
        openEditor(new LeaderEntity());
    }

    private void editProduct(LeaderEntity leaderEntity) {
        openEditor(leaderEntity);
    }

    private void deleteProduct(LeaderEntity leaderEntity) {
        leaderRepository.delete(leaderEntity);
        refreshGrid();
        Notification.show("Руководитель удален.");
    }

    private void openEditor(LeaderEntity leaderEntity) {
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
        binder.setBean(leaderEntity);

        Button saveButton = new Button("Сохранить", e -> {
            leaderRepository.save(binder.getBean());
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
        grid.setItems(leaderRepository.findAll());
    }
}
