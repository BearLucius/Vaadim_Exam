package com.example.application.views.user;

import com.example.application.Repository.ContractRepository;
import com.example.application.data.ContractEntity;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;



@Route(value = "contract", layout = MainLayout.class)
@PageTitle("Договора")
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class ContractView extends VerticalLayout {

    private final ContractRepository contractRepository;
    private final Grid<ContractEntity> grid;
    private final UserService userService;

    @Autowired
    public ContractView(ContractRepository contractRepository, UserService userService) {
        this.userService = userService;
        this.contractRepository = contractRepository;
        this.grid = new Grid<>(ContractEntity.class, false);


        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setSpacing(false);
        setPadding(false);
        setWidth("100%");
        setHeight("100vh");
        // Check if the user is authenticated
        if (userService.getCurrentUser() == null) {

            UI.getCurrent().navigate("my-view");
            return;
        }
        setupGrid();
        add(new H1(""), grid);
        refreshGrid();
    }

    private void setupGrid() {
        grid.addColumn(contractEntity -> contractEntity.getLeader().getLastname().toString()).setHeader("Руководитель");
        grid.addColumn(contractEntity -> contractEntity.getTeam().getLastname().toString()).setHeader("Участник команды");
        grid.addColumn(contractEntity -> contractEntity.getProjectPart().getCompanyName().toString()).setHeader("Участник проекта (Компания)");
        grid.addColumn(contractEntity -> contractEntity.getController().getLastname().toString()).setHeader("Контролёр");
        grid.addColumn(contractEntity -> contractEntity.getExperts().getLastname().toString()).setHeader("Эксперт");
    }

    private void refreshGrid() {
        grid.setItems(contractRepository.findAll());
    }
}