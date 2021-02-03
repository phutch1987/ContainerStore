/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LogIn {
    
    
    private DatabaseConnect db = new DatabaseConnect();
    private String dba;
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    public LogIn() throws SQLException {
        
        try{ db.DbCon();
        
            dba = "CREATE TABLE IF NOT EXISTS users ("
                    + "UserName STRING (20),"
                    + "Salt BLOB (100),"
                    + "Hash STRING (100),"
                    + "Clearance STRING (20));";
            
            ps = db.DbCon().prepareStatement(dba);
            
            ps.executeUpdate();
        
        }catch(SQLException e) {
            
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, e);
            
        }finally{
            db.DbCon().close();
        }     
    }
    
    /**
     * Adds new Entry of user into database
     * @param username
     * @param password
     * @param clearance
     * @throws java.security.NoSuchProviderException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.sql.SQLException
     */
    
    public void addEntry(String username, char[]password, String clearance) throws NoSuchProviderException, NoSuchAlgorithmException, SQLException{
        
        byte[] salt = getSalt();
        
        String hash = generateSecurePassword(Arrays.toString(password), salt);
        
        try{
            db.DbCon();
            dba = "INSERT INTO users(username, salt, hash, clearance) VALUES (?,?,?,?)";
            ps = db.DbCon().prepareStatement(dba);
                    
            ps.setString(1, username);
            ps.setBytes(2, salt);
            ps.setString(3, hash);
            ps.setString(4, clearance);
            
            ps.executeUpdate();
                                        
        } catch (SQLException ex) {
            
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            
            db.DbCon().close();
        }     
    }
    
    /**
     * generates secure password
     * @param password
     * @param salt
     * @return 
     * @throws java.security.NoSuchAlgorithmException 
     */
    
    private String generateSecurePassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        
        String securePassword = null;
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        md.update(salt);
        
        byte[] bytes = md.digest(password.getBytes());
        
        StringBuilder sb = new StringBuilder();
        
        for(int i =0; i < bytes.length; i++){
            
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            
        }
        
        securePassword = sb.toString();
        
        
        return securePassword;
    }
    
    /**
     * generates salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException 
     */
    
    private byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        
        byte[] Salt = new byte[16];
        
        sr.nextBytes(Salt);
        
        return Salt;
        
    }
    
    /**
     * checks for username and password
     * @param user
     * @param password
     * @return
     * @throws SQLException 
     */
    
    public Map<Boolean, String> identify(String user, char[]password) throws SQLException{
        
        Map<Boolean,String> identity = new TreeMap<>();
        
        identity.put(Boolean.FALSE, "X");
               
        try{
            
            db.DbCon();
            dba = "SELECT * FROM users WHERE username = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, user);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                if(user.matches(rs.getString("userName"))){
                    
                    byte[] salt = rs.getBytes("Salt");                   
                    
                    String securePassword = generateSecurePassword(Arrays.toString(password), salt);
                      
                    if(securePassword.matches(rs.getString("Hash"))) {                       

                        String clearance = rs.getString("Clearance");
                        
                        identity.clear();
                        identity.put(Boolean.TRUE, clearance);

                    }                    
                }
            }
            
        }catch(SQLException | NoSuchAlgorithmException e){
            
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, e);
            
        }finally{
 
            rs.close();
            db.DbCon().close();
        }
       
        return identity;
        
    }
    
    /**
     * checks if admin account initialized
     * @return
     * @throws SQLException 
     */
    
    public boolean checkAdmin() throws SQLException{
        
        boolean admin = false;
        
       try{
           db.DbCon();
           dba = "SELECT * FROM users WHERE clearance = ?";
           ps = db.DbCon().prepareStatement(dba);
           
           ps.setString(1, "ADMIN");
           
           rs = ps.executeQuery();
           
           while(rs.next()){
               
               String check = rs.getString("Clearance");
               
               if(check.matches("ADMIN")){
                   
                   admin = true;
               }
           }
           
       }catch(SQLException e){
           
           JOptionPane.showMessageDialog(null, e.getMessage());
       }
        
        return admin;
    }
    
    /**
     * gets all the usernames in database
     * @return 
     */
    
    public String[] getUsers() throws SQLException{
        
        String[] users = new String[getNumberOfUsers()]; 
        int place = 0;
        
        try{
            db.DbCon();
            dba = "SELECT * FROM users";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                users[place++] = rs.getString("username");
                
            }
            
        }catch(SQLException e){
           
           JOptionPane.showMessageDialog(null, e.getMessage());
           
       }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return users;
    }
    
   /**
    * gets the number of users in database
    * @return
    * @throws SQLException 
    */
    
    public int getNumberOfUsers() throws SQLException {
        
        int count = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT COUNT(*) AS count FROM users";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                count = rs.getInt("count");
            }
            
        }catch(SQLException e){
           
           JOptionPane.showMessageDialog(null, e.getMessage());
           
       }finally{
            
            rs.close();
            db.DbCon().close();
        }

        return count;
    }
 
}
