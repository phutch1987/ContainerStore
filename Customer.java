/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSetMetaData;

/**
 *
 * @author hutch
 */
public class Customer {
    
    private DatabaseConnect db = new DatabaseConnect();
    private Warehouse wh;
    private Log log = new Log();
    
    private String dba;
    private int id;
    private String action;
    private String actionTaken;
     
    PreparedStatement ps;
    ResultSet rs;
    
    public Customer() throws SQLException {
        
        CustomerDatabase();
    }
    
    /**
     * Creates the customers table
     * @throws SQLException 
     */
    
    public final void CustomerDatabase() throws SQLException {
        
        try {
            
            db.DbCon();
            dba = "CREATE TABLE IF NOT EXISTS Customers (\n" +
                "    ID         INTEGER      PRIMARY KEY ASC,\n" +
                "    Name       STRING (20)  NOT NULL,\n" +
                "    Address    STRING (100) NOT NULL,\n" +
                "    Number     STRING (20)     NOT NULL,\n" +
                "    Containers INT (10)      NOT NULL,\n" +
                "    Date       STRING (20) \n" +
                ");";
            
            ps = db.DbCon().prepareStatement(dba);
            
            ps.executeUpdate();
            
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
              
        }finally {
            
            db.DbCon().close();
        }        
    }
    
    /**
     * gets current date
     * @return 
     */
    
    public String getDate() {
        
      return LocalDate.now().toString();
        
    }
    
    /**
     * Adds new customer into database
     * @param name
     * @param address
     * @param number
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void addNewCustomer(String name, String address, String number, ArrayList<Short> container, ArrayList<String> location) throws SQLException {
         
        id = setId();
        
        try{
            
            db.DbCon();
            dba = "INSERT INTO customers (ID, Name, Address, Number, Containers, Date) VALUES (?,?,?,?,?,?)";
            ps = db.DbCon().prepareStatement(dba);
                
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, number);
            ps.setInt(5, container.size());
            ps.setString(6, getDate());
            
            ps.executeUpdate();
            
            //calls method to create a table of customers containers and locations
            
            createCustomersContainerList(id);
            addNewCustomersContainers(id, container, location);
            
            JOptionPane.showMessageDialog(null, name + " Added To Database");
            
            
            
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            db.DbCon().close();  
        }    
    }
    
    /**
     * creates a unique id number for new customer
     * @return 
     * @throws java.sql.SQLException 
     */
    
    public int setId() throws SQLException {
    
        String uniqueID = "";
        boolean unique = false;
        
        do{
            
            for(int i = 0; i < 5; i++) {

                int digit = (int) (Math.random() * 10);

                uniqueID += digit;

            }
            
            unique = checkId(Integer.parseInt(uniqueID));
            
        }while(unique == false);
        
        return Integer.parseInt(uniqueID);
    }
       
    /**
     * checks if id is unique
     * @param id
     * @return
     * @throws SQLException 
     */
    
    public boolean checkId(int id) throws SQLException {
        
        boolean unique = true;
        
        try{
            
            db.DbCon();
            dba = "SELECT ID From Customers";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                int checkId = rs.getInt("ID");
                
                if(checkId == id) {
                    
                    unique = false;   
                    break;
                }      
            }
            
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return unique;
    }
    
    /**
     * creates customers container and location table
     * @param id 
     * @throws java.sql.SQLException 
     */
    
    public void createCustomersContainerList(int id) throws SQLException {
        
        try {
            
            db.DbCon();
            dba = "CREATE TABLE '" + id + "'("
                    + "Container INT (5),"
                    + "Location STRING (5)"
                    + "); ";
            
            ps = db.DbCon().prepareStatement(dba);
            
            ps.executeUpdate();
                
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            db.DbCon().close();
        }  
    }
    
