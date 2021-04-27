/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplement;

import com.seanprawn.eha_stocksystem.Controller.DatabaseMethods;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import com.seanprawn.eha_stocksystem.Controller.ValidateFields;
import static com.seanprawn.eha_stocksystem.Controller.ValidateFields.validateNumberTextField;
import com.seanprawn.eha_stocksystem.MainApp;
import com.seanprawn.eha_stocksystem.Model.Messages;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import com.seanprawn.eha_stocksystem.View.Alerts;
import java.text.DecimalFormat;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 * Sets up the nodes for Supplement, and populates with data
 * Nodes are added to a GridPane Layout
 * 
 * suppl_num - int
 * suppl_id - str
 * supl_description - str
 * supl_cost_excl - float?
 * supl_cost_incl - float?
 * supl_perc_inc - float?
 * supl_cost_client - float?
 * supl_min_levels - int
 * supl_stock_levels - int
 * supl_nappi_code - str
 * supl_supplier_id - int
 */
public class SupplementViewPage extends Messages{
    
    GridPane supplementGrid = new GridPane();
    FlowPane supplementView = new FlowPane();
    Supplement selectedSupplement;
    boolean checkEdit = false; // Toggles the Edit Button to set for Edit or for Save new - false = edit , true = save new
    boolean save_edit = false; // if false - Then save new else if True - then edit supplement
    Supplement saveSupl = new Supplement();
    String errorMessage = "Please enter correct data in the fields above";
    DecimalFormat df = new DecimalFormat("#.00");
    
    //Setup the buttons
    Button btnEdit = new Button("Edit Supplement");
    Button btnDelete = new Button("Delete");
    Button btnClear = new Button("Clear Fields");
    Button btnClose = new Button("Close");
    Button btnSave = new Button("Save Changes");
    
    //Setup the text fields
       Text txtSupNum = new Text();
       TextField txtSupId = new TextField();
       TextField txtSupDesc = new TextField();
       TextField txtSupCostExc = new TextField();
       TextField txtSupCostInc = new TextField();
       TextField txtSupPercInt = new TextField();
       TextField txtSupCostClient = new TextField();
       TextField txtSupMinLevels = new TextField();
       TextField txtSupStockLevels = new TextField();
       TextField txtSupNappiCode = new TextField();
       TextField txtSupSupplierId = new TextField();
       Label txtError = new Label();
       
       
              //Setup the Labels
       Label lblSupNum = new Label("Supplement Number:");
       Label lblSupId = new Label("Supplement ID:");
       Label lblSupDesc = new Label("Supplement Description:");
       Label lblSupCostExc = new Label("Supplement Cost (Excl):");
       Label lblSupCostInc = new Label("Supplement Cost (Incl):");
       Label lblSupPercInc = new Label("Supplement Perc (Incl):");
       Label lblSupCostClient = new Label("Supplement Cost to Client:");
       Label lblSupMinLevels = new Label("Supplement Min Levels:");
       Label lblSupStockLevels = new Label("Supplement Stock Levels:");
       Label lblSupNappiCode = new Label("Supplement Nappi Code:");
       Label lblSupSupplierId = new Label("Supplement Supplier ID:");
       
       
    public FlowPane getSupplementView()
    {
        return this.supplementView;
        
    }
    
    public void setSupplementView(FlowPane supplement)
    {
        this.supplementView = supplement;
    }
    
    public void SupplementViewPage()
    {
        
    }
    
