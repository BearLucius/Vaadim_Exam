package com.example.application.views.user;

import com.example.application.Repository.ControllerRepository;
import com.example.application.Repository.ExpertsRepository;
import com.example.application.data.ControllerEntity;
import com.example.application.data.ExpertsEntity;
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

@Route(value = "experts", layout = MainLayout.class)
@PageTitle("Эксперты")
@RouteAlias(value = "experts", layout = MainLayout.class)
@AnonymousAllowed
public class ExpertsView extends VerticalLayout {

    private final Grid<ExpertsEntity> grid;
    private final UserService userService;
    private final ExpertsRepository expertsRepository;

    @Autowired
    public ExpertsView(UserService userService, ExpertsRepository expertsRepository) {
        this.userService = userService;
        this.expertsRepository = expertsRepository;
        this.grid = new Grid<>(ExpertsEntity.class, false);


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

        grid.addColumn(expertsEntity -> expertsEntity.getLastname().toString()).setHeader("Фамилия");
        grid.addColumn(expertsEntity -> expertsEntity.getName().toString()).setHeader("Имя");
        grid.addColumn(expertsEntity -> expertsEntity.getSurname().toString()).setHeader("Отчество");
        grid.addColumn(expertsEntity -> expertsEntity.getExperience().toString()).setHeader("Опыт работы");
    }
    private void refreshGrid() {
        grid.setItems(expertsRepository.findAll());
    }
}
