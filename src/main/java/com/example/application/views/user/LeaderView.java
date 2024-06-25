package com.example.application.views.user;

import com.example.application.Repository.LeaderRepository;
import com.example.application.data.LeaderEntity;
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

@Route(value = "leader-view", layout = MainLayout.class)
@PageTitle("Руководители")
@RouteAlias(value = "leader-view", layout = MainLayout.class)
@AnonymousAllowed
public class LeaderView extends VerticalLayout {

    private final LeaderRepository leaderRepository;
    private final Grid<LeaderEntity> grid;


    @Autowired
    public LeaderView(LeaderRepository leaderRepository, UserService userService) {
        this.leaderRepository = leaderRepository;
        this.grid = new Grid<>(LeaderEntity.class, false);


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
        grid.addColumn(LeaderEntity::getFio).setHeader("Фамилия Имя Отчество");
        grid.addColumn(LeaderEntity::getExperience).setHeader("Опыт работы");

    
    }

    private void refreshGrid() {
        grid.setItems(leaderRepository.findAll());
    }
}
