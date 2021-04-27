/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.Model.Client;
import com.seanprawn.eha_stocksystem.View.Client.ClientListTable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvClientListSearchTable {
    public static Client result;
     public static Client displayClientListSearch()
    {
        
        Stage clientSearchWindow = new Stage();
        
        
        clientSearchWindow.initModality(Modality.APPLICATION_MODAL);
        clientSearchWindow.setTitle("Select Client");
        
        ClientListTable listTable = new ClientListTable();
        GridPane table = listTable.CreateClientListTable();
        TableView<Client> tableG = listTable.GetClientListTable();
        
        Button closeBtn = new Button("Cancel");
        Button saveBtn = new Button("Save");
         
        closeBtn.setOnAction(e -> {
            result = null;
            clientSearchWindow.close();
        });
        
         saveBtn.setOnAction(e -> {
        result = (Client) tableG.getSelectionModel().getSelectedItem();
             System.out.println("Result Client == "+String.valueOf(result.getClId()));
            clientSearchWindow.close();
        });

        GridPane layout = new GridPane();
        layout.add(table, 0, 0);
        layout.add(closeBtn, 0, 1);
        layout.add(saveBtn, 0, 2);
        
        Scene scene = new Scene(layout);
        clientSearchWindow.setScene(scene);
        clientSearchWindow.showAndWait();
         
        return result;
    }
}
