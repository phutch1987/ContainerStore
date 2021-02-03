/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;

import java.sql.*;
import javax.swing.JOptionPane;


public class DatabaseConnect {
    
    private Connection con; 
    private String URL;
    Customer customer;
    Warehouse wh;

    public Connection DbCon() throws SQLException{
                
        try {

            URL = "jdbc:sqlite:C:\\ContainerStoreData\\ContainerStore.db";
            con = DriverManager.getConnection(URL);
            
            
        }catch(SQLException e) {
            
           JOptionPane.showInputDialog(e.getMessage());
        }
        
        return con;
    }
    
}
