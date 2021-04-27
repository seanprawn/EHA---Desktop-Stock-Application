/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import com.seanprawn.eha_stocksystem.View.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * This page is used for General display of objects once they have been clicked in the side menu
 * Called from the controller
 */
public class SupplementListPage  {
    
    //Container Variables
    GridPane mainListPane = new GridPane();
    GridPane newListPane = new GridPane();
    public static Button btnNewSupl = new Button("_Create Supplement");
    public static  Button btnViewSupl = new Button("_View Supplement");
    public static Button btnEditSupl = new Button("_Edit Supplement");
    SceneController control = new SceneController();

    // Blank constructor
    public SupplementListPage()
    {
    }
    
    public SupplementListPage(GridPane gridPane)
    {
        this.mainListPane = gridPane;
        
    }
    
    public GridPane getMainPage()
    {
        return this.mainListPane;
    }

    /**
     * Sets up and adds the nodes to a GridPane Layout
     * Returns the GridPane to the calling function
     * @return GridPane
     */
    public GridPane loadMainListScreen() {
        //        Initialise the nodes for Gridpane layout
        setupMainScreen();
        Label lblList = new Label("Supplements");
        lblList.setId("header");
        SupplementListTable list = new SupplementListTable(); //create the table
        ObservableList<Supplement> supList = list.getList();//get the list of supplements
        GridPane suplTable = list.CreateSupplementListTable(supList);//add the list to the table

        mainListPane.add(lblList, 0, 0);
        mainListPane.add(suplTable, 0, 1);
        
        TilePane btnSuplPane = new TilePane();
        btnSuplPane.setOrientation(Orientation.HORIZONTAL);
        btnSuplPane.getChildren().addAll(btnNewSupl, btnViewSupl);//, btnEditSupl);
        mainListPane.add(btnSuplPane,0,3);
        
          //ToolTip
          Tooltip suplTip = new Tooltip("View and Edit Supplement Data");
          Tooltip.install(mainListPane, suplTip);
        
        //onclick handlers for buttons
        btnViewSupl.setOnAction((event) -> {
            if(SupplementListTable.selectedSupplement == null)
            {
                Alerts.displayAlert("Select Supplement", "Please choose a Supplement from the table to View/Edit");
            }
            else
            {
                Supplement selectedSupplement = SupplementListTable.selectedSupplement; //Get selected supplement from Table
                SupplementViewPage suplView = new SupplementViewPage(); // create the view page
                suplView.setSelectedSupplement(selectedSupplement);//pass the selected supplement
                suplView.SetTextFieldsEditable(Boolean.FALSE); //User can only view
                suplView.SetTextFieldValues();
                
                FlowPane suplViewLayout = suplView.setupViewSupplement(); //Create the layout
                suplViewLayout.setId("root");
                Scene supplScene = new Scene(suplViewLayout, 500, 500);
                
                supplScene.getStylesheets().add("/styles/Styles.css");
                Stage suplWindow = new Stage();
                suplWindow.setTitle("View Supplement");

                // make sure user finishes here before navigating further
                suplWindow.initModality(Modality.APPLICATION_MODAL); 

                suplWindow.setScene(supplScene);
                suplWindow.showAndWait();
                
                if(MainApp.refresh)
                {
                    refreshSupplementListPage(event);//Refresh the main list page
                }
            }
    });
        
//        btnEditSupl.setOnAction((event) -> {
//            if(SupplementListTable.selectedSupplement == null)
//            {
//                Alerts.displayAlert("Select Supplement", "Please choose a Supplement from the table to View/Edit");
//            }
//            else
//            {
//                SupplementEditPage suplEdit = new SupplementEditPage();
//                Supplement selectedSupplement = SupplementListTable.selectedSupplement;
//                suplEdit.setSelectedSupplement(selectedSupplement);//pass the selected supplement
//                FlowPane suplViewLayout = suplEdit.setupViewSupplement();
//                suplEdit.setEditSupplementPage(); // Allow user to edit the fields
//                suplEdit.SetTextFieldValues();
//                Scene supplScene = new Scene(suplViewLayout, 500, 500);
//                supplScene.getStylesheets().add("/styles/Styles.css");
//                Stage suplWindow = new Stage();
//                suplWindow.setTitle("Edit Supplement");
//                suplWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
//                suplViewLayout.setId("root");
//                suplWindow.setScene(supplScene);
//                suplWindow.showAndWait();
//                if(MainApp.refresh)
//                {
//                    refreshSupplementListPage(event);//Refresh the main list page
//                }
//            }
//    });
         
          btnNewSupl.setOnAction((event) -> {
        SupplementEditPage suplCreate = new SupplementEditPage();
        FlowPane suplViewLayout = suplCreate.setupViewSupplement();
        suplCreate.setCreateSupplementPage();// Allow user to edit or clear the fields
        Scene supplScene = new Scene(suplViewLayout, 500, 500);
        supplScene.getStylesheets().add("/styles/Styles.css");
        
        Stage suplWindow = new Stage();
        suplWindow.setTitle("Create New Supplement");
        suplWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
        suplViewLayout.setId("root");
        suplWindow.setScene(supplScene);
        suplWindow.showAndWait();
        
        if(MainApp.refresh)
        {
            refreshSupplementListPage(event);//Refresh the main list page
        }
    });
        
        return mainListPane;
    }

    public void setupMainScreen()
    {
//        mainListPane.setPadding(new Insets(0, 0, 0, 0));
//        mainListPane.setHgap(5);
//        mainListPane.setVgap(5);
//        mainListPane.setPrefSize(500, 900);
        mainListPane.setId("gp");
    }

    private void refreshSupplementListPage(ActionEvent event) {
        SupplementListPage suplListPage = new SupplementListPage();
            newListPane = suplListPage.loadMainListScreen();
            Stage newStage = MainApp.getCurrentMainStage();
//            Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            control.setMainStage(newStage);
            control.loadMainApplication(newListPane);
            Scene currentScene = newStage.getScene();
            control.setNewMainPageScene(currentScene);
            newStage.requestFocus();
            MainApp.refresh = false;
    }
}
