/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hutch
 */
public class Warehouse {
    
    private DatabaseConnect db = new DatabaseConnect();
    private Customer customer = new Customer();
    private Log log = new Log();
    private String dba;
    private int id;
    private String action;
    private String actionTaken;
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    final String date = LocalDate.now().toString() + ".xlsx";
    
    public Warehouse() throws SQLException {
        
        warehouseManifest();
    }
    
    /**
     * creates the warehouse manifest table
     * @throws SQLException 
     */
    
    public final void warehouseManifest() throws SQLException {
        
        try{
            db.DbCon();
            dba = "CREATE TABLE IF NOT EXISTS WarehouseManifest ("
                    + "ID INT (5),"
                    + "Container INT (5),"
                    + "Location STRING (5)"
                    + ");";
            
            ps = db.DbCon().prepareStatement(dba);
            
            ps.executeUpdate();
            
             
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            db.DbCon().close();
        }
        
    }
    
    /**
     * adds id, container and location to the warehouse manifest table
     * @param id
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void addToManifest(int id, short container, String location) throws SQLException {
        
        try {
            
            db.DbCon();
            dba = "INSERT INTO WarehouseManifest (ID, Container, Location) VALUES (?,?,?)";
            ps =db.DbCon().prepareStatement(dba);
            
            ps.setInt(1, id);
            ps.setShort(2, container);
            ps.setString(3, location);
            
            ps.executeUpdate();
            
        }catch(SQLException e) {
        
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
        
            db.DbCon().close();
        }     
    }
    
    /**
     * creates inventoryList
     * @param container
     * @throws FileNotFoundException 
     */
    