    /**
     * adds contains and locations to customers table
     * @param id
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void addNewCustomersContainers(int id, ArrayList<Short> container, ArrayList<String> location) throws SQLException, IOException {
        
        wh = new Warehouse();
        
        try {
            
            db.DbCon();
            
            for(int i = 0; i < container.size(); i++) {

                dba = "INSERT INTO '" + id + "' VALUES (?,?)";
                ps = db.DbCon().prepareStatement(dba);
                
                ps.setShort(1, container.get(i));
                ps.setString(2, location.get(i));
  
                ps.executeUpdate();
                
                wh.addToManifest(id, container.get(i), location.get(i));
                
                wh.createInventoryList(container.get(i));
                
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            
            db.DbCon().close();
        }
     
    }
    
    /**
     * returns name of by customer by id number
     * @param id
     * @return
     * @throws SQLException 
     */
    
   public String getCustomerNameByID(int id) throws SQLException {
       
       String getName = "Not Found";
       
       try {
           
           db.DbCon();
           dba = "SELECT Name FROM customers WHERE ID = ?";
           ps = db.DbCon().prepareStatement(dba);
           
           ps.setInt(1, id);
           
           rs = ps.executeQuery();
           
           while(rs.next()) {
               
               getName = rs.getString("name");
               
               break;
           }
           
       }catch(SQLException e) {
           
            JOptionPane.showMessageDialog(null, e.getMessage());
            
       }finally{
           
           rs.close();
           db.DbCon().close();
       }
       
       return getName;
   }
   
   /**
     * returns name of by customer by id number
     * @param name
     * @return
     * @throws SQLException 
     */
    
   public int getCustomerIdFromName(String name) throws SQLException {
       
       int getId = 0;
       
       try {
           
           db.DbCon();
           dba = "SELECT id FROM customers WHERE Name = ?";
           ps = db.DbCon().prepareStatement(dba);
           
           ps.setString(1, name);
           
           rs = ps.executeQuery();
           
           while(rs.next()) {
               
               getId= rs.getInt("ID");
               
               break;
           }
           
       }catch(SQLException e) {
           
            JOptionPane.showMessageDialog(null, e.getMessage());
            
       }finally{
           
           rs.close();
           db.DbCon().close();
       }
       
       return getId;
   }
   
   /**
     * returns customer details by name
     * @param name
     * @return
     * @throws SQLException 
     */
    
   public StringBuilder getCustomerDetailsByName(String name) throws SQLException {
       
       int getId = 0;
       String getName = "Not Found";
       String getAddress = "";
       String getNumber = "";
       String getDate = "";
       
       try {
           
           db.DbCon();
           dba = "SELECT * FROM customers WHERE Name = ?";
           ps = db.DbCon().prepareStatement(dba);
           
           ps.setString(1, name);
           
           rs = ps.executeQuery();
           
           while(rs.next()) {
               
               getId = rs. getInt("ID");
               getName = rs.getString("name");
               getAddress = rs.getString("Address");
               getNumber = rs.getString("number");
               getDate = rs.getString("Date");
               
               break;
           }
           
       }catch(SQLException e) {
           
            JOptionPane.showMessageDialog(null, e.getMessage());
            
       }finally{
           
           rs.close();
           db.DbCon().close();
       }
       
       return new StringBuilder (getId + "/" + getName + "/" + getAddress  + "/" + "0" + getNumber  + "/" + getDate);
   }
   
   /**
     * returns customer details by ID
     * @param id
     * @return
     * @throws SQLException 
     */
    
   public StringBuilder getCustomerDetailsByID(int id) throws SQLException {
       
       int getId = 0;
       String getName = "Not Found";
       String getAddress = "";
       String getNumber = "";
       String getDate = "";
       
       try {
           
           db.DbCon();
           dba = "SELECT * FROM customers WHERE ID = ?";
           ps = db.DbCon().prepareStatement(dba);
           
           ps.setInt(1, id);
           
           rs = ps.executeQuery();
           
           while(rs.next()) {
               
               getId = rs. getInt("ID");
               getName = rs.getString("name");
               getAddress = rs.getString("Address");
               getNumber = rs.getString("number");
               getDate = rs.getString("Date");
               
               break;
           }
           
       }catch(SQLException e) {
           
            JOptionPane.showMessageDialog(null, e.getMessage());
            
       }finally{
           
           rs.close();
           db.DbCon().close();
       }
       
       return new StringBuilder (getId + "/" + getName + "/" + getAddress  + "/" + "0" + getNumber  + "/" + getDate);
   }
   
