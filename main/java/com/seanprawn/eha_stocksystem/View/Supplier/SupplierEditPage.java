/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.View.Supplier;

import com.seanprawn.eha_stocksystem.MainApp;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * 
 */
public class SupplierEditPage extends SupplierViewPage {
        public SupplierEditPage() {
    }

    @Override
    public void SupplierViewPage() {
        super.SupplierViewPage(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setEditSupplierPage()
    {
//        btnClear.setVisible(true);
        btnEdit.setText("View Supplier");
        btnSave.setVisible(true);
        this.SetTextFieldsEditable(Boolean.TRUE);
        save_edit = true; // Set true to use UPDATE query
        MainApp.refresh = true;
//        System.out.println("Save/Edit == "+save_edit);
    }
    
    public void setCreateSupplerPage()
    {
        btnClear.setVisible(true);
        btnSave.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setText("Save New Supplier");
        btnDelete.setVisible(false);
        save_edit = false; // Set false to use INSERT query
        this.SetTextFieldsEditable(Boolean.TRUE);
        MainApp.refresh = true;
    }
}
