/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Controller;

import com.seanprawn.eha_stocksystem.Model.Client;
import com.seanprawn.eha_stocksystem.Model.ClientEvent;
import com.seanprawn.eha_stocksystem.Model.ClientRef;
import com.seanprawn.eha_stocksystem.Model.Invoice;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplement;
import com.seanprawn.eha_stocksystem.Model.InvoiceSupplementSelect;
import com.seanprawn.eha_stocksystem.Model.Supplement;
import com.seanprawn.eha_stocksystem.Model.SupplementDataChart;
import com.seanprawn.eha_stocksystem.Model.Supplier;
import com.seanprawn.eha_stocksystem.Model.User;
import com.seanprawn.eha_stocksystem.View.Alerts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class DatabaseMethods {
DecimalFormat df = new DecimalFormat("#.00");
    //*******************************************************************************************
    //*************            User Methods                                    ******************
    //*******************************************************************************************
   
    /**
     * This method queries a user from the database and returns the user object
     * @param fName
     * @param password
     * @return user
     */
    public static User getUser(String fName, String password)
    {
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        
        try
        {
            String sql = "SELECT `user_id`,`user_fname`,"
                    + "`user_lname`,`user_email`,"
                    + "`user_group_id`,`user_password` "
                    + "FROM user WHERE "
                    + "`user_fname` = ? && `user_password` = ?;"; //secure sql using parameterised queries

            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, fName); // Set the query field
            statement.setString(2, password); // Set the query field
            resultSet = statement.executeQuery();
            
//            Iterate throug the results and save to user object
            while(resultSet.next())
            {
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("user_fname"));
                user.setLastName(resultSet.getString("user_lname"));
                user.setEmail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return user;
    }
    
    /******************End of User Methods*****************************************************
    *******************************************************************************************/
    
    //*******************************************************************************************
    //*************            Start of Dashboard Methods                      ******************
    //*******************************************************************************************
    
    /**
     * Gets a result of the top ten Supplements sold in the last year
     * @return Observable List
     */
    public static ObservableList<SupplementDataChart> getTop10SuplListYear()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT `inv_suppl_id` AS \"supplement\", SUM(`inv_suppl_qty`) AS \"qty_sold\" " 
                    +"FROM invoice_supplement " 
                    +"INNER JOIN invoice_info " 
                    +"ON invoice_supplement.inv_suppl_num = invoice_info.inv_id " 
                    +"WHERE invoice_info.inv_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR) "
                    +"AND CURRENT_DATE " 
                    +"GROUP BY `inv_suppl_id` ORDER BY qty_sold DESC limit 10;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                SupplementDataChart topTen = new SupplementDataChart();
                topTen.setSuplID(result.getString("supplement"));
                topTen.setSupplQtySold(result.getInt("qty_sold"));
                list.add(topTen);
            }
//            System.out.println(list.size()+" Top Ten supplements \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    
        
    }
    
    /**
     * Gets a result of the top ten Supplements sold in the last year
     * @return 
     */
    public static ObservableList<SupplementDataChart> getTop10SuplListMonth()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT `inv_suppl_id` AS \"supplement\", SUM(`inv_suppl_qty`) AS \"qty_sold\" " 
                    +"FROM invoice_supplement " 
                    +"INNER JOIN invoice_info " 
                    +"ON invoice_supplement.inv_suppl_num = invoice_info.inv_id " 
                    +"WHERE invoice_info.inv_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) "
                    +"AND CURRENT_DATE " 
                    +"GROUP BY `inv_suppl_id` ORDER BY qty_sold DESC limit 10;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                SupplementDataChart topTen = new SupplementDataChart();
                topTen.setSuplID(result.getString("supplement"));
                topTen.setSupplQtySold(result.getInt("qty_sold"));
                list.add(topTen);
            }
