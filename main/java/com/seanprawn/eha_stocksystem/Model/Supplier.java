/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 ** Properties *********************************
 * supplier_id - int
 * supplier_code - text
 * supplier_contact_person - text
 * supplier_tel = text
 * supplier_cell - text
 * supplier_fax - text
 * supplier_email - text
 * supplier_bank - text
 * supplier_branch_code - int
 * supplier_account_number - int
 * supplier_account_type - text
 * supplier_comments - text
 */
public class Supplier {
    //Declare Properties
    private IntegerProperty suplId;
    private StringProperty suplCode;
    private StringProperty suplContact;
    private StringProperty suplTel;
    private StringProperty suplCell;
    private StringProperty suplFax;
    private StringProperty suplEmail;
    private StringProperty suplBank;
    private IntegerProperty suplBranchCode;
    private IntegerProperty suplAccNum;
    private StringProperty suplAccType;
    private StringProperty suplComments;
    
    //Setup Supplier ID Property
    public void setSupplId(Integer value) 
    { 
        SuplIdProperty().set(value); 
    }
    public int getSuplId() 
    { 
        return SuplIdProperty().get(); 
    }
    public IntegerProperty SuplIdProperty() 
    {
        
        if (suplId == null) suplId = new SimpleIntegerProperty(this, "SuplId");
        return suplId;
     }
    
     //Setup Supplier Code Property
    public void setSupplCode(String value) 
    { 
        SuplCodeProperty().set(value); 
    }
    public String getSuplCode() 
    { 
        return SuplCodeProperty().get(); 
    }
    public StringProperty SuplCodeProperty() 
    {
        if (suplCode == null) suplCode = new SimpleStringProperty(this, "SuplCode");
        return suplCode;
     }
    
     //Setup Supplier Contact Property
    public void setSuplContact(String value) 
    { 
        SuplContactProperty().set(value); 
    }
    public String getSuplContact() 
    { 
        return SuplContactProperty().get(); 
    }
    public StringProperty SuplContactProperty() 
    {
        if (suplContact == null) suplContact = new SimpleStringProperty(this, "SuplContact");
        return suplContact;
     }
    
     //Setup Supplier Tel Property
    public void setSuplTel(String value) 
    { 
        SuplTelProperty().set(value); 
    }
    public String getSuplTel() 
    { 
        return SuplTelProperty().get(); 
    }
    public StringProperty SuplTelProperty() 
    {
        if (suplTel == null) suplTel = new SimpleStringProperty(this, "SuplTel");
        return suplTel;
     }
    
     //Setup Supplier Cell Property
    public void setSuplCell(String value) 
    { 
        SuplCellProperty().set(value); 
    }
    public String getSuplCell() 
    { 
        return SuplCellProperty().get(); 
    }
    public StringProperty SuplCellProperty() 
    {
        if (suplCell == null) suplCell = new SimpleStringProperty(this, "SuplCell");
        return suplCell;
     }
    
     //Setup Supplier Fax Property
    public void setSuplFax(String value) 
    { 
        SuplFaxProperty().set(value); 
    }
    public String getSuplFax() 
    { 
        return SuplFaxProperty().get(); 
    }
    public StringProperty SuplFaxProperty() 
    {
        if (suplFax == null) suplFax = new SimpleStringProperty(this, "SuplFax");
        return suplFax;
     }
    
     //Setup Supplier Email Property
    public void setSuplEmail(String value) 
    { 
        SuplEmailProperty().set(value); 
    }
    public String getSuplEmail() 
    { 
        return SuplEmailProperty().get(); 
    }
    public StringProperty SuplEmailProperty() 
    {
        if (suplEmail == null) suplEmail = new SimpleStringProperty(this, "SuplEmail");
        return suplEmail;
     }
    
     //Setup Supplier Bank Property
    public void setSuplBank(String value) 
    { 
        SuplBankProperty().set(value); 
    }
    public String getSuplBank() 
    { 
        return SuplBankProperty().get(); 
    }
    public StringProperty SuplBankProperty() 
    {
        if (suplBank == null) suplBank = new SimpleStringProperty(this, "SuplBank");
        return suplBank;
     }
    
    //Setup Supplier BranchCode Property
    public void setSuplBranchCode(Integer value) 
    { 
        SuplBranchCodeProperty().set(value); 
    }
    public int getSuplBranchCode() 
    { 
        return SuplBranchCodeProperty().get(); 
    }
    public IntegerProperty SuplBranchCodeProperty() 
    {
        
        if (suplBranchCode == null) suplBranchCode = new SimpleIntegerProperty(this, "SuplBranchCode");
        return suplBranchCode;
     }
    
        //Setup Supplier AccNum Property
    public void setSuplAccNum(Integer value) 
    { 
        SuplAccNumProperty().set(value); 
    }
    public int getSuplAccNum() 
    { 
        return SuplAccNumProperty().get(); 
    }
    public IntegerProperty SuplAccNumProperty() 
    {
        
        if (suplAccNum == null) suplAccNum = new SimpleIntegerProperty(this, "SuplAccNum");
        return suplAccNum;
     }
    
    //Setup Supplier AccType Property
    public void setSuplAccType(String value) 
    { 
        SuplAccTypeProperty().set(value); 
    }
    public String getSuplAccType() 
    { 
        return SuplAccTypeProperty().get(); 
    }
    public StringProperty SuplAccTypeProperty() 
    {
        if (suplAccType == null) suplAccType = new SimpleStringProperty(this, "SuplAccType");
        return suplAccType;
     }
    
    //Setup Supplier Comments Property
    public void setSuplComments(String value) 
    { 
        SuplCommentsProperty().set(value); 
    }
    public String getSuplComments() 
    { 
        return SuplCommentsProperty().get(); 
    }
    public StringProperty SuplCommentsProperty() 
    {
        if (suplComments == null) suplComments = new SimpleStringProperty(this, "SuplComments");
        return suplComments;
     }
}
