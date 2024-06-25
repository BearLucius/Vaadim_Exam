package com.example.application.views.admin;

import com.example.application.Repository.ExpertsRepository;
import com.example.application.data.ExpertsEntity;
import com.example.application.data.LeaderEntity;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.List;

@Route(value = "manage-expert", layout = MainLayout.class)
@PageTitle("Управление экспертами")
@CssImport("./styles/styles.css")
public class ExpertsManagementView extends VerticalLayout {

    private final ExpertsRepository expertsRepository;
    private final Grid<ExpertsEntity> grid;
    private final Binder<ExpertsEntity> binder;

    @Autowired
    public ExpertsManagementView(ExpertsRepository expertsRepository) {
        this.expertsRepository = expertsRepository;
        this.grid = new Grid<>(ExpertsEntity.class, false);
        this.binder = new Binder<>(ExpertsEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }




        private void setupGrid() {
        grid.addColumn(ExpertsEntity::getLastname).setHeader("Фамилия").setClassNameGenerator(p -> "grid-header");
            grid.addColumn(ExpertsEntity::getName).setHeader("Имя").setClassNameGenerator(p -> "grid-header");
            grid.addColumn(ExpertsEntity::getSurname).setHeader("Отчество").setClassNameGenerator(p -> "grid-header");
            grid.addColumn(ExpertsEntity::getExperience).setHeader("Опыт работы").setClassNameGenerator(p -> "grid-header");
        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addLeader());
        Button editButton = new Button("Изменить", event -> {
            ExpertsEntity selectedExpertsEntity = grid.asSingleSelect().getValue();
            if (selectedExpertsEntity != null) {
                editProduct(selectedExpertsEntity);
            } else {
                Notification.show("Выберите руководителя для его редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            ExpertsEntity selectedExpertsEntity = grid.asSingleSelect().getValue();
            if (selectedExpertsEntity != null) {
                deleteProduct(selectedExpertsEntity);
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
        LeaderEntity newExpertsEntity = new LeaderEntity();
        newExpertsEntity.setLastname(String.valueOf(new LeaderEntity()));
        newExpertsEntity.setName(String.valueOf(new LeaderEntity()));
        newExpertsEntity.setSurname(String.valueOf(new LeaderEntity()));
        newExpertsEntity.setExperience(String.valueOf(new LeaderEntity()));
        openEditor(new ExpertsEntity());
    }

    private void editProduct(ExpertsEntity expertsEntity) {
        openEditor(expertsEntity);
    }

    private void deleteProduct(ExpertsEntity expertsEntity) {
        expertsRepository.delete(expertsEntity);
        refreshGrid();
        Notification.show("Руководитель удален.");
    }

    private void openEditor(ExpertsEntity expertsEntity) {
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
        binder.setBean(expertsEntity);

        Button saveButton = new Button("Сохранить", e -> {
            expertsRepository.save(binder.getBean());
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
        grid.setItems(expertsRepository.findAll());
    }
}
