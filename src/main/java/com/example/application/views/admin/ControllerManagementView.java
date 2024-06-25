package com.example.application.views.admin;

import com.example.application.Repository.ControllerRepository;
import com.example.application.data.ControllerEntity;
import com.example.application.services.ControllerService;
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

@Route(value = "manage-controller", layout = MainLayout.class)
@PageTitle("Управление контролерами")
@CssImport("./styles/styles.css")
public class ControllerManagementView extends VerticalLayout {

    private final ControllerRepository controllerRepository;
    private final Grid<ControllerEntity> grid;
    private final Binder<ControllerEntity> binder;

    @Autowired
    public ControllerManagementView(ControllerRepository controllerRepository) {
        this.controllerRepository = controllerRepository;
        this.grid = new Grid<>(ControllerEntity.class, false);
        this.binder = new Binder<>(ControllerEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }

    private void setupGrid() {
        grid.addColumn(ControllerEntity::getLastname).setHeader("Фамилия").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(ControllerEntity -> ControllerEntity.getName()).setHeader("Имя").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(ControllerEntity -> ControllerEntity.getSurname()).setHeader("Отчество").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(ControllerEntity -> ControllerEntity.getExperience()).setHeader("Опыт работы").setClassNameGenerator(p -> "grid-header");
        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addLeader());
        Button editButton = new Button("Изменить", event -> {
            ControllerEntity selectedControllerEntity = grid.asSingleSelect().getValue();
            if (selectedControllerEntity != null) {
                editProduct(selectedControllerEntity);
            } else {
                Notification.show("Выберите контролера для его редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            ControllerEntity selectedControllerEntity = grid.asSingleSelect().getValue();
            if (selectedControllerEntity != null) {
                deleteProduct(selectedControllerEntity);
            } else {
                Notification.show("Выберите поле с контролером для удаления.");
            }
        });

        return new HorizontalLayout(addButton, editButton, deleteButton);
    }

    private void setupForm() {
        // В данном примере метод setupForm остается пустым, так как вся форма находится в диалоговом окне
    }

    private void addLeader() {
        ComboBox<ControllerEntity> comboBox = new ComboBox<>("Country");
        comboBox.setItems();
        comboBox.setItemLabelGenerator(ControllerEntity::getName);
        add(comboBox);
        ControllerEntity newControllerEntity = new ControllerEntity();
        newControllerEntity.setLastname(String.valueOf(new ControllerEntity()));
        newControllerEntity.setName(String.valueOf(new ControllerEntity()));
        newControllerEntity.setSurname(String.valueOf(new ControllerEntity()));
        newControllerEntity.setExperience(String.valueOf(new ControllerEntity()));
        openEditor(new ControllerEntity());
    }

    private void editProduct(ControllerEntity controllerEntity) {
        openEditor(controllerEntity);
    }

    private void deleteProduct(ControllerEntity leaderEntity) {
        controllerRepository.delete(leaderEntity);
        refreshGrid();
        Notification.show("Руководитель удален.");
    }

    private void openEditor(ControllerEntity controllerEntity) {
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
        binder.setBean(controllerEntity);

        Button saveButton = new Button("Сохранить", e -> {
            controllerRepository.save(binder.getBean());
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
        grid.setItems(controllerRepository.findAll());
    }
}