/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;

import static containerstore.StoreMain.user;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hutch
 */
public class Log {
    
    private DatabaseConnect db = new DatabaseConnect();
    private String dba;
    private int id;
    
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    public Log() throws SQLException{
        
        createLog();
        
    }
    
    /**
     * creates the log database
     * @throws SQLException 
     */
    
    public final void createLog() throws SQLException {
        
        try{
           
            db.DbCon();
            
            dba = "CREATE TABLE IF NOT EXISTS Logs ("
                    + "Action STRING (50),"
                    + "ActionTaken STRING (200),"
                    + "User STRING (20),"
                    + "Date STRING );";
            
            ps = db.DbCon().prepareStatement(dba);
                    
            ps.executeUpdate();
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            db.DbCon().close();
        }
        
    }
    
    public String getDate(){
        
        DateTimeFormatter lt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        return LocalDate.now().format(lt);
    }
    
    /**
     * adds info to the log database
     * @param action
     * @param actionTaken
     * @throws SQLException 
     */
    
    public void Log(String action, String actionTaken) throws SQLException{
        
        try{
            
            db.DbCon();
            dba = "INSERT INTO logs (action, actiontaken, user, date) VALUES(?,?,?,?)";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, action);
            ps.setString(2, actionTaken);
            ps.setString(3, user);
            ps.setString(4, getDate());
            
            ps.executeUpdate();
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            db.DbCon().close();
        }       
    }
    
    /**
     * returns total number of entries in logs
     * @return
     * @throws SQLException 
     */
    
    public int getLogRows() throws SQLException {
        
        int rows = 0;
        
        try{
            db.DbCon();
            dba = "SELECT COUNT(*) AS count FROM logs";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                rows = rs.getInt("count");
            }
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return rows;
    }
    
    /**
     * retrieves all information from logs
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetails() throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] =rs.getString("date");
                
                place++;
            }
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
       
    /**
     * retrieves all information from logs by user
     * @param user
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetailsByUser(String user) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE user = ?";
            ps = db.DbCon().prepareStatement(dba);
            ps.setString(1, user);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
     /**
     * retrieves all information from logs by user and date
     * @param user
     * @param date
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetailsByUserAndDate(String user, String date) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE user = ? AND date = ?";
            ps = db.DbCon().prepareStatement(dba);
            ps.setString(1, user);
            ps.setString(2, date);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
     /**
     * retrieves all information from logs by date
     * @param date
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetailsByDate(String date) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE date = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, date);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
    /**
     * gets the customers by who has been in the system the longest
     * @return 
     * @throws java.sql.SQLException 
     */
    
    public Object[][] getLongestCustomer()  throws SQLException{
      
        Warehouse wh = new Warehouse();
        
        int row = 0;

         Object[][] longCust = new Object[wh.getIDs().length][4];

        try{

            db.DbCon();
            dba = "SELECT * FROM customers ORDER BY date ASC";

            ps = db.DbCon().prepareStatement(dba);

            rs = ps.executeQuery();

            while(rs.next()) {

                longCust[row][0] = rs.getInt("ID");
                longCust[row][1] = rs.getString("Name");
                longCust[row][2] = rs.getInt("containers");
                longCust[row][3] = rs.getString("date");
                               
                row++;

            }

        } catch (SQLException ex) {
            
              Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
              
        }finally{
            
            rs.close();
            db.DbCon().close();
        }

          return longCust;
    }
    
     /**
     * gets the customers by who has the most containers
     * @return 
     * @throws java.sql.SQLException 
     */
    
    public Object[][] getMostContainers()  throws SQLException{
      
        Warehouse wh = new Warehouse();
        
        int row = 0;

         Object[][] mostCust = new Object[wh.getIDs().length][4];

        try{

            db.DbCon();
            dba = "SELECT * FROM customers ORDER BY containers DESC";

            ps = db.DbCon().prepareStatement(dba);

            rs = ps.executeQuery();

            while(rs.next()) {

                mostCust[row][0] = rs.getInt("ID");
                mostCust[row][1] = rs.getString("Name");
                mostCust[row][2] = rs.getInt("containers");
                mostCust[row][3] = rs.getString("date");

                row++;
                
            }

        } catch (SQLException ex) {
            
              Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
              
        }finally{
            
            rs.close();
            db.DbCon().close();
        }

          return mostCust;
    }
    
    /**
    * gets all the actions taken  in the log
     * @return 
    **/
    
    public TreeSet<String> getActions(){
        
        TreeSet<String> actions = new TreeSet<>();
        
        try{
            
            db.DbCon();
            dba = "SELECT action FROM logs";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                actions.add(rs.getString("Action"));
                
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return actions;
        
    }
    
    /**
     * returns all details by action chosen
     * @param action
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetailsByAction(String action) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE action = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, action);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
    /**
     * returns all details by action and date
     * @param action
     * @param date
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getAllLogDetailsByActionAndDate(String action, String date) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE action = ? AND date = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, action);
            ps.setString(2, date);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
    /**
     * gets details by action taken and user
     * @param action
     * @param user
     * @return
     * @throws SQLException 
     */
    
     public Object[][] getAllLogDetailsByActionAndUser(String action, String user) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE action = ? AND user = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, action);
            ps.setString(2,user);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
     
     /**
      * gets details by action, date and user
      * @param action
      * @param date
      * @param user
      * @return
      * @throws SQLException 
      */
     
      public Object[][] getAllLogDetailsByActionAndDateAndUser(String action, String date, String user) throws SQLException {
        
        Object[][] logs = new Object[getLogRows()][4];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM logs WHERE action = ? AND date = ? AND user = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, action);
            ps.setString(2, date);
            ps.setString(3, user);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                logs[place][0] = rs.getString("Action");
                logs[place][1] = rs.getString("actiontaken");
                logs[place][2] = rs.getString("user");
                logs[place][3] = rs.getString("date");
                
                place++;
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return logs;
    }
    
}