    public void setSelectedSupplement(Supplement supplement)
    {
        this.selectedSupplement = supplement;
    }
    
    
    /**
     * Sets up the Nodes, and adds them to a GridPane layout
     * The layout is returned to the calling method
     * @return GridPane
     */
    public FlowPane setupViewSupplement()
   {
//       supplementGrid.setId("root");
       btnClear.setVisible(false);
       btnSave.setVisible(false);
       txtError.setId("error");
       txtSupSupplierId.setEditable(false);
       
       Tooltip suplIdTip = new Tooltip("Click to select a Supplier");
       Tooltip.install(txtSupSupplierId, suplIdTip);
       Tooltip toggleTip = new Tooltip("Click to toggle Edit/ View");
       Tooltip.install(btnEdit, toggleTip);
       Tooltip backTip = new Tooltip("Click to close this component and go back to list");
       Tooltip.install(btnClose, backTip);
       Tooltip saveTip = new Tooltip("Click to Save this Supplement to the database");
       Tooltip.install(btnSave, saveTip);
       Tooltip delTip = new Tooltip("Click to Delete this Supplement from the database");
       Tooltip.install(btnDelete, delTip);
       Tooltip clearTip = new Tooltip("Click to Clear all fields and start again");
       Tooltip.install(btnClear, clearTip);
//       txtSupSupplierId.editableProperty().set(false);
       //Add Labels and TextFields to layout
       AddAllToLayout();
       
       
       //OnClick Handlers for Buttons
       btnClose.setOnAction((event)->{
           Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
           stage.close();
       });
       
       btnClear.setOnAction((event)->{
        //Clears all the text fields
        txtSupId.clear();
        txtSupDesc.clear();
        txtSupCostExc.clear();
        txtSupCostInc.clear();
        txtSupPercInt.clear();
        txtSupCostClient.clear();
        txtSupMinLevels.clear();
        txtSupStockLevels.clear();
        txtSupNappiCode.clear();
        txtSupSupplierId.clear();
       });
       
       btnDelete.setOnAction((event)->{
            Supplement supl = getEditedTextFieldValues();
            int result = 0;
            Boolean check = Alerts.displayVerifyAlert("Delete Supplement", "Are You sure you want to delete : "+supl.getSuplId()+"?");
            if (check) 
            {    
                result = DatabaseMethods.DeleteSupplement(supl);
                if(result>0)
                {
                    Alerts.displayAlert("Success", "Supplement Deleted Successfully");
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                     MainApp.refresh = true;
                    stage.close();
                }
                else{
                    Alerts.displayAlert("Failed", "Supplement Failed to Delete, please try again");
                }
            }
       });
       
       btnEdit.setOnAction((event)->{
           if(!checkEdit)
           {
               
               btnEdit.setText("View Supplement");
               btnSave.setVisible(true);
               btnDelete.setVisible(false);
               SetTextFieldValues();
               SetTextFieldsEditable(Boolean.TRUE);
               checkEdit = true;
               save_edit = true;
               MainApp.refresh = true;
//               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//               stage.setTitle("Edit Supplement");
           }
           else if(checkEdit)
         {
               btnEdit.setText("Edit Supplement");
               btnSave.setVisible(false);
               btnDelete.setVisible(true);
               SetTextFieldsEditable(Boolean.FALSE);
               checkEdit = false;
               save_edit = false;
               MainApp.refresh = true;
//               Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//               stage.setTitle("View Supplement");
           }
        });
       
       btnSave.setOnAction((event)->{
           int valid = ValidateAndGetSupplementFields();
           int result = 0;
           boolean check = false;
           if(valid == 0) 
           {
               check = Alerts.displayVerifyAlert("Save Supplement", "Are You sure you want to Save : "+saveSupl.getSuplId()+"?");
           }
           if(check) 
           {
              if(!save_edit) result = DatabaseMethods.SaveNewSupplement(saveSupl);
              else result = DatabaseMethods.EditSupplement(saveSupl);
              
               if(result>0)
               {
                   Alerts.displayAlert("Success", "Supplement Saved Successfully");
                   Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    MainApp.refresh = true;
                   stage.close();
               }
               else{
                 Alerts.displayAlert("Failed", "Supplement Failed to Update, please try again");
               }
           }
       });
       
       txtSupSupplierId.setOnMousePressed((event) ->{
           if(checkEdit)
           {
               Supplier selected = SupplSupplierListSearchTable.DisplaySupplierListSearch();
               if(selected != null) txtSupSupplierId.setText(String.valueOf(selected.getSuplId()));
           }
       });
       
        return supplementView; 
   }

    public void SetTextFieldValues() {
       txtSupNum.setText(String.valueOf(this.selectedSupplement.getSuplNum()));
       txtSupId.setText(String.valueOf(this.selectedSupplement.getSuplId()));
       txtSupDesc.setText(this.selectedSupplement.getSuplDesc());
       txtSupCostExc.setText(String.valueOf(df.format(this.selectedSupplement.getSuplCostExcl())));
       txtSupCostInc.setText(String.valueOf(df.format(this.selectedSupplement.getSuplCostIncl())));
       txtSupPercInt.setText(String.valueOf(df.format(this.selectedSupplement.getSuplPercIncl())));
       txtSupCostClient.setText(String.valueOf(df.format(this.selectedSupplement.getSuplCostClient())));
       txtSupMinLevels.setText(String.valueOf(this.selectedSupplement.getSuplMinLevels()));
       txtSupStockLevels.setText(String.valueOf(this.selectedSupplement.getSuplStockLevels()));
       txtSupNappiCode.setText(String.valueOf(this.selectedSupplement.getsuplNappiCode()));
       txtSupSupplierId.setText(String.valueOf(this.selectedSupplement.getSuplSupplierId()));
    }