    public void createInventoryList(short container) throws FileNotFoundException {
        
        String[] header = {"#Items", "Item", "Description", "Damage"};
        
        String con2str = String.valueOf(container);
      
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\ContainerStoreData\\Inventorys\\" + con2str + ".xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook()) {

            XSSFSheet sheet = wb.createSheet("constr");
            
            Row row = sheet.createRow(0);

            for(int i= 0; i < header.length; i++) {

                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);

                if(i == 0){sheet.autoSizeColumn(i);}
                else{sheet.setColumnWidth(i, 10_000);}
            }

           
                wb.write(bos);
                bos.flush();

            } catch (IOException ex) {

                Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                
            }         
      }
    
    
    /**
     * checks for duplicate container numbers
     * @param container
     * @return
     * @throws SQLException 
     */
    
    public boolean checkContainer(short container) throws SQLException {
        
        boolean available = true;
        
        try {
            db.DbCon();
            dba = "SELECT * FROM warehouseManifest WHERE Container = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setShort(1, container);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                short search = rs.getShort("Container");
                
                if (search == container) {
                    
                    int getId = rs.getInt("ID");
                    String getLocation = rs.getString("Location");
                    
                    if(getLocation.matches("EMPTY")) {
                        
                        available = true;
                        
                        break;
                    }
                    
                    String getName = customer.getCustomerNameByID(getId);
                    
                //displays details of customer Container is allocated to    
                    
                    JOptionPane.showMessageDialog(null, "Container in use by: \n " + "ID: " + getId + "\n Name:  " + getName + "\n Container:  " + container + "\n Location: " + getLocation);
                    
                    available = false;
                    
                    break;
                }               
            }
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally {
            
            rs.close();
            db.DbCon().close();
        }
        
        return available;
    }
    
    /**
     * checks for duplicate location
     * @param location
     * @return
     * @throws SQLException 
     */
    
    public boolean checkLocation(String location) throws SQLException {
        
        boolean available = true;
        
        try {
            db.DbCon();
            dba = "SELECT * FROM warehouseManifest WHERE Location = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, location);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                String search = rs.getString("Location");
                
                if (search.matches(location)) {
                    
                    int getId = rs.getInt("ID");
                    short getCon = rs.getShort("Container");
                    
                    String getName = customer.getCustomerNameByID(getId);
                    
                //displays details of customer location is allocated to
                    
                     JOptionPane.showMessageDialog(null, "Location in use by: \n " + "ID: " + getId + "\n Name:  " + getName + "\n Container:  " + getCon + "\n Location: " + location);
                    
                    available = false;
                    
                    break;
                }               
            }
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally {
            
            rs.close();
            db.DbCon().close();
        }
        
        return available;
    }
    
    /**
     * gets number of rows in container list
     * @param ID
     * @return
     * @throws SQLException 
     */
    
    public int getContainerCount(int ID) throws SQLException {
        
        int count = 0;
               
        try {
            
            db.DbCon();
            dba = "SELECT COUNT(*) AS counted FROM '" + ID + "'";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                count = rs.getInt("counted");
                
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }

        return count;
    }
    
    /**
     * gets all of the customers containers and their locations
     * @param Id
     * @return
     * @throws SQLException 
     */
    
    public Object[][] getConainersAndLocations(int Id) throws SQLException {
        
        int place = 0;
        Object[][] conLoc = new Object[getContainerCount(Id)][2];
        
        try{
            db.DbCon();
            dba = "SELECT * From '" + Id + "'";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                  
                short con = rs.getShort("Container");
                String loc = rs.getString("Location");
                
                conLoc[place][0] = con;
                conLoc[place][1] = loc;  
                place++;
                            
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
               
        return conLoc;
        
    }    
    
    /**
     * gets all customers containers by name
     * @param name
     * @return
     * @throws SQLException 
     */
    
    public Short[] getCustomersContainers(String name) throws SQLException {
        
        customer = new Customer();
        int getId = customer.getCustomerIdFromName(name);
        Short[] containers = new Short[getContainerCount(getId)];
        
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT container FROM '" + getId + "'";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                containers[place++] = rs.getShort("container");
                
            }
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return containers;
        
    }
    /**
     * adds new containers to existing customer
     * @param ID
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void addContainers(int ID, short container, String location) throws SQLException {
        
        action = "Container Added";
        
        try {
            
            db.DbCon();
            dba = "INSERT INTO '" + ID + "' (Container, Location) VALUES (?,?)";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setShort(1, container);
            ps.setString(2, location);
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "ContainerList '" + ID + "' Updated With Container Number:'" + container + "'");
            
            createInventoryList(container);
            
            int update = getContainerCount(ID);
            
            customer.updateContainerCount(ID, update);
            
            log.Log(action, container + " In " + location + " Added To " + ID);
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
     
            db.DbCon().close();
        }   
    }
    
    /**
     * gets all the ids of the customers
     * @return
     * @throws SQLException 
     */
    
    public int[] getIDs() throws SQLException {
        
        int[] ids = new int [customer.getNumberOfCustomers()];
        int place = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT ID From Customers";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                ids[place++] = rs.getInt("ID");
               
            }      
      
        }catch(SQLException e) {
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return ids;
    }
  
    /**
     * searches all tables for container location
     * @param container
     * @return
     * @throws SQLException 
     */
    
    public String getLocation(short container) throws SQLException {
        
        String Location = "NOT FOUND";
        int[] ids = getIDs();
        
        try{
            db.DbCon();
            
    idLoop: for(int i : ids) {
                
                dba = "SELECT Location FROM '" + i + "' WHERE container = ?";
                ps = db.DbCon().prepareStatement(dba);
                ps.setShort(1, container);
                
                rs = ps.executeQuery();
                
                while(rs.next()) {
                    
                    Location = rs.getString("location");
                    
                    break idLoop;
                }
            }
    
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return Location;
    }
    
    /**
     * gets id by container
     * @param container
     * @return
     * @throws SQLException 
     */
    
    public int getIdByContainer(short container) throws SQLException {
        
        id = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT id FROM WarehouseManifest WHERE container = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setShort(1, container);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                id = rs.getInt("ID");
                break;
            }

         }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(1);
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return id;
    }
    
    /**
     * updates container with new location
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void moveContainer(short container, String location) throws SQLException {
        
        int getId = getIdByContainer(container);
        action = "Container Moved";
        
        if(getId != 0) {  
            
            try {
                    db.DbCon();
                    dba = "UPDATE'" + getId + "' SET location = ? WHERE container = ?";
                    ps = db.DbCon().prepareStatement(dba);

                    ps.setString(1, location);
                    ps.setShort(2, container);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Moved Container: '"+ container + " Into Location: '" + location+ "");

                    updateManifestLocation(getId, container, location);

                    log.Log(action, container + " Moved To " + location);

            }catch(SQLException e) {

                JOptionPane.showMessageDialog(null, e.getMessage());

            }finally{

                db.DbCon().close();
            }
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Container: '" + container + "' Is Not Assigned To Any Customer");
            
        }    
    }
    
    /**
     * update the warehouse manifest
     * @param container
     * @param location
     * @throws SQLException 
     */
    
    public void updateManifestLocation(int id, short container, String location) throws SQLException {


    try {
            db.DbCon();
            dba = "UPDATE wareHouseManifest SET location = ?, id = ? WHERE container = ?";
            ps = db.DbCon().prepareStatement(dba);

            ps.setString(1, location);
            ps.setInt(2, id);
            ps.setShort(3, container);

            ps.executeUpdate();

    }catch(SQLException e) {

        JOptionPane.showMessageDialog(null, e.getMessage());

    }finally{

        db.DbCon().close();
    }
}
    
    /**
     * deletes container from customer database
     * @param container
     * @throws SQLException 
     */
    
    public void emptyContainer(short container) throws SQLException {
        
        int getId = getIdByContainer(container);
        action = "Container Emptied";
        String name = customer.getCustomerNameByID(getId);
             
        try {
            
            db.DbCon();
            dba = "DELETE FROM '" + getId + "' WHERE container = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setShort(1, container);
            
            ps.executeUpdate();
            
            updateContainerToEmptyManifest(container);
            
            saveEmptiedContainer(String.valueOf(container), name);
            createInventoryList(container);
            
    //gets customer container count
             
            int  number = getContainerCount(getId);            
    
    //Removes customer from database if all containers removed        
            
            if(number == 0) {
                
               int choice = JOptionPane.showConfirmDialog(null, "This Is The Customers Last Container, Clicking OK Will Remove This Customer From The Database, Do You Want To Remove?",
                       "Remove Customer", JOptionPane.OK_CANCEL_OPTION);
                
                if(choice == 0) {

                    customer.removeCustomer(getId);
                    
                }else{
                    
                    customer.updateContainerCount(getId, number);
                }
                
            }else{
    
    //else updates customer container count
    
                customer.updateContainerCount(getId, number);
            
            }
            
    // logs action
            
            log.Log(action, container + " Emptied");
            
            JOptionPane.showMessageDialog(null, container + " Emptied");
            
         }catch(SQLException e) {
                         
            JOptionPane.showMessageDialog(null, e.getMessage());

        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{

            db.DbCon().close();
        }
    }
    
    /**
     * sets container location to empty in warehouse manifest
     * @param container
     * @throws SQLException 
     */
    
    public void updateContainerToEmptyManifest(Short container) throws SQLException {
                     
        try {
            
            db.DbCon();
            dba = "UPDATE warehouseManifest SET id = ?, location = ? WHERE container = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setInt(1, 0);
            ps.setString(2, "EMPTY");
            ps.setShort(3, container);
            
            ps.executeUpdate();
                       
         }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }finally{

            db.DbCon().close();
        }
    }

    /**
     * updates all containers in manifest to EMPTY when customer removed
     * @param cons
     * @param ID
     * @throws SQLException 
     */
    
    public void removeCustomerManifest(Short[] cons, int ID) throws SQLException{
        
           
        for(Short c: cons){

            updateContainerToEmptyManifest(c);
        }

        removeCustomerTable(ID);
    }
    
    /**
     * remove customers container table
     * @param id
     * @throws SQLException 
     */

    public void removeCustomerTable(int id) throws SQLException {        
             
        try{

            db.DbCon();
            dba = "DROP TABLE '" + id + "'";

            ps = db.DbCon().prepareStatement(dba);
            
            ps.executeUpdate();
             
             
        }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }finally{

            db.DbCon().close();                   
        }    
    }
    
    /**
     * gets number of containers in system
     * @return
     * @throws SQLException 
     */
    
    public int containerCount() throws SQLException{
               
        int number = 0;
        
        try{
            
            db.DbCon();
            dba = "SELECT COUNT(*) AS numContainers FROM warehouseManifest";
            ps = db.DbCon().prepareStatement(dba);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                number = rs.getInt("numContainers");
            }
                    
           }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }finally{
            
            rs.close();
            db.DbCon().close();                   
        } 
        
        return number;
        
    }

    /**
     * gets all container numbers from system
     * @return
     * @throws SQLException 
     */
    
   public Short[] getAllContainers() throws SQLException {
       
       Short[] containers = new Short[containerCount()];
       int c = 0;
       
       try{
           db.DbCon();
           dba = "SELECT * FROM WarehouseManifest";
           ps = db.DbCon().prepareStatement(dba);
           
           rs = ps.executeQuery();
           
           while(rs.next()) {
               
               containers[c++] = rs.getShort("container");
               
           }

       }catch(SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

        }finally{

            rs.close();
            db.DbCon().close();                   
        } 
       
       return containers;
   }
   
   /**
    * returns all containers and locations from manifest
    * @return 
     * @throws java.sql.SQLException 
    */
   
   public Map<Short, String> getAllContainersAndLocations() throws SQLException {
       
        Map<Short, String> conLocs = new TreeMap<>();
                
        try{

            db.DbCon();
            dba = "SELECT * FROM warehouseManifest";
            ps = db.DbCon().prepareStatement(dba);

            rs = ps.executeQuery();

            while(rs.next()){
                
               conLocs.put(rs.getShort("container"), rs.getString("location"));

            }

        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close(); 
            db.DbCon().close();
        }
        
        return conLocs;
   }
   
   /**
    * search all containers for search item
     * @param Item
     * @param name
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.io.FileNotFoundException 
    */
   
   public Object[][] searchAllContainers(String Item, String name) throws SQLException, FileNotFoundException, IOException {
       
        Short[] containers;
       
        if(name.isEmpty()) {
            
            containers = getAllContainers();
            
        }else{
            
            containers = getCustomersContainers(name);            
        }
        
       String searched = "";
       Object[][] results = new Object[100][6];
       int place = 0;
       boolean found = false;
       
       customer = new Customer();
              
            for(Short con : containers) {

                if(con != null) {

                    File inventory = new File("C:\\ContainerStoreData\\Inventorys\\"+ con +".xlsx");

                    try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inventory));
                        XSSFWorkbook wb = new XSSFWorkbook(bis)  ){

                        XSSFSheet sheet = wb.getSheetAt(0);

                        Iterator <Row> itr = sheet.iterator();

                        while(itr.hasNext()) {

                            Row rows = itr.next();
                            Iterator<Cell> cellItr = rows.cellIterator();

                            while(cellItr.hasNext()) {

                                Cell cell = cellItr.next();

                                if(cell.getColumnIndex() == 1){

                                     searched = cell.getStringCellValue();

                                     if(searched.contains(Item)){

                                         int ID = getIdByContainer(con);
                                         name = customer.getCustomerNameByID(ID);
                                         String location = getLocation(con);

                                         results[place][0] = ID;
                                         results[place][1] = name;
                                         results[place][2] = searched;
                                         found = true;
                                         results[place][4] = con;
                                         results[place][5] = location;               
                                     }

                                 }if(cell.getColumnIndex() == 2 && found == true){

                                     results[place][3] = cell.getStringCellValue();
                                     found = false;
                                     place++;
                                 } 
                            }              
                        }           
                     }catch(IOException e) {

                        JOptionPane.showMessageDialog(null, e.getMessage());
                
                    }  
                }
            }

       return results;
   }
   
   /**
    * searches for containers with a description
    * @param item
    * @param description
    * @param name
    * @return
    * @throws SQLException
    * @throws FileNotFoundException
    * @throws IOException 
    */
   
   public Object[][] searchAllContainersWithDescription(String item, String description, String name) throws SQLException, FileNotFoundException, IOException{
       
       Short[] containers;
       
       if(name.isEmpty()){
           
           containers = getAllContainers();
       }else{
           
           containers = getCustomersContainers(name);
       }
       
       Object[][] results = new Object[100][6];
       int place = 0;
       String searched = "";
       String described = "";
       boolean found = false;

                for(Short con : containers){
                    
                    if(con != null) {
                        
                        File inventory = new File("C:\\ContainerStoreData\\Inventorys\\" + con + ".xlsx");

                        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inventory));
                            XSSFWorkbook wb = new XSSFWorkbook(bis)  ){
            
                        XSSFSheet sheet = wb.getSheetAt(0);

                        Iterator<Row> itr = sheet.iterator();

                        while(itr.hasNext()) {

                            found = false;      
                            Row row = itr.next();
                            Iterator<Cell> cellIterator = row.cellIterator();

                            while(cellIterator.hasNext()){

                                Cell cell = cellIterator.next();

                                if(cell.getColumnIndex() == 1) {

                                    searched = cell.getStringCellValue();

                                    if(searched.contains(item)) {

                                        found = true;
                                    }                         
                                }

                                if(cell.getColumnIndex() == 2 && found == true){

                                    described = cell.getStringCellValue();

                                    if(described.contains(description)) {

                                        int ID = getIdByContainer(con);
                                        name = customer.getCustomerNameByID(ID);
                                        String location = getLocation(con);

                                        results[place][0] = ID;
                                        results[place][1] = name;
                                        results[place][2] = searched;
                                        results[place][3] = described;
                                        results[place][4] = con;
                                        results[place][5] = location;  

                                        place++;

                                    } 
                               }       
                           }  
                       }              
                    }catch(IOException e) {

                        JOptionPane.showMessageDialog(null, e.getMessage());
                
                    }  
                }
           }           
       return results;
   }
   
   /** 
    * gets inventory from single container
    * @param container
    * @return
    * @throws IOException 
    */
   
    public Object[][] findInventory(Short container) throws IOException {
       
        Object[][] result = new Object[getNumberOfRowsInInventoryList(container)][4];
        
        int r = -1;
        int value = 0;
             
        File inventory = new File("C:\\ContainerStoreData\\Inventorys\\" + container + ".xlsx");
            
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inventory));
               XSSFWorkbook wb = new XSSFWorkbook(bis)  ){

                XSSFSheet sheet = wb.getSheetAt(0);

                Iterator<Row> itr = sheet.iterator();

                while(itr.hasNext()) {

                    Row row = itr.next();
                    Iterator<Cell> cellItr = row.iterator();
                    
                    value = 0;
                                      
                    while(cellItr.hasNext()) {

                        Cell cell = cellItr.next();
                        
                        if(row.getRowNum() > 0) {
                                                             
                            result[r][value] = cell;
                            value++;    
                            
                        }
                    }

                    r++;
                    
                }

            } catch (FileNotFoundException ex) {
                
                JOptionPane.showMessageDialog(null, "Inventory Not Found");
                
            }catch (IOException | org.apache.poi.EmptyFileException ex) {
                    
                //message repeated
        }
        
        return result;
    }
    
    /**
     * updates inventory with new values
     * @param container 
     * @param newValues 
     * @throws java.io.FileNotFoundException 
     */
    
    public synchronized void updateInventory(Short container, Object[][] newValues) throws FileNotFoundException, IOException{
    
        action = "Inventory Updated";
        Cell cCell;
        Row cRow;
        int r = 0;
        int c = 0;

        File inventory = new File("C:\\ContainerStoreData\\Inventorys\\" + container + ".xlsx");
            
            try(FileInputStream xfis = new FileInputStream(inventory); 
                XSSFWorkbook xwb = new XSSFWorkbook(xfis);
                FileOutputStream xfos = new FileOutputStream(inventory)){

                XSSFSheet sheet = xwb.getSheetAt(0);
                               
                int totalRows = sheet.getPhysicalNumberOfRows();
      
    //adding new rows to the length of the excell table if adding more entries
            
            if(totalRows < newValues.length + 1) {
                
                while(totalRows < newValues.length + 1) {

                     cRow = sheet.createRow(totalRows++);

                     for(int i = 0; i < 4; i++) {

                         cCell = cRow.createCell(i);

                     }
                 }
                
    //removing rows if the new entrys are less than original
    
            }else{
            
                while(totalRows > newValues.length + 1){
                    
                    int last = sheet.getLastRowNum();
                    Row row = sheet.getRow(last);
                    
                    sheet.removeRow(row);
                    
                    totalRows = sheet.getPhysicalNumberOfRows();
                }
            }
            
                xwb.write(xfos);
                xwb.close();
                xfos.flush();
                
                                        
    //adding new values
            
            try(FileInputStream fisx = new FileInputStream(inventory); 
                XSSFWorkbook wbx = new XSSFWorkbook(fisx);
                FileOutputStream fosx = new FileOutputStream(inventory)) {   

                sheet = wbx.getSheetAt(0);

                Iterator<Row> itr = sheet.iterator();

                while(itr.hasNext()) {

                    Row row = itr.next();
                    Iterator<Cell> cellItr = row.iterator();
                    c = 0;

                    while(cellItr.hasNext()) {

                        Cell cell = cellItr.next();

                        if(row.getRowNum() > 0) {

                            String entry = newValues[r][c].toString();
                            cell.setCellValue(entry);
                            c++;
                        }                    
                    }

                    if(row.getRowNum() > 0) {

                        r++;
                    }
                }

                wbx.write(fosx);
                fosx.flush();

                JOptionPane.showMessageDialog(null, "Container: " + container + " Has Been Updated.");

                log.Log(action, container + " Updated ");

            }catch(FileNotFoundException e){

                JOptionPane.showMessageDialog(null, e.getMessage());

            } catch (SQLException ex) {

                Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    } 
    
    /**
     * removes a certain amount of the items under single item name
     * @param item
     * @param number
     * @param container 
     * @param description 
     */
    
    public void removeItem(String item, int number, String container, String description){
        
        boolean found = false;
        int changeRow = 0;
        int startNum = 0;
        Row rowToChange = null;
        Cell cellNum = null;
        
        File remove = new File("C:\\ContainerStoreData\\Inventorys\\" + container + ".xlsx");
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(remove));
                XSSFWorkbook wb = new XSSFWorkbook(bis);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(remove));){
        
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            
            while(itr.hasNext()){
                
                Row row = itr.next();
                Iterator<Cell> cellItr = row.iterator();
                
                while(cellItr.hasNext()){
                    
                    Cell cell =  cellItr.next();
                    
                    if(cell.getStringCellValue().matches(item)){

                        found = true;
                    }
                    
                    if(found == true && cell.getStringCellValue().matches(description)){

                        changeRow = row.getRowNum();
                        cellNum = sheet.getRow(changeRow).getCell(0);
                        startNum = Integer.parseInt(cellNum.getStringCellValue());

                        rowToChange = sheet.getRow(changeRow);
                    }
                }
            }
       
    //checks if all the items have been removed by pick list
    
            if(startNum - number == 0) {
                
                sheet.removeRow(rowToChange);
                
                action = "Removed Item";
                actionTaken = "Item: '" + item + "' Description: '" + description + "' From Container: '" + container;

                log.Log(action, actionTaken);
                
            }else{
            
                String ajust = String.valueOf(startNum - number); 
                cellNum.setCellValue(ajust);
                
                action = "Removed Item";
                actionTaken = "Number Of Items: '" + number + " Item: '" + item + "' Description: '" + description + "' From Container: '" + container;

                log.Log(action, actionTaken);
            
            wb.write(bos);
            bos.flush();
            
            }
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException | SQLException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
        
    /**
     * gets the number of rows in the inventory of chosen container
     * @param container
     * @return 
     */
    
    public synchronized int getNumberOfRowsInInventoryList(Short container){
              
        int rowNumbers = 0;
        File inventory = new File("C:\\ContainerStoreData\\Inventorys\\" + container + ".xlsx");
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inventory)); 
             XSSFWorkbook xwb = new XSSFWorkbook(bis)){

            XSSFSheet sheet = xwb.getSheetAt(0);

            rowNumbers = sheet.getLastRowNum();        
             
        } catch (FileNotFoundException ex) {
            
            JOptionPane.showMessageDialog(null, "Inventory Not Found");
            
        } catch (IOException | org.apache.poi.EmptyFileException ex) {
            
            JOptionPane.showMessageDialog(null, "Inventory Empty");
            
        }
        
        return rowNumbers;
    }
    
    /**
     * saves the pick list into eXcell format
     * @param picked
     * @throws IOException 
     */
    
    public void savePickList(Object[][]picked) throws IOException{
        
        String[] header = {"Name", "container", "location", "#Items", "Item", "Notes"};
        
            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\ContainerStoreData\\PickLists\\" + date)); 
                    XSSFWorkbook wbpl = new XSSFWorkbook()){

                XSSFSheet sheet = wbpl.createSheet("date");

                Row row = sheet.createRow(0);

                for(int i = 0; i < header.length; i++){

                    Cell cell = row.createCell(i);
                    cell.setCellValue(header[i]);

                    sheet.setColumnWidth(i, 5_000);
                }

                int addRow = 1;

                for (Object[] picked1 : picked) {

                    Row newRow = sheet.createRow(addRow++);

                    for (int k = 0; k < picked1.length; k++) {

                        Cell cell = newRow.createCell(k);
                        cell.setCellValue(picked1[k].toString());
                    }
                }

                
                wbpl.write(bos);
                bos.flush();
                
                JOptionPane.showMessageDialog(null, "Pick List Created");

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
    
    /**
     * adding to existing pickList
     * @return 
     * @throws IOException 
     */
    
    public Object[][] addToPickList() throws IOException {
    
        int r = -1, c = 0;
        
        Object[][]List = new Object[getNumberOfRowsInTable()][6];
                
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\ContainerStoreData\\PickLists\\" + date));                
                     XSSFWorkbook Wb = new XSSFWorkbook(bis)){
            
            XSSFSheet sheet = Wb.getSheetAt(0);
 
            Iterator<Row> itr = sheet.iterator();
            
            while(itr.hasNext()){
                
                Row row = itr.next();
                Iterator<Cell> cellItr = row.iterator();
                c = 0;
                
                while(cellItr.hasNext()) {
                    
                    Cell cell = cellItr.next();
                                        
                    if(row.getRowNum()>0){
                                                
                        List[r][c] = cell;
                        c++;
                    }
                }
                
                    r++;
                
            }
            
            JOptionPane.showMessageDialog(null, "Pick List Updated");
            
        }   catch (FileNotFoundException ex) {
            
            //not found because it has not been created yet\
        }
        return List;
    }
    
    /**
     * get number of rows in pick list
     * @return 
     */
    public synchronized int getNumberOfRowsInTable(){

        int rowNumbers = 0;

        File pickList = new File("C:\\ContainerStoreData\\PickLists\\" + date);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pickList)); 
             XSSFWorkbook xwb = new XSSFWorkbook(bis)){

            XSSFSheet sheet = xwb.getSheetAt(0);

            rowNumbers = sheet.getLastRowNum();        

        } catch (FileNotFoundException ex) {

            JOptionPane.showMessageDialog(null, "No Pick List Currently Active");

        } catch (IOException ex) {

            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return rowNumbers;
    }
    
    /**
     * retrieves all pick lists that have not been finalized in folder
     * @return
     * @throws IOException 
     */
    
    public ArrayList<String> getLists() throws IOException{
        
        ArrayList<String> list = new ArrayList<>();
        
        File[] files = new File("C:\\ContainerStoreData\\PickLists").listFiles();

        for(File s: files){
            
            if(!s.getName().contains("Finalized-")){
                
                    list.add(s.getName());
            }
        }
        
        return list;
    }
    
    /**
     * retrieves pickList
     * @param list
     * @return 
     * @throws IOException 
     */
    
    public Object[][] getPickList(String list) throws IOException {
    
        int r = -1, c = 0;
        
        Object[][]List = new Object[getNumberOfRowsInList(list)][6];
                
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\ContainerStoreData\\PickLists\\" + list));                
                     XSSFWorkbook Wb = new XSSFWorkbook(bis)){
            
            XSSFSheet sheet = Wb.getSheetAt(0);
 
            Iterator<Row> itr = sheet.iterator();
            
            while(itr.hasNext()){
                
                Row row = itr.next();
                Iterator<Cell> cellItr = row.iterator();
                c = 0;
                
                while(cellItr.hasNext()) {
                    
                    Cell cell = cellItr.next();
                                        
                    if(row.getRowNum()>0){
                                                
                        List[r][c] = cell;
                        c++;
                    }
                }
                
                    r++;
                
            }
            
        }   catch (FileNotFoundException ex) {
            
            JOptionPane.showMessageDialog(null, "PickList Not Found");
        }
        return List;
    }
    
    /**
     * get number of rows in pick list
     * @param list
     * @return 
     */
    public synchronized int getNumberOfRowsInList(String list){

        int rowNumbers = 0;

        File pickList = new File("C:\\ContainerStoreData\\PickLists\\" + list);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(pickList)); 
             XSSFWorkbook xwb = new XSSFWorkbook(bis)){

            XSSFSheet sheet = xwb.getSheetAt(0);

            rowNumbers = sheet.getLastRowNum();        

        } catch (FileNotFoundException ex) {

     

        } catch (IOException ex) {

            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return rowNumbers;
    }
    
    /**
     * finalizes the pick list
     * @param list 
     * @throws java.io.IOException 
     */
    
    public void setToFinalized(String list) throws IOException {

        LocalDateTime ldt = LocalDateTime.now();
        
        byte num = 1;
               
        Path from = Paths.get("C:\\ContainerStoreData\\PickLists\\" + list);
        
        Path To = Paths.get("C:\\ContainerStoreData\\PickLists\\Finalized-#" + num + "-" + list);
        
        while(To.toFile().exists()){
            
            num++;
            To = Paths.get("C:\\ContainerStoreData\\PickLists\\Finalized-#" + num + "-" + list);
            
        }

        try{
            
            Files.move(from, To, StandardCopyOption.REPLACE_EXISTING);
            
            JOptionPane.showMessageDialog(null, "Pick List Finalized");
            
            log.Log("Pick List Finalized", To.toFile().toString());
            
        }catch(IOException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * gets the number of empty containers in the system;
     * @return 
     * @throws java.sql.SQLException 
     */
    
    public int getNumOfEmptyContainers() throws SQLException{
        
        int emptys = 0;
        
        try{
            db.DbCon();
            dba = "SELECT * FROM warehouseManifest WHERE location = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setString(1, "EMPTY");
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                emptys++;
                
            }
                         
        }catch(SQLException e){
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return emptys;
    }
    
    /**
     * checks if container exists in warehouse manifest
     * @param container
     * @return
     * @throws SQLException 
     */
    
    public boolean containerPresent(Short container) throws SQLException{
        
        boolean found = false;
        
        try{
            
            db.DbCon();
            dba = "SELECT * FROM warehouseManifest WHERE container = ?";
            ps = db.DbCon().prepareStatement(dba);
            
            ps.setShort(1, container);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                found = true;
                
            }
            
        }catch(SQLException e){
            
             JOptionPane.showMessageDialog(null, e.getMessage());
            
        }finally{
            
            rs.close();
            db.DbCon().close();
        }
        
        return found;
    }
    
    /**
     * saves inventory of emptied container
     * @param container
     * @param name
     * @throws IOException 
     */
    
    public void saveEmptiedContainer(String container, String name) throws IOException {

        String thisDate = log.getDate();
        
        Path from = Paths.get("C:\\ContainerStoreData\\EmptiedContainers\\'" + container + ".xlsx");
        
        Path To = Paths.get("C:\\ContainerStoreData\\EmptiedContainers\\'" + name + "-" + thisDate + "-Container-" + container + ".xlsx");

        try{
            
            Files.move(from, To, StandardCopyOption.REPLACE_EXISTING);
            
            JOptionPane.showMessageDialog(null, "Container Number: " +  container + " Inventory Saved");
            
            log.Log("Container Saved", To.toFile().toString());
            
        }catch(IOException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
