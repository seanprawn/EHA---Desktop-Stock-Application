/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Controller;

import com.seanprawn.eha_stocksystem.View.LoginPage;
import com.seanprawn.eha_stocksystem.View.SideMenu;
import com.seanprawn.eha_stocksystem.View.TopMenuBar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 * public class SceneController
 * Controls all containers and nodes for the application
 * Controls user access via Login Method, and the main page
 * 
 */
public class SceneController {

    public Scene mainScene;
    public Stage mainStage;
    BorderPane mainBPane = new BorderPane(); // Main Container for Application
    VBox vBox = new VBox(); //SidePanel Container
    HBox hBox = new HBox(); //Top Menu Bar container
    VBox vBoxLogin = new VBox();
//    FlowPane dashBoardPane = new FlowPane();
    
    GridPane middleListGPane = new GridPane();
    GridPane loginPane = new GridPane();
    TilePane sideMenu = new TilePane(); // Container for Side Panel Menu Buttons
    Label dateText = new Label();
    
    /**
     *
     * @param stage
     */
    public void setMainStage(Stage stage)
    {
        mainStage = stage; 
    }
      
    /**
     *
     * @return
     */
    public Stage getMainStage()
    {
        return this.mainStage;  
    }
      
    /**
     *
     * @param scene
     */
    public void setMainScene(Scene scene)
    {
        this.mainScene = scene;
    }
      
    /**
     *
     * @return
     */
    public Scene getMainScene()
    {
        return this.mainScene;       
    }

    /**
     * Entry point for the user
     * Authentication required
     */
    public void login()
    {
        LoginPage loginPage = new LoginPage();
        loginPane = loginPage.loadLoginPane();
        setLoginScene();
    }
    
    /**
     * Sets up all the containers before loading them to the stage
     * BorderPane is used as the main container
     * If user not logged in as HCP then dashboard will not load
     * @param layout -> the GridPane layout to add to the container
     */
    public void loadMainApplication(GridPane layout)
    {
        setupLogo();
        showClockTime();
        setupSidePanel();
        setupMenu();
//        if(MainApp.user.getUserGroupId() == 1) loadDashboard(); // Only load Dashboard if HCP or Super user loags in
        loadMiddleListScreen(layout);
        setupMainContainer();
    }

    /**
     * Sets up the side menu containers before loading them to the stage
     * Buttons are added to a TilePane container
     * which is added to a VBox - (used as the main side container)
     * which is later loaded into the BorderPane
     */
    private void setupSidePanel() {
       
//      
       SideMenu sidePanel = new SideMenu();
       sideMenu = sidePanel.createSideMenu();
//       ImageView logo = getImageLogo();
//        vBox.getChildren().add(logo);
         vBox.getChildren().add(sideMenu);
    }

    /**
     * Sets up the top menu containers before loading them to the stage
     * Buttons are added to a TilePane container
     * which is added to a HBox - (used as the main top container)
     * which is later loaded into the BorderPane
     */
    private void setupMenu() {
        TopMenuBar menuBar = new TopMenuBar();
        hBox = menuBar.loadTopMenuBar();
    }

    /**
     * Sets up theMain Screen container before loading to the stage
     * 
     */
    private void loadMiddleListScreen(GridPane layout) {
        
        middleListGPane =  layout;
    }

    /**
     * Sets up the containers' settings before loading them to the stage
     */
    private void setupMainContainer() {
        //Setup HBOX container and Top Menu buttons
        hBox.setMinHeight(50);
        hBox.setMinWidth(400);
        
        // Setup VBOX container and sideMenu (TilePane)
        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(true);
        vBox.setId("sm");
        middleListGPane.setPadding(new Insets(50, 20, 0, 20)); 
        
        //Add containers to main container
        mainBPane.setTop(hBox);
        mainBPane.setLeft(vBox);
        mainBPane.setCenter(middleListGPane);
        mainBPane.setId("root");
    }
    
      /**
     * Sets up the Image Logo container  
     * @return ImageView
     */
    public static ImageView getImageLogo() {
         //Add Image 
        String logo_file = "file:images/eha_logo.jpg";
        Image image = new  Image(logo_file);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(180);
        imageView.setFitWidth(220);
        imageView.setImage(image);
        imageView.setId("img");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }
    
    /**
     * Loads the BorderPane container into the Scene
     * Displays the Main Page after login
     */
    public void setMainPageScene() {
        
         Scene mainAppScene = new Scene(mainBPane,1200,700);
        mainAppScene.getStylesheets().add("/styles/Styles.css");

        mainStage.setScene(mainAppScene);
        mainStage.show();
        if(SideMenu.dashTrue == 2) SideMenu.dashWindow.requestFocus(); // Set Dashboard focus at startup
    }

            /**
     * Loads the BorderPane container into the Scene
     * Displays the Main Page after login
     * @param scene
     */
    public void setNewMainPageScene(Scene scene) {
        
//         Scene mainAppScene = new Scene(mainBPane,1200,700);
//        mainAppScene.getStylesheets().add("/styles/Styles.css");
        scene.setRoot(mainBPane);
//        mainBPane.setCenter(layout);
        mainStage.setScene(scene);
        mainStage.show();
        if(SideMenu.dashTrue == 2) SideMenu.dashWindow.requestFocus(); // Set Dashboard focus at startup
    }

    
                /**
     * Loads the BorderPane container into the Scene
     * Displays the Main Page after login
     * @param scene
     * @param mainListPane
     */
    public void RefreshMainPageWithScene(Scene scene, GridPane mainListPane) {
        
        mainBPane.setCenter(mainListPane);
        mainStage.setScene(scene);
        mainStage.show();
        if(SideMenu.dashTrue == 2) SideMenu.dashWindow.requestFocus(); // Set Dashboard focus at startup
    }
    
    /**
     * Sets up the Login Screen and containers before loading them to the stage
     * GridPane Nodes are loaded into a FLOWPANE container then effects are added
     * Which is added to a vBOX container
     * which is later loaded into the BorderPane
     */
    private void setLoginScene() {
//         Add Image to Flowpane
        FlowPane flowPane = new FlowPane();
        
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setAlignment(Pos.CENTER);
        ImageView imageView = getImageLogo();
        flowPane.getChildren().add(imageView);
        
        flowPane.getChildren().add(loginPane);
        
        vBoxLogin.getChildren().add(flowPane);
        vBoxLogin.setId("root");

        Scene loginScene = new Scene(vBoxLogin,1200,700);
        loginScene.getStylesheets().add("/styles/Styles.css");

        mainStage.setTitle("EHA-Enhanced Health Alternative");
        mainStage.setScene(loginScene);
        mainStage.show();
    }

    private void setupLogo() {
        ImageView image = getImageLogo();
        FlowPane anchorFlow = new FlowPane();
        anchorFlow.setOrientation(Orientation.HORIZONTAL);
        anchorFlow.setVgap(150);
        anchorFlow.getChildren().add(image);
        anchorFlow.getChildren().add(dateText);
        
        AnchorPane anchor = new AnchorPane(anchorFlow);
        mainBPane.getChildren().add(anchor);
    }
    
    public void showClockTime() 
    {
        
        Timeline timeline = new Timeline(
        new KeyFrame(javafx.util.Duration.seconds(0), (ActionEvent actionEvent) -> {
            Calendar time = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy - HH:mm:ss");
            dateText.setId("clock");
            dateText.setText(simpleDateFormat.format(time.getTime()));
        }),
            new KeyFrame(javafx.util.Duration.seconds(1))
    );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