       public Supplement getEditedTextFieldValues() {
        Supplement supl = new Supplement();
        if(txtSupNum.getText() != null) supl.setSupplNum(Integer.valueOf(txtSupNum.getText()));
        if(txtSupId.getText() != null) supl.setSupplId(txtSupId.getText());
        if(txtSupDesc.getText() != null) supl.setSupplDesc(txtSupDesc.getText());
        if(txtSupCostExc.getText() != null) supl.setSuplCostExcl(Double.valueOf(txtSupCostExc.getText()));
        if(txtSupCostInc.getText() != null) supl.setSuplCostIncl(Double.valueOf(txtSupCostInc.getText()));
        if(txtSupPercInt.getText() != null) supl.setSuplPercIncl(Double.valueOf(txtSupPercInt.getText()));
        if(txtSupCostClient.getText() != null) supl.setSuplCostClient(Double.valueOf(txtSupCostClient.getText()));
        if(txtSupMinLevels.getText() != null) supl.setSuplMinLevels(Integer.valueOf(txtSupMinLevels.getText()));
        if(txtSupStockLevels.getText() != null)  supl.setSuplStockLevels(Integer.valueOf(txtSupStockLevels.getText()));
        if(txtSupNappiCode.getText() != null) supl.setsuplNappiCode(txtSupNappiCode.getText());
        if(txtSupSupplierId.getText() != null) supl.setSuplSupplierId(Integer.valueOf(txtSupSupplierId.getText()));
        return supl;
    }
    
    public void SetTextFieldsEditable(Boolean flag) {
       txtSupId.setEditable(flag);
       txtSupDesc.setEditable(flag);
       txtSupCostExc.setEditable(flag);
       txtSupCostInc.setEditable(flag);
       txtSupPercInt.setEditable(flag);
       txtSupCostClient.setEditable(flag);
       txtSupMinLevels.setEditable(flag);
       txtSupStockLevels.setEditable(flag);
       txtSupNappiCode.setEditable(flag);
//       txtSupSupplierId.setEditable(flag);
    }

    private void AddAllToLayout() {
        
       supplementGrid.add(lblSupNum, 0, 0);
       supplementGrid.add(txtSupNum, 1, 0);
       supplementGrid.add(lblSupId, 0, 1);
       supplementGrid.add(txtSupId, 1, 1);
       supplementGrid.add(lblSupDesc, 0, 2);
       supplementGrid.add(txtSupDesc, 1, 2);
       
       supplementGrid.add(lblSupCostExc, 0, 3);
       supplementGrid.add(txtSupCostExc, 1, 3);
       supplementGrid.add(lblSupCostInc, 0, 4);
       supplementGrid.add(txtSupCostInc, 1, 4);
       supplementGrid.add(lblSupPercInc, 0, 5);
       supplementGrid.add(txtSupPercInt, 1, 5);
       
       supplementGrid.add(lblSupCostClient, 0, 6);
       supplementGrid.add(txtSupCostClient, 1, 6);
       supplementGrid.add(lblSupMinLevels, 0, 7);
       supplementGrid.add(txtSupMinLevels, 1, 7);
       supplementGrid.add(lblSupStockLevels, 0, 8);
       supplementGrid.add(txtSupStockLevels, 1, 8);
       
       supplementGrid.add(lblSupNappiCode, 0, 9);
       supplementGrid.add(txtSupNappiCode, 1, 9);
       supplementGrid.add(lblSupSupplierId, 0, 10);
       supplementGrid.add(txtSupSupplierId, 1, 10);
       supplementGrid.add(txtError, 1, 11);
       
       //Add Buttons to layout
       supplementGrid.add(btnClear, 0, 13);
       supplementGrid.add(btnEdit, 1, 13);
       supplementGrid.add(btnClose, 0, 14);
       supplementGrid.add(btnDelete, 1, 14);
       supplementGrid.add(btnSave, 0, 16);
        
        supplementView.setOrientation(Orientation.VERTICAL);
        supplementView.setVgap(5);
        supplementView.getChildren().addAll(supplementGrid);
    }
    