//            System.out.println(list.size()+" Top Ten supplements \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    
        
    }
    
    /**
     * Gets a List of all the supplements sold in the last year
     * @return 
     */
     public static ObservableList<SupplementDataChart> getSuplSoldListYear()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT `inv_suppl_id` AS \"supplement\", SUM(`inv_suppl_qty`) AS \"qty_sold\" " 
                    +"FROM invoice_supplement " 
                    +"INNER JOIN invoice_info " 
                    +"ON invoice_supplement.inv_suppl_num = invoice_info.inv_id " 
                    +"WHERE invoice_info.inv_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR) "
                    +"AND CURRENT_DATE " 
                    +"GROUP BY `inv_suppl_id` ORDER BY qty_sold DESC;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                SupplementDataChart topTen = new SupplementDataChart();
                topTen.setSuplID(result.getString("supplement"));
                topTen.setSupplQtySold(result.getInt("qty_sold"));
                list.add(topTen);
            }
//            System.out.println(list.size()+" supplements sold year \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    
        
    }
     
     /**
     * Gets a List of all the supplements sold in the last Month
     * @return 
     */
     public static ObservableList<SupplementDataChart> getSuplSoldListMnth()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT `inv_suppl_id` AS \"supplement\", SUM(`inv_suppl_qty`) AS \"qty_sold\" " 
                    +"FROM invoice_supplement " 
                    +"INNER JOIN invoice_info " 
                    +"ON invoice_supplement.inv_suppl_num = invoice_info.inv_id " 
                    +"WHERE invoice_info.inv_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) "
                    +"AND CURRENT_DATE " 
                    +"GROUP BY `inv_suppl_id` ORDER BY qty_sold DESC;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                SupplementDataChart topTen = new SupplementDataChart();
                topTen.setSuplID(result.getString("supplement"));
                topTen.setSupplQtySold(result.getInt("qty_sold"));
                list.add(topTen);
            }
