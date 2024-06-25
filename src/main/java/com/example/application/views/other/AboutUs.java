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

@PageTitle("О нас")
@Route(value = "my-view", layout = MainLayout.class)
@CssImport("./styles/styles2.css")
@RolesAllowed("USER")
public class AboutUs extends Composite<VerticalLayout> {

    public AboutUs() {
        VerticalLayout layout = getContent();
        layout.setWidth("100%");
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Создание секций "О нас"
        layout.add(createHeader());
        layout.add(createCompanyDescription());
        layout.add(createProductsSection());
        layout.add(createMissionVisionSection());
        layout.add(createValuesSection());
        layout.add(new Hr());
        layout.add(createContactInformation());
    }

    private HorizontalLayout createHeader() {
        H1 logoText = new H1("Организация по управлению проектами 'Достижение!'");
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
                "Организация 'Достижение!' представляет из себя что-то"
        );
        description.addClassName("company-description");

        Paragraph extendedDescription = new Paragraph(
                "Нашими услугами довольны свыше 1000 клиентов"
        );
        extendedDescription.addClassName("company-description");

        Image image = new Image("/image/Group 130.png", "Company Image");
        image.addClassName("section-image");

        Div section = new Div(description, extendedDescription, image);
        section.addClassName("section");
        return section;
    }

    private Div createProductsSection() {
        Paragraph products = new Paragraph(
                "Наш ассортимент включает разнообразие напитков для всех возрастных и вкусовых предпочтений. " +
                        "Мы постоянно расширяем линейку продуктов, учитывая запросы рынка и предпочтения потребителей."
        );
        products.addClassName("company-products");

        Paragraph extendedProducts = new Paragraph(
                "Среди наших продуктов вы найдёте как классические вкусы, так и новые инновационные решения. " +
                        "Мы предлагаем безалкогольные напитки с натуральными вкусами, витаминизированные напитки, а также " +
                        "продукты с пониженным содержанием сахара для тех, кто следит за своим здоровьем."
        );
        extendedProducts.addClassName("company-products");

        Image image = new Image("/image/Group 129.png", "Products Image");
        image.addClassName("section-image");

        Div section = new Div(products, extendedProducts, image);
        section.addClassName("section");
        return section;
    }

    private Div createMissionVisionSection() {
        Paragraph missionVision = new Paragraph(
                "Миссия нашей компании - предоставлять потребителям качественные и инновационные безалкогольные напитки, " +
                        "способствуя здоровому образу жизни и удовлетворению их вкусовых потребностей. " +
                        "Мы стремимся быть лидерами в отрасли и устанавливать новые стандарты качества."
        );
        missionVision.addClassName("company-mission-vision");

        Paragraph extendedMissionVision = new Paragraph(
                "Наша видение - это будущее, где каждый человек может наслаждаться нашими напитками, зная, что они сделаны " +
                        "из натуральных ингредиентов и безопасны для здоровья. Мы стремимся развивать культуру здорового питания и " +
                        "внедрять инновации в производство."
        );
        extendedMissionVision.addClassName("company-mission-vision");

        Image image = new Image("/image/Group 131.png", "Mission Image");
        image.addClassName("section-image");

        Div section = new Div(missionVision, extendedMissionVision, image);
        section.addClassName("section");
        return section;
    }

    private Div createValuesSection() {
        Paragraph values = new Paragraph(
                "Наши ценности включают в себя ответственность перед потребителями и партнёрами, " +
                        "инновационный подход к разработке и производству, " +
                        "а также устойчивое взаимодействие с окружающей средой и обществом."
        );
        values.addClassName("company-values");

        Paragraph extendedValues = new Paragraph(
                "Мы придерживаемся принципов прозрачности и честности в ведении бизнеса. Наши сотрудники - это главная ценность " +
                        "компании, и мы обеспечиваем им комфортные условия труда и возможности для профессионального роста. " +
                        "Экологическая устойчивость является неотъемлемой частью нашей стратегии."
        );
        extendedValues.addClassName("company-values");



        Div section = new Div(values, extendedValues);
        section.addClassName("section");
        return section;
    }

    private Div createContactInformation() {
        Paragraph contactInfo = new Paragraph(
                "Контактная информация: Телефон: +7 928 460-20-65, Email: info@акватэрра.рф"
        );
        contactInfo.addClassName("contact-info");

        Paragraph extendedContactInfo = new Paragraph(
                "Наш адрес: Россия,385000, Республика Адыгея (адыгея), г.о. Город Майкоп, г Майкоп, ул Загородная, д. 8. Мы всегда рады сотрудничеству и открыты для ваших предложений."
        );
        extendedContactInfo.addClassName("contact-info");



        Div section = new Div(contactInfo, extendedContactInfo);
        section.addClassName("section");
        return section;
    }
}
