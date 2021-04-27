/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Client;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.ClientRef;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class ClientRefSearchTable {
    public static ClientRef result;
    public static ClientRef selected;
    TableView table = new TableView<>();
    
    public ClientRef DisplayClientRef()
    {
         Stage searchWindow = new Stage();
        
        
        searchWindow.initModality(Modality.APPLICATION_MODAL);
        searchWindow.setTitle("Select Client Reference");
        
        table = CreateClientRefListTable();
        
        Button closeBtn = new Button("Cancel");
        Button saveBtn = new Button("Save");
         
        closeBtn.setOnAction(e -> {
            result = null;
            searchWindow.close();
        });
        
         saveBtn.setOnAction(e -> {
        result = (ClientRef) table.getSelectionModel().getSelectedItem();
            searchWindow.close();
        });

        GridPane layout = new GridPane();
        layout.add(table, 0, 0);
        layout.add(closeBtn, 0, 1);
        layout.add(saveBtn, 0, 2);
        
        Scene scene = new Scene(layout);
        searchWindow.setScene(scene);
        searchWindow.showAndWait();
        return result;
    }

    private TableView CreateClientRefListTable() {
                ObservableList<ClientRef> suplList = getList(); // create the list
                table.setItems(suplList); // add the list irems to the table
                
            //Setup the Table Columns
        TableColumn<ClientRef,Integer> clRef = new TableColumn<>("Client Ref ID");
        clRef.setCellValueFactory(new PropertyValueFactory("RefClRef"));
        
        TableColumn<Supplier,String> clRefDesc = new TableColumn<>("Client Ref Description");
        clRefDesc.setCellValueFactory(new PropertyValueFactory("RefClRefDesc"));
                
              //Add the clumns to the Table
        table.getColumns().add(clRef);
        table.getColumns().add(clRefDesc);
        
          //Set OnClick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click
        {
            onSelectClientRef();
        }
    });
        
        return table;
    }

    private static ObservableList<ClientRef> getList() {
        ObservableList<ClientRef> clResult = DatabaseMethods.getClientRefList();
        return clResult;
    }

    private void onSelectClientRef() {
         if (table.getSelectionModel().getSelectedItem() != null) 
         {
            selected = (ClientRef) table.getSelectionModel().getSelectedItem();
         }
    }
}
