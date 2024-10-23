package org.example.block1project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class projectGui extends Application {

    // Main content panes for each tab
    private StackPane homePage, graphsPage, otherPage;

    @Override
    public void start(Stage primaryStage) {
        // Create a BorderPane as the main layout
        BorderPane root = new BorderPane();

        // Create the buttons for the tabs
        Button homeTab = new Button("Home");
        Button graphsTab = new Button("Graphs");
        Button otherTab = new Button("Other");

        // Set actions for each button to change the page
        homeTab.setOnAction(e -> root.setCenter(homePage));
        graphsTab.setOnAction(e -> root.setCenter(graphsPage));
        otherTab.setOnAction(e -> root.setCenter(otherPage));

        // Layout for the tab buttons (you could use HBox for horizontal layout)
        BorderPane topMenu = new BorderPane();
        topMenu.setLeft(homeTab);
        topMenu.setCenter(graphsTab);
        topMenu.setRight(otherTab);

        // Set the tabs at the top
        root.setTop(topMenu);

        // Initialize content for each tab
        homePage = new StackPane(new Button("This is the Home page"));
        graphsPage = new StackPane(new Button("This is the Graphs page"));
        otherPage = new StackPane(new Button("This is the Other page"));

        // Set initial content (Home page)
        root.setCenter(homePage);

        // Create the scene and show the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Basic Tab App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
