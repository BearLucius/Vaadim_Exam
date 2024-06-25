package com.example.application.views.other;

import com.example.application.data.UserEntity;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;

import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;



@Route(value = "sign-in-log-out", layout = MainLayout.class)
@PageTitle("Вход | Выход")
@RouteAlias(value = "sing-in-log-out", layout = MainLayout.class)
@AnonymousAllowed
public class logout extends VerticalLayout {
    private final Grid<logout> grid;
    private final UserService userService;

    @Autowired
    public logout(UserService userService) {
        this.userService = userService;
        this.grid = new Grid<>(logout.class, false);


        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setSpacing(false);
        setPadding(false);
        setWidth("100%");
        setHeight("100vh");

        Header header = createHeader();
        add(header);

        // Check if the user is authenticated
        if (userService.getCurrentUser() == null) {

            UI.getCurrent().navigate("my-view");

        }
    }

    private Header createHeader() {
        H1 logoText = new H1("АкваТэрра");
        logoText.addClassName("logo-text");

        HorizontalLayout logoLayout = new HorizontalLayout(logoText);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.addClassName("logo-layout");

        Button actionButton;
        UserEntity currentUserEntity = userService.getCurrentUser();

        if (currentUserEntity != null) {
            actionButton = new Button("Выйти из аккаунта", event -> {
                userService.logout();
                UI.getCurrent().navigate("my-view");
                UI.getCurrent().getPage().reload();
            });
        } else {
            actionButton = new Button("Войти");
            actionButton.addClickListener(e -> showLoginDialog());
        }

        actionButton.addClassName("action-button");

        HorizontalLayout headerLayout = new HorizontalLayout(logoLayout, actionButton);
        headerLayout.setWidth("100%");
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setPadding(true);
        headerLayout.addClassName("header");

        return new Header(headerLayout);
    }

    private void showLoginDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.getElement().getStyle().set("background-color", "#1a237e"); // Темно-синий цвет фона

        TextField usernameField = new TextField();
        usernameField.setLabel("Логин");
        usernameField.setWidth("100%");

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Пароль");
        passwordField.setWidth("100%");

        Button loginButton = new Button("Войти", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            UserEntity userEntity = userService.getUserByUsername(username);

            if (userEntity != null && userEntity.getPassword().equals(password)) {
                userService.setCurrentUser(userEntity);
                Notification.show("Добро пожаловать, " + username, 3000, Notification.Position.MIDDLE);
                dialog.close();
                UI.getCurrent().getPage().reload();
            } else {
                Notification.show("Неправильный логин или пароль", 3000, Notification.Position.MIDDLE);
            }
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button registerButton = new Button("Зарегистрироваться", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            UserEntity existingUserEntity = userService.getUserByUsername(username);

            if (existingUserEntity == null) {
                userService.saveUser(new UserEntity(username, password, "USER"));
                Notification.show("Регистрация успешна, пожалуйста, войдите", 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Пользователь с таким логином уже существует", 3000, Notification.Position.MIDDLE);
            }
        });
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        closeButton.addClickListener(event -> dialog.close());
        closeButton.getStyle().set("position", "absolute");
        closeButton.getStyle().set("top", "10px");
        closeButton.getStyle().set("right", "10px");

        H2 title = new H2("Авторизация");
        title.getStyle().set("color", "#ffffff"); // Белый цвет заголовка

        VerticalLayout formLayout = new VerticalLayout(title, usernameField, passwordField, new HorizontalLayout(loginButton, registerButton));
        formLayout.setSpacing(true);
        formLayout.setPadding(true);
        formLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout dialogLayout = new VerticalLayout(closeButton, formLayout);
        dialogLayout.setWidthFull();
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        dialog.add(dialogLayout);
        dialog.open();
    }
}