    /**
     * Gets the values from the input fields, and validates them according to type (string, varchar, or Number)
     * If an error is found, the validation process will stop and 
     * focus to the input textbox in question
     * If there is an error then the error Variable gets incremented
     * @return error
     * 0 - Validated
     * 1 - Not valid
     */
    public int ValidateAndGetSupplementFields()
    {
        int error = 0;
       
        if(txtSupNum.getText() == null || txtSupNum.getText().equals("")) 
        {
            saveSupl.setSupplNum(Integer.valueOf("0"));
        }else
        {
            saveSupl.setSupplNum(Integer.valueOf(txtSupNum.getText())); //pk - not expected to be null!
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSupId.getText())) 
        {
            error++;
            txtSupId.requestFocus();
            txtSupId.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_ID);
            return error;
        }else
        {
            txtSupId.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSupplId(txtSupId.getText()); //Save the value to supplement object
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSupDesc.getText())) 
        {
            error++;
            txtSupDesc.requestFocus();
            txtSupDesc.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_DESC);
            return error;
        }else
        {
            txtSupDesc.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSupplDesc(txtSupDesc.getText()); //Save the value to supplement object
        }
        
        
        if(!validateNumberTextField(txtSupCostExc.getText())) 
        {
            error++;
            txtSupCostExc.requestFocus();
            txtSupCostExc.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_PRICE);
            return error;
        }else
        {
            txtSupCostExc.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplCostExcl(Double.valueOf(txtSupCostExc.getText())); //save the value
        }
        
         if(!validateNumberTextField(txtSupCostInc.getText())) 
        {
            error++;
            txtSupCostInc.requestFocus();
            txtSupCostInc.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_PRICE);
            return error;
        }else
        {
            txtSupCostInc.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplCostIncl(Double.valueOf(txtSupCostInc.getText())); //save the value
        }
         
           if(!validateNumberTextField(txtSupPercInt.getText())) 
        {
            error++;
            txtSupPercInt.requestFocus();
            txtSupPercInt.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_PRICE);
            return error;
        }else
        {
            txtSupPercInt.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplPercIncl(Double.valueOf(txtSupPercInt.getText())); //save the value
        }
           
              if(!validateNumberTextField(txtSupCostClient.getText())) 
        {
            error++;
            txtSupCostClient.requestFocus();
            txtSupCostClient.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_PRICE);
            return error;
        }else
        {
            txtSupCostClient.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplCostClient(Double.valueOf(txtSupCostClient.getText())); //save the value
        }
              
        if(!validateNumberTextField(txtSupMinLevels.getText())) 
        {
            error++;
            txtSupMinLevels.requestFocus();
            txtSupMinLevels.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_NUM);
            return error;
        }else
        {
            txtSupMinLevels.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplMinLevels(Integer.valueOf(txtSupMinLevels.getText())); //save the value
        }
        
        if(!validateNumberTextField(txtSupStockLevels.getText())) 
        {
            error++;
            txtSupStockLevels.requestFocus();
            txtSupStockLevels.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLEMENT_NUM);
            return error;
        }else
        {
            txtSupStockLevels.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplStockLevels(Integer.valueOf(txtSupStockLevels.getText())); //save the value
        }
        
        if(!ValidateFields.validateStringCharTextField(txtSupNappiCode.getText())) 
        {
            error++;
            txtSupNappiCode.requestFocus();
            txtSupNappiCode.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_SUPLIER_BRANCH_CODE);
            return error;
        }else
        {
            txtSupNappiCode.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setsuplNappiCode(txtSupNappiCode.getText()); //Save the value to supplement object
        }
        
        if(!validateNumberTextField(txtSupSupplierId.getText())) 
        {
            error++;
            txtSupSupplierId.requestFocus();
            txtSupSupplierId.setStyle("-fx-border-color: red;");
            
            txtError.setText(ERR_ID);
            return error;
        }else
        {
            txtSupSupplierId.setStyle("-fx-border-color: transparent;");
            txtError.setText("");
            saveSupl.setSuplSupplierId(Integer.valueOf(txtSupSupplierId.getText())); //save the value
        }
        
        return error;
    }
}
