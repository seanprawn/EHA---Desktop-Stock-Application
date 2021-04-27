/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View;

import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.View.Client.ClientListPage;
import com.seanprawn.eha_stocksystem.View.Dashboard.Dashboard;
import com.seanprawn.eha_stocksystem.View.Invoice.InvoiceListPage;
import com.seanprawn.eha_stocksystem.View.Supplement.SupplementListPage;
import com.seanprawn.eha_stocksystem.View.Supplier.SupplierListPage;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SideMenu {
    
     TilePane sideMenu = new TilePane(); // Container for Side Panel Menu Buttons

     //    Menu Buttons for Side Panel
    Button suppliersBtn = new Button("Suppliers");
    Button invoiceMenuBtn = new Button("Invoices");
    Button SupplementMenuBtn = new Button("Supplements");
    Button clientMenuBtn = new Button("Clients");
    GridPane layoutGPane = new GridPane();
    public static CheckBox dashCheck = new CheckBox("View Dashboard");
    public static Stage dashWindow = new Stage();
    SceneController control = new SceneController();

//    Flag to determine if dashboard is started for first time 
    public static int dashStart = 0; // (0 = first time, 1 = many)
    
    //Flag to determine if dashboard is active
    public static int dashTrue = 0; // (0 = not active, 1 = active)
    
    public SideMenu()
    {
    
    }
    
    public TilePane createSideMenu()
    {
       setupSideMenu();
       //Add Tooltip
         Tooltip sideTip = new Tooltip("Click to View / Refresh Component");
         Tooltip.install(sideMenu, sideTip);
       //Only for Super user
       if(LoginPage.userGroup == 1) 
       {
           //Add Tooltip if Super User only
            Tooltip dashTip = new Tooltip("Click to toggle Dashboard");
            Tooltip.install(dashCheck, dashTip);
            
            if(dashStart == 0) //Only for first time startup use
            {
                
                createDashboard(); // User can view the dashboard first on startup
                dashStart++; // Only for first time startup use, once incremented no longer first time
                dashTrue = 2;
                dashCheck.selectedProperty().set(true);
                dashWindow.requestFocus(); // Dashboard gets focus on startup
            }
            
            //If dashboard is active later than first time, then checkbox must be checked
            if(dashStart >0 && !dashCheck.isSelected() && dashTrue > 0)
            {
                dashCheck.selectedProperty().set(true); // Make sure checkbox is selected
            }
            
            //If dashboard is closed or not active then deselect checkbox
            if(dashCheck.isSelected() && dashTrue == 0)
            {
                dashCheck.selectedProperty().set(false);
            }
            
                sideMenu.getChildren().add(dashCheck); // Only load CheckBox if SuperUser
       }
       sideMenu.getChildren().addAll(SupplementMenuBtn,suppliersBtn,invoiceMenuBtn, clientMenuBtn); 
       SupplementMenuBtn.setId("btnSideMenu");
       suppliersBtn.setId("btnSideMenu");
       invoiceMenuBtn.setId("btnSideMenu");
       clientMenuBtn.setId("btnSideMenu");
       
      
       //Set Onaction for Client Button
       clientMenuBtn.setOnAction(event->
       {
        ClientListPage clientListPage = new ClientListPage();
        layoutGPane = clientListPage.loadMainClientListScreen();
            replaceLayoutWithNewLayout(event);
       });
       
       //Set Action for Suppliers Button
       suppliersBtn.setOnAction((event) -> {
           SupplierListPage suplList = new SupplierListPage();
           layoutGPane = suplList.loadMainSupplierListPage();
            replaceLayoutWithNewLayout(event);
       });
       
              //Set Action for Invoice Button
       invoiceMenuBtn.setOnAction((event) -> {
           InvoiceListPage inv = new InvoiceListPage();
           layoutGPane = inv.loadMainInvoiceListPage();
            replaceLayoutWithNewLayout(event);
       });
       
              //Set Action for Supplements Button
       SupplementMenuBtn.setOnAction((event) -> {
           SupplementListPage suplList = new SupplementListPage();
           layoutGPane = suplList.loadMainListScreen();
            replaceLayoutWithNewLayout(event);
       });
       
              //Set Action for Dashboard checkbox
        dashCheck.setOnAction((event) ->{  //Create the dashboard only of user clicks on checkbox
            if(dashCheck.isSelected())
            {
                createDashboard();
                dashTrue = 1;
            }else 
            {
                dashWindow.close(); // Close the dashboard if user unselects checkbox
                dashTrue = 0;
            }
        });
        
        //Set action for close Dashboard
        dashWindow.setOnCloseRequest((event) ->{ //If the window is closed by the user, then unselect checkbox
            dashCheck.selectedProperty().set(false); 
            dashTrue = 0;
        });
         return sideMenu;
    }

     private void setupSideMenu()
     {
        dashCheck.setId("header");
        sideMenu.setAlignment(Pos.CENTER_LEFT);
        sideMenu.setOrientation(Orientation.VERTICAL);
        sideMenu.vgapProperty().set(6);
        
        //Set all buttons to max width
        clientMenuBtn.setMaxWidth(100);
        suppliersBtn.setMaxWidth(100);
        invoiceMenuBtn.setMaxWidth(100);
        SupplementMenuBtn.setMaxWidth(100);
     }

    private void setSceneWithNewLayout(ActionEvent event) {
        Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        control.setMainStage(newStage);
        control.loadMainApplication(layoutGPane);
        control.setMainPageScene();
        newStage.requestFocus();
    }

        private void replaceLayoutWithNewLayout(ActionEvent event) {
        Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        control.setMainStage(newStage);
        control.loadMainApplication(layoutGPane);
        Scene currentScene = newStage.getScene();
        control.setNewMainPageScene(currentScene);
        newStage.requestFocus();
    }
        
    private void createDashboard() {
        Dashboard dash = new Dashboard();
        GridPane dashBoardPane = dash.createDashboard();
        Scene dashScene = new Scene(dashBoardPane);

        dashScene.getStylesheets().add("/styles/Styles.css");
        dashBoardPane.setId("root");

        dashWindow.setTitle("EHA - Dashboard");
        dashWindow.setScene(dashScene);
        dashWindow.show();
    }   
}
