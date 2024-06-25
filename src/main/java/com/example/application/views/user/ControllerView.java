package com.example.application.views.user;

import com.example.application.Repository.ContractRepository;
import com.example.application.Repository.ControllerRepository;
import com.example.application.data.ContractEntity;
import com.example.application.data.ControllerEntity;
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

@Route(value = "controller", layout = MainLayout.class)
@PageTitle("Контролеры")
@RouteAlias(value = "controller", layout = MainLayout.class)
@AnonymousAllowed
public class ControllerView extends VerticalLayout {

    private final Grid<ControllerEntity> grid;
    private final UserService userService;
    private final ControllerRepository controllerRepository;

    @Autowired
    public ControllerView(UserService userService, ControllerRepository controllerRepository) {
        this.userService = userService;
        this.controllerRepository = controllerRepository;
        this.grid = new Grid<>(ControllerEntity.class, false);


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
        grid.addColumn(controllerEntity -> controllerEntity.getLastname().toString()).setHeader("Фамилия");
        grid.addColumn(controllerEntity -> controllerEntity.getName().toString()).setHeader("Имя");
        grid.addColumn(controllerEntity -> controllerEntity.getSurname().toString()).setHeader("Отчество");
        grid.addColumn(controllerEntity -> controllerEntity.getExperience().toString()).setHeader("Опыт работы");
    }

    private void refreshGrid() {
        grid.setItems(controllerRepository.findAll());
    }
}
