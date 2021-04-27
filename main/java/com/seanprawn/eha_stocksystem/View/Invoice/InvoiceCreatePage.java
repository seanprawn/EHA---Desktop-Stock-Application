/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Invoice;

import com.seanprawn.eha_stocksystem.MainApp;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvoiceCreatePage extends InvoiceViewPage {
    
    public InvoiceCreatePage(){};
    
    @Override
    public void InvoiceViewPage(){
    super.InvoiceViewPage();
    }
    
//     public void setInvoiceView()
//    {
//        btnClear.setVisible(false);
////        btnEdit.setText("View Supplier");
//        btnSave.setVisible(false);
////        txtInvDate.setVisible(true);
//
//        this.SetTextFieldsEditable(Boolean.FALSE);
////        save_edit = true; // Set true to use UPDATE query
////        System.out.println("Save/Edit == "+save_edit);
//    }
    
    public void setInvoiceCreate() {
        txtInvId.setVisible(false);
        btnSave.setText("Save New Invoice");
        btnGenerate.setVisible(false);
        this.setInvoiceNew();
        MainApp.refresh = true;
        txtInvClientId.setPromptText("Click to Select");
    }
}
