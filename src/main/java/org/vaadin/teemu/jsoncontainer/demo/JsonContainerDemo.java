package org.vaadin.teemu.jsoncontainer.demo;

import org.vaadin.teemu.jsoncontainer.JsonContainer;

import com.vaadin.Application;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

/**
 * Simple demo application for the JsonContainer Vaadin add-on.
 */
@SuppressWarnings("serial")
public class JsonContainerDemo extends Application {

    private static final String DEMO_JSON = "[\n"
            + "    { \"name\" : \"Teemu Tester\", \"age\" : 31 },\n"
            + "    { \"name\" : \"John Doe\" },\n"
            + "    { \"name\" : \"Johnny\", \"age\" : 25,  \"language\": \"English\"   },\n"
            + "    { \"name\" : \"Neo\", \"age\" : 38, \"Inside Matrix\": false },\n"
            + "    { \"name\" : \"Mr. Smith\", \"age\" : 42, \"Inside Matrix\": true },\n"
            + "]";

    private Window window;
    private Table table;

    @Override
    public void init() {
        VerticalLayout windowLayout = new VerticalLayout();
        windowLayout.setMargin(true);
        window = new Window("JsonContainer Demo Application");
        window.setContent(windowLayout);
        setMainWindow(window);
        setTheme("jsoncontainer");

        table = new Table();
        table.setSizeFull();

        final TextArea jsonArea = new TextArea();
        jsonArea.setStyleName("jsonarea");
        jsonArea.setValue(DEMO_JSON);
        jsonArea.setRows(10);
        jsonArea.setWidth("100%");

        Button updateButton = new Button("Update table",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        updateTable(jsonArea.getValue().toString());
                    }
                });

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        Panel mainPanel = new Panel();
        mainPanel.setContent(mainLayout);
        mainPanel.setWidth("750px");
        mainPanel.addComponent(getHeader());
        mainPanel.addComponent(jsonArea);
        mainPanel.addComponent(updateButton);
        mainPanel.addComponent(table);

        windowLayout.addComponent(mainPanel);
        windowLayout.setComponentAlignment(mainPanel, Alignment.TOP_CENTER);
        windowLayout.addComponent(getTooltip());

        // update with the default JSON
        updateTable(DEMO_JSON);
    }

    private Component getHeader() {
        Label h1 = new Label("JsonContainer", Label.CONTENT_XHTML);
        h1.setWidth(null);
        h1.setStyleName(Reindeer.LABEL_H1);

        Label tagline = new Label("Make your Table speak JSON");
        tagline.setWidth(null);
        tagline.setStyleName("tagline");

        CssLayout header = new CssLayout();
        header.setWidth("100%");
        header.addComponent(h1);
        header.addComponent(tagline);
        return header;
    }

    private Component getTooltip() {
        final CssLayout tooltip = new CssLayout();
        tooltip.setStyleName("edit-tooltip");
        tooltip.addComponent(new Label(
                "<div id=\"tooltip\">Edit JSON and click on the button below to update.<br /><br />"
                        + "Try for example to add new properties and new rows.</div>",
                Label.CONTENT_XHTML));
        tooltip.addListener(new LayoutClickListener() {
            public void layoutClick(LayoutClickEvent event) {
                tooltip.addStyleName("dismissed");
            }
        });
        return tooltip;
    }

    private void updateTable(String json) {
        try {
            // Use the factory method of JsonContainer to instantiate the
            // data source for the table.
            JsonContainer dataSource = JsonContainer.Factory.newInstance(json);
            table.setContainerDataSource(dataSource);
        } catch (IllegalArgumentException e) {
            getMainWindow()
                    .showNotification(
                            "There seems to be something wrong with your JSON.",
                            "Please check the syntax.",
                            Notification.TYPE_ERROR_MESSAGE);
        }
    }
}
