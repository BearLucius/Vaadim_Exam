package com.example.application.views.admin;

import com.example.application.Repository.ContractRepository;
import com.example.application.data.*;
import com.example.application.services.LeaderService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Select;
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

import java.util.ArrayList;
import java.util.List;

@Route(value = "manage-contracts", layout = MainLayout.class)
@PageTitle("Управление договорами")
@CssImport("./styles/styles.css")
public class ContractManagementView extends VerticalLayout {

    private final ContractRepository contractRepository;
    private final Grid<ContractEntity> grid;
    private final Binder<ContractEntity> binder;

    @Autowired
    public ContractManagementView(ContractRepository contractRepository, LeaderService leaderService) {
        this.contractRepository = contractRepository;
        this.grid = new Grid<>(ContractEntity.class, false);
        this.binder = new Binder<>(ContractEntity.class);

        setupGrid();
        setupForm();

        add(new H1(""), createButtonsLayout(), grid);
        refreshGrid();
    }
    private void setSelectSampleData(Select select) {
        List<ContractEntity> sampleItems = new ArrayList<>();
        sampleItems.add(new ContractEntity());
    }
    private void setupGrid() {

        grid.addColumn(contractEntity -> contractEntity.getLeader().getLastname()).setHeader("Руководство").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(contractEntity -> contractEntity.getTeam().getLastname()).setHeader("Команда").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(contractEntity -> contractEntity.getController().getLastname()).setHeader("Контролер").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(contractEntity -> contractEntity.getProjectPart().getCompanyName()).setHeader("Участник проекта (Компания)").setClassNameGenerator(p -> "grid-header");
        grid.addColumn(contractEntity -> contractEntity.getExperts().getLastname()).setHeader("Эксперт").setClassNameGenerator(p -> "grid-header");


        grid.setClassNameGenerator(p -> "grid-row");
    }

    private HorizontalLayout createButtonsLayout() {
        Button addButton = new Button("Добавить", event -> addContract());
        Button editButton = new Button("Изменить", event -> {
            ContractEntity selectedContractEntity = grid.asSingleSelect().getValue();
            if (selectedContractEntity != null) {
                editProduct(selectedContractEntity);
            } else {
                Notification.show("Выберите договор для редактирования.");
            }
        });
        Button deleteButton = new Button("Удалить", event -> {
            ContractEntity selectedContractEntity = grid.asSingleSelect().getValue();
            if (selectedContractEntity != null) {
                deleteProduct(selectedContractEntity);
            } else {
                Notification.show("Выберите договор для удаления.");
            }
        });

        return new HorizontalLayout(addButton, editButton, deleteButton);
    }

    private void setupForm() {
        // В данном примере метод setupForm остается пустым, так как вся форма находится в диалоговом окне
    }

    private void addContract() {

        ContractEntity newContractEntity = new ContractEntity();
        newContractEntity.setLeader(new LeaderEntity());
        newContractEntity.setTeam(new TeamEntity());
        newContractEntity.setProjectPart(new ProjectPartEntity());
        newContractEntity.setController(new ControllerEntity());
        newContractEntity.setExperts(new ExpertsEntity());
        openEditor(newContractEntity);
    }

    private void editProduct(ContractEntity contractEntity) {
        openEditor(contractEntity);
    }

    private void deleteProduct(ContractEntity contractEntity) {
        contractRepository.delete(contractEntity);
        refreshGrid();
        Notification.show("Договор удален.");
    }

    private void openEditor(ContractEntity contractEntity) {
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.addClassName("dialog-container");

        FormLayout formLayout = new FormLayout();
        TextField leader = new TextField("Руководитель");
        TextField team = new TextField("Участник команды");
        TextField project = new TextField("Участник проекта (Компания)");
        TextField controller = new TextField("Контролёр");
        TextField expert = new TextField("Эксперт");


        binder.forField(leader).bind(
                p -> p.getLeader().getLastname(),
                (p, value) -> p.getLeader().setSurname(value)
        );

        binder.forField(team).bind(
                p -> p.getTeam().getSurname(),
                (p, value) -> p.getTeam().setSurname(value)
        );
        binder.forField(project).bind(
                p -> p.getProjectPart().getCompanyName(),
                (p, value) -> p.getProjectPart().setCompanyName(value)
        );
        binder.forField(controller).bind(
                p -> p.getController().getSurname(),
                (p, value) -> p.getController().setSurname(value)
        );
        binder.forField(expert).bind(
                p -> p.getExperts().getSurname(),
                (p, value) -> p.getExperts().setSurname(value)
        );


        formLayout.add(leader, team, project, controller, expert);
        binder.setBean(contractEntity);

        Button saveButton = new Button("Сохранить", e -> {
            contractRepository.save(binder.getBean());
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
        grid.setItems(contractRepository.findAll());
    }
}