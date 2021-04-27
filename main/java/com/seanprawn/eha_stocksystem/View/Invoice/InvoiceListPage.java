/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Controller.SceneController;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Invoice;
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
public class InvoiceListPage {
    public InvoiceListPage()
    {}
    
    GridPane invPane = new GridPane();
    Button btnNewInv = new Button("_Create Invoice");
    Button btnViewInv = new Button("_View Invoice");
    SceneController control = new SceneController();

    
    public void setInvoiceListPage(GridPane pane)
    {
        this.invPane = pane;
    }
    
    public GridPane getInvoiceListPage()
    {
        return this.invPane;
    }
    
    public GridPane loadMainInvoiceListPage()
    {   
        setupInvListScreen();
        Label header = new Label("Invoices");
        header.setId("header");
        InvoiceListTable list = new InvoiceListTable();
        GridPane invListTable = list.CreateInvoiceListTable();
        
        invPane.add(header, 0, 0);
        invPane.add(invListTable, 0, 1);
        //Create Buttons
        TilePane btnInvPane = new TilePane();
        btnInvPane.setOrientation(Orientation.HORIZONTAL);

        btnInvPane.getChildren().addAll(btnNewInv,btnViewInv);
        
        invPane.add(btnInvPane, 0, 3);
        
        btnViewInv.setOnAction((ActionEvent event) -> {
            if(InvoiceListTable.selectedInvoice == null)
            {
                Alerts.displayAlert("Select Invoice", "Please choose an Invoice from the table to View");
            }
            else
            {
                Invoice seleectedInv = InvoiceListTable.selectedInvoice;
                InvoiceViewPage invoiceView = new InvoiceViewPage();
                invoiceView.setSelectedInvoice(seleectedInv);
                invoiceView.setInvoiceView(); // Set the page to view only - not edit!
                invoiceView.SetTextFieldsEditable(Boolean.FALSE);
                FlowPane invLayout = invoiceView.createInvoicePage();
//                InvoiceViewPage.viewInvoice = 1;

                Scene invScene = new Scene(invLayout, 500, 730);
                invScene.getStylesheets().add("/styles/Styles.css");
                Stage invoiceWindow = new Stage();
                invoiceWindow.setTitle("View Invoice");
                invoiceWindow.initModality(Modality.APPLICATION_MODAL);
                invLayout.setId("root");
                invoiceWindow.setScene(invScene);
                invoiceWindow.showAndWait();
            }
        });
        
        btnNewInv.setOnAction((event) -> {
            InvoiceCreatePage invoiceNew = new InvoiceCreatePage();
            invoiceNew.setInvoiceCreate();
            FlowPane invLayout = invoiceNew.createInvoicePage();
//            InvoiceCreatePage.viewInvoice = 0;

            Scene invScene = new Scene(invLayout, 500, 730);
            
            invScene.getStylesheets().add("/styles/Styles.css");
            Stage invoiceWindow = new Stage();
            invoiceWindow.setTitle("Create New Invoice");
            invoiceWindow.initModality(Modality.APPLICATION_MODAL);
            invLayout.setId("root");
            invoiceWindow.setScene(invScene);
            invoiceWindow.showAndWait();
                
            if(MainApp.refresh)
            {
                refreshInvoiceListPage(event);//Refresh the main list page
            }
        });
        
          //ToolTip
          Tooltip invTip = new Tooltip("View and Edit Invoice Data");
          Tooltip.install(invPane, invTip);
          
        return invPane;
    }

    private void setupInvListScreen() {
        invPane.setId("gp");
//        invPane.setHgap(5);
//        invPane.setVgap(5);
    }

    private void refreshInvoiceListPage(ActionEvent event) {
           InvoiceListPage invListPage = new InvoiceListPage();
            invPane = invListPage.loadMainInvoiceListPage();
            Stage newStage = MainApp.getCurrentMainStage();
//            Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            control.setMainStage(newStage);
            control.loadMainApplication(invPane);
            Scene currentScene = newStage.getScene();
            control.setNewMainPageScene(currentScene);
            newStage.requestFocus();
            MainApp.refresh = false;
    }
}
