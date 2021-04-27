/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Client;

import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Client;
import com.seanprawn.eha_stocksystem.View.Alerts;
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
 */
public class ClientListPage {
    
    GridPane mainListPane = new GridPane();
    Button btnNewClient = new Button("Create Client");
    Button btnViewClient = new Button("View Client");
    Button btnEditClient = new Button("Edit CLient");
    SceneController control = new SceneController();

    
    public ClientListPage()
    {
    }
    
    public void setClientListPage(GridPane gridPane)
    {
        this.mainListPane = gridPane;
    }
    
    public GridPane getMainListPane()
    {
        return this.mainListPane;
    
    }
    
    public GridPane loadMainClientListScreen()
    {
        setupClientListScreen();
        Label header = new Label("Clients");
        header.setId("header");
        ClientListTable list = new ClientListTable();
        GridPane clListTable = list.CreateClientListTable();
        
        mainListPane.add(header, 0, 0);
        mainListPane.add(clListTable, 0, 1);
        
        TilePane btnClientPane = new TilePane();
        btnClientPane.setOrientation(Orientation.HORIZONTAL);

        btnClientPane.getChildren().addAll(btnNewClient, btnViewClient);//, btnEditClient);
        mainListPane.add(btnClientPane,0,3);
        
        btnViewClient.setOnAction(event ->
        {
            if(ClientListTable.selectedClient == null)
            {
                Alerts.displayAlert("Select Client", "Please choose a Client from the table to View/Edit");
            }
            else
            {
                Client selected = ClientListTable.selectedClient;
                ClientViewPage clientView = new ClientViewPage();
                clientView.setSelectedClient(selected);
                clientView.SetTextFieldsEditable(Boolean.FALSE);
                clientView.SetTextFieldValues();
                
                FlowPane clientLayout = clientView.createClientViewPage();
                Scene clientScene = new Scene(clientLayout, 500, 500);
                clientScene.getStylesheets().add("/styles/Styles.css");
                Stage clientWindow = new Stage();
                clientWindow.setTitle("View Client");
                
               // make sure user finishes here before navigating further
                clientWindow.initModality(Modality.APPLICATION_MODAL);
                clientLayout.setId("root");
                clientWindow.setScene(clientScene);
                clientWindow.showAndWait();
                System.out.println("\n Refresh: "+MainApp.refresh);
                if(MainApp.refresh)
                {
                    refreshClientListPage(event);//Refresh the main list page
                }
            }
        });
        
                btnEditClient.setOnAction((event) -> {
            if(ClientListTable.selectedClient == null)
            {
                Alerts.displayAlert("Select Client", "Please choose a Client from the table to View/Edit");
            }
            else
            {
                ClientEditPage clEdit = new ClientEditPage();
                Client selected = ClientListTable.selectedClient;
                clEdit.setSelectedClient(selected);//pass the selected client
                FlowPane clViewLayout = clEdit.createClientViewPage();
                clEdit.setEditClientDataPage(); // Allow user to edit the fields
                clEdit.SetTextFieldValues();
                Scene clScene = new Scene(clViewLayout, 500, 500);
                clScene.getStylesheets().add("/styles/Styles.css");
                Stage clWindow = new Stage();
                clWindow.setTitle("Edit Client Details");
                clWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
                clViewLayout.setId("root");
                clWindow.setScene(clScene);
                clWindow.showAndWait();
                if(MainApp.refresh)
                {
                    refreshClientListPage(event);//Refresh  the page
                }
            }
    });
         
          btnNewClient.setOnAction((event) -> {
        ClientEditPage clCreate = new ClientEditPage();
        FlowPane clViewLayout = clCreate.createClientViewPage();
        clCreate.setCreateClientDataViewPage();// Allow user to edit or clear the fields
        Scene clScene = new Scene(clViewLayout, 500, 500);
        clScene.getStylesheets().add("/styles/Styles.css");
        Stage clWindow = new Stage();
        clWindow.setTitle("Create New Client");
        clWindow.initModality(Modality.APPLICATION_MODAL); // User Must finish and close this window to continue
        clViewLayout.setId("root");
        clWindow.setScene(clScene);
        clWindow.showAndWait();
        
        if(MainApp.refresh)
        {
            refreshClientListPage(event);//Refresh the main list page
        }
    });
        
          //ToolTip
          Tooltip clientTip = new Tooltip("View and Edit Client Data");
          Tooltip.install(mainListPane, clientTip);
          
        return mainListPane;
    }

    private void setupClientListScreen() {
//        mainListPane.setPadding(new Insets(0, 0, 0, 0));
//        mainListPane.setHgap(5);
//        mainListPane.setVgap(5);
//        mainListPane.setPrefSize(500, 900);
        mainListPane.setId("gp");
    }

    private void refreshClientListPage(ActionEvent event) {
            ClientListPage clientListPage = new ClientListPage();
            mainListPane = clientListPage.loadMainClientListScreen();
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
