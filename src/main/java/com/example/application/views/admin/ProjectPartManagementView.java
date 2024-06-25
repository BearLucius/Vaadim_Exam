package com.example.application.views.admin;

import com.example.application.Repository.ProjectPartRepository;
import com.example.application.data.ProjectPartEntity;
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

@Route(value = "manage-projectpart", layout = MainLayout.class)
@PageTitle("Управление участниками проекта (Компании)")
@CssImport("./styles/styles.css")
public class ProjectPartManagementView extends VerticalLayout {

    private final ProjectPartRepository projectPartRepository;
    private final Grid<ProjectPartEntity> grid;
    private final Binder<ProjectPartEntity> binder;

    @Autowired
    public ProjectPartManagementView(ProjectPartRepository projectPartRepository) {
        this.projectPartRepository = projectPartRepository;
        this.grid = new Grid<>(ProjectPartEntity.class, false);
        this.binder = new Binder<>(ProjectPartEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }

    private void setupGrid() {
        grid.addColumn(ProjectPartEntity::getCompanyName).setHeader("Название компании-спонсора").setClassNameGenerator(p -> "grid-header");
        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addLeader());
        Button editButton = new Button("Изменить", event -> {
            ProjectPartEntity selectedProjectPartEntity = grid.asSingleSelect().getValue();
            if (selectedProjectPartEntity != null) {
                editProduct(selectedProjectPartEntity);
            } else {
                Notification.show("Выберите руководителя для его редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            ProjectPartEntity selectedProjectPartEntity = grid.asSingleSelect().getValue();
            if (selectedProjectPartEntity != null) {
                deleteProduct(selectedProjectPartEntity);
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
        ProjectPartEntity projectPartEntity = new ProjectPartEntity();
        projectPartEntity.setCompanyName(String.valueOf(new TeamEntity()));
        openEditor(new ProjectPartEntity());
    }

    private void editProduct(ProjectPartEntity projectPartEntity) {
        openEditor(projectPartEntity);
    }

    private void deleteProduct(ProjectPartEntity leaderEntity) {
        projectPartRepository.delete(leaderEntity);
        refreshGrid();
        Notification.show("Руководитель удален.");
    }

    private void openEditor(ProjectPartEntity teamEntity) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.addClassName("dialog-container");

        FormLayout formLayout = new FormLayout();
        TextField companyName = new TextField("Компания-Спонсор");


        binder.forField(companyName).bind(
                p -> p.getCompanyName(),
                (p, value) -> p.setCompanyName(value)
        );

        formLayout.add(companyName);
        binder.setBean(teamEntity);

        Button saveButton = new Button("Сохранить", e -> {
            projectPartRepository.save(binder.getBean());
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
        grid.setItems(projectPartRepository.findAll());
    }
}
