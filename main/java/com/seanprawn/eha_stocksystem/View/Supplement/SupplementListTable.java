/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * Loads a list of Supplements to view directly in a table
 * @Return returns a  TableView object
 */
public class SupplementListTable {
    TableView<Supplement> table = new TableView<>();
    public static Supplement selectedSupplement;
    Label lblSearch = new Label("Search ID");
    TextField txtSearch = new TextField();
    Button btnClear = new Button("Clear Search");
    public void SupplementListTable()
    {
        
    }
    
    public GridPane CreateSupplementListTable(ObservableList<Supplement> suplist)
    {
        table.setItems(suplist);
        txtSearch.setPromptText("Search ID");
        //clear the Search Field
        btnClear.setOnAction((event) ->{
            txtSearch.clear();
        });
        
        GridPane tablePane = new GridPane();
        FlowPane searchP = new FlowPane(txtSearch, btnClear);
        tablePane.add(searchP,0,0);
        tablePane.add(table,0,1);
        Tooltip serchTip = new Tooltip("Search the ID Field of this table");
        Tooltip.install(searchP, serchTip);
        
        //Search the Table
        //Wrap supplement list in a Filtered list
          FilteredList<Supplement> filteredData = new FilteredList<>(suplist, p -> true);
        
         // Set the filter Predicate whenever the filter changes.
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Supplement -> {
                
                // If filter text is empty, display all Supplements as usual.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                   // Compare Supplement ID of every Supplement with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (Supplement.getSuplId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Supplement ID.
                } 

                return false; // Does not match.
            });
                 // 3. Wrap the FilteredList in a SortedList. 
                SortedList<Supplement> sortedData = new SortedList<>(filteredData);

                // 4. Bind the SortedList comparator to the TableView comparator.
                sortedData.comparatorProperty().bind(table.comparatorProperty());

                // 5. Add sorted (and filtered) data to the table.
                table.setItems(sortedData);
        });
        
                
        //Setup the Table Columns
        TableColumn<Supplement,Integer> suplNumCol = new TableColumn<>("Number");
        suplNumCol.setCellValueFactory(new PropertyValueFactory("SuplNum"));
//        valuColumn.setCellFactory(TextFieldTableCell.<FormTokens>forTableColumn());
        TableColumn<Supplement,String> suplIdCol = new TableColumn<>("Id");
        suplIdCol.setCellValueFactory(new PropertyValueFactory("SuplId"));
        
        TableColumn<Supplement,String> suplDescCol = new TableColumn<>("Description");
        suplDescCol.setCellValueFactory(new PropertyValueFactory("SuplDesc"));

        TableColumn<Supplement,Double> suplCostExclCol = new TableColumn<>("Cost Excl");
        suplCostExclCol.setCellValueFactory(new PropertyValueFactory("SuplCostExcl"));
        
        TableColumn<Supplement,Double> suplCostInclCol = new TableColumn<>("Cost Incl");
        suplCostInclCol.setCellValueFactory(new PropertyValueFactory("SuplCostIncl"));
        
        TableColumn<Supplement,Double> suplPercInclCol = new TableColumn<>("Perc Incl");
        suplPercInclCol.setCellValueFactory(new PropertyValueFactory("SuplPercIncl"));
        
        TableColumn<Supplement,Double> suplCostClientCol = new TableColumn<>("Cost to Client");
        suplCostClientCol.setCellValueFactory(new PropertyValueFactory("SuplCostClient"));
        
        TableColumn<Supplement,Integer> suplMinLevelsCol = new TableColumn<>("Min Levels");
        suplMinLevelsCol.setCellValueFactory(new PropertyValueFactory("SuplMinLevels"));
        
        TableColumn<Supplement,Integer> suplStockLevelsCol = new TableColumn<>("Stock Levels");
        suplStockLevelsCol.setCellValueFactory(new PropertyValueFactory("SuplStockLevels"));
        
        TableColumn<Supplement,String> suplNappiCodeCol = new TableColumn<>("Nappi Code");
        suplNappiCodeCol.setCellValueFactory(new PropertyValueFactory("SuplNappiCode"));
        
        TableColumn<Supplement,Integer> suplSupplierIdCol = new TableColumn<>("Supplier ID");
        suplSupplierIdCol.setCellValueFactory(new PropertyValueFactory("SuplSupplierId"));
        
        //Add the clumns to the Table
        table.getColumns().add(suplNumCol);
        table.getColumns().add(suplIdCol);
        table.getColumns().add(suplDescCol); 
        table.getColumns().add(suplCostExclCol); 
        table.getColumns().add(suplCostInclCol); 
        table.getColumns().add(suplPercInclCol); 
        table.getColumns().add(suplCostClientCol); 
        table.getColumns().add(suplMinLevelsCol); 
        table.getColumns().add(suplStockLevelsCol);
        table.getColumns().add(suplNappiCodeCol);
        table.getColumns().add(suplSupplierIdCol);
        
        //Set Onclick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click selects a supplement
        {
            onSelectSupplement();
        }
    });

        return tablePane;
    }

    ObservableList<Supplement> getList() {
        ObservableList<Supplement> list = DatabaseMethods.getSupplementList();
//        System.out.println("List[0] supl cost  == \n"+list.get(0).getSuplCostExcl());
        return list;
    }
    
    public ObservableList<Supplement> getLowStockList() {
        ObservableList<Supplement> list = DatabaseMethods.getSupplementLowStockList();
//        System.out.println("List[0] supl cost  == \n"+list.get(0).getSuplCostExcl());
        return list;
    }
    
    public void onSelectSupplement() {
    // check the table's selected item and get selected item
    if (table.getSelectionModel().getSelectedItem() != null) 
    {
        selectedSupplement = table.getSelectionModel().getSelectedItem();
//        System.out.println("\n Supplement Selected is : \n"+selectedSupplement.getSuplNum());
    }
}
}