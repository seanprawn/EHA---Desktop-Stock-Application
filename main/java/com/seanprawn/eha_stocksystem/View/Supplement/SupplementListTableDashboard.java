/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SupplementListTableDashboard {
     TableView<Supplement> table = new TableView<>();
    public static Supplement selectedSupplement;
    public void SupplementListTable()
    {
        
    }
    
    public TableView CreateSupplementListTable(ObservableList<Supplement> suplist)
    {
        table.setItems(suplist);
        table.setPrefWidth(500);

        Tooltip splTableTool = new Tooltip("Double Click to select and view Supplement Details");
        Tooltip.install(table, splTableTool);
        //Setup the Table Columns
        TableColumn<Supplement,Integer> suplNumCol = new TableColumn<>("Number");
        suplNumCol.setCellValueFactory(new PropertyValueFactory("SuplNum"));

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
        
        suplStockLevelsCol.setStyle("-fx-background-color: #E58B8B");
       
        //Add the clumns to the Table
        table.getColumns().add(suplIdCol);
        table.getColumns().add(suplDescCol); 
        table.getColumns().add(suplMinLevelsCol); 
        table.getColumns().add(suplStockLevelsCol);
        
        //Set Onclick Listener for Table
        table.setOnMouseClicked((MouseEvent event) -> {
        if (event.getClickCount() > 0) //Single click selects a supplement
        {
//            onSelectSupplement();
            if (table.getSelectionModel().getSelectedItem() != null) 
            {
            selectedSupplement = table.getSelectionModel().getSelectedItem();
            System.out.println("\n Supplement Selected is : \n"+selectedSupplement.getSuplNum());
            }
        }
        if (event.getClickCount() > 1) //Double click selects a supplement and opens view of supplement
        {
            onSelectSupplement();
        }
    });

        return table;
    }

    ObservableList<Supplement> getList() {
        ObservableList<Supplement> list = DatabaseMethods.getSupplementList();
        return list;
    }
    
    public ObservableList<Supplement> getLowStockList() {
        ObservableList<Supplement> list = DatabaseMethods.getSupplementLowStockList();
        return list;
    }
    
    public void onSelectSupplement() {
    // check the table's selected item and get selected item
    if (table.getSelectionModel().getSelectedItem() != null) 
    {
        selectedSupplement = table.getSelectionModel().getSelectedItem();
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
    }
}
}