//            System.out.println(list.size()+" supplements sold month \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    
        
    }
     
     /**
     * Gets a Count of all the Patients seen in the last Month
     * @return 
     */
     public static int getPatientsLastMonth() 
     {
         int res = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT COUNT(client_data.client_name) AS \"patients_total\"" +
                    " FROM client_data" +
                    " INNER JOIN appointment" +
                    " ON client_data.client_id=appointment.client_data_client_id" +
                    " WHERE appointment.app_date BETWEEN" +
                    " DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) AND CURRENT_DATE;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            result = statement.executeQuery();
            while(result.next())
            {
                res = result.getInt("patients_total");
            }
            System.out.println(res+" patients last month \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return res;
     }
     
      /**
     * Gets a Count of all the Patients seen in the last Year
     * @return 
     */
     public static int getPatientsLastYear()
     {
         int res = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT COUNT(client_data.client_name) AS \"patients_total\"" +
                    " FROM client_data" +
                    " INNER JOIN appointment" +
                    " ON client_data.client_id=appointment.client_data_client_id" +
                    " WHERE appointment.app_date BETWEEN" +
                    " DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR) AND CURRENT_DATE;";
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            result = statement.executeQuery();
             while(result.next())
            {
                res = result.getInt("patients_total");
            }
            System.out.println(res+" patients last year \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return res;
     }
     
     /**
     * Gets a List of all the Clients with appointments for the current day
     * @return Client Event List
     */
     public static ObservableList<ClientEvent> getClientAppointmentsToday()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        String sql = "SELECT "
                + " client_data.client_id AS client_id,"
                + " client_data.client_name AS client_name,"
                + " client_data.client_surname AS client_surname,"
                + " appointment.app_time AS appointment_time" 
                + " FROM client_data" 
                + " INNER JOIN appointment " 
                + " ON client_data.client_id = appointment.client_data_client_id " 
                + " WHERE appointment.app_date = CURRENT_DATE" 
                + " ORDER BY appointment.app_time ASC;";
        
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                ClientEvent client = new ClientEvent();
                client.setEventClId(result.getObject("client_id"));
                client.setEventClName(result.getString("client_name"));
                client.setEventClSurname(result.getString("client_surname"));
                client.setEventClAppTime(result.getObject("appointment_time"));
                list.add(client);
            }
//            System.out.println(list.size()+" supplements sold year \n");
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    
        
    }

         public static ObservableList<ClientEvent> getClientBirthdaysMonth() {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String sql = "SELECT"
                + " client_id, client_name, client_surname"
                + " FROM client_data" 
                + " WHERE SUBSTRING(`client_id`,3,2) = SUBSTRING(CURRENT_DATE,6,2);"; 
        
        try
        {
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            list = FXCollections.observableArrayList();
            result = statement.executeQuery();
            while(result.next())
            {
                ClientEvent client = new ClientEvent();
                client.setEventClId(result.getObject("client_id"));
                client.setEventClName(result.getString("client_name"));
                client.setEventClSurname(result.getString("client_surname"));
//                client.setEventClAppTime(null);
                list.add(client);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
//            close all connections once completed query
        if (result != null) try { result.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
     
    /******************End of Dashboard Methods*************************************************
    *******************************************************************************************/
    
          
     
    //*******************************************************************************************
    //*************            start of Invoice Methods                        ******************
    //*******************************************************************************************
     
     /**
     * Get all Invoice data
     * @return as Observable list result
     */
    public static ObservableList<Invoice> getInvoiceList() 
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM invoice_info ";
        
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();
        
        while(resultSet.next())
        {
            Invoice invoice = new Invoice();
            invoice.setInvId(resultSet.getInt("inv_id"));
            invoice.setInvNum(resultSet.getString("inv_num"));
            invoice.setInvDate(resultSet.getObject("inv_date"));
            invoice.setInvClientId(resultSet.getObject("inv_client_id"));
            invoice.setInvConsultFee(resultSet.getDouble("inv_consultation_fee"));
            list.add(invoice);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        finally
        {
//            close all connections once completed query
            if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
            if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
            if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }

    /**
     * Saves the data given by the user into the database
     * The Invoice is saved first, if successful then Supplement data is saved.
     * @param saveInv - Invoice data to be saved to the Invoice table - Passed as an Invoice Object
     * @param supls - All the supplements to be saved to the Invoice_supplement table - Passed as Observable array list of InvoiceSupplement Objects
     * @return - int 
     * return >= 1 means success, but only returned after all supplements have been saved correctly
     * return 0 means unsuccessful
     */
    public static int SaveNewInvoice(Invoice saveInv, ObservableList<InvoiceSupplement> supls) 
    {
        int result = 0;
        int resSupl = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //First Insert Invoice details into Invoice_Info Table
        try
        {
            String sql = "INSERT INTO `invoice_info` SET"
                    + "`inv_num` = ?, `inv_date` = ?, "
                    + "`inv_client_id` = ?, `inv_consultation_fee` = ?; ";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, saveInv.getInvNum());
            statement.setString(2, String.valueOf(saveInv.getInvDate()));
            statement.setString(3, String.valueOf(saveInv.getInvClientId()));
            statement.setString(4, Double.toString(saveInv.getInvConsultFee()));
            result = statement.executeUpdate();
            
            //If Insert successful, then get last insert ID to use in Invoice_Supplement table
            if(result >0)
            {
                long insertId = 0;
                PreparedStatement getLastInsertId = connect.prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = getLastInsertId.executeQuery();
                if (rs.next())
                {
                     insertId = rs.getLong("last_insert_id()");    //save the last insert id        
                }
                
                //use the last insert ID and save all the supplements to the invoice_supplement table
              String sqlSupl = "INSERT INTO `invoice_supplement` "
                    + "(`inv_suppl_numID`, `inv_suppl_num`, "
                    + "`inv_suppl_id`, `inv_suppl_price`, `inv_suppl_qty`, `inv_suppl_price_total`, "
                         + "`inv_total_price_suppl_consultation`) "
                         + "VALUES (NULL, ?, ?, ?, ?, ?, ?);";

                for (int i = 0; i < supls.size(); i++) 
                {
                    //Insert Supplements with the last insert id as inv_supl_num
                    //Each one will have the same  inv_supl_num(last insert id)
                    statement = connect.prepareStatement(sqlSupl);
                    statement.setString(1, String.valueOf(insertId));
                    statement.setString(2, String.valueOf(supls.get(i).getInvSuplId()));
                    statement.setString(3, String.valueOf(supls.get(i).getInvSuplPrice()));
                    statement.setString(4, Integer.toString(supls.get(i).getInvSuplQty()));
                    statement.setString(5, Double.toString(supls.get(i).getInvSuplPriceTotal()));
                    statement.setString(6, Double.toString(supls.get(i).getInvTotPriceSuplConsult()));
                    resSupl = statement.executeUpdate();
                }
        }else
            {
                Alerts.displayAlert("Error", "Could not Insert new Invoice Data");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return resSupl;
    }

        public static ObservableList<InvoiceSupplement> getInvoiceSupplements(Invoice inv) {
           ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * FROM invoice_supplement "
                + "WHERE `inv_suppl_num` = '"+inv.getInvId()+"' ";
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            InvoiceSupplement supl = new InvoiceSupplement();
            supl.setInvSuplNumId(resultSet.getInt("inv_suppl_numID"));
            supl.setInvSuplNum(resultSet.getInt("inv_suppl_num")); // Same as Invoice ID
            supl.setInvSuplId(resultSet.getString("inv_suppl_id"));
            supl.setInvSuplPrice(Double.valueOf(resultSet.getString("inv_suppl_price")));
            supl.setInvSuplQty(resultSet.getInt("inv_suppl_qty"));
            supl.setInvSuplPriceTotal(resultSet.getDouble("inv_suppl_price_total"));
            supl.setInvTotPriceSuplConsult(resultSet.getDouble("inv_total_price_suppl_consultation"));
            list.add(supl);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
    

    /**
     *  retrieves the Supplement details to be used as a searchable table when adding supplements to an invoice
     *   Gets only the Supplement ID and the price
     *
     * @return
     */
        public static ObservableList<InvoiceSupplementSelect> getListOfSupplementsForInvoice() {
         ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT `suppl_id` AS `inv_suppl_id`, `supl_cost_client` AS `inv_suppl_price` FROM supplement;";

        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();

        while(resultSet.next())
        {
            InvoiceSupplementSelect supl = new InvoiceSupplementSelect();
            supl.setInvSuplId(resultSet.getString("inv_suppl_id"));
            supl.setInvSuplPrice(Double.valueOf(resultSet.getString("inv_suppl_price")));
            list.add(supl);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
        
        
     /******************End of Invoice Methods*************************************************
    *******************************************************************************************/
    
    
    
    //*******************************************************************************************
    //*************           start of Supplement Methods                      ******************
    //*******************************************************************************************
    
     /**
     * Get all supplement data
     * @return as Observable result
     */
    public static ObservableList<Supplement> getSupplementList()
    {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM supplement ";
//                    + "`user_fname` = ? && `user_password` = ?;";
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
//        result = (ObservableList) statement.executeQuery(sql);
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            Supplement supl = new Supplement();
            supl.setSupplNum(resultSet.getInt("suppl_num"));
            supl.setSupplId(resultSet.getString("suppl_id"));
            supl.setSupplDesc(resultSet.getString("supl_description"));
            supl.setSuplCostExcl(resultSet.getDouble("supl_cost_excl"));
            supl.setSuplCostIncl(resultSet.getDouble("supl_cost_incl"));
            supl.setSuplPercIncl(resultSet.getDouble("supl_perc_inc"));
            supl.setSuplCostClient(resultSet.getDouble("supl_cost_client"));
            supl.setSuplMinLevels(resultSet.getInt("supl_min_levels"));
            supl.setSuplStockLevels(resultSet.getInt("supl_stock_levels"));
            supl.setsuplNappiCode(resultSet.getString("supl_nappi_code"));
            supl.setSuplSupplierId(resultSet.getInt("supl_supplier_id"));
            list.add(supl);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
    
      public static ObservableList<Supplement> getSupplementLowStockList() {
         ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM supplement "
                    + " WHERE `supl_stock_levels` <= `supl_min_levels`;";
                     
//                    + "`user_fname` = ? && `user_password` = ?;";
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
//        result = (ObservableList) statement.executeQuery(sql);
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            Supplement supl = new Supplement();
            supl.setSupplNum(resultSet.getInt("suppl_num"));
            supl.setSupplId(resultSet.getString("suppl_id"));
            supl.setSupplDesc(resultSet.getString("supl_description"));
            supl.setSuplCostExcl(resultSet.getDouble("supl_cost_excl"));
            supl.setSuplCostIncl(resultSet.getDouble("supl_cost_incl"));
            supl.setSuplPercIncl(resultSet.getDouble("supl_perc_inc"));
            supl.setSuplCostClient(resultSet.getDouble("supl_cost_client"));
            supl.setSuplMinLevels(resultSet.getInt("supl_min_levels"));
            supl.setSuplStockLevels(resultSet.getInt("supl_stock_levels"));
            supl.setsuplNappiCode(resultSet.getString("supl_nappi_code"));
            supl.setSuplSupplierId(resultSet.getInt("supl_supplier_id"));
            list.add(supl);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
    
    public static int EditSupplement(Supplement supl) 
    {
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "UPDATE `supplement` SET"
                    + "`suppl_id` = ?, `supl_description` = ?, `supl_cost_excl` = ?, "
                    + "`supl_cost_incl` = ?, `supl_perc_inc` = ?, `supl_cost_client` = ?, "
                    + "`supl_min_levels` = ?, `supl_stock_levels` = ?, `supl_nappi_code` = ?, `supl_supplier_id` = ? "
                    + "WHERE `suppl_num` = ?;";
           
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, supl.getSuplId());
            statement.setString(2, supl.getSuplDesc());
            statement.setString(3, Double.toString(supl.getSuplCostExcl()));
            statement.setString(4, Double.toString(supl.getSuplCostIncl()));
            statement.setString(5, Double.toString(supl.getSuplPercIncl()));
            statement.setString(6, Double.toString(supl.getSuplCostClient()));
            statement.setString(7, Integer.toString(supl.getSuplMinLevels()));
            statement.setString(8, Integer.toString(supl.getSuplStockLevels()));
            statement.setString(9, supl.getsuplNappiCode());
            statement.setString(10, Integer.toString(supl.getSuplSupplierId()));
            statement.setString(11, Integer.toString(supl.getSuplNum()));
            System.out.println("\n Sql Update Query :"+statement.toString());
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int DeleteSupplement(Supplement supl) {

        int result = 0;
         Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "DELETE FROM `supplement` "
                    + "WHERE `suppl_num` = ?;";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, Integer.toString(supl.getSuplNum()));
            System.out.println("\n Sql Delete Query :"+statement.toString());
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Delete from DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int SaveNewSupplement(Supplement supl) {
        int result = 0;
         Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "INSERT INTO `supplement` SET "
                    + "`suppl_id` = ?, `supl_description` = ?, `supl_cost_excl` = ?, "
                    + "`supl_cost_incl` = ?, `supl_perc_inc` = ?, `supl_cost_client` = ?, "
                    + "`supl_min_levels` = ?, `supl_stock_levels` = ?, `supl_nappi_code` = ?, "
                    + "`supl_supplier_id` = ?; ";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, supl.getSuplId());
            statement.setString(2, supl.getSuplDesc());
            statement.setString(3, Double.toString(supl.getSuplCostExcl()));
            statement.setString(4, Double.toString(supl.getSuplCostIncl()));
            statement.setString(5, Double.toString(supl.getSuplPercIncl()));
            statement.setString(6, Double.toString(supl.getSuplCostClient()));
            statement.setString(7, Integer.toString(supl.getSuplMinLevels()));
            statement.setString(8, Integer.toString(supl.getSuplStockLevels()));
            statement.setString(9, supl.getsuplNappiCode());
            statement.setString(10, Integer.toString(supl.getSuplSupplierId()));
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

   /******************End of Supplement Methods*************************************************
    *******************************************************************************************/
    
    
    //*******************************************************************************************
    //*************            start of Supplier Methods                       ******************
    //*******************************************************************************************
   
    public static ObservableList<Supplier> getSupplierList() {
       ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM supplier_Info ";
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
//        result = (ObservableList) statement.executeQuery(sql);
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            Supplier supl = new Supplier();
            supl.setSupplId(resultSet.getInt("supplier_id"));
            supl.setSupplCode(resultSet.getString("supplier_code"));
            supl.setSuplContact(resultSet.getString("supplier_contact_person"));
            supl.setSuplTel(resultSet.getString("supplier_tel"));
            supl.setSuplCell(resultSet.getString("supplier_cell"));
            supl.setSuplFax(resultSet.getString("supplier_fax"));
            supl.setSuplEmail(resultSet.getString("supplier_email"));
            supl.setSuplBank(resultSet.getString("supplier_bank"));
            supl.setSuplBranchCode(resultSet.getInt("supplier_branch_code"));
            supl.setSuplAccNum(resultSet.getInt("supplier_account_number"));
            supl.setSuplAccType(resultSet.getString("supplier_account_type"));
            supl.setSuplComments(resultSet.getString("supplier_comments"));
            list.add(supl);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        System.out.println("\n list count supplements: "+list.size());
        return list;
    }
    
    public static int SaveNewSupplierInfo(Supplier supl) {
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "INSERT INTO `supplier_Info` SET "
                    + "`supplier_code` = ?, `supplier_contact_person` = ?, `supplier_tel` = ?, "
                    + "`supplier_cell` = ?, `supplier_fax` = ?, `supplier_email` = ?, "
                    + "`supplier_bank` = ?, `supplier_branch_code` = ?, `supplier_account_number` = ?, "
                    + "`supplier_account_type` = ?, `supplier_comments` = ?; ";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, supl.getSuplCode());
            statement.setString(2, supl.getSuplContact());
            statement.setString(3, supl.getSuplTel());
            statement.setString(4, supl.getSuplCell());
            statement.setString(5, supl.getSuplFax());
            statement.setString(6, supl.getSuplEmail());
            statement.setString(7, supl.getSuplBank());
            statement.setString(8, Integer.toString(supl.getSuplBranchCode()));
            statement.setString(9, Integer.toString(supl.getSuplAccNum()));
            statement.setString(10, supl.getSuplAccType());
            statement.setString(11, supl.getSuplComments());
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int EditSupplierInfo(Supplier supl) {
        
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "UPDATE `supplier_Info` SET "
                    + "`supplier_code` = ?, `supplier_contact_person` = ?, `supplier_tel` = ?, "
                    + "`supplier_cell` = ?, `supplier_fax` = ?, `supplier_email` = ?, "
                    + "`supplier_bank` = ?, `supplier_branch_code` = ?, `supplier_account_number` = ?, "
                    + "`supplier_account_type` = ?, `supplier_comments` = ? "
                    + "WHERE `supplier_id` = ?;";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, supl.getSuplCode());
            statement.setString(2, supl.getSuplContact());
            statement.setString(3, supl.getSuplTel());
            statement.setString(4, supl.getSuplCell());
            statement.setString(5, supl.getSuplFax());
            statement.setString(6, supl.getSuplEmail());
            statement.setString(7, supl.getSuplBank());
            statement.setString(8, Integer.toString(supl.getSuplBranchCode()));
            statement.setString(9, Integer.toString(supl.getSuplAccNum()));
            statement.setString(10, supl.getSuplAccType());
            statement.setString(11, supl.getSuplComments());
            statement.setString(12, Integer.toString(supl.getSuplId()));
            result = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int DeleteSupplierInfo(Supplier supl) {
       
        int result = 0;
         Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "DELETE FROM `supplier_Info` "
                    + "WHERE `supplier_id` = ?;";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, Integer.toString(supl.getSuplId()));
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Delete from DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }
    
    
    
    
    /******************End of Supplier Methods*************************************************
    *******************************************************************************************/

    
    //*******************************************************************************************
    //*************          Start of Client Methods                           ******************

    
    /**
     * 
     * @return 
    */
    public static ObservableList<Client> getClientList() {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM client_data ";
        
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            Client client = new Client();
            client.setClId(resultSet.getObject("client_id"));
            client.setClName(resultSet.getString("client_name"));
            client.setClSurname(resultSet.getString("client_surname"));
            client.setClAddress(resultSet.getString("client_address"));
            client.setClAddrCode(resultSet.getInt("client_address_code"));
            client.setClTelHome(resultSet.getString("client_tel_h"));
            client.setClTelWork(resultSet.getString("client_tel_w"));
            client.setClTelCell(resultSet.getString("client_tel_cell"));
            client.setClEmail(resultSet.getString("client_email"));
            client.setClRef(resultSet.getInt("client_ref"));
            list.add(client);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }
    
    public static ObservableList<ClientRef> getClientRefList() {
        ObservableList list = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM client_reference ";
        
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        list = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            ClientRef client = new ClientRef();
            client.setRefClRef(resultSet.getInt("client_ref"));
            client.setRefClRefDesc(resultSet.getString("client_ref_desc"));
            list.add(client);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return list;
    }

    public static int DeleteClientInfo(Client cl) {
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "DELETE FROM `client_data` "
                    + "WHERE `client_id` = ?;";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, String.valueOf(cl.getClId()));
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Delete from DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int SaveNewClientInfo(Client client) {
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "INSERT INTO `client_data` SET"
                    + "`client_id` = ?, `client_name` = ?, `client_surname` = ?, "
                    + "`client_address` = ?, `client_address_code` = ?, `client_tel_h` = ?, "
                    + "`client_tel_w` = ?, `client_tel_cell` = ?, `client_email` = ?, "
                    + "`client_ref` = ?; ";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, String.valueOf(client.getClId()));
            statement.setString(2, client.getClName());
            statement.setString(3, client.getClSurname());
            statement.setString(4, client.getClAddress());
            statement.setString(5, Integer.toString(client.getClAddrCode()));
            statement.setString(6, client.getClTelHome());
            statement.setString(7, client.getClTelWork());
            statement.setString(8, client.getClTelCell());
            statement.setString(9, client.getClEmail());
            statement.setString(10, Integer.toString(client.getClRef()));
            result = statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static int EditClientInfo(Client client) {
        int result = 0;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            String sql = "UPDATE `client_data` SET"
                    + "`client_name` = ?, `client_surname` = ?, `client_address` = ?, "
                    + "`client_address_code` = ?, `client_tel_h` = ?, `client_tel_w` = ?, "
                    + "`client_tel_cell` = ?, `client_email` = ?, `client_ref` = ? "
                    + "WHERE `client_id` = ?;";
            
            connect = DatabaseAccess.startConnection();
            statement = connect.prepareStatement(sql);
            statement.setString(1, client.getClName());
            statement.setString(2, client.getClSurname());
            statement.setString(3, client.getClAddress());
            statement.setString(4, String.valueOf(client.getClAddrCode()));
            statement.setString(5, client.getClTelHome());
            statement.setString(6, client.getClTelWork());
            statement.setString(7, client.getClTelCell());
            statement.setString(8, client.getClEmail());
            statement.setString(9, String.valueOf(client.getClRef()));
            statement.setString(10, String.valueOf(client.getClId()));
            result = statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMethods.class.getName()).log(Level.SEVERE, "Update DB", ex);
        }finally
        {
//            close all connections once completed query
        if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
        if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
        if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return result;
    }

    public static Client getClient(Object eventClId) {
         Client clientData = null;
        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String sql = "SELECT * "
                    + "FROM client_data "
                + " WHERE client_id = ? ;";
        
        try
        {
        connect = DatabaseAccess.startConnection();
        statement = connect.prepareStatement(sql);
        statement.setString(1, eventClId.toString()); // Set the ID field

//        clientData = FXCollections.observableArrayList();
        resultSet = statement.executeQuery();
        while(resultSet.next())
        {
//            Client client = new Client();
            System.out.println("\n\n Client ID "+resultSet.getObject("client_id"));
            clientData.setClId(resultSet.getObject("client_id"));
            clientData.setClName(resultSet.getString("client_name"));
            clientData.setClSurname(resultSet.getString("client_surname"));
            clientData.setClAddress(resultSet.getString("client_address"));
            clientData.setClAddrCode(resultSet.getInt("client_address_code"));
            clientData.setClTelHome(resultSet.getString("client_tel_h"));
            clientData.setClTelWork(resultSet.getString("client_tel_w"));
            clientData.setClTelCell(resultSet.getString("client_tel_cell"));
            clientData.setClEmail(resultSet.getString("client_email"));
            clientData.setClRef(resultSet.getInt("client_ref"));
//            clientData.add(client);
        }
        }catch
        (SQLException sqlE) {
            sqlE.printStackTrace(System.out);
        }
        
        finally
        {
//            close all connections once completed query
            if (resultSet != null) try { resultSet.close(); } catch (SQLException resexc) {resexc.printStackTrace(System.out);}
            if (statement != null) try { statement.close(); } catch (SQLException stexc) {stexc.printStackTrace(System.out);}
            if (connect != null) try { connect.close(); } catch (SQLException connexc) {connexc.printStackTrace(System.out);}
        }
        return clientData;
    }







  

       
}
