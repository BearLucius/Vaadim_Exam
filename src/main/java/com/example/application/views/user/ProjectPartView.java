package com.example.application.views.user;

import com.example.application.Repository.ProjectPartRepository;
import com.example.application.data.ProjectPartEntity;
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

@Route(value = "projectpart-view", layout = MainLayout.class)
@PageTitle("Участники проекта")
@RouteAlias(value = "projectpart-view", layout = MainLayout.class)
@AnonymousAllowed
public class ProjectPartView extends VerticalLayout {

    private final ProjectPartRepository projectPartRepository;
    private final Grid<ProjectPartEntity> grid;


    @Autowired
    public ProjectPartView(ProjectPartRepository projectPartRepository, UserService userService) {
        this.projectPartRepository = projectPartRepository;
        this.grid = new Grid<>(ProjectPartEntity.class, false);


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
        grid.addColumn(ProjectPartEntity::getCompanyName).setHeader("Компания-Спонсор");

    }

    private void refreshGrid() {
        grid.setItems(projectPartRepository.findAll());
    }
}
