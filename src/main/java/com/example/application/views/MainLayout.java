package com.example.application.views;

import com.example.application.data.UserEntity;
import com.example.application.services.UserService;

import com.example.application.views.admin.*;
import com.example.application.views.other.HelpWeb;
import com.example.application.views.other.logout;
import com.example.application.views.user.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/css.css")
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private final UserService userService;

    public MainLayout(UserService userService) {
        this.userService = userService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Управление проектами 'Достижение'");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Главная", HelpWeb.class, LineAwesomeIcon.HOME_SOLID.create()));
        nav.addItem(new SideNavItem("Учетная запись", logout.class, LineAwesomeIcon.ADDRESS_BOOK.create()));
        UserEntity currentUserEntity = userService.getCurrentUser();

        if (currentUserEntity != null) {

            if ("USER".equals(currentUserEntity.getRole())) {
                nav.addItem(new SideNavItem("Договора", ContractView.class, LineAwesomeIcon.BOOK_SOLID.create()));
                nav.addItem(new SideNavItem("Руководители", LeaderView.class, LineAwesomeIcon.BOOK_SOLID.create()));
                nav.addItem(new SideNavItem("Контролеры", ControllerView.class, LineAwesomeIcon.BOOK_SOLID.create()));
                nav.addItem(new SideNavItem("Участники команды", TeamView.class, LineAwesomeIcon.BOOK_SOLID.create()));
                nav.addItem(new SideNavItem("Участники проекта", ProjectPartView.class, LineAwesomeIcon.BOOK_SOLID.create()));
                nav.addItem(new SideNavItem("Эксперты", ExpertsView.class, LineAwesomeIcon.BOOK_SOLID.create()));


            }
                if ("ADMIN".equals(currentUserEntity.getRole())) {
                    nav.addItem(new SideNavItem("Управление договорами", ContractManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
                    nav.addItem(new SideNavItem("Управление руководителями", LeaderManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
                    nav.addItem(new SideNavItem("Управление контролерами", ControllerManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
                    nav.addItem(new SideNavItem("Управление командами", TeamManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
                    nav.addItem(new SideNavItem("Управление участникми проектами", ProjectPartManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
                    nav.addItem(new SideNavItem("Управление экспертами", ExpertsManagementView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

                }
        }
        return nav;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    }
