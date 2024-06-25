package com.example.application.views.other;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Справка обычного пользователя и администратора")
@Route(value = "my-view", layout = MainLayout.class)
@CssImport("./styles/styles2.css")
@RolesAllowed("USER")
public class HelpWeb extends Composite<VerticalLayout> {

    public HelpWeb() {
        VerticalLayout layout = getContent();
        layout.setWidth("100%");
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Создание секций "О нас"
        layout.add(createHeader());
        layout.add(createCompanyDescription());
        layout.add(createProductsSection());
        layout.add(createMissionVisionSection());

        layout.add(new Hr());

    }

    private HorizontalLayout createHeader() {
        H1 logoText = new H1("Справка");
        logoText.addClassName("logo-text");

        HorizontalLayout logoLayout = new HorizontalLayout(logoText);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.addClassName("logo-layout");

        HorizontalLayout headerLayout = new HorizontalLayout(logoLayout);
        headerLayout.setWidth("100%");
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setPadding(true);
        headerLayout.addClassName("header");

        return headerLayout;
    }

    private Div createCompanyDescription() {
        Paragraph description = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        description.addClassName("company-description");

        Paragraph extendedDescription = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        extendedDescription.addClassName("company-description");

        Div section = new Div(description, extendedDescription);
        section.addClassName("section");
        return section;
    }

    private Div createProductsSection() {
        Paragraph products = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        products.addClassName("company-products");

        Paragraph extendedProducts = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        extendedProducts.addClassName("company-products");

        Div section = new Div(products, extendedProducts);
        section.addClassName("section");
        return section;
    }

    private Div createMissionVisionSection() {
        Paragraph missionVision = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        missionVision.addClassName("company-mission-vision");

        Paragraph extendedMissionVision = new Paragraph(
                "Управление договорами - Администратор может добавлять, удалять или изменять договора в БД." +
                        "Нажмите на кнопку Добавить и введите нужные вам данные, после чего нажмите ещё раз на кнопку Добавить, чтобы применить" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку удалить, для удаления из базы данных" +
                        "Нажмите на одно из нужных вам полей на таблице и нажмите кнопку Изменить, для изменение нужных вам данных, а затем потвердите изменение"
        );
        extendedMissionVision.addClassName("company-mission-vision");
        Div section = new Div(missionVision, extendedMissionVision);
        section.addClassName("section");
        return section;
    }
}
