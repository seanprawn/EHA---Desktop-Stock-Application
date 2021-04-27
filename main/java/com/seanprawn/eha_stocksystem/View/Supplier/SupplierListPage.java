/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplier;

import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import com.seanprawn.eha_stocksystem.View.Alerts;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplierListPage {
    GridPane mainListPane = new GridPane();
    
    public static Button btnNewSupplier = new Button("_Create Supplier");
    public static Button btnViewSupplier = new Button("_View Supplier");
    public static Button btnEditSupplier = new Button("_Edit Supplier");
    SceneController control = new SceneController();

    public SupplierListPage()
    {
    }
    
    public void setSupplierListPane(GridPane pane)
    {
        this.mainListPane = pane;
    }
    
    public GridPane getSuplierListPane()
    {
        return this.mainListPane;
    }
    
    public GridPane loadMainSupplierListPage()
    {
        setupSupplierScreen();
        Label header = new Label("Suppliers");
        header.setId("header");
        SupplierListTable list = new SupplierListTable();
        TableView suplListTable = list.CreateSuplierListTable();
        
        mainListPane.add(header, 0, 0);
        mainListPane.add(suplListTable, 0, 1);
        
        TilePane btnSupplierPane = new TilePane();
        btnSupplierPane.setOrientation(Orientation.HORIZONTAL);
        
        btnSupplierPane.getChildren().addAll(btnNewSupplier, btnViewSupplier);//, btnEditSupplier);
        mainListPane.add(btnSupplierPane,0,3);
        
        btnViewSupplier.setOnAction(event ->
        {
            if(SupplierListTable.selectedSupplier == null)
            {
                Alerts.displayAlert("Select Supplier", "Please choose a Supplier from the table to View/Edit");
            }
            else
            {
                Supplier selected = SupplierListTable.selectedSupplier; // Get selected Supplier from table
                SupplierViewPage supplier = new SupplierViewPage(); // Create layout
                supplier.setSelectedSupplier(selected); //pass the selected supplier
                supplier.SetTextFieldValues();
                FlowPane suplLayout = supplier.setupViewSupplier();
                suplLayout.setId("root");
                
                Scene supplierScene = new Scene(suplLayout, 500, 500);
                supplierScene.getStylesheets().add("/styles/Styles.css");
                Stage supplierWindow = new Stage();
                supplierWindow.setTitle("View Supplier");
                supplierWindow.initModality(Modality.APPLICATION_MODAL);
                supplierWindow.setScene(supplierScene);
                supplierWindow.showAndWait();
                if(MainApp.refresh)
                {
                    refreshSupplierListPage(event);//Refresh the main list page
                }
            }
        });
        
         btnNewSupplier.setOnAction((event) -> {
        SupplierEditPage suplCreate = new SupplierEditPage();
        FlowPane suplViewLayout = suplCreate.setupViewSupplier();
        suplCreate.setCreateSupplerPage(); // Allow user to edit or clear the fields
       
        Scene supplScene = new Scene(suplViewLayout, 500, 500);
        supplScene.getStylesheets().add("/styles/Styles.css");
        Stage suplWindow = new Stage();
        suplWindow.setTitle("Create New Supplier");
        suplWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
        suplViewLayout.setId("root");
        suplWindow.setScene(supplScene);
        suplWindow.showAndWait();
        
        if(MainApp.refresh)
                {
                    refreshSupplierListPage(event);//Refresh the main list page
                }
    });
         
          btnEditSupplier.setOnAction((event) -> {
            if(SupplierListTable.selectedSupplier == null)
            {
                Alerts.displayAlert("Select Supplier", "Please choose a Supplier from the table to View/Edit");
            }
            else
            {
                SupplierEditPage suplEdit = new SupplierEditPage();
                Supplier selectedSupplier = SupplierListTable.selectedSupplier;
                suplEdit.setSelectedSupplier(selectedSupplier);//pass the selected supplier
                suplEdit.setEditSupplierPage(); // Allow user to edit the fields
                suplEdit.SetTextFieldValues();
                FlowPane suplViewLayout = suplEdit.setupViewSupplier();
                
                Scene supplScene = new Scene(suplViewLayout, 500, 500);
                supplScene.getStylesheets().add("/styles/Styles.css");
                Stage suplWindow = new Stage();
                suplWindow.setTitle("Edit Supplier");
                suplWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
                suplViewLayout.setId("root");
                suplWindow.setScene(supplScene);
                suplWindow.showAndWait();
                
//                if(MainApp.refresh)
//                {
//                    refreshSupplierListPage(event);//Refresh the main list page
//                }
            }
    });
          
            //ToolTip
          Tooltip supplierTip = new Tooltip("View and Edit Supplier Data");
          Tooltip.install(mainListPane,supplierTip);
          
        return mainListPane;
    
    }

    private void setupSupplierScreen() {
//        mainListPane.setPadding(new Insets(0, 0, 0, 0));
//        mainListPane.setHgap(5);
//        mainListPane.setVgap(5);
//        mainListPane.setPrefSize(500, 900);
        mainListPane.setId("gp");
    }

    private void refreshSupplierListPage(ActionEvent event) {
            SupplierListPage suplListPage = new SupplierListPage();
            mainListPane = suplListPage.loadMainSupplierListPage();
            Stage newStage = MainApp.getCurrentMainStage();
//            Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            control.setMainStage(newStage);
            control.loadMainApplication(mainListPane);
            Scene currentScene = newStage.getScene();
            control.setNewMainPageScene(currentScene);
            newStage.requestFocus();
            MainApp.refresh = false;
    }
}
