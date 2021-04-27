/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Client;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Client;
import java.math.BigInteger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 */
public class ClientListTable {
    TableView<Client> table = new TableView<>();
     public static Client selectedClient;
    public void ClientListTable()
    {
    }
    TextField txtSearch = new TextField();
    Button btnClear = new Button("Clear Search");
    /**
     * Creates a TableView object, gets data from database and adds it to the table
     * Returns the Table and all data to the calling method
     * @return table
     */
    public GridPane CreateClientListTable()
    {
        
        ObservableList<Client> clientList = getList();
        table.setItems(clientList);
        
        txtSearch.setPromptText("Search Fields");
        //clear the Search Field
        btnClear.setOnAction((event) ->{
            txtSearch.clear();
        });
        
        GridPane tablePane = new GridPane();
        FlowPane searchP = new FlowPane(txtSearch, btnClear);
        tablePane.add(searchP,0,0);
        tablePane.add(table,0,1);
        Tooltip serchTip = new Tooltip("Search the ID, or Name Fields of this table");
        Tooltip.install(searchP, serchTip);
        
        //Search the Table
        //Wrap Client list in a Filtered list
          FilteredList<Client> filteredData = new FilteredList<>(clientList, p -> true);
        
         // Set the filter Predicate whenever the filter changes.
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Client -> {
                
                // If filter text is empty, display all Clients as usual.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                   // Compare Supplement ID of every Supplement with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (String.valueOf(Client.getClId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Client ID.
                }
                else if (Client.getClName().toLowerCase().contains(lowerCaseFilter))
                {
                    return true; // Filter matches Client First Name.
                }
                else if (Client.getClSurname().toLowerCase().contains(lowerCaseFilter))
                {
                    return true; // Filter matches Client Last Name.
                }

                return false; // Does not match.
            });
                 //Wrap the FilteredList in a SortedList. 
                SortedList<Client> sortedData = new SortedList<>(filteredData);

                //Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(table.comparatorProperty());

                // Add sorted (and filtered) data to the table.
                table.setItems(sortedData);
        });
        
        //Setup the Table Columns
        TableColumn<Client,BigInteger> clId = new TableColumn<>("Client ID");
        clId.setCellValueFactory(new PropertyValueFactory("ClId"));
        
        TableColumn<Client,String> clFirstName = new TableColumn<>("First Name");
        clFirstName.setCellValueFactory(new PropertyValueFactory("ClName"));
        
        TableColumn<Client,String> clLastName = new TableColumn<>("Last Name");
        clLastName.setCellValueFactory(new PropertyValueFactory("ClSurname"));

        TableColumn<Client,String> clAddress = new TableColumn<>("Adress");
        clAddress.setCellValueFactory(new PropertyValueFactory("ClAddress"));
        
        TableColumn<Client,Integer> clAddrCode = new TableColumn<>("Address Code");
        clAddrCode.setCellValueFactory(new PropertyValueFactory("ClAddrCode"));
        
        TableColumn<Client,String> clTelHome = new TableColumn<>("Tel Home");
        clTelHome.setCellValueFactory(new PropertyValueFactory("ClTelHome"));
        
        TableColumn<Client,String> clTelWork = new TableColumn<>("Tel Work");
        clTelWork.setCellValueFactory(new PropertyValueFactory("ClTelWork"));
        
        TableColumn<Client,String> clTelCell = new TableColumn<>("Tel Cell");
        clTelCell.setCellValueFactory(new PropertyValueFactory("ClTelCell"));
        
        TableColumn<Client,String> clEmail = new TableColumn<>("Email");
        clEmail.setCellValueFactory(new PropertyValueFactory("ClEmail"));
        
        TableColumn<Client,Integer> clRef = new TableColumn<>("Client Reference");
        clRef.setCellValueFactory(new PropertyValueFactory("ClRef"));
        
       
        
        //Add the clumns to the Table
        table.getColumns().add(clId);
        table.getColumns().add(clFirstName);
        table.getColumns().add(clLastName); 
        table.getColumns().add(clAddress); 
        table.getColumns().add(clAddrCode); 
        table.getColumns().add(clTelHome); 
        table.getColumns().add(clTelWork); 
        table.getColumns().add(clTelCell); 
        table.getColumns().add(clEmail);
        table.getColumns().add(clRef);
        
        //Set Onclick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click selects a Client
        {
            onSelectClient();
        }
    });
        
        return tablePane;
    
    }

    private ObservableList<Client> getList() {
        ObservableList<Client> list = DatabaseMethods.getClientList();
        return list;
    }
    
    public void onSelectClient() 
    {
        // check the table's selected item and get selected item
        if (table.getSelectionModel().getSelectedItem() != null) 
        {
            selectedClient = table.getSelectionModel().getSelectedItem();
//            System.out.println("\n Client Selected is : \n"+selectedClient.getClName()+" "+selectedClient.getClSurname());
        }
    }
    
    public TableView<Client> GetClientListTable()
    {
        return this.table;
    }
}