   /**
    * updates customer information
    * @param id
    * @param name
    * @param address
    * @param number
    * @throws SQLException 
    */
   
   public void updateCustomer(int id, String name, String address, String number) throws SQLException {
       
       log = new Log();
       action = "Update Customer Details";
       
        try{
           
            db.DbCon();
            dba = "UPDATE customers SET Name = ?, Address = ?, Number = ? WHERE ID = ? ";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, number);
            ps.setInt(4, id);
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Customer: " + name + " Updated");
            
            log.Log(action, name + " " + address + " " + number + " " + id + " Updated");

       }catch(SQLException e){
           
           JOptionPane.showMessageDialog(null, e.getMessage());
           
       }finally{

           db.DbCon().close();
       }             
   }
   

   /**
    * updates the containers number in the customer table
    * @param id
    * @param number 
     * @throws java.sql.SQLException 
    */
   
    public void updateContainerCount(int id, int number) throws SQLException {
       
        try {
           
            db.DbCon();
            dba = "UPDATE Customers SET Containers = ? WHERE ID = ?";
            ps = db.DbCon().prepareStatement(dba);
            ps.setInt(1, number);
            ps.setInt(2, id);
            
            ps.executeUpdate();
           
        }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{

            db.DbCon().close();                   
        }  
    }
    
    /**
     * gets the number of customers in the table
     * @return
     * @throws SQLException 
     */
    
    public int getNumberOfCustomers() throws SQLException {
        
        int numberOfCustomers = 0;
        
        try {
            
            db.DbCon();
            dba = "SELECT COUNT(*) AS number FROM customers";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
            
                numberOfCustomers = rs.getInt("number");
            }
            
        }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();                   
        }  
        
        return numberOfCustomers;
    }
    
    /**
     * removes customer from database
     * @param id
     * @throws SQLException 
     */
    
    public void removeCustomer(int id) throws SQLException {
        
        wh = new Warehouse();
        action = "Removed Customer";  
        String name = getCustomerNameByID(id);
        
        try{

            db.DbCon();
            dba = "DELETE FROM customers WHERE id =?";
            
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setInt(1, id);

            ps.executeUpdate();
            
            log.Log(action, "Id: " + id + " Name: " + name);
            
            JOptionPane.showMessageDialog(null, "Customer Removed");

        }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }finally{

            db.DbCon().close();                   
        }    
    }
    
    /**
     * gets customer id from container
     * @param container
     * @return
     * @throws SQLException 
     */
    
     public int getIdFromContainer(short container) throws SQLException {
        
         
         wh = new Warehouse();
         
        int id = 0;
        int[] ids = wh.getIDs();
        
        try{
            db.DbCon();
            
    idLoop: for(int i : ids) {
                
                dba = "SELECT container FROM '" + i + "' WHERE container = ?";
                ps = db.DbCon().prepareStatement(dba);
                ps.setShort(1, container);
                
                rs = ps.executeQuery();
                
                ResultSetMetaData rsm = rs.getMetaData();
                
                while(rs.next()) {
                    
                    id = Integer.parseInt(rsm.getTableName(1));
                    
                    break idLoop;
                }
                               
            }

        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            if(rs != null) rs.close();
            db.DbCon().close();
        }
        
        return id;
    }
     
     /**
      * gets customers name by container
      * @param container
      * @return
      * @throws SQLException 
      */
     
     public String getNameByContainer(Short container) throws SQLException{
         
         int id = getIdFromContainer(container);
        
         String name = getCustomerNameByID(id);
         
         return name;
         
     }

  
}
