package com.example.application.views.user;

import com.example.application.Repository.TeamRepository;
import com.example.application.data.TeamEntity;
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

@Route(value = "team-view", layout = MainLayout.class)
@PageTitle("Участники команды")
@RouteAlias(value = "team-view", layout = MainLayout.class)
@AnonymousAllowed
public class TeamView extends VerticalLayout {

    private final TeamRepository teamRepository;
    private final Grid<TeamEntity> grid;


    @Autowired
    public TeamView(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.grid = new Grid<>(TeamEntity.class, false);


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
        grid.addColumn(TeamEntity::getLastname).setHeader("Фамилия");
        grid.addColumn(TeamEntity::getName).setHeader("Имя");
        grid.addColumn(TeamEntity::getSurname).setHeader("Отчество");
        grid.addColumn(TeamEntity::getExperience).setHeader("Опыт работы");


    }

    private void refreshGrid() {
        grid.setItems(teamRepository.findAll());
    }
}