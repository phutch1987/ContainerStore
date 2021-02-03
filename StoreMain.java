/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containerstore;

import java.awt.Color;
import java.awt.print.PrinterException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author hutch
 */
public class StoreMain extends javax.swing.JFrame implements java.io.Serializable {
      
    static String user;
    private String cleared = "";
    private String action;
    private String actionTaken;
    
    
    private boolean open = false; // variable to stop new rows being added to blank table with no container association
    private boolean edited = false; // variable to stop duplication of items during relocation
    
    
    private int ID;
    private String name;
    private String address;
    private String number;
    private short container;
    private ArrayList<Short> containerList = new ArrayList<>();
    private String location;
    private ArrayList<String> locationList = new ArrayList<>();
    private Stack<Object[]> deletedRows = new Stack<>();
      
    private String dba;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Customer customer = new Customer();
    private Warehouse wh = new Warehouse();
    private DatabaseConnect db = new DatabaseConnect();
    private Log log = new Log();
    private LogIn li = new LogIn();
    
  
    public StoreMain() throws SQLException {
               
        initComponents();
                 
        Tables();
        
        populateMainTable();
        populateMainConLocTable();
        
        checkFinalized();
    
    }
    
    /**
     * edits the visuals of the main tables
     */
    
    public final void Tables() {
         
        MainCustomerScroll.getViewport().setOpaque(false);
        MainCustomerScroll.setBackground(new Color(0,0,0,0));
        
        MainCustomerTable.setBackground(new Color(0,0,0,0));
        
        MainCustomerTable.setShowGrid(true);
        
        MainCustomerTable.setRowHeight(MainCustomerTable.getRowHeight() + 5);
        
        MainCustomerTable.setForeground(Color.white);
        
        MainCustomerTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)MainCustomerTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
        
        MainConLocScroll.getViewport().setOpaque(false);
        MainConLocScroll.setBackground(new Color(0,0,0,0));
        
        MainConLocTable.setBackground(new Color(0,0,0,0));
        
        MainConLocTable.setShowGrid(true);
        
        MainConLocTable.setRowHeight(MainConLocTable.getRowHeight() + 5);
        
        MainConLocTable.setForeground(Color.white);
        
        MainConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)MainConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
        
        MainInventoryScroll.getViewport().setOpaque(false);
        MainInventoryScroll.setBackground(new Color(0,0,0,0));
        
        MainInventoryTable.setBackground(new Color(0,0,0,0));
        
        MainInventoryTable.setShowGrid(true);
        
        MainInventoryTable.setRowHeight(MainInventoryTable.getRowHeight() + 5);
        
        MainInventoryTable.setForeground(Color.white);
        
        MainInventoryTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)MainInventoryTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        ANCConLocScroll.getViewport().setOpaque(false);
        ANCConLocScroll.setBackground(new Color(0,0,0,0));
        
        ANCConLocTable.setBackground(new Color(0,0,0,0));
        
        ANCConLocTable.setShowGrid(true);
        
        ANCConLocTable.setRowHeight(ANCConLocTable.getRowHeight() + 5);
        
        ANCConLocTable.setForeground(Color.white);
        
        ANCConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)ANCConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        SCConLocScroll.getViewport().setOpaque(false);
        SCConLocScroll.setBackground(new Color(0,0,0,0));
        
        SCConLocTable.setBackground(new Color(0,0,0,0));
        
        SCConLocTable.setShowGrid(true);
        
        SCConLocTable.setRowHeight(SCConLocTable.getRowHeight() + 5);
        
        SCConLocTable.setForeground(Color.white);
        
        SCConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)SCConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        ACConLocScroll.getViewport().setOpaque(false);
        ACConLocScroll.setBackground(new Color(0,0,0,0));
        
        ACConLocTable.setBackground(new Color(0,0,0,0));
        
        ACConLocTable.setShowGrid(true);
        
        ACConLocTable.setRowHeight(ACConLocTable.getRowHeight() + 5);
        
        ACConLocTable.setForeground(Color.white);
        
        ACConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)ACConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        INConLocScroll.getViewport().setOpaque(false);
        INConLocScroll.setBackground(new Color(0,0,0,0));
        
        INConLocTable.setBackground(new Color(0,0,0,0));
        
        INConLocTable.setShowGrid(true);
        
        INConLocTable.setRowHeight(INConLocTable.getRowHeight() + 5);
        
        INConLocTable.setForeground(Color.white);
        
        INConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)INConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        INInventoryScroll.getViewport().setOpaque(false);
        INInventoryScroll.setBackground(new Color(0,0,0,0));
        
        INInventoryTable.setBackground(new Color(0,0,0,0));
        
        INInventoryTable.setShowGrid(true);
        
        INInventoryTable.setRowHeight(INInventoryTable.getRowHeight() + 5);
        
        INInventoryTable.setForeground(Color.white);
        
        INInventoryTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)INInventoryTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        SIInventorySearchedScroll.getViewport().setOpaque(false);
        SIInventorySearchedScroll.setBackground(new Color(0,0,0,0));
        
        SIInventorySearchedTable.setBackground(new Color(0,0,0,0));
        
        SIInventorySearchedTable.setShowGrid(true);
        
        SIInventorySearchedTable.setRowHeight(SIInventorySearchedTable.getRowHeight() + 5);
        
        SIInventorySearchedTable.setForeground(Color.white);
        
        SIInventorySearchedTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)SIInventorySearchedTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        EIInventoryScroll.getViewport().setOpaque(false);
        EIInventoryScroll.setBackground(new Color(0,0,0,0));
        
        EIInventoryTable.setBackground(new Color(0,0,0,0));
        
        EIInventoryTable.setShowGrid(true);
        
        EIInventoryTable.setRowHeight(EIInventoryTable.getRowHeight() + 5);
        
        EIInventoryTable.setForeground(Color.white);
        
        EIInventoryTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)EIInventoryTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        EIConLocScroll.getViewport().setOpaque(false);
        EIConLocScroll.setBackground(new Color(0,0,0,0));
        
        EIConLocTable.setBackground(new Color(0,0,0,0));
        
        EIConLocTable.setShowGrid(true);
        
        EIConLocTable.setRowHeight(EIConLocTable.getRowHeight() + 5);
        
        EIConLocTable.setForeground(Color.white);
        
        EIConLocTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)EIConLocTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        EIItemRemovedScroll.getViewport().setOpaque(false);
        EIItemRemovedScroll.setBackground(new Color(0,0,0,0));
        
        EIItemRemovedTable.setBackground(new Color(0,0,0,0));
        
        EIItemRemovedTable.setShowGrid(true);
        
        EIItemRemovedTable.setRowHeight(EIItemRemovedTable.getRowHeight() + 5);
        
        EIItemRemovedTable.setForeground(Color.white);
        
        EIItemRemovedTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)EIItemRemovedTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        VLLogScroll.getViewport().setOpaque(false);
        VLLogScroll.setBackground(new Color(0,0,0,0));
        
        VLLogTable.setBackground(new Color(0,0,0,0));
        
        VLLogTable.setShowGrid(true);
        
        VLLogTable.setRowHeight(VLLogTable.getRowHeight() + 5);
        
        VLLogTable.setForeground(Color.white);
        
        VLLogTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)VLLogTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        PLPickListScroll.getViewport().setOpaque(false);
        PLPickListScroll.setBackground(new Color(0,0,0,0));
        
        PLPickListTable.setBackground(new Color(0,0,0,0));
        
        PLPickListTable.setShowGrid(true);
        
        PLPickListTable.setRowHeight(PLPickListTable.getRowHeight() + 5);
        
        PLPickListTable.setForeground(Color.white);
        
        PLPickListTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)PLPickListTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        PLItemScroll.getViewport().setOpaque(false);
        PLItemScroll.setBackground(new Color(0,0,0,0));
        
        PLItemTable.setBackground(new Color(0,0,0,0));
        
        PLItemTable.setShowGrid(true);
        
        PLItemTable.setRowHeight(PLItemTable.getRowHeight() + 5);
        
        PLItemTable.setForeground(Color.white);
        
        PLItemTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)PLItemTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        PLContainerScroll.getViewport().setOpaque(false);
        PLContainerScroll.setBackground(new Color(0,0,0,0));
        
        PLContainerTable.setBackground(new Color(0,0,0,0));
        
        PLContainerTable.setShowGrid(true);
        
        PLContainerTable.setRowHeight(PLContainerTable.getRowHeight() + 5);
        
        PLContainerTable.setForeground(Color.white);
        
        PLContainerTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)PLContainerTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        FLPickListScroll.getViewport().setOpaque(false);
        FLPickListScroll.setBackground(new Color(0,0,0,0));
        
        FLPickListTable.setBackground(new Color(0,0,0,0));
        
        FLPickListTable.setShowGrid(true);
        
        FLPickListTable.setRowHeight(FLPickListTable.getRowHeight() + 5);
        
        FLPickListTable.setForeground(Color.white);
        
        FLPickListTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)FLPickListTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
            
        AnalyticScroll.getViewport().setOpaque(false);
        AnalyticScroll.setBackground(new Color(0,0,0,0));
        
        AnalyticTable.setBackground(new Color(0,0,0,0));
        
        AnalyticTable.setShowGrid(true);
        
        AnalyticTable.setRowHeight(AnalyticTable.getRowHeight() + 5);
        
        AnalyticTable.setForeground(Color.white);
        
        AnalyticTable.setOpaque(false);
        
            ((DefaultTableCellRenderer)AnalyticTable.getDefaultRenderer(Object.class)).setBackground(new Color(0,0,0,0));
    }
 
    /**
     * populates main customer table in the main screen
     * @throws SQLException 
     */
    
    public final void populateMainTable() throws SQLException {
        
        DefaultTableModel tm = (DefaultTableModel)MainCustomerTable.getModel();
        tm.setRowCount(0);
        
        try{
            
            customer.CustomerDatabase();
            db.DbCon();
            dba = "SELECT * FROM Customers ORDER BY Name ASC";
            ps = db.DbCon().prepareStatement(dba);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                
                tm.addRow(new Object[]{rs.getInt("ID"), rs.getString("Name"),rs.getString("Address"), "0" + rs.getString("Number"), rs.getInt("Containers"), rs.getString("Date")});
                
            }
            
        }catch(SQLException e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
        }       
    }
    
    /**
     * populates the main container and location table
     */
    
    public final void populateMainConLocTable(){
                
        try {
            
            DefaultTableModel tm = (DefaultTableModel)MainConLocTable.getModel();
            
            Map<Short, String> conLocs = wh.getAllContainersAndLocations();
            
            tm.setRowCount(0);
            
            for(Short c: conLocs.keySet()) {
                
                tm.addRow(new Object[]{c, conLocs.get(c)});
                
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddNewCustomerWindow = new javax.swing.JFrame();
        jPanel18 = new javax.swing.JPanel();
        ANCPanel = new javax.swing.JPanel();
        ANCNameLabel = new javax.swing.JLabel();
        ANCAddressLabel = new javax.swing.JLabel();
        ANCTelePhoneNumberLabel = new javax.swing.JLabel();
        ANCContainerLabel = new javax.swing.JLabel();
        ANCAddConLocBTN = new javax.swing.JButton();
        ANCLocationLabel = new javax.swing.JLabel();
        ANCConLocScroll = new javax.swing.JScrollPane();
        ANCConLocTable = new javax.swing.JTable();
        ANCConfirmBTN = new javax.swing.JButton();
        ANCRemoveBTN = new javax.swing.JButton();
        ANCClearBTN = new javax.swing.JButton();
        ANCAddressInput = new javax.swing.JTextArea();
        ANCNameInput = new javax.swing.JTextField();
        ANCTelePhoneNumberInput = new javax.swing.JTextField();
        ANCContainerInput = new javax.swing.JTextField();
        ANCLocationInput = new javax.swing.JTextField();
        ANCBackgroundLabel = new javax.swing.JLabel();
        EditCustomerWindow = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ECNameSearchLabel = new javax.swing.JLabel();
        ECIDSearchLabel = new javax.swing.JLabel();
        ECNameSearchInput = new javax.swing.JTextField();
        ECIDSearchInput = new javax.swing.JTextField();
        ECSearchBTN = new javax.swing.JButton();
        ECNameLabel = new javax.swing.JLabel();
        ECAdressLabel = new javax.swing.JLabel();
        ECTelephoneLabel = new javax.swing.JLabel();
        ECNameInput = new javax.swing.JTextField();
        ECTelephoneInput = new javax.swing.JTextField();
        ECUpdateBTN = new javax.swing.JButton();
        ECDateAddedLabel = new javax.swing.JLabel();
        ECClearBTN = new javax.swing.JButton();
        ECAddressOutput = new javax.swing.JTextArea();
        ECAddressInput = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        ECNameOutput = new javax.swing.JLabel();
        ECTelephoneOutput = new javax.swing.JLabel();
        ECDateOutput = new javax.swing.JLabel();
        ECBackgroudLabel = new javax.swing.JLabel();
        SearchCustomerWindow = new javax.swing.JFrame();
        jPanel20 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        SCNameSearchLabel = new javax.swing.JLabel();
        SCIDSearchLabel = new javax.swing.JLabel();
        SCIDOutputLabel = new javax.swing.JLabel();
        SCNameOutputLabel = new javax.swing.JLabel();
        SCAddressOutputLabel = new javax.swing.JLabel();
        SCTelephoneOutputLabel = new javax.swing.JLabel();
        SCNameSearchInput = new javax.swing.JTextField();
        SCConLocScroll = new javax.swing.JScrollPane();
        SCConLocTable = new javax.swing.JTable();
        SCSearchBTN = new javax.swing.JButton();
        SCClearBTN = new javax.swing.JButton();
        SCIDSearchInput = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        SCAddressOutput = new javax.swing.JTextArea();
        SCContainerInput = new javax.swing.JTextField();
        SCContainerSearchLabel = new javax.swing.JLabel();
        SCSearchContainerBTN = new javax.swing.JButton();
        SCIDOutput = new javax.swing.JLabel();
        SCNameOutput = new javax.swing.JLabel();
        SCTelephoneOutput = new javax.swing.JLabel();
        SCBackgroudLabel = new javax.swing.JLabel();
        AddContainerToExistingCustomer = new javax.swing.JFrame();
        jPanel23 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        ACNameSearchInput = new javax.swing.JTextField();
        ACConLocScroll = new javax.swing.JScrollPane();
        ACConLocTable = new javax.swing.JTable();
        ACContainerInput = new javax.swing.JTextField();
        ACLocationInput = new javax.swing.JTextField();
        ACSearchBTN = new javax.swing.JButton();
        ACAddBTN = new javax.swing.JButton();
        ACUpdateBTN = new javax.swing.JButton();
        ACNameLabel = new javax.swing.JLabel();
        ACIDLabel = new javax.swing.JLabel();
        ACNameOutputLabel = new javax.swing.JLabel();
        ACIDOutputLabel = new javax.swing.JLabel();
        ACCustomerLabel = new javax.swing.JLabel();
        ACContainerLabel = new javax.swing.JLabel();
        ACLocationLabel = new javax.swing.JLabel();
        ACIDSearchInput = new javax.swing.JTextField();
        ACRemoveBTN = new javax.swing.JButton();
        ACNameOutput = new javax.swing.JLabel();
        ACIDOutput = new javax.swing.JLabel();
        ACBackgroudLabel = new javax.swing.JLabel();
        SearchContainerWindow = new javax.swing.JFrame();
        jPanel24 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        SCONContainerLabel = new javax.swing.JLabel();
        SCONLocationLabel = new javax.swing.JLabel();
        SCONContainerInput = new javax.swing.JTextField();
        SCONSearchBTN = new javax.swing.JButton();
        SCONLocationOutput = new javax.swing.JLabel();
        SCBackgroundLabel = new javax.swing.JLabel();
        MoveContainerWindow = new javax.swing.JFrame();
        jPanel25 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        MCContainerInput = new javax.swing.JTextField();
        MCMoveToInput = new javax.swing.JTextField();
        MCMoveBTN = new javax.swing.JButton();
        MCContainerLabel = new javax.swing.JLabel();
        MCMoveToLabel = new javax.swing.JLabel();
        MCBackgroundLabel = new javax.swing.JLabel();
        EmptyContainerWindow = new javax.swing.JFrame();
        jPanel26 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        ECContainerInput = new javax.swing.JTextField();
        ECContainerLabel = new javax.swing.JLabel();
        ECEmptyBTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        RemoveCustomerWindow = new javax.swing.JFrame();
        jPanel27 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        RCUSSearchLabel = new javax.swing.JLabel();
        RCUSSearchInput = new javax.swing.JTextField();
        RCUSSearchBTN = new javax.swing.JButton();
        RCUSNameLabel = new javax.swing.JLabel();
        RCUSAdressLabel = new javax.swing.JLabel();
        RCUSTelephoneLabel = new javax.swing.JLabel();
        RCUSDateAddedLabel = new javax.swing.JLabel();
        RCUSClearBTN = new javax.swing.JButton();
        RCUSRemoveBTN = new javax.swing.JButton();
        RCUSAddressOutput = new javax.swing.JTextArea();
        RCUSNameOutput = new javax.swing.JLabel();
        RCUSTelephoneOutput = new javax.swing.JLabel();
        RCUSDateOutput = new javax.swing.JLabel();
        RCBackgroundLabel = new javax.swing.JLabel();
        InventoryWindow = new javax.swing.JFrame();
        jPanel28 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        INInventoryScroll = new javax.swing.JScrollPane();
        INInventoryTable = new javax.swing.JTable();
        INContainerInput = new javax.swing.JTextField();
        INIDInput = new javax.swing.JTextField();
        INContainerLabel = new javax.swing.JLabel();
        INIDLabel = new javax.swing.JLabel();
        INConLocScroll = new javax.swing.JScrollPane();
        INConLocTable = new javax.swing.JTable();
        INSearchNameIdBTN = new javax.swing.JButton();
        INOpenBTN = new javax.swing.JButton();
        INNameLabel = new javax.swing.JLabel();
        INNameInput = new javax.swing.JTextField();
        INSearchConBTN = new javax.swing.JButton();
        INPrintBTN = new javax.swing.JButton();
        INNameLabelOutput = new javax.swing.JLabel();
        INDateLabelOutput = new javax.swing.JLabel();
        INNumberLabelOutput = new javax.swing.JLabel();
        INIDLabelOutput = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        INAddressLabelOutput = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        INBackgroundLabel = new javax.swing.JLabel();
        SearchInventoryWindow = new javax.swing.JFrame();
        jPanel29 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        SIInventorySearchedScroll = new javax.swing.JScrollPane();
        SIInventorySearchedTable = new javax.swing.JTable();
        SINameInput = new javax.swing.JTextField();
        SIItemInput = new javax.swing.JTextField();
        SINameLabel = new javax.swing.JLabel();
        SIItemLabel = new javax.swing.JLabel();
        SIPrintTableBTN = new javax.swing.JButton();
        SIClearBTN = new javax.swing.JButton();
        SISearchBTN = new javax.swing.JButton();
        SIIDescriptionLabel = new javax.swing.JLabel();
        SIDescriptionInput = new javax.swing.JTextField();
        SIBackgroundLabel = new javax.swing.JLabel();
        EditInventorysWindow = new javax.swing.JFrame();
        jPanel30 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        EIConLocScroll = new javax.swing.JScrollPane();
        EIConLocTable = new javax.swing.JTable();
        EIInventoryScroll = new javax.swing.JScrollPane();
        EIInventoryTable = new javax.swing.JTable();
        EINameInput = new javax.swing.JTextField();
        EIIDInput = new javax.swing.JTextField();
        EISearchBTN = new javax.swing.JButton();
        EIOpenBTN = new javax.swing.JButton();
        EINameLabel = new javax.swing.JLabel();
        EIIDLabel = new javax.swing.JLabel();
        EIClearBTN = new javax.swing.JButton();
        EIRemoveBTN = new javax.swing.JButton();
        EIUndoRelocateBTN = new javax.swing.JButton();
        EIAddNewBTN = new javax.swing.JButton();
        EIOpenedContainerLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        EIItemRemovedScroll = new javax.swing.JScrollPane();
        EIItemRemovedTable = new javax.swing.JTable();
        EIEmptyTableBTN = new javax.swing.JButton();
        EIAddressOutput = new javax.swing.JTextArea();
        EIUpdateBTN = new javax.swing.JButton();
        EINameOutput = new javax.swing.JLabel();
        EINumberOutput = new javax.swing.JLabel();
        EIDateOutput = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ViewLogWindow = new javax.swing.JFrame();
        jPanel31 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        VLLogScroll = new javax.swing.JScrollPane();
        VLLogTable = new javax.swing.JTable();
        VLDateChooser = new datechooser.beans.DateChooserCombo();
        VLSearchBTN = new javax.swing.JButton();
        VLUserLable = new javax.swing.JLabel();
        VLPrintBTN = new javax.swing.JButton();
        VLRadioUser = new javax.swing.JRadioButton();
        VLRadioDate = new javax.swing.JRadioButton();
        VLUserCombo = new javax.swing.JComboBox<>();
        VLActionCombo = new javax.swing.JComboBox<>();
        VLRadioAction = new javax.swing.JRadioButton();
        VLResetBTN = new javax.swing.JButton();
        VLBackgroundLabel = new javax.swing.JLabel();
        PickListWindow = new javax.swing.JFrame();
        jPanel32 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        PLItemScroll = new javax.swing.JScrollPane();
        PLItemTable = new javax.swing.JTable();
        PLPickListScroll = new javax.swing.JScrollPane();
        PLPickListTable = new javax.swing.JTable();
        PLContainerScroll = new javax.swing.JScrollPane();
        PLContainerTable = new javax.swing.JTable();
        PLOpenBTN = new javax.swing.JButton();
        PLIDLable = new javax.swing.JLabel();
        PLIDInput = new javax.swing.JTextField();
        PLSearchBTN = new javax.swing.JButton();
        PLAddItemBTN = new javax.swing.JButton();
        PLContainerInput = new javax.swing.JTextField();
        PLContainerLabel = new javax.swing.JLabel();
        PLAddBTN = new javax.swing.JButton();
        PLCreateBTN = new javax.swing.JButton();
        PLAddAllContainersBTN = new javax.swing.JButton();
        PLNameLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PLClearBTN = new javax.swing.JButton();
        PLRemoveBTN = new javax.swing.JButton();
        PLAddCustomBTN = new javax.swing.JButton();
        PLContainerChosenLabel = new javax.swing.JLabel();
        PLOpenContainerBTN = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        PLBackgroundLabel = new javax.swing.JLabel();
        FinalizePickListWindow = new javax.swing.JFrame();
        jPanel33 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        FLPickListScroll = new javax.swing.JScrollPane();
        FLPickListTable = new javax.swing.JTable();
        FLComboBox = new javax.swing.JComboBox<>();
        FLPickListLable = new javax.swing.JLabel();
        FLFinalizeBTN = new javax.swing.JButton();
        FLNoActionTakenBTN = new javax.swing.JButton();
        FLOpenBTN = new javax.swing.JButton();
        FPBackgroundLabel = new javax.swing.JLabel();
        LogInWindowFirst = new javax.swing.JFrame();
        jPanel34 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        LogInUserInput = new javax.swing.JTextField();
        LogInUserLabel = new javax.swing.JLabel();
        LogInPasswordLabel = new javax.swing.JLabel();
        LogInPasswordInput = new javax.swing.JPasswordField();
        LogInFirstEnterBTN = new javax.swing.JButton();
        LogInPasswordLabel1 = new javax.swing.JLabel();
        LIEnterLabel = new javax.swing.JLabel();
        LogInReEnterPasswordInput = new javax.swing.JPasswordField();
        LIFBackgroundLabel = new javax.swing.JLabel();
        LogInWindowMain = new javax.swing.JFrame();
        jPanel35 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        LogInUserInputMain = new javax.swing.JTextField();
        LogInUserLabelMain = new javax.swing.JLabel();
        LogInPasswordLabelMain = new javax.swing.JLabel();
        LogInPasswordInputMain = new javax.swing.JPasswordField();
        LIEnterMainBTN = new javax.swing.JButton();
        LIFailedLabel = new javax.swing.JLabel();
        LIMBackgroundLabel = new javax.swing.JLabel();
        AddUserWindow = new javax.swing.JFrame();
        jPanel36 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        AUUserNameLabel = new javax.swing.JLabel();
        AUPasswordLabel = new javax.swing.JLabel();
        AUPasswordInput = new javax.swing.JPasswordField();
        AUEnterBTN = new javax.swing.JButton();
        AUreEnterPasswordLabel = new javax.swing.JLabel();
        AUMainLabel = new javax.swing.JLabel();
        AUReEnterPasswordInput = new javax.swing.JPasswordField();
        AUUserNameInput = new javax.swing.JTextField();
        AUClearanceLabel = new javax.swing.JLabel();
        AUComboBox = new javax.swing.JComboBox<>();
        AUBackgroundLabel = new javax.swing.JLabel();
        AnalyticWindow = new javax.swing.JFrame();
        jPanel37 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        AnalyticScroll = new javax.swing.JScrollPane();
        AnalyticTable = new javax.swing.JTable();
        ATNumberOfCustomersLabel = new javax.swing.JLabel();
        ATNumberOfContainersLabel = new javax.swing.JLabel();
        ATNumberOfEmptyContainersLabel = new javax.swing.JLabel();
        ATComboBox = new javax.swing.JComboBox<>();
        ATSearchBTN = new javax.swing.JButton();
        ATNumberOfCustomersOutput = new javax.swing.JLabel();
        ATNumberOfContainersOutput = new javax.swing.JLabel();
        ATNumberOfEmptyContainersOutput = new javax.swing.JLabel();
        ANLBackgroundLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        AddNewCustomerWindowBTN = new javax.swing.JButton();
        EditCustomerWindowBTN = new javax.swing.JButton();
        SearchCustomerWindowBTN = new javax.swing.JButton();
        AddContainerToExistingCustomerBTN = new javax.swing.JButton();
        SearchContainerWindowBTN = new javax.swing.JButton();
        MoveContainerWindowBTN = new javax.swing.JButton();
        EmptyContainerWindowBTN = new javax.swing.JButton();
        RemoveCustomerWindowBTN = new javax.swing.JButton();
        InventoryListBTN = new javax.swing.JButton();
        SearchInventorysBTN = new javax.swing.JButton();
        EditInventorysBTN = new javax.swing.JButton();
        PickListBTN = new javax.swing.JButton();
        FinalizePickListBTN = new javax.swing.JButton();
        MainLable = new javax.swing.JLabel();
        MainCustomerScroll = new javax.swing.JScrollPane();
        MainCustomerTable = new javax.swing.JTable();
        MainConLocScroll = new javax.swing.JScrollPane();
        MainConLocTable = new javax.swing.JTable();
        MainInventoryScroll = new javax.swing.JScrollPane();
        MainInventoryTable = new javax.swing.JTable();
        userNameLabel = new javax.swing.JLabel();
        MainOpenBTN = new javax.swing.JButton();
        LogOutBTN = new javax.swing.JButton();
        MainCustomersLabel = new javax.swing.JLabel();
        MainContainersLabel = new javax.swing.JLabel();
        MainInventoryLabel = new javax.swing.JLabel();
        MainExploreBTN = new javax.swing.JButton();
        MainResetTable = new javax.swing.JButton();
        ADMINPanel = new javax.swing.JPanel();
        MainAddUserBTN = new javax.swing.JButton();
        AnalyticsBTN = new javax.swing.JButton();
        ViewLogWindowBTN = new javax.swing.JButton();
        backgroundlabel = new javax.swing.JLabel();

        AddNewCustomerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddNewCustomerWindow.setTitle("Add New Customer");
        AddNewCustomerWindow.setBackground(new java.awt.Color(102, 102, 102));
        AddNewCustomerWindow.setMinimumSize(new java.awt.Dimension(793, 409));
        AddNewCustomerWindow.setResizable(false);

        jPanel18.setLayout(null);

        ANCPanel.setBackground(new java.awt.Color(51, 51, 51));
        ANCPanel.setMinimumSize(new java.awt.Dimension(793, 410));
        ANCPanel.setOpaque(false);
        ANCPanel.setPreferredSize(new java.awt.Dimension(793, 410));

        ANCNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        ANCNameLabel.setText("Name:");

        ANCAddressLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCAddressLabel.setForeground(new java.awt.Color(255, 255, 255));
        ANCAddressLabel.setText("Address:");

        ANCTelePhoneNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCTelePhoneNumberLabel.setForeground(new java.awt.Color(255, 255, 255));
        ANCTelePhoneNumberLabel.setText("Telephone:");

        ANCContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        ANCContainerLabel.setText("Container:");

        ANCAddConLocBTN.setText("ADD");
        ANCAddConLocBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ANCAddConLocBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ANCAddConLocBTNActionPerformed(evt);
            }
        });

        ANCLocationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCLocationLabel.setForeground(new java.awt.Color(255, 255, 255));
        ANCLocationLabel.setText("Location:");

        ANCConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ANCConLocScroll.setViewportView(ANCConLocTable);
        if (ANCConLocTable.getColumnModel().getColumnCount() > 0) {
            ANCConLocTable.getColumnModel().getColumn(0).setResizable(false);
            ANCConLocTable.getColumnModel().getColumn(1).setResizable(false);
        }

        ANCConfirmBTN.setText("CONFIRM");
        ANCConfirmBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ANCConfirmBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ANCConfirmBTNActionPerformed(evt);
            }
        });

        ANCRemoveBTN.setText("REMOVE");
        ANCRemoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ANCRemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ANCRemoveBTNActionPerformed(evt);
            }
        });

        ANCClearBTN.setText("CLEAR ");
        ANCClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ANCClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ANCClearBTNActionPerformed(evt);
            }
        });

        ANCAddressInput.setColumns(13);
        ANCAddressInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ANCAddressInput.setRows(5);
        ANCAddressInput.setMaximumSize(new java.awt.Dimension(5, 22));

        ANCNameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ANCTelePhoneNumberInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ANCContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ANCLocationInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout ANCPanelLayout = new javax.swing.GroupLayout(ANCPanel);
        ANCPanel.setLayout(ANCPanelLayout);
        ANCPanelLayout.setHorizontalGroup(
            ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ANCPanelLayout.createSequentialGroup()
                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(ANCTelePhoneNumberLabel))
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(ANCNameLabel)
                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(ANCContainerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ANCContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ANCPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ANCClearBTN)
                                            .addComponent(ANCTelePhoneNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(ANCPanelLayout.createSequentialGroup()
                                        .addGap(250, 250, 250)
                                        .addComponent(ANCLocationLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ANCAddConLocBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ANCLocationInput, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(29, 29, 29)
                                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ANCPanelLayout.createSequentialGroup()
                                        .addComponent(ANCRemoveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(ANCConfirmBTN))
                                    .addComponent(ANCConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ANCNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addComponent(ANCAddressLabel)
                                .addGap(9, 9, 9)
                                .addComponent(ANCAddressInput, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(56, 56, 56))
        );

        ANCPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ANCClearBTN, ANCConfirmBTN, ANCRemoveBTN});

        ANCPanelLayout.setVerticalGroup(
            ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ANCPanelLayout.createSequentialGroup()
                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(ANCConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ANCRemoveBTN)
                            .addComponent(ANCConfirmBTN)))
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ANCContainerLabel)
                            .addComponent(ANCContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ANCPanelLayout.createSequentialGroup()
                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ANCPanelLayout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ANCAddressLabel)
                                            .addComponent(ANCAddressInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(ANCPanelLayout.createSequentialGroup()
                                        .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(ANCLocationLabel)
                                            .addComponent(ANCLocationInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ANCAddConLocBTN))))
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addGap(210, 210, 210)
                                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ANCTelePhoneNumberLabel)
                                    .addComponent(ANCTelePhoneNumberInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(ANCPanelLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(ANCPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ANCNameLabel)
                                    .addComponent(ANCNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ANCClearBTN)))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jPanel18.add(ANCPanel);
        ANCPanel.setBounds(0, 0, 793, 410);
        ANCPanel.getAccessibleContext().setAccessibleName("");

        ANCBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel18.add(ANCBackgroundLabel);
        ANCBackgroundLabel.setBounds(0, 0, 800, 410);

        javax.swing.GroupLayout AddNewCustomerWindowLayout = new javax.swing.GroupLayout(AddNewCustomerWindow.getContentPane());
        AddNewCustomerWindow.getContentPane().setLayout(AddNewCustomerWindowLayout);
        AddNewCustomerWindowLayout.setHorizontalGroup(
            AddNewCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
        );
        AddNewCustomerWindowLayout.setVerticalGroup(
            AddNewCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );

        EditCustomerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        EditCustomerWindow.setTitle("Edit Customer Information");
        EditCustomerWindow.setMinimumSize(new java.awt.Dimension(603, 491));
        EditCustomerWindow.setResizable(false);

        jPanel4.setMinimumSize(new java.awt.Dimension(508, 446));
        jPanel4.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setMinimumSize(new java.awt.Dimension(508, 446));
        jPanel3.setOpaque(false);

        ECNameSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECNameSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECNameSearchLabel.setText("Name:");

        ECIDSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECIDSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECIDSearchLabel.setText("ID:");

        ECNameSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ECIDSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ECSearchBTN.setText("SEARCH");
        ECSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ECSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECSearchBTNActionPerformed(evt);
            }
        });

        ECNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECNameLabel.setText("Name:");

        ECAdressLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECAdressLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECAdressLabel.setText("Address:");

        ECTelephoneLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECTelephoneLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECTelephoneLabel.setText("Telephone:");

        ECNameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ECTelephoneInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ECUpdateBTN.setText("UPDATE");
        ECUpdateBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ECUpdateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECUpdateBTNActionPerformed(evt);
            }
        });

        ECDateAddedLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECDateAddedLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECDateAddedLabel.setText("Date Added:");

        ECClearBTN.setText("CLEAR");
        ECClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ECClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECClearBTNActionPerformed(evt);
            }
        });

        ECAddressOutput.setEditable(false);
        ECAddressOutput.setBackground(new java.awt.Color(0, 0, 0));
        ECAddressOutput.setColumns(20);
        ECAddressOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECAddressOutput.setForeground(new java.awt.Color(255, 255, 255));
        ECAddressOutput.setRows(5);
        ECAddressOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, null, java.awt.Color.gray, java.awt.Color.lightGray));

        ECAddressInput.setColumns(20);
        ECAddressInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECAddressInput.setRows(5);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("New Details:");

        ECNameOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECNameOutput.setForeground(new java.awt.Color(255, 255, 255));
        ECNameOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ECTelephoneOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECTelephoneOutput.setForeground(new java.awt.Color(255, 255, 255));
        ECTelephoneOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ECDateOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECDateOutput.setForeground(new java.awt.Color(255, 255, 255));
        ECDateOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ECNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ECNameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(ECNameSearchLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ECNameSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(ECIDSearchLabel)
                                        .addGap(10, 10, 10)
                                        .addComponent(ECIDSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(ECSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ECAdressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ECAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10)
                            .addComponent(ECAddressInput, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(ECNameInput)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ECDateAddedLabel)
                            .addComponent(ECTelephoneLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ECTelephoneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ECDateOutput))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ECClearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ECUpdateBTN))
                            .addComponent(ECTelephoneInput, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ECDateOutput, ECNameOutput, ECTelephoneOutput});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ECClearBTN, ECSearchBTN, ECUpdateBTN});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECNameSearchLabel)
                    .addComponent(ECNameSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ECIDSearchLabel)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ECIDSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ECSearchBTN)))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECNameLabel)
                    .addComponent(ECNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ECNameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ECAdressLabel)
                    .addComponent(ECAddressInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECTelephoneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ECTelephoneLabel)
                    .addComponent(ECTelephoneInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECDateAddedLabel)
                    .addComponent(ECDateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ECClearBTN)
                    .addComponent(ECUpdateBTN))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3);
        jPanel3.setBounds(0, 0, 600, 500);

        ECBackgroudLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel4.add(ECBackgroudLabel);
        ECBackgroudLabel.setBounds(0, -30, 600, 520);

        javax.swing.GroupLayout EditCustomerWindowLayout = new javax.swing.GroupLayout(EditCustomerWindow.getContentPane());
        EditCustomerWindow.getContentPane().setLayout(EditCustomerWindowLayout);
        EditCustomerWindowLayout.setHorizontalGroup(
            EditCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );
        EditCustomerWindowLayout.setVerticalGroup(
            EditCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        SearchCustomerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SearchCustomerWindow.setTitle("Search Customer");
        SearchCustomerWindow.setMinimumSize(new java.awt.Dimension(742, 573));
        SearchCustomerWindow.setResizable(false);

        jPanel20.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setMinimumSize(new java.awt.Dimension(742, 573));
        jPanel5.setOpaque(false);

        SCNameSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCNameSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCNameSearchLabel.setText("Name:");

        SCIDSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCIDSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCIDSearchLabel.setText("ID:");

        SCIDOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCIDOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCIDOutputLabel.setText("ID:");

        SCNameOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCNameOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCNameOutputLabel.setText("Name:");

        SCAddressOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCAddressOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCAddressOutputLabel.setText("Address:");

        SCTelephoneOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCTelephoneOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCTelephoneOutputLabel.setText("Telephone:");

        SCNameSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        SCConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SCConLocScroll.setViewportView(SCConLocTable);
        if (SCConLocTable.getColumnModel().getColumnCount() > 0) {
            SCConLocTable.getColumnModel().getColumn(0).setResizable(false);
            SCConLocTable.getColumnModel().getColumn(1).setResizable(false);
        }

        SCSearchBTN.setText("SEARCH");
        SCSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SCSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCSearchBTNActionPerformed(evt);
            }
        });

        SCClearBTN.setText("CLEAR");
        SCClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SCClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCClearBTNActionPerformed(evt);
            }
        });

        SCIDSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        SCAddressOutput.setEditable(false);
        SCAddressOutput.setBackground(new java.awt.Color(0, 0, 0));
        SCAddressOutput.setColumns(13);
        SCAddressOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCAddressOutput.setForeground(new java.awt.Color(255, 255, 255));
        SCAddressOutput.setRows(5);
        SCAddressOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        SCAddressOutput.setFocusable(false);
        jScrollPane15.setViewportView(SCAddressOutput);

        SCContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        SCContainerSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCContainerSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCContainerSearchLabel.setText("Search By Container:");

        SCSearchContainerBTN.setText("SEARCH");
        SCSearchContainerBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SCSearchContainerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCSearchContainerBTNActionPerformed(evt);
            }
        });

        SCIDOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCIDOutput.setForeground(new java.awt.Color(255, 255, 255));
        SCIDOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        SCNameOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCNameOutput.setForeground(new java.awt.Color(255, 255, 255));
        SCNameOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        SCTelephoneOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCTelephoneOutput.setForeground(new java.awt.Color(255, 255, 255));
        SCTelephoneOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(SCTelephoneOutputLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SCTelephoneOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(SCIDOutputLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SCIDOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(SCAddressOutputLabel)
                                .addComponent(SCNameOutputLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                .addComponent(SCNameOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(SCNameSearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SCNameSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(SCIDSearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(SCClearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SCSearchBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(SCIDSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(101, 101, 101)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SCConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(SCContainerSearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SCSearchContainerBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SCContainerInput, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))))
                .addGap(90, 90, 90))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SCIDSearchInput, SCNameSearchInput});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SCIDOutput, SCNameOutput, SCTelephoneOutput});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SCClearBTN, SCSearchBTN});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SCContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SCContainerSearchLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SCSearchContainerBTN)
                        .addGap(26, 26, 26)
                        .addComponent(SCConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SCNameSearchLabel)
                            .addComponent(SCNameSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SCIDSearchLabel)
                            .addComponent(SCIDSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SCSearchBTN)
                            .addComponent(SCClearBTN))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCIDOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SCIDOutputLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCNameOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addComponent(SCNameOutputLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCAddressOutputLabel)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SCTelephoneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SCTelephoneOutputLabel))))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SCClearBTN, SCSearchBTN});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SCIDOutput, SCNameOutput, SCTelephoneOutput});

        jPanel20.add(jPanel5);
        jPanel5.setBounds(2, 3, 740, 570);

        SCBackgroudLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel20.add(SCBackgroudLabel);
        SCBackgroudLabel.setBounds(0, 0, 740, 570);

        javax.swing.GroupLayout SearchCustomerWindowLayout = new javax.swing.GroupLayout(SearchCustomerWindow.getContentPane());
        SearchCustomerWindow.getContentPane().setLayout(SearchCustomerWindowLayout);
        SearchCustomerWindowLayout.setHorizontalGroup(
            SearchCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        SearchCustomerWindowLayout.setVerticalGroup(
            SearchCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );

        AddContainerToExistingCustomer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddContainerToExistingCustomer.setTitle("Add Container");
        AddContainerToExistingCustomer.setMinimumSize(new java.awt.Dimension(633, 371));
        AddContainerToExistingCustomer.setResizable(false);

        jPanel23.setLayout(null);

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setOpaque(false);

        ACNameSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ACConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ACConLocScroll.setViewportView(ACConLocTable);
        if (ACConLocTable.getColumnModel().getColumnCount() > 0) {
            ACConLocTable.getColumnModel().getColumn(0).setResizable(false);
            ACConLocTable.getColumnModel().getColumn(1).setResizable(false);
        }

        ACContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ACLocationInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ACSearchBTN.setText("SEARCH");
        ACSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ACSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACSearchBTNActionPerformed(evt);
            }
        });

        ACAddBTN.setText("ADD");
        ACAddBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ACAddBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACAddBTNActionPerformed(evt);
            }
        });

        ACUpdateBTN.setText("UPDATE");
        ACUpdateBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ACUpdateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACUpdateBTNActionPerformed(evt);
            }
        });

        ACNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACNameLabel.setText("Name:");

        ACIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACIDLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACIDLabel.setText("ID:");

        ACNameOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACNameOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACNameOutputLabel.setText("Name:");

        ACIDOutputLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACIDOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACIDOutputLabel.setText("ID:");

        ACCustomerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACCustomerLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACCustomerLabel.setText("Customer:");

        ACContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACContainerLabel.setText("Container:");

        ACLocationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACLocationLabel.setForeground(new java.awt.Color(255, 255, 255));
        ACLocationLabel.setText("Location:");

        ACIDSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ACRemoveBTN.setText("REMOVE");
        ACRemoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ACRemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACRemoveBTNActionPerformed(evt);
            }
        });

        ACNameOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACNameOutput.setForeground(new java.awt.Color(255, 255, 255));
        ACNameOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ACIDOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ACIDOutput.setForeground(new java.awt.Color(255, 255, 255));
        ACIDOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ACNameOutputLabel)
                            .addComponent(ACIDOutputLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ACNameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ACIDOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ACNameLabel)
                            .addComponent(ACIDLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ACIDSearchInput, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(ACNameSearchInput, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(ACSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ACContainerLabel)
                                    .addComponent(ACLocationLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ACAddBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ACContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ACLocationInput, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(ACCustomerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(ACRemoveBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ACUpdateBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ACConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ACRemoveBTN, ACUpdateBTN});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(ACConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ACRemoveBTN)
                            .addComponent(ACUpdateBTN)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ACNameSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ACContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ACNameLabel)
                            .addComponent(ACContainerLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ACLocationInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ACIDLabel)
                            .addComponent(ACLocationLabel)
                            .addComponent(ACIDSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ACSearchBTN)
                            .addComponent(ACAddBTN))
                        .addGap(26, 26, 26)
                        .addComponent(ACCustomerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ACNameOutputLabel)
                            .addComponent(ACNameOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ACIDOutputLabel)
                            .addComponent(ACIDOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ACIDOutput, ACNameOutput});

        jPanel23.add(jPanel6);
        jPanel6.setBounds(3, 2, 640, 380);

        ACBackgroudLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel23.add(ACBackgroudLabel);
        ACBackgroudLabel.setBounds(0, 0, 640, 380);

        javax.swing.GroupLayout AddContainerToExistingCustomerLayout = new javax.swing.GroupLayout(AddContainerToExistingCustomer.getContentPane());
        AddContainerToExistingCustomer.getContentPane().setLayout(AddContainerToExistingCustomerLayout);
        AddContainerToExistingCustomerLayout.setHorizontalGroup(
            AddContainerToExistingCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        AddContainerToExistingCustomerLayout.setVerticalGroup(
            AddContainerToExistingCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        SearchContainerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SearchContainerWindow.setTitle("Search Container");
        SearchContainerWindow.setMinimumSize(new java.awt.Dimension(400, 300));
        SearchContainerWindow.setResizable(false);

        jPanel24.setLayout(null);

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setOpaque(false);

        SCONContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCONContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCONContainerLabel.setText("Container:");

        SCONLocationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCONLocationLabel.setForeground(new java.awt.Color(255, 255, 255));
        SCONLocationLabel.setText("Location:");

        SCONContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCONContainerInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        SCONSearchBTN.setText("SEARCH");
        SCONSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SCONSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCONSearchBTNActionPerformed(evt);
            }
        });

        SCONLocationOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SCONLocationOutput.setForeground(new java.awt.Color(255, 255, 255));
        SCONLocationOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SCONLocationOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SCONLocationLabel)
                    .addComponent(SCONContainerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SCONContainerInput)
                    .addComponent(SCONLocationOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SCONSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SCONContainerLabel)
                    .addComponent(SCONContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SCONSearchBTN))
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SCONLocationLabel)
                    .addComponent(SCONLocationOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel8);
        jPanel8.setBounds(-2, 0, 410, 300);

        SCBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel24.add(SCBackgroundLabel);
        SCBackgroundLabel.setBounds(0, 0, 410, 300);

        javax.swing.GroupLayout SearchContainerWindowLayout = new javax.swing.GroupLayout(SearchContainerWindow.getContentPane());
        SearchContainerWindow.getContentPane().setLayout(SearchContainerWindowLayout);
        SearchContainerWindowLayout.setHorizontalGroup(
            SearchContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );
        SearchContainerWindowLayout.setVerticalGroup(
            SearchContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        MoveContainerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        MoveContainerWindow.setTitle("Move Container");
        MoveContainerWindow.setMinimumSize(new java.awt.Dimension(400, 300));
        MoveContainerWindow.setResizable(false);

        jPanel25.setLayout(null);

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));
        jPanel9.setMinimumSize(new java.awt.Dimension(400, 300));
        jPanel9.setOpaque(false);

        MCContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MCContainerInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        MCMoveToInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MCMoveToInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        MCMoveBTN.setText("MOVE");
        MCMoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MCMoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MCMoveBTNActionPerformed(evt);
            }
        });

        MCContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MCContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        MCContainerLabel.setText("Container:");

        MCMoveToLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MCMoveToLabel.setForeground(new java.awt.Color(255, 255, 255));
        MCMoveToLabel.setText("Move To:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(MCMoveToLabel)
                    .addComponent(MCContainerLabel))
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MCMoveBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MCMoveToInput)
                        .addComponent(MCContainerInput, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MCContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MCContainerLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MCMoveToInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MCMoveToLabel))
                .addGap(18, 18, 18)
                .addComponent(MCMoveBTN)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel9);
        jPanel9.setBounds(0, 1, 420, 290);

        MCBackgroundLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\hutch\\OneDrive\\Desktop\\background3.jpg")); // NOI18N
        jPanel25.add(MCBackgroundLabel);
        MCBackgroundLabel.setBounds(0, 0, 420, 290);

        javax.swing.GroupLayout MoveContainerWindowLayout = new javax.swing.GroupLayout(MoveContainerWindow.getContentPane());
        MoveContainerWindow.getContentPane().setLayout(MoveContainerWindowLayout);
        MoveContainerWindowLayout.setHorizontalGroup(
            MoveContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );
        MoveContainerWindowLayout.setVerticalGroup(
            MoveContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );

        EmptyContainerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        EmptyContainerWindow.setTitle("Remove Container");
        EmptyContainerWindow.setMinimumSize(new java.awt.Dimension(379, 258));

        jPanel26.setLayout(null);

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));
        jPanel10.setOpaque(false);

        ECContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECContainerInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ECContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ECContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        ECContainerLabel.setText("Container: ");

        ECEmptyBTN.setText("EMPTY");
        ECEmptyBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ECEmptyBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ECEmptyBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(ECContainerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ECContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ECEmptyBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ECContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ECContainerLabel)
                    .addComponent(ECEmptyBTN))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jPanel26.add(jPanel10);
        jPanel10.setBounds(-1, -1, 380, 260);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel26.add(jLabel2);
        jLabel2.setBounds(0, -6, 380, 270);

        javax.swing.GroupLayout EmptyContainerWindowLayout = new javax.swing.GroupLayout(EmptyContainerWindow.getContentPane());
        EmptyContainerWindow.getContentPane().setLayout(EmptyContainerWindowLayout);
        EmptyContainerWindowLayout.setHorizontalGroup(
            EmptyContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );
        EmptyContainerWindowLayout.setVerticalGroup(
            EmptyContainerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        );

        RemoveCustomerWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        RemoveCustomerWindow.setTitle("Remove Customer");
        RemoveCustomerWindow.setMinimumSize(new java.awt.Dimension(548, 522));
        RemoveCustomerWindow.setResizable(false);

        jPanel27.setLayout(null);

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));
        jPanel11.setMinimumSize(new java.awt.Dimension(508, 446));
        jPanel11.setOpaque(false);

        RCUSSearchLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSSearchLabel.setForeground(new java.awt.Color(255, 255, 255));
        RCUSSearchLabel.setText("ID:");

        RCUSSearchInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        RCUSSearchBTN.setText("SEARCH");
        RCUSSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RCUSSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCUSSearchBTNActionPerformed(evt);
            }
        });

        RCUSNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        RCUSNameLabel.setText("Name:");

        RCUSAdressLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSAdressLabel.setForeground(new java.awt.Color(255, 255, 255));
        RCUSAdressLabel.setText("Address:");

        RCUSTelephoneLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSTelephoneLabel.setForeground(new java.awt.Color(255, 255, 255));
        RCUSTelephoneLabel.setText("Telephone:");

        RCUSDateAddedLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSDateAddedLabel.setForeground(new java.awt.Color(255, 255, 255));
        RCUSDateAddedLabel.setText("Date Added:");

        RCUSClearBTN.setText("CLEAR");
        RCUSClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RCUSClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCUSClearBTNActionPerformed(evt);
            }
        });

        RCUSRemoveBTN.setText("REMOVE");
        RCUSRemoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RCUSRemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCUSRemoveBTNActionPerformed(evt);
            }
        });

        RCUSAddressOutput.setEditable(false);
        RCUSAddressOutput.setBackground(new java.awt.Color(0, 0, 0));
        RCUSAddressOutput.setColumns(20);
        RCUSAddressOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSAddressOutput.setForeground(new java.awt.Color(255, 255, 255));
        RCUSAddressOutput.setRows(5);
        RCUSAddressOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.gray));
        RCUSAddressOutput.setFocusable(false);

        RCUSNameOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSNameOutput.setForeground(new java.awt.Color(255, 255, 255));
        RCUSNameOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        RCUSTelephoneOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSTelephoneOutput.setForeground(new java.awt.Color(255, 255, 255));
        RCUSTelephoneOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        RCUSDateOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RCUSDateOutput.setForeground(new java.awt.Color(255, 255, 255));
        RCUSDateOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGap(170, 170, 170)
                            .addComponent(RCUSSearchLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(RCUSSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(RCUSSearchBTN))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGap(107, 107, 107)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(RCUSDateAddedLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(RCUSDateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(RCUSAdressLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel11Layout.createSequentialGroup()
                                            .addComponent(RCUSClearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(RCUSRemoveBTN))
                                        .addComponent(RCUSAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(RCUSNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RCUSNameOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(RCUSTelephoneLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RCUSTelephoneOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RCUSDateOutput, RCUSNameOutput, RCUSTelephoneOutput});

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RCUSClearBTN, RCUSRemoveBTN, RCUSSearchBTN});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCUSSearchLabel)
                    .addComponent(RCUSSearchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCUSSearchBTN))
                .addGap(40, 40, 40)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RCUSNameLabel)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RCUSNameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCUSTelephoneOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCUSTelephoneLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RCUSDateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCUSDateAddedLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCUSAdressLabel)
                    .addComponent(RCUSAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCUSRemoveBTN)
                    .addComponent(RCUSClearBTN))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {RCUSDateOutput, RCUSNameOutput, RCUSTelephoneOutput});

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {RCUSClearBTN, RCUSRemoveBTN, RCUSSearchBTN});

        jPanel27.add(jPanel11);
        jPanel11.setBounds(0, 0, 550, 520);

        RCBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel27.add(RCBackgroundLabel);
        RCBackgroundLabel.setBounds(-6, 0, 560, 520);

        javax.swing.GroupLayout RemoveCustomerWindowLayout = new javax.swing.GroupLayout(RemoveCustomerWindow.getContentPane());
        RemoveCustomerWindow.getContentPane().setLayout(RemoveCustomerWindowLayout);
        RemoveCustomerWindowLayout.setHorizontalGroup(
            RemoveCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        RemoveCustomerWindowLayout.setVerticalGroup(
            RemoveCustomerWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );

        InventoryWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        InventoryWindow.setTitle("Inventory");
        InventoryWindow.setMinimumSize(new java.awt.Dimension(1103, 627));
        InventoryWindow.setResizable(false);

        jPanel28.setLayout(null);

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setOpaque(false);

        INInventoryTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INInventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Items", "Item", "Description", "Damage"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        INInventoryScroll.setViewportView(INInventoryTable);
        if (INInventoryTable.getColumnModel().getColumnCount() > 0) {
            INInventoryTable.getColumnModel().getColumn(0).setResizable(false);
            INInventoryTable.getColumnModel().getColumn(1).setResizable(false);
            INInventoryTable.getColumnModel().getColumn(2).setResizable(false);
            INInventoryTable.getColumnModel().getColumn(3).setResizable(false);
        }

        INContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        INIDInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        INContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        INContainerLabel.setText("Container:");

        INIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INIDLabel.setForeground(new java.awt.Color(255, 255, 255));
        INIDLabel.setText("ID:");

        INConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Short.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        INConLocScroll.setViewportView(INConLocTable);

        INSearchNameIdBTN.setText("SEARCH");
        INSearchNameIdBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        INSearchNameIdBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INSearchNameIdBTNActionPerformed(evt);
            }
        });

        INOpenBTN.setText("OPEN");
        INOpenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        INOpenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INOpenBTNActionPerformed(evt);
            }
        });

        INNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        INNameLabel.setText("Name:");

        INNameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        INSearchConBTN.setText("SEARCH");
        INSearchConBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        INSearchConBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INSearchConBTNActionPerformed(evt);
            }
        });

        INPrintBTN.setText("PRINT");
        INPrintBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        INPrintBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INPrintBTNActionPerformed(evt);
            }
        });

        INNameLabelOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INNameLabelOutput.setForeground(new java.awt.Color(255, 255, 255));
        INNameLabelOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        INDateLabelOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INDateLabelOutput.setForeground(new java.awt.Color(255, 255, 255));
        INDateLabelOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        INNumberLabelOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INNumberLabelOutput.setForeground(new java.awt.Color(255, 255, 255));
        INNumberLabelOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        INIDLabelOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INIDLabelOutput.setForeground(new java.awt.Color(255, 255, 255));
        INIDLabelOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        INAddressLabelOutput.setEditable(false);
        INAddressLabelOutput.setBackground(new java.awt.Color(0, 0, 0));
        INAddressLabelOutput.setColumns(10);
        INAddressLabelOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        INAddressLabelOutput.setForeground(new java.awt.Color(255, 255, 255));
        INAddressLabelOutput.setRows(5);
        INAddressLabelOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane4.setViewportView(INAddressLabelOutput);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("ID:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Name:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Address:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Telephone:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Date:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INIDLabelOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(INNumberLabelOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(INDateLabelOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(INNameLabelOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(42, 42, 42))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(INIDLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(INIDInput, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(INNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(INNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(INContainerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(INContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INSearchConBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(INSearchNameIdBTN))
                        .addGap(36, 36, 36)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(INOpenBTN)
                    .addComponent(INConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(INInventoryScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(INPrintBTN, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(87, 87, 87))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {INOpenBTN, INPrintBTN, INSearchConBTN, INSearchNameIdBTN});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(INContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(INContainerLabel)
                            .addComponent(INSearchConBTN))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(INNameLabel)
                            .addComponent(INNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(INIDLabel)
                            .addComponent(INIDInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(INSearchNameIdBTN))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INIDLabelOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INNameLabelOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INNumberLabelOutput)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(INDateLabelOutput)
                            .addComponent(jLabel17)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(INInventoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(INConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(INPrintBTN)
                            .addComponent(INOpenBTN))))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {INDateLabelOutput, INIDLabelOutput, INNameLabelOutput, INNumberLabelOutput});

        jPanel28.add(jPanel7);
        jPanel7.setBounds(0, 0, 1100, 620);

        INBackgroundLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\hutch\\OneDrive\\Desktop\\background2.jpg")); // NOI18N
        jPanel28.add(INBackgroundLabel);
        INBackgroundLabel.setBounds(-26, 0, 1130, 630);

        javax.swing.GroupLayout InventoryWindowLayout = new javax.swing.GroupLayout(InventoryWindow.getContentPane());
        InventoryWindow.getContentPane().setLayout(InventoryWindowLayout);
        InventoryWindowLayout.setHorizontalGroup(
            InventoryWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE)
        );
        InventoryWindowLayout.setVerticalGroup(
            InventoryWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );

        SearchInventoryWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SearchInventoryWindow.setTitle("Search Inventory");
        SearchInventoryWindow.setMinimumSize(new java.awt.Dimension(1060, 608));
        SearchInventoryWindow.setResizable(false);

        jPanel29.setLayout(null);

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));
        jPanel12.setOpaque(false);

        SIInventorySearchedTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SIInventorySearchedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Item", "Description", "Container", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SIInventorySearchedScroll.setViewportView(SIInventorySearchedTable);
        if (SIInventorySearchedTable.getColumnModel().getColumnCount() > 0) {
            SIInventorySearchedTable.getColumnModel().getColumn(0).setResizable(false);
            SIInventorySearchedTable.getColumnModel().getColumn(1).setResizable(false);
            SIInventorySearchedTable.getColumnModel().getColumn(2).setResizable(false);
            SIInventorySearchedTable.getColumnModel().getColumn(3).setResizable(false);
            SIInventorySearchedTable.getColumnModel().getColumn(4).setResizable(false);
            SIInventorySearchedTable.getColumnModel().getColumn(5).setResizable(false);
        }

        SINameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        SIItemInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        SINameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SINameLabel.setForeground(new java.awt.Color(255, 255, 255));
        SINameLabel.setText("Name:");

        SIItemLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SIItemLabel.setForeground(new java.awt.Color(255, 255, 255));
        SIItemLabel.setText("Item:");

        SIPrintTableBTN.setText("PRINT");
        SIPrintTableBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SIPrintTableBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SIPrintTableBTNActionPerformed(evt);
            }
        });

        SIClearBTN.setText("CLEAR");
        SIClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SIClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SIClearBTNActionPerformed(evt);
            }
        });

        SISearchBTN.setText("SEARCH");
        SISearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SISearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SISearchBTNActionPerformed(evt);
            }
        });

        SIIDescriptionLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SIIDescriptionLabel.setForeground(new java.awt.Color(255, 255, 255));
        SIIDescriptionLabel.setText("Description:");

        SIDescriptionInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SIPrintTableBTN))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SIItemLabel)
                            .addComponent(SINameLabel)
                            .addComponent(SIIDescriptionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(SIClearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SISearchBTN))
                            .addComponent(SIDescriptionInput, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SINameInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SIItemInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(SIInventorySearchedScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {SIClearBTN, SIPrintTableBTN, SISearchBTN});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SIInventorySearchedScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SINameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SINameLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SIItemInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SIItemLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SIIDescriptionLabel)
                            .addComponent(SIDescriptionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SIClearBTN)
                            .addComponent(SISearchBTN))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SIPrintTableBTN)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SIClearBTN, SIPrintTableBTN, SISearchBTN});

        jPanel29.add(jPanel12);
        jPanel12.setBounds(0, 0, 1060, 610);

        SIBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background2.jpg"))); // NOI18N
        jPanel29.add(SIBackgroundLabel);
        SIBackgroundLabel.setBounds(0, -6, 1060, 620);

        javax.swing.GroupLayout SearchInventoryWindowLayout = new javax.swing.GroupLayout(SearchInventoryWindow.getContentPane());
        SearchInventoryWindow.getContentPane().setLayout(SearchInventoryWindowLayout);
        SearchInventoryWindowLayout.setHorizontalGroup(
            SearchInventoryWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
        );
        SearchInventoryWindowLayout.setVerticalGroup(
            SearchInventoryWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );

        EditInventorysWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        EditInventorysWindow.setTitle("Edit Inventory's");
        EditInventorysWindow.setMinimumSize(new java.awt.Dimension(1071, 727));
        EditInventorysWindow.setResizable(false);

        jPanel30.setLayout(null);

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));
        jPanel13.setOpaque(false);

        EIConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EIConLocScroll.setViewportView(EIConLocTable);
        if (EIConLocTable.getColumnModel().getColumnCount() > 0) {
            EIConLocTable.getColumnModel().getColumn(0).setResizable(false);
            EIConLocTable.getColumnModel().getColumn(1).setResizable(false);
        }

        EIInventoryTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIInventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Item", "Item", "Description", "Damage"
            }
        ));
        EIInventoryScroll.setViewportView(EIInventoryTable);

        EINameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        EIIDInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        EISearchBTN.setText("SEARCH");
        EISearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EISearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EISearchBTNActionPerformed(evt);
            }
        });

        EIOpenBTN.setText("OPEN");
        EIOpenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIOpenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIOpenBTNActionPerformed(evt);
            }
        });

        EINameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EINameLabel.setForeground(new java.awt.Color(255, 255, 255));
        EINameLabel.setText("Name:");

        EIIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIIDLabel.setForeground(new java.awt.Color(255, 255, 255));
        EIIDLabel.setText("ID:");

        EIClearBTN.setText("CLEAR");
        EIClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIClearBTNActionPerformed(evt);
            }
        });

        EIRemoveBTN.setText("REMOVE");
        EIRemoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIRemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIRemoveBTNActionPerformed(evt);
            }
        });

        EIUndoRelocateBTN.setText("UNDO \\ RELOCATE");
        EIUndoRelocateBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIUndoRelocateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIUndoRelocateBTNActionPerformed(evt);
            }
        });

        EIAddNewBTN.setText("ADD NEW");
        EIAddNewBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIAddNewBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIAddNewBTNActionPerformed(evt);
            }
        });

        EIOpenedContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIOpenedContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        EIOpenedContainerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EIOpenedContainerLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Container: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Address:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Telephone:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Date:");

        EIItemRemovedTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIItemRemovedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Items", "Item", "Container"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EIItemRemovedScroll.setViewportView(EIItemRemovedTable);
        if (EIItemRemovedTable.getColumnModel().getColumnCount() > 0) {
            EIItemRemovedTable.getColumnModel().getColumn(0).setMinWidth(50);
            EIItemRemovedTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        EIEmptyTableBTN.setText("EMPTY");
        EIEmptyTableBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIEmptyTableBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIEmptyTableBTNActionPerformed(evt);
            }
        });

        EIAddressOutput.setEditable(false);
        EIAddressOutput.setBackground(new java.awt.Color(0, 0, 0));
        EIAddressOutput.setColumns(20);
        EIAddressOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIAddressOutput.setForeground(new java.awt.Color(255, 255, 255));
        EIAddressOutput.setRows(5);
        EIAddressOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray));

        EIUpdateBTN.setText("UPDATE");
        EIUpdateBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EIUpdateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EIUpdateBTNActionPerformed(evt);
            }
        });

        EINameOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EINameOutput.setForeground(new java.awt.Color(255, 255, 255));
        EINameOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        EINumberOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EINumberOutput.setForeground(new java.awt.Color(255, 255, 255));
        EINumberOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        EIDateOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EIDateOutput.setForeground(new java.awt.Color(255, 255, 255));
        EIDateOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 114, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EIAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EINumberOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EIDateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(EIEmptyTableBTN)
                        .addComponent(EIItemRemovedScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EIIDLabel)
                            .addComponent(EINameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(EIIDInput)
                            .addComponent(EINameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(EIClearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EISearchBTN))
                            .addComponent(EINameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EIConLocScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EIOpenBTN, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(EIInventoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(EIRemoveBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EIUndoRelocateBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EIAddNewBTN))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EIOpenedContainerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EIUpdateBTN)))
                .addGap(60, 60, 60))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {EIDateOutput, EINameOutput, EINumberOutput});

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {EIAddNewBTN, EIClearBTN, EIEmptyTableBTN, EIOpenBTN, EIRemoveBTN, EISearchBTN, EIUpdateBTN});

        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(EIOpenedContainerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EIInventoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EIConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EIOpenBTN)
                            .addComponent(EIRemoveBTN)
                            .addComponent(EIUndoRelocateBTN)
                            .addComponent(EIUpdateBTN)
                            .addComponent(EIAddNewBTN)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EINameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EINameLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EIIDInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EIIDLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EISearchBTN)
                            .addComponent(EIClearBTN))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(EINameOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(EIAddressOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(EINumberOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addComponent(EIDateOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(EIItemRemovedScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(EIEmptyTableBTN)))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EIDateOutput, EINameOutput, EINumberOutput});

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EIAddNewBTN, EIClearBTN, EIEmptyTableBTN, EIOpenBTN, EIRemoveBTN, EISearchBTN, EIUpdateBTN});

        jPanel30.add(jPanel13);
        jPanel13.setBounds(0, 0, 1070, 730);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background2.jpg"))); // NOI18N
        jPanel30.add(jLabel3);
        jLabel3.setBounds(0, 0, 1070, 730);

        javax.swing.GroupLayout EditInventorysWindowLayout = new javax.swing.GroupLayout(EditInventorysWindow.getContentPane());
        EditInventorysWindow.getContentPane().setLayout(EditInventorysWindowLayout);
        EditInventorysWindowLayout.setHorizontalGroup(
            EditInventorysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
        );
        EditInventorysWindowLayout.setVerticalGroup(
            EditInventorysWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
        );

        ViewLogWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ViewLogWindow.setTitle("Log");
        ViewLogWindow.setMinimumSize(new java.awt.Dimension(1080, 678));
        ViewLogWindow.setResizable(false);

        jPanel31.setLayout(null);

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));
        jPanel14.setOpaque(false);

        VLLogTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        VLLogTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Action", "Action Taken", "User", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VLLogScroll.setViewportView(VLLogTable);
        if (VLLogTable.getColumnModel().getColumnCount() > 0) {
            VLLogTable.getColumnModel().getColumn(0).setResizable(false);
            VLLogTable.getColumnModel().getColumn(1).setMinWidth(500);
        }

        VLSearchBTN.setText("SEARCH");
        VLSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VLSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VLSearchBTNActionPerformed(evt);
            }
        });

        VLUserLable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        VLUserLable.setForeground(new java.awt.Color(255, 255, 255));
        VLUserLable.setText("User:");

        VLPrintBTN.setText("PRINT");
        VLPrintBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VLPrintBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VLPrintBTNActionPerformed(evt);
            }
        });

        VLResetBTN.setText("RESET");
        VLResetBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VLResetBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VLResetBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(VLLogScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(VLRadioAction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(VLActionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(VLRadioDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(VLDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(VLRadioUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(VLUserLable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VLUserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(VLSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(VLResetBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VLPrintBTN)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {VLPrintBTN, VLSearchBTN});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VLLogScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VLRadioUser)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(VLSearchBTN)
                        .addComponent(VLUserLable)
                        .addComponent(VLPrintBTN)
                        .addComponent(VLUserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(VLResetBTN))
                    .addComponent(VLRadioDate)
                    .addComponent(VLDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VLActionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VLRadioAction))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanel31.add(jPanel14);
        jPanel14.setBounds(0, -2, 1080, 680);

        VLBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background2.jpg"))); // NOI18N
        jPanel31.add(VLBackgroundLabel);
        VLBackgroundLabel.setBounds(0, -6, 1080, 690);

        javax.swing.GroupLayout ViewLogWindowLayout = new javax.swing.GroupLayout(ViewLogWindow.getContentPane());
        ViewLogWindow.getContentPane().setLayout(ViewLogWindowLayout);
        ViewLogWindowLayout.setHorizontalGroup(
            ViewLogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
        );
        ViewLogWindowLayout.setVerticalGroup(
            ViewLogWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );

        PickListWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PickListWindow.setTitle("Create Pick List");
        PickListWindow.setMinimumSize(new java.awt.Dimension(1331, 708));
        PickListWindow.setResizable(false);

        jPanel32.setLayout(null);

        jPanel15.setBackground(new java.awt.Color(51, 51, 51));
        jPanel15.setOpaque(false);

        PLItemTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Items", "Item", "Description", "Damage"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PLItemScroll.setViewportView(PLItemTable);

        PLPickListTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLPickListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Container", "Location", "#Items", "Item", "Notes - Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PLPickListScroll.setViewportView(PLPickListTable);
        if (PLPickListTable.getColumnModel().getColumnCount() > 0) {
            PLPickListTable.getColumnModel().getColumn(1).setMinWidth(75);
            PLPickListTable.getColumnModel().getColumn(1).setMaxWidth(75);
            PLPickListTable.getColumnModel().getColumn(2).setMinWidth(75);
            PLPickListTable.getColumnModel().getColumn(2).setMaxWidth(75);
            PLPickListTable.getColumnModel().getColumn(3).setMinWidth(50);
            PLPickListTable.getColumnModel().getColumn(3).setMaxWidth(50);
        }

        PLContainerTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLContainerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PLContainerScroll.setViewportView(PLContainerTable);

        PLOpenBTN.setText("OPEN");
        PLOpenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLOpenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLOpenBTNActionPerformed(evt);
            }
        });

        PLIDLable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLIDLable.setForeground(new java.awt.Color(255, 255, 255));
        PLIDLable.setText("ID:");

        PLIDInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        PLSearchBTN.setText("SEARCH");
        PLSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLSearchBTNActionPerformed(evt);
            }
        });

        PLAddItemBTN.setText("ADD ITEM");
        PLAddItemBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLAddItemBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLAddItemBTNActionPerformed(evt);
            }
        });

        PLContainerInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        PLContainerLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLContainerLabel.setForeground(new java.awt.Color(255, 255, 255));
        PLContainerLabel.setText("Container:");

        PLAddBTN.setText("ADD");
        PLAddBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLAddBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLAddBTNActionPerformed(evt);
            }
        });

        PLCreateBTN.setText("CREATE");
        PLCreateBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLCreateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLCreateBTNActionPerformed(evt);
            }
        });

        PLAddAllContainersBTN.setText("ADD ALL CONTAINERS");
        PLAddAllContainersBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLAddAllContainersBTNActionPerformed(evt);
            }
        });

        PLNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PLNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        PLNameLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Customer:");

        PLClearBTN.setText("CLEAR");
        PLClearBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PLRemoveBTN.setText("REMOVE");
        PLRemoveBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLRemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLRemoveBTNActionPerformed(evt);
            }
        });

        PLAddCustomBTN.setText("ADD CUSTOM");
        PLAddCustomBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLAddCustomBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLAddCustomBTNActionPerformed(evt);
            }
        });

        PLContainerChosenLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PLContainerChosenLabel.setForeground(new java.awt.Color(255, 255, 255));
        PLContainerChosenLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PLContainerChosenLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        PLOpenContainerBTN.setText("OPEN");
        PLOpenContainerBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PLOpenContainerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PLOpenContainerBTNActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Container:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(PLOpenContainerBTN))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(PLIDLable)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PLIDInput)
                                        .addGap(18, 18, 18)
                                        .addComponent(PLSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 181, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(PLClearBTN)
                                .addGap(59, 59, 59)
                                .addComponent(PLContainerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PLContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PLAddBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PLNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(PLContainerScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(PLOpenBTN)
                                .addComponent(PLAddAllContainersBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PLContainerChosenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(PLItemScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(PLAddItemBTN)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(PLPickListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(PLRemoveBTN)
                        .addGap(18, 18, 18)
                        .addComponent(PLAddCustomBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PLCreateBTN)))
                .addGap(43, 43, 43))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {PLAddItemBTN, PLClearBTN, PLCreateBTN, PLOpenBTN, PLRemoveBTN, PLSearchBTN});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {PLAddBTN, PLOpenContainerBTN});

        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(PLOpenContainerBTN)
                                .addGap(51, 51, 51))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PLContainerLabel)
                                    .addComponent(PLIDLable)
                                    .addComponent(PLIDInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PLSearchBTN)
                                    .addComponent(PLClearBTN)
                                    .addComponent(PLAddBTN)
                                    .addComponent(PLContainerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PLNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PLContainerChosenLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(PLAddAllContainersBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PLContainerScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(PLItemScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(PLPickListScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PLAddItemBTN)
                    .addComponent(PLCreateBTN)
                    .addComponent(PLOpenBTN)
                    .addComponent(PLRemoveBTN)
                    .addComponent(PLAddCustomBTN))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel32.add(jPanel15);
        jPanel15.setBounds(0, 0, 1330, 710);

        PLBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel32.add(PLBackgroundLabel);
        PLBackgroundLabel.setBounds(-6, 0, 1340, 710);

        javax.swing.GroupLayout PickListWindowLayout = new javax.swing.GroupLayout(PickListWindow.getContentPane());
        PickListWindow.getContentPane().setLayout(PickListWindowLayout);
        PickListWindowLayout.setHorizontalGroup(
            PickListWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 1335, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        PickListWindowLayout.setVerticalGroup(
            PickListWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );

        FinalizePickListWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        FinalizePickListWindow.setTitle("Finalize Pick List");
        FinalizePickListWindow.setMinimumSize(new java.awt.Dimension(859, 598));
        FinalizePickListWindow.setResizable(false);

        jPanel33.setLayout(null);

        jPanel16.setBackground(new java.awt.Color(51, 51, 51));
        jPanel16.setOpaque(false);

        FLPickListTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FLPickListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Container", "Location", "#Items", "Item", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        FLPickListScroll.setViewportView(FLPickListTable);

        FLPickListLable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FLPickListLable.setForeground(new java.awt.Color(255, 255, 255));
        FLPickListLable.setText("Pick List:");

        FLFinalizeBTN.setText("FINALIZE");
        FLFinalizeBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FLFinalizeBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FLFinalizeBTNActionPerformed(evt);
            }
        });

        FLNoActionTakenBTN.setText("NO ACTION TAKEN");
        FLNoActionTakenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FLNoActionTakenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FLNoActionTakenBTNActionPerformed(evt);
            }
        });

        FLOpenBTN.setText("OPEN");
        FLOpenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FLOpenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FLOpenBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(FLPickListLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FLComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FLOpenBTN)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(FLNoActionTakenBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FLFinalizeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(FLPickListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {FLFinalizeBTN, FLOpenBTN});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FLComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FLPickListLable)
                    .addComponent(FLOpenBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FLPickListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FLFinalizeBTN)
                    .addComponent(FLNoActionTakenBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.add(jPanel16);
        jPanel16.setBounds(39, 8, 820, 590);

        FPBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background2.jpg"))); // NOI18N
        jPanel33.add(FPBackgroundLabel);
        FPBackgroundLabel.setBounds(-16, 0, 880, 600);

        javax.swing.GroupLayout FinalizePickListWindowLayout = new javax.swing.GroupLayout(FinalizePickListWindow.getContentPane());
        FinalizePickListWindow.getContentPane().setLayout(FinalizePickListWindowLayout);
        FinalizePickListWindowLayout.setHorizontalGroup(
            FinalizePickListWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
        );
        FinalizePickListWindowLayout.setVerticalGroup(
            FinalizePickListWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );

        LogInWindowFirst.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        LogInWindowFirst.setTitle("Log In");
        LogInWindowFirst.setMinimumSize(new java.awt.Dimension(548, 277));
        LogInWindowFirst.setResizable(false);

        jPanel34.setLayout(null);

        jPanel19.setBackground(new java.awt.Color(51, 51, 51));
        jPanel19.setOpaque(false);

        LogInUserInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInUserInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        LogInUserLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInUserLabel.setForeground(new java.awt.Color(255, 255, 255));
        LogInUserLabel.setText("Username:");

        LogInPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInPasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        LogInPasswordLabel.setText("Password:");

        LogInPasswordInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInPasswordInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        LogInFirstEnterBTN.setText("ENTER");
        LogInFirstEnterBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LogInFirstEnterBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInFirstEnterBTNActionPerformed(evt);
            }
        });

        LogInPasswordLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInPasswordLabel1.setForeground(new java.awt.Color(255, 255, 255));
        LogInPasswordLabel1.setText("Re-Enter Password:");

        LIEnterLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LIEnterLabel.setForeground(new java.awt.Color(255, 255, 255));
        LIEnterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LIEnterLabel.setText("Please Enter The Administrator Login details");
        LIEnterLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        LogInReEnterPasswordInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInReEnterPasswordInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(LogInUserLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LogInUserInput, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(LogInPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LogInPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(LogInPasswordLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LogInReEnterPasswordInput)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LogInFirstEnterBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(LIEnterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(LIEnterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInUserInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogInUserLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInPasswordLabel)
                    .addComponent(LogInPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInPasswordLabel1)
                    .addComponent(LogInReEnterPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogInFirstEnterBTN))
                .addGap(85, 85, 85))
        );

        jPanel34.add(jPanel19);
        jPanel19.setBounds(-2, -3, 550, 280);

        LIFBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel34.add(LIFBackgroundLabel);
        LIFBackgroundLabel.setBounds(0, 0, 550, 280);

        javax.swing.GroupLayout LogInWindowFirstLayout = new javax.swing.GroupLayout(LogInWindowFirst.getContentPane());
        LogInWindowFirst.getContentPane().setLayout(LogInWindowFirstLayout);
        LogInWindowFirstLayout.setHorizontalGroup(
            LogInWindowFirstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        LogInWindowFirstLayout.setVerticalGroup(
            LogInWindowFirstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );

        LogInWindowMain.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        LogInWindowMain.setTitle("Log In");
        LogInWindowMain.setMinimumSize(new java.awt.Dimension(472, 353));
        LogInWindowMain.setResizable(false);

        jPanel35.setLayout(null);

        jPanel21.setBackground(new java.awt.Color(51, 51, 51));
        jPanel21.setOpaque(false);

        LogInUserInputMain.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInUserInputMain.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        LogInUserLabelMain.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInUserLabelMain.setForeground(new java.awt.Color(255, 255, 255));
        LogInUserLabelMain.setText("Username:");

        LogInPasswordLabelMain.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInPasswordLabelMain.setForeground(new java.awt.Color(255, 255, 255));
        LogInPasswordLabelMain.setText("Password:");

        LogInPasswordInputMain.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogInPasswordInputMain.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        LIEnterMainBTN.setText("ENTER");
        LIEnterMainBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LIEnterMainBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LIEnterMainBTNActionPerformed(evt);
            }
        });

        LIFailedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LIFailedLabel.setForeground(new java.awt.Color(255, 0, 51));
        LIFailedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LIFailedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(LogInUserLabelMain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LogInUserInputMain, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(LIEnterMainBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                            .addComponent(LogInPasswordLabelMain)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(LogInPasswordInputMain, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(LIFailedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInUserInputMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogInUserLabelMain))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInPasswordInputMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogInPasswordLabelMain))
                .addGap(18, 18, 18)
                .addComponent(LIEnterMainBTN)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jPanel35.add(jPanel21);
        jPanel21.setBounds(-2, -3, 490, 360);

        LIMBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel35.add(LIMBackgroundLabel);
        LIMBackgroundLabel.setBounds(-6, 0, 480, 360);

        javax.swing.GroupLayout LogInWindowMainLayout = new javax.swing.GroupLayout(LogInWindowMain.getContentPane());
        LogInWindowMain.getContentPane().setLayout(LogInWindowMainLayout);
        LogInWindowMainLayout.setHorizontalGroup(
            LogInWindowMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );
        LogInWindowMainLayout.setVerticalGroup(
            LogInWindowMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
        );

        AddUserWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddUserWindow.setTitle("Add User");
        AddUserWindow.setMinimumSize(new java.awt.Dimension(548, 366));
        AddUserWindow.setResizable(false);

        jPanel36.setLayout(null);

        jPanel22.setBackground(new java.awt.Color(51, 51, 51));
        jPanel22.setOpaque(false);

        AUUserNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUUserNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        AUUserNameLabel.setText("Username:");

        AUPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUPasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        AUPasswordLabel.setText("Password:");

        AUPasswordInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUPasswordInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        AUEnterBTN.setText("ENTER");
        AUEnterBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AUEnterBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AUEnterBTNActionPerformed(evt);
            }
        });

        AUreEnterPasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUreEnterPasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        AUreEnterPasswordLabel.setText("Re-Enter Password:");

        AUMainLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUMainLabel.setForeground(new java.awt.Color(255, 255, 255));
        AUMainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AUMainLabel.setText("Please Enter The User Details");
        AUMainLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        AUReEnterPasswordInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUReEnterPasswordInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AUReEnterPasswordInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AUReEnterPasswordInputActionPerformed(evt);
            }
        });

        AUUserNameInput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUUserNameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        AUClearanceLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AUClearanceLabel.setForeground(new java.awt.Color(255, 255, 255));
        AUClearanceLabel.setText("Clearance:");

        AUComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BASIC", "ADMIN" }));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(AUClearanceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AUComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AUEnterBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(AUPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AUPasswordInput))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(AUUserNameLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(AUreEnterPasswordLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AUReEnterPasswordInput)
                            .addComponent(AUUserNameInput)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(AUMainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(158, 158, 158))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(AUMainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AUUserNameLabel)
                    .addComponent(AUUserNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AUPasswordLabel)
                    .addComponent(AUPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AUreEnterPasswordLabel)
                    .addComponent(AUReEnterPasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AUClearanceLabel)
                    .addComponent(AUComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AUEnterBTN))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jPanel36.add(jPanel22);
        jPanel22.setBounds(2, -4, 550, 370);

        AUBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background3.jpg"))); // NOI18N
        jPanel36.add(AUBackgroundLabel);
        AUBackgroundLabel.setBounds(-6, 0, 560, 370);

        javax.swing.GroupLayout AddUserWindowLayout = new javax.swing.GroupLayout(AddUserWindow.getContentPane());
        AddUserWindow.getContentPane().setLayout(AddUserWindowLayout);
        AddUserWindowLayout.setHorizontalGroup(
            AddUserWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );
        AddUserWindowLayout.setVerticalGroup(
            AddUserWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );

        AnalyticWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AnalyticWindow.setTitle("Analytics");
        AnalyticWindow.setMinimumSize(new java.awt.Dimension(749, 683));
        AnalyticWindow.setResizable(false);

        jPanel37.setLayout(null);

        jPanel17.setBackground(new java.awt.Color(51, 51, 51));
        jPanel17.setOpaque(false);

        AnalyticTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AnalyticTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Containers", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        AnalyticScroll.setViewportView(AnalyticTable);

        ATNumberOfCustomersLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfCustomersLabel.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfCustomersLabel.setText("Number Of Customers:");

        ATNumberOfContainersLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfContainersLabel.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfContainersLabel.setText("Number Of Containers:");

        ATNumberOfEmptyContainersLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfEmptyContainersLabel.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfEmptyContainersLabel.setText("Number Of Empty Containers:");

        ATComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Longest Customer", "Most Containers" }));

        ATSearchBTN.setText("SEARCH");
        ATSearchBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ATSearchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATSearchBTNActionPerformed(evt);
            }
        });

        ATNumberOfCustomersOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfCustomersOutput.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfCustomersOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ATNumberOfCustomersOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ATNumberOfContainersOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfContainersOutput.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfContainersOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ATNumberOfContainersOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ATNumberOfEmptyContainersOutput.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ATNumberOfEmptyContainersOutput.setForeground(new java.awt.Color(255, 255, 255));
        ATNumberOfEmptyContainersOutput.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ATNumberOfEmptyContainersOutput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(AnalyticScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ATComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ATSearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ATNumberOfEmptyContainersLabel)
                            .addComponent(ATNumberOfContainersLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ATNumberOfCustomersLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ATNumberOfEmptyContainersOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ATNumberOfContainersOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ATNumberOfCustomersOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ATNumberOfContainersOutput, ATNumberOfCustomersOutput, ATNumberOfEmptyContainersOutput});

        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ATNumberOfCustomersLabel)
                    .addComponent(ATNumberOfCustomersOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ATNumberOfContainersLabel)
                    .addComponent(ATNumberOfContainersOutput))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ATNumberOfEmptyContainersLabel)
                    .addComponent(ATNumberOfEmptyContainersOutput))
                .addGap(35, 35, 35)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(ATComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ATSearchBTN))
                    .addComponent(AnalyticScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ATNumberOfContainersOutput, ATNumberOfCustomersOutput, ATNumberOfEmptyContainersOutput});

        jPanel37.add(jPanel17);
        jPanel17.setBounds(9, -2, 740, 660);

        ANLBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/background2.jpg"))); // NOI18N
        jPanel37.add(ANLBackgroundLabel);
        ANLBackgroundLabel.setBounds(-16, 0, 780, 660);

        javax.swing.GroupLayout AnalyticWindowLayout = new javax.swing.GroupLayout(AnalyticWindow.getContentPane());
        AnalyticWindow.getContentPane().setLayout(AnalyticWindowLayout);
        AnalyticWindowLayout.setHorizontalGroup(
            AnalyticWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
        );
        AnalyticWindowLayout.setVerticalGroup(
            AnalyticWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Container Store");
        setMinimumSize(new java.awt.Dimension(1386, 854));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(null);

        jPanel38.setOpaque(false);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(170, 210, 230), new java.awt.Color(102, 102, 102), null));
        jPanel2.setOpaque(false);

        AddNewCustomerWindowBTN.setBackground(new java.awt.Color(209, 232, 244));
        AddNewCustomerWindowBTN.setText("Add New Customer");
        AddNewCustomerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AddNewCustomerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewCustomerWindowBTNActionPerformed(evt);
            }
        });

        EditCustomerWindowBTN.setText("Edit Customer");
        EditCustomerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditCustomerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditCustomerWindowBTNActionPerformed(evt);
            }
        });

        SearchCustomerWindowBTN.setText("Search Customer");
        SearchCustomerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SearchCustomerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchCustomerWindowBTNActionPerformed(evt);
            }
        });

        AddContainerToExistingCustomerBTN.setText("Add Container To Existing Customer");
        AddContainerToExistingCustomerBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AddContainerToExistingCustomerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddContainerToExistingCustomerBTNActionPerformed(evt);
            }
        });

        SearchContainerWindowBTN.setText("Search Container");
        SearchContainerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SearchContainerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchContainerWindowBTNActionPerformed(evt);
            }
        });

        MoveContainerWindowBTN.setText("Move Container");
        MoveContainerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MoveContainerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveContainerWindowBTNActionPerformed(evt);
            }
        });

        EmptyContainerWindowBTN.setText("Empty Container");
        EmptyContainerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EmptyContainerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmptyContainerWindowBTNActionPerformed(evt);
            }
        });

        RemoveCustomerWindowBTN.setText("Remove Customer");
        RemoveCustomerWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RemoveCustomerWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveCustomerWindowBTNActionPerformed(evt);
            }
        });

        InventoryListBTN.setText("Inventory List");
        InventoryListBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        InventoryListBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InventoryListBTNActionPerformed(evt);
            }
        });

        SearchInventorysBTN.setText("Search Inventorys");
        SearchInventorysBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SearchInventorysBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchInventorysBTNActionPerformed(evt);
            }
        });

        EditInventorysBTN.setText("Edit Inventorys");
        EditInventorysBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditInventorysBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditInventorysBTNActionPerformed(evt);
            }
        });

        PickListBTN.setText("Pick List");
        PickListBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PickListBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickListBTNActionPerformed(evt);
            }
        });

        FinalizePickListBTN.setText("Finalize Pick List");
        FinalizePickListBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        FinalizePickListBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizePickListBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddNewCustomerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditCustomerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchCustomerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddContainerToExistingCustomerBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchContainerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MoveContainerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmptyContainerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RemoveCustomerWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryListBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchInventorysBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditInventorysBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PickListBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FinalizePickListBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(AddNewCustomerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(EditCustomerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(SearchCustomerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(AddContainerToExistingCustomerBTN)
                .addGap(50, 50, 50)
                .addComponent(SearchContainerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(MoveContainerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(EmptyContainerWindowBTN)
                .addGap(18, 18, 18)
                .addComponent(RemoveCustomerWindowBTN)
                .addGap(50, 50, 50)
                .addComponent(InventoryListBTN)
                .addGap(18, 18, 18)
                .addComponent(SearchInventorysBTN)
                .addGap(18, 18, 18)
                .addComponent(EditInventorysBTN)
                .addGap(50, 50, 50)
                .addComponent(PickListBTN)
                .addGap(18, 18, 18)
                .addComponent(FinalizePickListBTN)
                .addContainerGap())
        );

        MainLable.setIcon(new javax.swing.ImageIcon("C:\\Users\\hutch\\Downloads\\cooltext370318148149987.png")); // NOI18N
        MainLable.setFocusable(false);
        MainLable.setRequestFocusEnabled(false);

        MainCustomerScroll.setBackground(new java.awt.Color(0, 0, 0));
        MainCustomerScroll.setOpaque(false);
        MainCustomerScroll.setRequestFocusEnabled(false);

        MainCustomerTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MainCustomerTable.setForeground(new java.awt.Color(255, 255, 255));
        MainCustomerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address", "Telephone", "#Containers", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Byte.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MainCustomerTable.setOpaque(false);
        MainCustomerTable.getTableHeader().setReorderingAllowed(false);
        MainCustomerScroll.setViewportView(MainCustomerTable);
        if (MainCustomerTable.getColumnModel().getColumnCount() > 0) {
            MainCustomerTable.getColumnModel().getColumn(0).setMinWidth(100);
            MainCustomerTable.getColumnModel().getColumn(0).setMaxWidth(100);
            MainCustomerTable.getColumnModel().getColumn(3).setMinWidth(150);
            MainCustomerTable.getColumnModel().getColumn(3).setMaxWidth(150);
            MainCustomerTable.getColumnModel().getColumn(4).setMinWidth(100);
            MainCustomerTable.getColumnModel().getColumn(4).setMaxWidth(100);
            MainCustomerTable.getColumnModel().getColumn(5).setMinWidth(100);
            MainCustomerTable.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        MainConLocScroll.setOpaque(false);

        MainConLocTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MainConLocTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Container", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MainConLocTable.setOpaque(false);
        MainConLocScroll.setViewportView(MainConLocTable);

        MainInventoryScroll.setOpaque(false);

        MainInventoryTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MainInventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Items", "Item", "Description", "Damage"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MainInventoryTable.setOpaque(false);
        MainInventoryScroll.setViewportView(MainInventoryTable);

        userNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(152, 218, 240));
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNameLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        MainOpenBTN.setText("Open");
        MainOpenBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainOpenBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainOpenBTNActionPerformed(evt);
            }
        });

        LogOutBTN.setText("Log Out");
        LogOutBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LogOutBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutBTNActionPerformed(evt);
            }
        });

        MainCustomersLabel.setForeground(new java.awt.Color(255, 255, 255));
        MainCustomersLabel.setText("Customers:");

        MainContainersLabel.setForeground(new java.awt.Color(255, 255, 255));
        MainContainersLabel.setText("Containers:");

        MainInventoryLabel.setForeground(new java.awt.Color(255, 255, 255));
        MainInventoryLabel.setText("Inventory:");

        MainExploreBTN.setText("Explore");
        MainExploreBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainExploreBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainExploreBTNActionPerformed(evt);
            }
        });

        MainResetTable.setText("Reset");
        MainResetTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainResetTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainResetTableActionPerformed(evt);
            }
        });

        ADMINPanel.setBackground(new java.awt.Color(0, 0, 0));
        ADMINPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 153), new java.awt.Color(170, 210, 230), null));

        MainAddUserBTN.setText("Add User");
        MainAddUserBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainAddUserBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainAddUserBTNActionPerformed(evt);
            }
        });

        AnalyticsBTN.setText("Analytics");
        AnalyticsBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AnalyticsBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalyticsBTNActionPerformed(evt);
            }
        });

        ViewLogWindowBTN.setText("View Logs");
        ViewLogWindowBTN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ViewLogWindowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewLogWindowBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ADMINPanelLayout = new javax.swing.GroupLayout(ADMINPanel);
        ADMINPanel.setLayout(ADMINPanelLayout);
        ADMINPanelLayout.setHorizontalGroup(
            ADMINPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ADMINPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ADMINPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MainAddUserBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AnalyticsBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ADMINPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ViewLogWindowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ADMINPanelLayout.setVerticalGroup(
            ADMINPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ADMINPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainAddUserBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AnalyticsBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ViewLogWindowBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MainCustomersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MainConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MainContainersLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MainInventoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                                            .addComponent(MainOpenBTN)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(MainResetTable, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(MainInventoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(MainExploreBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(MainCustomerScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(MainLable, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ADMINPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LogOutBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );

        jPanel38Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {MainExploreBTN, MainOpenBTN, MainResetTable});

        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MainLable, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(LogOutBTN))))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(ADMINPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(MainCustomersLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MainCustomerScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MainExploreBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(MainInventoryLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MainInventoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(MainOpenBTN)
                                    .addComponent(MainResetTable))
                                .addContainerGap(77, Short.MAX_VALUE))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(MainContainersLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MainConLocScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(77, 77, 77))))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(70, Short.MAX_VALUE))))
        );

        jPanel1.add(jPanel38);
        jPanel38.setBounds(0, 0, 1390, 850);

        backgroundlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/containerstore/block background.jpg"))); // NOI18N
        backgroundlabel.setFocusable(false);
        jPanel1.add(backgroundlabel);
        backgroundlabel.setBounds(0, 0, 1390, 850);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1390, 860);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Adds Containers and locations to the ConLocTable in the Add New Customer Window
     * @param evt 
     */
    
    private void ANCAddConLocBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ANCAddConLocBTNActionPerformed
         
        try{
            
            container = Short.parseShort(ANCContainerInput.getText());
            location = ANCLocationInput.getText().toUpperCase();
            
        //checks if inputs are not empty 
        
            if(!ANCContainerInput.getText().isEmpty() && !location.isEmpty()) {
        
            //checks if container and location are in use
                
                if(wh.checkContainer(container) == true && wh.checkLocation(location) == true) {

                //checks if container and location have not already been entered in the instance    
                    
                    if(!containerList.contains(container) && !locationList.contains(location)) {
                                           
                        DefaultTableModel tm = (DefaultTableModel)ANCConLocTable.getModel();

                        tm.addRow(new Object[]{container, location});
 
                        
                    }else {
                        
                        JOptionPane.showMessageDialog(null, "Container and/or Location already entered!");
                    }                   
                }
                
            }else{

                JOptionPane.showMessageDialog(null, "Please Enter Container Number And Location");
            }
   
        }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits For Container Number");
            ANCContainerInput.setText("");
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
           
        }

        ANCContainerInput.setText("");
        ANCLocationInput.setText("");
        
    }//GEN-LAST:event_ANCAddConLocBTNActionPerformed

    /**
     * Deletes containers and location from Add New Customer Table
     * @param evt 
     */
    
    private void ANCRemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ANCRemoveBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)ANCConLocTable.getModel();
                
        int remove = ANCConLocTable.getSelectedRow();
        
        if(remove != -1) {
        
            tm.removeRow(remove);
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Please Select Container To Remove");
        }       
    }//GEN-LAST:event_ANCRemoveBTNActionPerformed
    
    /**
     * Clears All Entered Information From Add New Customer
     * @param evt 
     */
    
    private void ANCClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ANCClearBTNActionPerformed
               
        ANCNameInput.setText("");
        ANCAddressInput.setText("");
        ANCTelePhoneNumberInput.setText("");
        ANCContainerInput.setText("");
        ANCLocationInput.setText("");
        
        DefaultTableModel tm = (DefaultTableModel)ANCConLocTable.getModel();
        
        for(int i = tm.getRowCount() -1; i >= 0; i--) {
            
            tm.removeRow(i);
            
        }    
        
        containerList.clear();
        locationList.clear();
        
    }//GEN-LAST:event_ANCClearBTNActionPerformed

    /**
     * Confirms entered information and calls new customer to enter into database
     * @param evt 
     */
    
    private void ANCConfirmBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ANCConfirmBTNActionPerformed
        
         DefaultTableModel tm = (DefaultTableModel)ANCConLocTable.getModel();
        
        action = "Added New Customer";
        name = ANCNameInput.getText().toUpperCase().trim();
        
        address = tidyAddressForDatabase(ANCAddressInput.getText().toUpperCase());
        
        if(!name.isEmpty() && !address.isEmpty()) {

            try {
                               
                number = ANCTelePhoneNumberInput.getText().trim();
                
                for(int i = tm.getRowCount() - 1; i >=0; i--){
                    
                    containerList.add(Short.parseShort(tm.getValueAt(i, 0).toString()));
                    locationList.add(String.valueOf(tm.getValueAt(i, 1)));
                    
                }
                

                customer.addNewCustomer(name, address, number, containerList, locationList);
               
                log.Log(action, name + " Added");
                
        //deletes fields after succesfull data entry
                
                ANCNameInput.setText("");
                ANCAddressInput.setText("");
                ANCTelePhoneNumberInput.setText("");
                ANCContainerInput.setText("");
                ANCLocationInput.setText("");

               

                for(int i = tm.getRowCount() -1; i >= 0; i--) {

                    tm.removeRow(i);

                }
                
                containerList.clear();
                locationList.clear();
                
                AddNewCustomerWindow.setVisible(false);
                
                populateMainTable();
                populateMainConLocTable();
                
                  
        //option to go and complete the inventory lists for all containers
                
                int choice = JOptionPane.showConfirmDialog(null,"Would You Like To Complete The Inventory's", "Inventory", JOptionPane.OK_CANCEL_OPTION);
                
                if(choice == 0) {
                    
                    DefaultTableModel tm2 = (DefaultTableModel)EIConLocTable.getModel();
                    
                    EditInventorysWindow.setVisible(true);
                    EditInventorysWindow.setLocationRelativeTo(null);
                    
                    ID = customer.getCustomerIdFromName(name);
                    
                    EINameInput.setText(name);
                    
                    String getId =  String.valueOf(ID);
                    EIIDInput.setText(getId);
                    
                    EINameOutput.setText(name);
                    EIAddressOutput.setText(address); 
                    EINumberOutput.setText(number);
                    
                    Object[][] conLocs = wh.getConainersAndLocations(ID);
                    
                    tm2.setRowCount(0);
                    
                    for(Object[] r : conLocs) {
                        
                        tm2.addRow(new Object[] {r[0], r[1]});
                        
                    }
                    
                }
                     
            } catch (SQLException ex) {

                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                
            }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits For Phone Number");
        }
                    
        }else{

            JOptionPane.showMessageDialog(null, "Please Fill In All Fields");
        }   
    }//GEN-LAST:event_ANCConfirmBTNActionPerformed

    /**
     * button to search for customer to edit information
     * @param evt 
     */
    
    private void ECSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECSearchBTNActionPerformed
 
        name = ECNameSearchInput.getText().toUpperCase();
        
        try {
            
            ID = Integer.parseInt(ECIDSearchInput.getText());
            
        }catch(NumberFormatException e) {
            
            //left blank to stop exception when name is searched for not id
        }
        
        if(!name.isEmpty()) {
            
            try {

                String ci = new String (customer.getCustomerDetailsByName(name));
                
                String[] split = ci.split("/");
                
               ECIDSearchInput.setText(split[0]);
               ECNameOutput.setText(split[1]);
               
               address = tidyAddress(address);
               
               ECAddressOutput.setText(address);
               
               ECTelephoneOutput.setText(split[3]);
               ECDateOutput.setText(split[4]);
                
            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
        if(name.isEmpty()) {

            try {

                String ci = new String (customer.getCustomerDetailsByID(ID));

                String[] split = ci.split("/");

                ECIDSearchInput.setText(split[0]);
                ECNameOutput.setText(split[1]);
                
                address = tidyAddress(address);

                ECAddressOutput.setText(address);
                
                ECTelephoneOutput.setText(split[3]);
                ECDateOutput.setText(split[4]);

            } catch (SQLException ex) {
                
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }//GEN-LAST:event_ECSearchBTNActionPerformed
   
    /**
     * button to clear all fields in the edit customer window
     * @param evt 
     */
    
    private void ECClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECClearBTNActionPerformed
        
        ECNameSearchInput.setText("");
        ECIDSearchInput.setText("");
                
        ECIDSearchInput.setText("");
        ECNameOutput.setText("");
        ECAddressOutput.setText("");
        ECTelephoneOutput.setText("");
        ECDateOutput.setText("");
        
        ECNameInput.setText("");
        ECAddressInput.setText("");
        ECTelephoneInput.setText("");
        
    }//GEN-LAST:event_ECClearBTNActionPerformed

    /**
     * button to execute update of customers details
     * @param evt 
     */
    
    private void ECUpdateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECUpdateBTNActionPerformed
        
        try{
            
            ID = Integer.parseInt(ECIDSearchInput.getText());
            name = ECNameInput.getText().toUpperCase();
            address = ECAddressInput.getText().toUpperCase();
            
            try{
                
                number = ECTelephoneInput.getText();
                
            }catch(NumberFormatException e) {
                
                number = ECTelephoneOutput.getText();
            }
            
            if(name.isEmpty()) {
                
                name = ECNameOutput.getText();
            }
            if(address.isEmpty()) {
                
                address = ECAddressOutput.getText();
            }

            customer.updateCustomer(ID, name, address, number);
            
        //Clears all fields after completion
            
            ECNameSearchInput.setText("");
            ECIDSearchInput.setText("");

            ECIDSearchInput.setText("");
            ECNameOutput.setText("");
            ECAddressOutput.setText("");
            ECTelephoneOutput.setText("");
            ECDateOutput.setText("");

            ECNameInput.setText("");
            ECAddressInput.setText("");
            ECTelephoneInput.setText("");
            
            populateMainTable();
            populateMainConLocTable();
            
        }catch(SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }//GEN-LAST:event_ECUpdateBTNActionPerformed

    /**
     * button to search for customer information in search customer window
     * @param evt 
     */
    
    private void SCSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCSearchBTNActionPerformed
 
        DefaultTableModel tm = (DefaultTableModel)SCConLocTable.getModel();
        name = SCNameSearchInput.getText().toUpperCase();
           
        try{
            
            ID = Integer.parseInt(SCIDSearchInput.getText());
            
         }catch(NumberFormatException e) {

            //left blank
         }
               
        if(!name.isEmpty()) {
 
            try{
                           
                String ci  = new String(customer.getCustomerDetailsByName(name));
                
                String[] split = ci.split("/");
                
                SCIDOutput.setText(split[0]);
                SCNameOutput.setText(split[1]);
                
                address = tidyAddress(split[2]);
                
                SCAddressOutput.setText(address);
                
                SCTelephoneOutput.setText(split[3]);
                
                ID = Integer.parseInt(split[0]);
                        
                Object[][] conLocs = wh.getConainersAndLocations(ID);
                
                tm.setRowCount(0);
                
                for(int i = 0; i < conLocs.length; i++) {
                    
                    tm.addRow(new Object[]{conLocs[i][0], conLocs[i][1]});
                }
                
            }catch (SQLException ex) {
                
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            
        }else {
 
            try{

                String ci  = new String(customer.getCustomerDetailsByID(ID));

                String[] split = ci.split("/");

                SCIDOutput.setText(split[0]);
                SCNameOutput.setText(split[1]);
                              
                address = tidyAddress(split[2]);
                
                SCAddressOutput.setText(address.trim());
                
                SCTelephoneOutput.setText(split[3]);
                
                tm.setRowCount(0);

                Object[][] conLocs = wh.getConainersAndLocations(ID);

                for(int i = 0; i < conLocs.length; i++) {

                    tm.addRow(new Object[]{conLocs[i][0], conLocs[i][1]});
                }

            }catch (SQLException ex) {

            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }//GEN-LAST:event_SCSearchBTNActionPerformed

    /**
     * search button for the add container to existing customer window
     * @param evt 
     */
    
    private void ACSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACSearchBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)ACConLocTable.getModel();
        tm.setRowCount(0);
        
        name = ACNameSearchInput.getText().toUpperCase();
           
        try{
            
            ID = Integer.parseInt(ACIDSearchInput.getText());
            
         }catch(NumberFormatException e) {

            //left blank
         }
               
        if(!name.isEmpty()) {
 
            try{
                
                String ci  = new String(customer.getCustomerDetailsByName(name));
                
                String[] split = ci.split("/");
                
                ACIDOutput.setText(split[0]);
                ACNameOutput.setText(split[1]);


            }catch (SQLException ex) {
                
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            
        }else {
 
            try{

                ID = Integer.parseInt(ACIDSearchInput.getText());

                String ci  = new String(customer.getCustomerDetailsByID(ID));

                String[] split = ci.split("/");

                ACIDOutput.setText(split[0]);
                ACNameOutput.setText(split[1]);


            }catch (SQLException ex) {

                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);

            }catch(NumberFormatException e) {

                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_ACSearchBTNActionPerformed

    /**
     * adds new to container and location to table in add container to existing customer
     * @param evt 
     */
    
    private void ACAddBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACAddBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)ACConLocTable.getModel();
              
        try {

            container = Short.parseShort(ACContainerInput.getText().trim());
            location = ACLocationInput.getText().toUpperCase().trim();
        
            if(!location.isEmpty() && container != 0) {
                
                if(wh.checkContainer(container) == true && wh.checkLocation(location) == true){
                
                    tm.addRow(new Object[]{container, location});

                    ACContainerInput.setText("");
                    ACLocationInput.setText("");
                }        
            }
            
        }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter A Valid Number For Container");
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ACAddBTNActionPerformed

    /**
     * removes chosen container from add container to existing customer ConLocTable
     * @param evt 
     */
    
    private void ACRemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACRemoveBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)ACConLocTable.getModel();
        
        int remove = ACConLocTable.getSelectedRow();
        
        tm.removeRow(remove);
        
    }//GEN-LAST:event_ACRemoveBTNActionPerformed

    /**
     * button to update added containers into customers container list
     * @param evt 
     */
    
    private void ACUpdateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ACUpdateBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)ACConLocTable.getModel();
        
        try {

            ID = Integer.parseInt(ACIDOutput.getText());
            
            for(int i = tm.getRowCount() -1; i >= 0; i--) {
                                                            
                    container = Short.valueOf(tm.getValueAt(i, 0).toString());
                    location = (tm.getValueAt(i, 1)).toString();

                    wh.addContainers(ID, container, location);
                    
                    boolean exist = wh.containerPresent(container);

                    if(exist){
                        
                        wh.updateManifestLocation(ID, container, location);
                        
                    }else{
                        
                        wh.addToManifest(ID, container, location);
                    } 
                    
    //option to go and complete the inventory lists for all containers
                
                int choice = JOptionPane.showConfirmDialog(null,"Would You Like To Complete The Inventory's", "Inventory", JOptionPane.OK_CANCEL_OPTION);
                
                if(choice == 0) {
                    
                    DefaultTableModel tm2 = (DefaultTableModel)EIConLocTable.getModel();
                    
                    EditInventorysWindow.setVisible(true);
                    EditInventorysWindow.setLocationRelativeTo(null);
                                        
                    String[] details = customer.getCustomerDetailsByID(ID).toString().split("/");
                       
                    EIIDInput.setText(details[0]);
                    
                    EINameOutput.setText(details[1]);
                                  
                    address = tidyAddress(details[2]);
                                    
                    EIAddressOutput.setText(address);
                                        
                    EINumberOutput.setText(details[3]);
                    
                    EIDateOutput.setText(details[4]);
                    
                    Object[][] conLocs = wh.getConainersAndLocations(ID);
                    
                    tm2.setRowCount(0);
                    
                    for(Object[] r : conLocs) {
                        
                        tm2.addRow(new Object[] {r[0], r[1]});
                        
                    }    
                }
            }    
            
            populateMainTable();
            populateMainConLocTable();
           
        }catch (NumberFormatException | SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }//GEN-LAST:event_ACUpdateBTNActionPerformed

    /**
     * search customer clear button
     * @param evt 
     */
    
    private void SCClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCClearBTNActionPerformed
       
        SCIDOutput.setText("");
        SCNameOutput.setText("");
        SCAddressOutput.setText("");
        SCTelephoneOutput.setText("");
        
        SCNameSearchInput.setText("");
        SCIDSearchInput.setText("");
        
        SCContainerInput.setText("");
                       
        name = "";
        ID = 0;
        
        DefaultTableModel tm = (DefaultTableModel)SCConLocTable.getModel();
        
        tm.setRowCount(0);
        
    }//GEN-LAST:event_SCClearBTNActionPerformed

    /**
     * Search container search button
     * @param evt 
     */
    
    private void SCONSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCONSearchBTNActionPerformed
        
        try {

            container = Short.parseShort(SCONContainerInput.getText());
            
            location = wh.getLocation(container);
            
            SCONLocationOutput.setText(location);
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
        }                 
    }//GEN-LAST:event_SCONSearchBTNActionPerformed

    /**
     * button to move container to different location
     * @param evt 
     */
    
    private void MCMoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MCMoveBTNActionPerformed
        
        try{
                    
            container = Short.parseShort(MCContainerInput.getText().trim());
            location = MCMoveToInput.getText().toUpperCase().trim();
            
            wh.moveContainer(container, location);
            
            populateMainTable();
            populateMainConLocTable();
            
            MCContainerInput.setText("");
            MCMoveToInput.setText("");
            
        } catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_MCMoveBTNActionPerformed

    /**
     * removes the container from the customers container list and the warehouse manifest
     * @param evt 
     */
    
    private void ECEmptyBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ECEmptyBTNActionPerformed
        
        try{
            
            container = Short.parseShort(ECContainerInput.getText());
            
            wh.emptyContainer(container);
                       
            wh.updateInventory(container, new Object[0][0]);
            
            populateMainTable();
            populateMainConLocTable();
            
        }catch(NumberFormatException e) {
                  
             JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");    
            
        } catch (SQLException | IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }//GEN-LAST:event_ECEmptyBTNActionPerformed

    /**
     * search for customer to remove button
     * @param evt 
     */
    
    private void RCUSSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCUSSearchBTNActionPerformed
        
        try {

            ID = Integer.parseInt(RCUSSearchInput.getText());
            
            String details = customer.getCustomerDetailsByID(ID).toString();
            String[] split = details.split("/");
            
            RCUSNameOutput.setText(split[1]);
            
            address = tidyAddress(split[2]);
            
            RCUSAddressOutput.setText(address.trim());
            
            RCUSTelephoneOutput.setText(split[3]);
            RCUSDateOutput.setText(split[4]);
        
        }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");  
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_RCUSSearchBTNActionPerformed

    /**
     * clear search in remove customer window
     * @param evt 
     */
    
    private void RCUSClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCUSClearBTNActionPerformed
        
        RCUSSearchInput.setText("");
        RCUSNameOutput.setText("");
        RCUSAddressOutput.setText("");
        RCUSTelephoneOutput.setText("");
        RCUSDateOutput.setText("");
        
    }//GEN-LAST:event_RCUSClearBTNActionPerformed

    /**
     * remove customer button
     * @param evt 
     */
    
    private void RCUSRemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCUSRemoveBTNActionPerformed
        
        try {

            ID = Integer.parseInt(RCUSSearchInput.getText());
            
            int choice = JOptionPane.showConfirmDialog(null, "Do you really Want To Delete This Customer");
            
            if(choice == 0) {
                
                name = customer.getCustomerNameByID(ID);
                Short[] cons = wh.getCustomersContainers(name);
                
                wh.removeCustomerManifest(cons, ID);
                
                customer.removeCustomer(ID);
  
            }
            
            populateMainTable();
            populateMainConLocTable();
            
        }catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");  
            
        } 
    }//GEN-LAST:event_RCUSRemoveBTNActionPerformed

    /**
     * searches individual container in inventory window
     * @param evt 
     */
    
    private void INSearchConBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INSearchConBTNActionPerformed
       
        try{
                        
            container = Short.parseShort(INContainerInput.getText());
            DefaultTableModel tm = (DefaultTableModel) INConLocTable.getModel();
            
            location = wh.getLocation(container);
            
            tm.setRowCount(0);
            
            tm.addRow(new Object[] {container, location});
            
            int getId = wh.getIdByContainer(container);
            
            String details = new String (customer.getCustomerDetailsByID(getId));            
            
            String[] split = details.split("/");
            
            INIDLabelOutput.setText(split[0]);
            INNameLabelOutput.setText(split[1]);
            
            address = tidyAddress(split[2]);
            
            INAddressLabelOutput.setText(address);
            
            INNumberLabelOutput.setText("0" + split[3]);
            INDateLabelOutput.setText(split[4]);
            
        }catch(NumberFormatException e) {
            
             JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
             
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_INSearchConBTNActionPerformed

    /**
     * searches by name or id in inventory window
     * @param evt 
     */
    
    private void INSearchNameIdBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INSearchNameIdBTNActionPerformed
        
        INContainerInput.setText("");  
       name = INNameInput.getText().toUpperCase();      
        
       if(!name.isEmpty()) {
            
           try {                              
               
                DefaultTableModel tm = (DefaultTableModel) INConLocTable.getModel();
                               
                String getId = new String(customer.getCustomerDetailsByName(name));
                
                String [] split = getId.split("/");
                
                ID = Integer.parseInt(split[0]); 
                
                INIDLabelOutput.setText(split[0]);
                INNameLabelOutput.setText(split[1]);
                
                address = tidyAddress(split[2]);
                
                INAddressLabelOutput.setText(address);
                
                INNumberLabelOutput.setText("0" + split[3]);
                INDateLabelOutput.setText(split[4]);
               
                Object[][] conLoc = wh.getConainersAndLocations(ID);
                
                tm.setRowCount(0);
                
                for(int i = 0; i < conLoc.length; i++) {

                    tm.addRow(new Object[]{conLoc[i][0], conLoc[i][1]});
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }else{
        
            try{
                
                DefaultTableModel tm = (DefaultTableModel) INConLocTable.getModel();

                ID = Integer.parseInt(INIDInput.getText());
                
                String details = new String(customer.getCustomerDetailsByID(ID));
                String[] split = details.split("/");
                
                INIDLabelOutput.setText(split[0]);
                INNameLabelOutput.setText(split[1]);
                
                address = tidyAddress(split[2]);
                
                INAddressLabelOutput.setText(address);
                
                INNumberLabelOutput.setText(split[3]);
                INDateLabelOutput.setText(split[4]);
                
                Object[][] conLoc = wh.getConainersAndLocations(ID);
                
                tm.setRowCount(0);
                
                for(int i = 0; i < conLoc.length; i++) {

                    tm.addRow(new Object[]{conLoc[i][0], conLoc[i][1]});
                }
                
                
            }catch(NumberFormatException e){
                
                  JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
                  
            } catch (SQLException ex) {
                
               Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
           }           
       }
    }//GEN-LAST:event_INSearchNameIdBTNActionPerformed

    /**
     * picks and opens up inventory of chosen container
     * @param evt 
     */
    
    private void INOpenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INOpenBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)INConLocTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)INInventoryTable.getModel();
        
        StringBuilder table = new StringBuilder();
        
        tm2.setRowCount(0);
        
        int row = INConLocTable.getSelectedRow();
        
        container = Short.parseShort(INConLocTable.getValueAt(row, 0).toString());
               
        File inventory = new File("C:\\ContainerStoreData\\Inventorys\\" + container + ".xlsx");
                
          try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inventory)); 
             XSSFWorkbook wb = new XSSFWorkbook(bis)){
            
            int skip = 0;           
          
            XSSFSheet sheet = wb.getSheetAt(0);
            
            Iterator<Row> itr = sheet.iterator();
                                    
            while(itr.hasNext()) {
                Row rows = itr.next();
                Iterator <Cell> cellIterator = rows.cellIterator();
                
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    
                    table.append(cell).append("/");
                    
                }
                
                if(skip>0){
                
                    String r = new String(table);
                    String[] split = r.split("/");
                    
                    if(split.length != 0) {
                        
                        tm2.addRow(new Object[] {split[0], split[1], split[2], split[3]});
                        
                    }
                   table.delete(0, table.length());
                   
                }else{
                    
                    table.delete(0, table.length());
                }   
                
                skip++;
            }
                        
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }//GEN-LAST:event_INOpenBTNActionPerformed

    /**
     * prints inventory table
     * @param evt 
     */
    
    private void INPrintBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INPrintBTNActionPerformed
        
        try {
            
            INInventoryTable.print();
            
        } catch (PrinterException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_INPrintBTNActionPerformed

    /**
     * search inventory search button
     * @param evt 
     */
    
    private void SISearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SISearchBTNActionPerformed
             
        DefaultTableModel tm = (DefaultTableModel)SIInventorySearchedTable.getModel();
        String Item = SIItemInput.getText().trim();
        String description = SIDescriptionInput.getText();
        
        if(Item.isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Item To Search");
            
        }else{
            
    //search without description            
            if(description.isEmpty()) {
                
                try{

                    name = SINameInput.getText().toUpperCase().trim();

                    Object[][] results = wh.searchAllContainers(Item, name);

                    tm.setRowCount(0);

                    for (Object[] result : results) {
                        
                        if(result[0].toString() != null) {
                            
                            tm.addRow(new Object[]{result[0], result[1], result[2], result[3], result[4], result[5]});
                        }                       
                    }

                } catch (SQLException | IOException ex) {

                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);

                }  
            }else{
                
    //search with description            
                try{

                    name = SINameInput.getText().toUpperCase().trim();

                    Object[][] results = wh.searchAllContainersWithDescription(Item, description, name);

                    tm.setRowCount(0);

                    for (Object[] result : results) {
                        
                        if(result[0].toString() != null) {
                        
                            tm.addRow(new Object[]{result[0], result[1], result[2], result[3], result[4], result[5]});
                        }
                    }

                } catch (SQLException | IOException ex) {

                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);

                }                  
            }
        }        
    }//GEN-LAST:event_SISearchBTNActionPerformed

    /**
     * button to clear entries in search inventory window
     * @param evt 
     */
    
    private void SIClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SIClearBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)SIInventorySearchedTable.getModel();
        
        tm.setRowCount(0);
        
        SIItemInput.setText("");
        SINameInput.setText("");
        SIDescriptionInput.setText("");
        
    }//GEN-LAST:event_SIClearBTNActionPerformed

    /**
     * search button for the edit inventory window
     * @param evt 
     */
    
    private void EISearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EISearchBTNActionPerformed
     
        if(edited == false) {
            
            DefaultTableModel tm = (DefaultTableModel)EIConLocTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)EIInventoryTable.getModel();
            
            tm2.setRowCount(0);
            
            EIOpenedContainerLabel.setText("");

            name = EINameInput.getText().toUpperCase().trim();

            if(!name.isEmpty()) {

                try{

                    ID = customer.getCustomerIdFromName(name);

                    String id = String.valueOf(ID);
                    EIIDInput.setText(id);

                    String details = new String(customer.getCustomerDetailsByName(name));
                    String[] split = details.split("/");

                    EINameOutput.setText(split[1]);
                    
                    address = tidyAddress(split[2]);
                    
                    EIAddressOutput.setText(address);
                    
                    EINumberOutput.setText(split[3]);
                    EIDateOutput.setText(split[4]);

                    Object[][] conLocs = wh.getConainersAndLocations(ID);

                    tm.setRowCount(0);

                    for(int i = 0; i < conLocs.length; i++) {

                        tm.addRow(new Object[] {conLocs[i][0], conLocs[i][1]});

                    }

                }catch (SQLException ex) {
                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{

                try{

                    ID = Integer.parseInt(EIIDInput.getText());

                    String details = new String(customer.getCustomerDetailsByID(ID));
                    String[] split = details.split("/");

                    EINameOutput.setText(split[1]);
                    
                    address = tidyAddress(split[2]);
                    
                    EIAddressOutput.setText(address);
                    
                    EINumberOutput.setText(split[3]);
                    EIDateOutput.setText(split[4]);

                    EINameInput.setText(String.valueOf(split[1]));

                    Object[][] conLocs = wh.getConainersAndLocations(ID);

                    tm.setRowCount(0);

                    for(int i = 0; i < conLocs.length; i++) {

                        tm.addRow(new Object[] {conLocs[i][0], conLocs[i][1]});

                    }

                }catch(NumberFormatException e){

                    JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");

                }catch (SQLException ex) {

                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }    
        }else{
            
            JOptionPane.showMessageDialog(null, "Please Update Container Before Changing Containers Or Undo Relocations");
            
        } 
    }//GEN-LAST:event_EISearchBTNActionPerformed

    /**
     * clears the inputs and labels in edit inventory window
     * @param evt 
     */
    
    private void EIClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIClearBTNActionPerformed
     
        if(edited == false) {
        
            DefaultTableModel tm = (DefaultTableModel)EIConLocTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)EIInventoryTable.getModel();

            tm.setRowCount(0);
            tm2.setRowCount(0);

            EINameOutput.setText("");
            EIAddressOutput.setText("");
            EINumberOutput.setText("");
            EIDateOutput.setText("");
            EINameInput.setText("");
            EIIDInput.setText("");
            EIOpenedContainerLabel.setText("");
            
        }else{
            
            JOptionPane.showMessageDialog(null, "Please Update Container Before Changing Containers Or Undo Relocations");
            
        }
    }//GEN-LAST:event_EIClearBTNActionPerformed

    /**
     * button to open the selected container in the edit inventory window
     * @param evt 
     */
    
    private void EIOpenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIOpenBTNActionPerformed
       
        if(edited == false) {
            
            try{
                     
                DefaultTableModel tm = (DefaultTableModel)EIConLocTable.getModel();
                DefaultTableModel tm2 = (DefaultTableModel)EIInventoryTable.getModel();
                
                tm2.setRowCount(0);

                int choice = EIConLocTable.getSelectedRow();

                container = Short.valueOf(EIConLocTable.getValueAt(choice, 0).toString());
                
                EIOpenedContainerLabel.setText(String.valueOf(container));

                Object[][] inventory = wh.findInventory(container);  

                for(Object[] r : inventory) {

                    tm2.addRow(new Object[]{r[0], r[1], r[2], r[3]});
                }

                open = true;

            } catch (IOException ex) {

                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);

            }
        }else{
            
            JOptionPane.showMessageDialog(null, "Please Update Container Before Changing Containers Or Undo Relocations");
            
        }
    }//GEN-LAST:event_EIOpenBTNActionPerformed

    /**
     * removes entire item row from the edit inventory table
     * @param evt 
     */
    
    private void EIRemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIRemoveBTNActionPerformed
             
        
        DefaultTableModel tm = (DefaultTableModel)EIInventoryTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)EIItemRemovedTable.getModel();
            
        try{

            action = "Removed Item";           

            int selected = EIInventoryTable.getSelectedRow();

            int itemNum = Integer.parseInt(tm.getValueAt(selected, 0).toString());
            Object item = tm.getValueAt(selected, 1);
            Object descr = tm.getValueAt(selected, 2);
            Object damage = tm.getValueAt(selected, 3);
            

            if(itemNum > 1){

                int numToTake = Integer.parseInt(JOptionPane.showInputDialog(null, "Please Enter The Amount Of Items To Remove"));


                if(!item.toString().isEmpty()) {   

                    deletedRows.add(new Object[]{numToTake, item, descr, damage});

                    tm2.addRow(new Object[]{numToTake, item, EIOpenedContainerLabel.getText()});

                    log.Log(action, "Item: " + item + " Number Of Items: " + numToTake + "From Container: " + EIOpenedContainerLabel.getText());

                    int total = itemNum - numToTake;

                    tm.setValueAt(total, selected, 0);
                }

            }else{

                deletedRows.add(new Object[]{itemNum, item, descr, damage});

                tm2.addRow(new Object[]{itemNum, item, EIOpenedContainerLabel.getText()});

                log.Log(action, "Item: " + item + " Number Of Items: " + itemNum + "From Container: " + EIOpenedContainerLabel.getText());

                tm.removeRow(selected);
            }
 
            edited = true;
            
        }catch(ArrayIndexOutOfBoundsException e) {
            
            JOptionPane.showMessageDialog(null, "Please Select A Row To Delete");
            
        }catch(NullPointerException ex) {
            
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(NumberFormatException ex){
            
            tm.removeRow(EIInventoryTable.getSelectedRow());
            
        }
    }//GEN-LAST:event_EIRemoveBTNActionPerformed

    /**
     * undoes removal of item in the edit inventory table
     * @param evt 
     */
    
    private void EIUndoRelocateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIUndoRelocateBTNActionPerformed
        
        try {
            
            DefaultTableModel tm = (DefaultTableModel)EIInventoryTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)EIItemRemovedTable.getModel();
            
            action = "Item Relocated";
            
            if(!deletedRows.empty()) {
                     
                tm.addRow(deletedRows.pop());
                edited = true;
                
                String item = String.valueOf(tm2.getValueAt(0, 1));
                
                log.Log(action, "Item: " + item + " Relocated Into Container:  " + EIOpenedContainerLabel.getText());
                
                tm2.removeRow(0); 
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EIUndoRelocateBTNActionPerformed

    /**
     * adds new row into the edit inventory table
     * @param evt 
     */
    
    private void EIAddNewBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIAddNewBTNActionPerformed
   
        if(open == true) {
            
            DefaultTableModel tm = (DefaultTableModel)EIInventoryTable.getModel();

            tm.addRow(new Object[] {"","","",""});
        }
    }//GEN-LAST:event_EIAddNewBTNActionPerformed

    /**
     * removes all the removed items table
     * @param evt 
     */
    
    private void EIEmptyTableBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIEmptyTableBTNActionPerformed
        
        int choice = JOptionPane.showConfirmDialog(null, "Do You Really Want To Empty The Item Hold Items Will Be Lost Forever", "Warning", JOptionPane.OK_CANCEL_OPTION);
        
        if(choice == 0 && edited == false) {
            
            try {
                
                DefaultTableModel tm = (DefaultTableModel)EIItemRemovedTable.getModel();
                
                action = "Item Left Warehouse";
                
                String[][] items = new String[tm.getRowCount()][2];
                
                for(int i = 0; i < items.length; i++) {
                    
                    items[i][0] = String.valueOf(tm.getValueAt(i, 0));
                    items[i][1] = String.valueOf(tm.getValueAt(i, 1));
                    items[i][2] = String.valueOf(tm.getValueAt(i, 1));
                }
                
                for(String[] act : items) {
                    
                    log.Log(action,"Number Of Items: " + act[0] + " Item:  " + act[1] + " From " + act[2]);
                    
                }
                
                for(int j = tm.getRowCount() -1; j >= 0; j--) {
                
                tm.removeRow(j);
                
                }

            } catch (SQLException ex) {
                
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else {
            
            JOptionPane.showMessageDialog(null, "Please Update Container Before Permanently Removing Item");
        }        
    }//GEN-LAST:event_EIEmptyTableBTNActionPerformed

    /**
    prints search inventory table
    **/
    
    private void SIPrintTableBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SIPrintTableBTNActionPerformed
        
        try {
            
            SIInventorySearchedTable.print();
            
                    } catch (PrinterException ex) {
                        
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SIPrintTableBTNActionPerformed

    /**
     * search button for the view log table
     * @param evt 
     */
    
    private void VLSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VLSearchBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)VLLogTable.getModel();
        String getDate = "";
                   
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

        getDate = dt.format(VLDateChooser.getSelectedDate().getTime());

        String getUser = String.valueOf(VLUserCombo.getSelectedItem());
        
        String getAction = String.valueOf(VLActionCombo.getSelectedItem().toString());

//get by user only

        if(VLRadioUser.isSelected() && !VLRadioAction.isSelected() && !VLRadioDate.isSelected()) { 

            try {

                Object[][] details = log.getAllLogDetailsByUser(getUser);

                tm.setRowCount(0);

                for(Object[] o : details) {

                    if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

// get by date only

        if(VLRadioDate.isSelected() && !VLRadioAction.isSelected() && !VLRadioUser.isSelected()) {

            try {

                Object[][] details = log.getAllLogDetailsByDate(getDate);

                tm.setRowCount(0);

                for(Object[] o : details) {

                     if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }

//get by action only

        if(VLRadioAction.isSelected() && !VLRadioDate.isSelected() && !VLRadioUser.isSelected()){

            try{

                Object[][] details = log.getAllLogDetailsByAction(getAction);

                 tm.setRowCount(0);

                for(Object[] o : details) {

                    if(o[0] != null){

                       tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                   }
               }

            } catch (SQLException ex) {

                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }  

        }

    //get by date and user

        if(VLRadioDate.isSelected() && VLRadioUser.isSelected() && !VLRadioAction.isSelected()) {

            try {

                Object[][] details = log.getAllLogDetailsByUserAndDate(getUser, getDate);

                tm.setRowCount(0);

                for(Object[] o : details) {

                     if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }

    //get by date and Action
        
        if(VLRadioDate.isSelected() && VLRadioAction.isSelected() && !VLRadioUser.isSelected()){
            
             try {

                Object[][] details = log.getAllLogDetailsByActionAndDate(getAction, getDate);

                tm.setRowCount(0);

                for(Object[] o : details) {

                     if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        
    //get action and user
        
        if(VLRadioUser.isSelected() && VLRadioAction.isSelected() && !VLRadioDate.isSelected()){
            
             try {

                Object[][] details = log.getAllLogDetailsByActionAndUser(getAction, getUser);

                tm.setRowCount(0);

                for(Object[] o : details) {

                     if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
     
    //get by user, date and action
    
    if(VLRadioDate.isSelected() && VLRadioAction.isSelected() && VLRadioAction.isSelected()){
            
             try {

                Object[][] details = log.getAllLogDetailsByActionAndDate(getAction, getDate);

                tm.setRowCount(0);

                for(Object[] o : details) {

                     if(o[0] != null){

                        tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }//GEN-LAST:event_VLSearchBTNActionPerformed

    /**
     * prints view log table
     * @param evt 
     */
    
    private void VLPrintBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VLPrintBTNActionPerformed
        
        try {
            
            VLLogTable.print();
            
        } catch (PrinterException ex) {
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_VLPrintBTNActionPerformed

    /**
     * search button to find customer and add containers to table
     * @param evt 
     */
    
    private void PLSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLSearchBTNActionPerformed
      
        DefaultTableModel tm = (DefaultTableModel)PLContainerTable.getModel();
        
        try{
            
            ID = Integer.parseInt(PLIDInput.getText().trim());
            
            Object[][] conLocs = wh.getConainersAndLocations(ID);
            
            tm.setRowCount(0);
            
            for(Object[] o : conLocs) {
                
                tm.addRow(new Object[] {o[0], o[1]});
            }
            
            name = customer.getCustomerNameByID(ID);
            
            PLNameLabel.setText(name);
            
        }catch(NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
            
        } catch (SQLException ex) {
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_PLSearchBTNActionPerformed

    /**
     * buttons to add all containers to the pick list table
     * @param evt 
     */
    
    private void PLAddAllContainersBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLAddAllContainersBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)PLContainerTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)PLPickListTable.getModel();
        
        Object[] cons = new Object[tm.getRowCount()];
        
        name = PLNameLabel.getText(); 

        for(int i = tm.getRowCount()-1; i>=0; i--){

            try {
                cons[i] = tm.getValueAt(i, 0);
                
                location = wh.getLocation(Short.parseShort(cons[i].toString()));
                
                tm2.addRow(new Object[] {name, cons[i], location, "ALL", "ALL"});
                
            } catch (SQLException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        tm.setRowCount(0);

    }//GEN-LAST:event_PLAddAllContainersBTNActionPerformed

    /**
     * button to add individual container to pick list
     * @param evt 
     */
    
    private void PLAddBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLAddBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)PLPickListTable.getModel();
        
        try{
            
            container = Short.parseShort(PLContainerInput.getText().trim());
            
            ID = customer.getIdFromContainer(container);
            
            name = customer.getCustomerNameByID(ID);
            
            location = wh.getLocation(container);
            
            tm.addRow(new Object[]{name, container, location, "ALL",  "ALL", ""});
            
            PLContainerInput.setText("");
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PLAddBTNActionPerformed

    /**
     * opens up the inventory of chosen container in pick list window
     * @param evt 
     */
    
    private void PLOpenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLOpenBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)PLContainerTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)PLItemTable.getModel();
        
        int selected = PLContainerTable.getSelectedRow();
        
        container = Short.parseShort(tm.getValueAt(selected, 0).toString());
        
        try{
            
            Object[][] inventory = wh.findInventory(container);
            
            tm2.setRowCount(0);
            
            for(Object[] o: inventory) {
                
                tm2.addRow(new Object[] {o[0],o[1],o[2],o[3]});
            }
            
            String chosen = String.valueOf(container);
            
            PLContainerChosenLabel.setText(chosen);
            
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(ArrayIndexOutOfBoundsException e){
            
            JOptionPane.showMessageDialog(null, "Please Select Container To Open");
        }      
    }//GEN-LAST:event_PLOpenBTNActionPerformed

    /**
     * button to add individual item into pick list
     * @param evt 
     */
    
    private void PLAddItemBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLAddItemBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)PLItemTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)PLPickListTable.getModel();

        try {

            int selected = PLItemTable.getSelectedRow();

            int num = Integer.parseInt(tm.getValueAt(selected, 0).toString());
            String item = String.valueOf(tm.getValueAt(selected, 1));
            String descr = String.valueOf(tm.getValueAt(selected, 2));
        
    //choose how many items to take from inventory    
    
            if(num > 1){
                
                int choiceNum = Integer.parseInt(JOptionPane.showInputDialog("Please Enter Number Of Items To Add To Pick List"));

                int finalNum = num - choiceNum;

                tm.setValueAt(finalNum, selected, 0);

                container = Short.parseShort(PLContainerChosenLabel.getText());

                name = customer.getNameByContainer(container);

                location = wh.getLocation(container);

                tm2.addRow(new Object[]{name, container, location, choiceNum, item, descr});
               
            }else{

                container = Short.parseShort(PLContainerChosenLabel.getText());

                name = customer.getNameByContainer(container);

                location = wh.getLocation(container);

                tm2.addRow(new Object[]{name, container, location, num, item, descr});

                tm.removeRow(selected);
            
            }

        } catch (SQLException ex) {

            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(ArrayIndexOutOfBoundsException e){
            
            JOptionPane.showMessageDialog(null, "Please Select Item To Add To The Pick List");
        }
    }//GEN-LAST:event_PLAddItemBTNActionPerformed

    /**
     * prints the pick list and creates an Excel file
     * @param evt 
     */
    
    private void PLCreateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLCreateBTNActionPerformed
       
        try {
            DefaultTableModel tm = (DefaultTableModel)PLPickListTable.getModel();
            Object[][]rows = new Object[tm.getRowCount()][tm.getColumnCount()];
            
            for(int i = 0; i < tm.getRowCount(); i++){
                
                for(int j = 0; j < tm.getColumnCount(); j++) {
                    
                    if(tm.getValueAt(i, j) != null) {
                        
                        rows[i][j] = tm.getValueAt(i, j);
                        
                    }else{
                        
                        rows[i][j] = "XXXX";
                    }
                }
                
            }
                        
            wh.savePickList(rows);
            
            PLPickListTable.print();
            
            checkFinalized();
            
        } catch (IOException | PrinterException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }//GEN-LAST:event_PLCreateBTNActionPerformed

    /**
     * removes selected row from pick list table
     * @param evt 
     */
    
    private void PLRemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLRemoveBTNActionPerformed

        try{
            DefaultTableModel tm = (DefaultTableModel)PLPickListTable.getModel();

            int selected = PLPickListTable.getSelectedRow();

            tm.removeRow(selected);
            
        }catch(ArrayIndexOutOfBoundsException e){
            
            JOptionPane.showMessageDialog(null, "Please Select Item To Remove");
        }
    }//GEN-LAST:event_PLRemoveBTNActionPerformed

    /**
     * adds a custom row for editing bespoke choice in pick list
     * @param evt 
     */
    
    private void PLAddCustomBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLAddCustomBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)PLPickListTable.getModel();
        
        String customName = "##CUSTOM##";
        
        String customContainer = JOptionPane.showInputDialog(null,"Please Enter Container","CUSTOM");
        
        String customLocation = JOptionPane.showInputDialog(null,"Please Enter Location","CUSTOM");
        
        String customItemNumber = JOptionPane.showInputDialog(null,"Please Enter Number Of Items","CUSTOM");
        
        String customItem = JOptionPane.showInputDialog(null,"Please Enter Item","CUSTOM");
         
            tm.addRow(new Object[]{customName, customContainer, customLocation, customItemNumber, customItem});
                
    }//GEN-LAST:event_PLAddCustomBTNActionPerformed

    /**
     * opens up the chosen pick list
     * @param evt 
     */
    
    private void FLOpenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FLOpenBTNActionPerformed
       
        try {
            
            DefaultTableModel tm = (DefaultTableModel)FLPickListTable.getModel();
            
            String list = FLComboBox.getSelectedItem().toString();
             
            Object[][] picked = wh.getPickList(list);
            
            tm.setRowCount(0);
            
            for(Object[] o : picked){
                
                tm.addRow(new Object[]{o[0],o[1],o[2],o[3],o[4],o[5]});
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_FLOpenBTNActionPerformed

    /**
     * removes item or container that was not removed from warehouse
     * @param evt 
     */
    
    private void FLNoActionTakenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FLNoActionTakenBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)FLPickListTable.getModel();
        
        int selected = FLPickListTable.getSelectedRow();
        
        tm.removeRow(selected);
        
    }//GEN-LAST:event_FLNoActionTakenBTNActionPerformed

    /**
     * finalizes contents and removes items and or containers from databases and files
     * @param evt 
     */
    
    private void FLFinalizeBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FLFinalizeBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)FLPickListTable.getModel();
        
        String file = FLComboBox.getSelectedItem().toString();
       
        int rows = tm.getRowCount();
        int cols = tm.getColumnCount();
        Object[][] list = new Object[rows][cols];
        
        for(int i = 0; i < rows; i++) {
            
            for(int j = 0; j < cols; j++){
            
                list[i][j] = tm.getValueAt(i, j).toString();
                
            }
        }

        for(Object[] o : list){
            
            try {

                if(!o[0].toString().matches("##CUSTOM##")){  //checking if the entry was custom
                    
                    container = Short.parseShort(o[1].toString());
                    String item = o[4].toString();
                    String description = o[5].toString(); //notes
                    
                    String con = o[1].toString();
                    
                    if(item.matches("ALL")){

                        wh.emptyContainer(container);
                        
                    }else{
                        
                        int num = Integer.parseInt(o[3].toString());
                        
                        wh.removeItem(item, num, con, description);

                    }
                }
                
                wh.setToFinalized(file);
                
                FLComboBox.removeItem(file);
                
                tm.setRowCount(0);
                
                checkFinalized();
                
                populateMainTable();
                populateMainConLocTable();
                
            } catch (SQLException | IOException ex) {
                Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }//GEN-LAST:event_FLFinalizeBTNActionPerformed

   
    /**
     * First login window to add an admin
     * @param evt 
     */
    
    private void LogInFirstEnterBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInFirstEnterBTNActionPerformed
                        
        String username = LogInUserInput.getText().trim();
        char[] password = LogInPasswordInput.getPassword();
        
        char[] reType = LogInReEnterPasswordInput.getPassword();
        
        if(Arrays.equals(reType, password)){
            
            if(!username.isEmpty() && username.length() <= 10){
            
                try {

                    li.addEntry(username, password, "ADMIN");
                    
                    this.setVisible(true);
                    
                    user = username;
                    
                    userNameLabel.setText(user);
                    
                    LogInWindowFirst.setVisible(false);
                    
                } catch (NoSuchProviderException | NoSuchAlgorithmException | SQLException ex) {
                    
                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Username Cannot Be Blank And Must Be Not Have a Length Greater Than 10");

            } 
        }else{
            
            JOptionPane.showMessageDialog(null, "Password Does Not Match!");            
    
        }
        
    }//GEN-LAST:event_LogInFirstEnterBTNActionPerformed

    /**
     * Log In Main enter button
     * @param evt 
     */
    
    private void LIEnterMainBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LIEnterMainBTNActionPerformed
        
        String username = LogInUserInputMain.getText().trim();
        
        try {
            
            char[] password = LogInPasswordInputMain.getPassword();
             
            Map<Boolean, String> identity = li.identify(username, password);
            
            if(identity.containsKey(Boolean.TRUE)){
                
                if(identity.get(Boolean.TRUE).matches("ADMIN")){
                    
                     this.setVisible(true);
                                        
                     user = username;
                    
                     userNameLabel.setText(user);
                                         
                     LogInWindowMain.setVisible(false);
                     
                     this.ADMINPanel.setVisible(true);
                     
                }else{
                    
                    this.setVisible(true);
                       
                    this.ADMINPanel.setVisible(false);
                    
                    user = username;
                    
                    userNameLabel.setText(user);
                                       
                    LogInWindowMain.setVisible(false);
                        
                }
                
            }else{
                
                LIFailedLabel.setText("Username Or Password Incorrect");
                
                       try {
                    
                    log.Log("Failed Login", username + " Failed To Enter The System");
                    
                } catch (SQLException ex) {
                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
                    
                    
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
                 
            LogInUserInputMain.setText("");
            LogInPasswordInputMain.setText("");
        }
    }//GEN-LAST:event_LIEnterMainBTNActionPerformed

    /**
     * the add user enter button
     * @param evt 
     */
    
    private void AUEnterBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AUEnterBTNActionPerformed
        
        String username = AUUserNameInput.getText().trim();
        char[] password = AUPasswordInput.getPassword();
        
        char[] reType = AUReEnterPasswordInput.getPassword();
        
        String clearance = String.valueOf(AUComboBox.getSelectedItem()).toUpperCase();
        
        if(Arrays.equals(reType, password)){
            
            if(!username.isEmpty() && username.length() <= 10){
            
                int choice = JOptionPane.showConfirmDialog(null, "Are These Details Correct? \n\n Username: '" + username + "' \n Clearance: '" + clearance, "Confirm Entry", JOptionPane.OK_CANCEL_OPTION);
                
                if (choice == 0) {
                
                    try {

                        li.addEntry(username, password, clearance);

                        this.setVisible(true);

                        AddUserWindow.setVisible(false);
                        
                        JOptionPane.showMessageDialog(null, "New User Added: " + username);
                        
                        log.Log("User Added", username + " Added To System"); 
                        

                    } catch (NoSuchProviderException | NoSuchAlgorithmException | SQLException ex) {

                        Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }                   
                }
                
            }else{
                
                JOptionPane.showMessageDialog(null, "Username Cannot Be Blank And Must Be Not Have a Length Greater Than 10");
                
            } 
        }else{
            
            JOptionPane.showMessageDialog(null, "Passwords Do Not Match!");
    
        }
    }//GEN-LAST:event_AUEnterBTNActionPerformed

    /**
     * resets the tables in the main page
     * @param evt 
     */
    
    private void MainResetTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainResetTableActionPerformed
            
        try {
            
            DefaultTableModel tm = (DefaultTableModel)MainInventoryTable.getModel();
            
            tm.setRowCount(0);
            
            populateMainTable();
            populateMainConLocTable();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }//GEN-LAST:event_MainResetTableActionPerformed

    /**
     * adds containers and locations onto the main conloc table
     * @param evt 
     */
    
    private void MainExploreBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainExploreBTNActionPerformed

        try {

            DefaultTableModel tm = (DefaultTableModel)MainCustomerTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)MainConLocTable.getModel();

            int selected = MainCustomerTable.getSelectedRow();

            ID = Integer.parseInt(tm.getValueAt(selected, 0).toString());

            tm2.setRowCount(0);

            Object[][]conLocs = wh.getConainersAndLocations(ID);

            for(Object[] o : conLocs){

                tm2.addRow(new Object[] {o[0], o[1]});

            }

        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(ArrayIndexOutOfBoundsException e){
            
            JOptionPane.showMessageDialog(null, "Please Select Customer");
        }
    }//GEN-LAST:event_MainExploreBTNActionPerformed

    /**
     * opens up the add user window 
     * @param evt 
     */
    
    private void MainAddUserBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainAddUserBTNActionPerformed

        AddUserWindow.setVisible(true);
        AddUserWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_MainAddUserBTNActionPerformed

    /**
     * log out button
     * @param evt 
     */
    
    private void LogOutBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutBTNActionPerformed

        this.setVisible(false);
        LIFailedLabel.setText("");

        LogInWindowMain.setVisible(true);
        LogInWindowMain.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_LogOutBTNActionPerformed

    /**
     * opens up the inventory of chosen container in main conloc table in main inventory table
     * @param evt 
     */
    
    private void MainOpenBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainOpenBTNActionPerformed

        try {

            DefaultTableModel tm = (DefaultTableModel)MainConLocTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)MainInventoryTable.getModel();

            int selected = MainConLocTable.getSelectedRow();

            container = Short.parseShort(tm.getValueAt(selected, 0).toString());

            Object[][] inventory = wh.findInventory(container);

            tm2.setRowCount(0);

            for(Object[] o: inventory){

                tm2.addRow(new Object[]{o[0],o[1],o[2],o[3]});
            }

        } catch (IOException ex) {

            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(ArrayIndexOutOfBoundsException e){
            
            JOptionPane.showMessageDialog(null, "Please Select Container To Open");
        }       
    }//GEN-LAST:event_MainOpenBTNActionPerformed

    /**
     * opens up the finalize pick list window and populates combo box
     * @param evt 
     */
    
    private void FinalizePickListBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizePickListBTNActionPerformed

        try {

            FinalizePickListWindow.setVisible(true);
            FinalizePickListWindow.setLocationRelativeTo(null);

            ArrayList<String> lists = new ArrayList<>();

            lists = wh.getLists();

            for(String l : lists) {

                FLComboBox.addItem(l);
            }
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FinalizePickListBTNActionPerformed

    /**
     * opens the pick list window
     * @param evt 
     */
    
    private void PickListBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickListBTNActionPerformed

        try {
            
            PickListWindow.setVisible(true);
            PickListWindow.setLocationRelativeTo(null);

            DefaultTableModel tm = (DefaultTableModel)PLPickListTable.getModel();

            Object[][] picked = wh.addToPickList();

            tm.setRowCount(0);

            for(Object[] p : picked){

                tm.addRow(new Object[]{p[0],p[1],p[2],p[3],p[4]});

            }
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PickListBTNActionPerformed

    /**
     * opens up user log window and populates table
     * @param evt 
     */
    
    private void ViewLogWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewLogWindowBTNActionPerformed

        ViewLogWindow.setVisible(true);
        ViewLogWindow.setLocationRelativeTo(null);

        DefaultTableModel tm = (DefaultTableModel)VLLogTable.getModel();

        tm.setRowCount(0);

        try{
            
            String[] users = li.getUsers();
            
            for(String u : users){
                
                VLUserCombo.addItem(u);
            }
            
            log = new Log();

            Object[][] logs = log.getAllLogDetails();

            for(Object[] l : logs) {

                tm.addRow(new Object[]{l[0], l[1], l[2], l[3]});
            }
            
    //adds all actions taken to the combo box
    
        VLActionCombo.removeAllItems();
    
        TreeSet<String> actions = log.getActions();
        
        actions.forEach(s -> {
            VLActionCombo.addItem(s);
            });
        

        } catch (SQLException ex) {

            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ViewLogWindowBTNActionPerformed

    /**
     * opens the edit inventory's window
     * @param evt 
     */
    
    private void EditInventorysBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditInventorysBTNActionPerformed

        EditInventorysWindow.setVisible(true);
        EditInventorysWindow.setLocationRelativeTo(null);

        open = false;
        
    }//GEN-LAST:event_EditInventorysBTNActionPerformed

    /**
     * opens search inventory window
     * @param evt 
     */
    
    private void SearchInventorysBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchInventorysBTNActionPerformed

        SearchInventoryWindow.setVisible(true);
        SearchInventoryWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_SearchInventorysBTNActionPerformed

    /**
     * opens up inventory window
     * @param evt 
     */
    
    private void InventoryListBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventoryListBTNActionPerformed

        InventoryWindow.setVisible(true);
        InventoryWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_InventoryListBTNActionPerformed

    /**
     * opens remove customer window
     * @param evt 
     */
    
    private void RemoveCustomerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveCustomerWindowBTNActionPerformed

        RemoveCustomerWindow.setVisible(true);
        RemoveCustomerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_RemoveCustomerWindowBTNActionPerformed

    /**
     * opens remove container window
     * @param evt 
     */
    
    private void EmptyContainerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmptyContainerWindowBTNActionPerformed

        EmptyContainerWindow.setVisible(true);
        EmptyContainerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_EmptyContainerWindowBTNActionPerformed

    /**
     * opens move container window
     * @param evt 
     */
    
    private void MoveContainerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveContainerWindowBTNActionPerformed

        MoveContainerWindow.setVisible(true);
        MoveContainerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_MoveContainerWindowBTNActionPerformed

    /**
     * opens search container window
     * @param evt 
     */
    
    private void SearchContainerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchContainerWindowBTNActionPerformed

        SearchContainerWindow.setVisible(true);
        SearchContainerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_SearchContainerWindowBTNActionPerformed

    /**
     * button to open AddContainerToExistingCustomer window
     * @param evt 
     */
    
    private void AddContainerToExistingCustomerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddContainerToExistingCustomerBTNActionPerformed

        AddContainerToExistingCustomer.setVisible(true);
        AddContainerToExistingCustomer.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_AddContainerToExistingCustomerBTNActionPerformed

    /**
     * Button to open the search customer window
     * @param evt 
     */
    
    private void SearchCustomerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchCustomerWindowBTNActionPerformed

        SearchCustomerWindow.setVisible(true);
        SearchCustomerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_SearchCustomerWindowBTNActionPerformed

    /**
     * opens the edit customer window
     * @param evt 
     */
    
    private void EditCustomerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditCustomerWindowBTNActionPerformed

        EditCustomerWindow.setVisible(true);
        EditCustomerWindow.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_EditCustomerWindowBTNActionPerformed

    /**
     * Opens the Add New Customer Window
     * @param evt 
     */
    
    private void AddNewCustomerWindowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewCustomerWindowBTNActionPerformed

        AddNewCustomerWindow.setVisible(true);
        AddNewCustomerWindow.setLocationRelativeTo(null);
             
    }//GEN-LAST:event_AddNewCustomerWindowBTNActionPerformed

    /**
     * opens analytic window and populates data
     * @param evt 
     */
    
    private void AnalyticsBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalyticsBTNActionPerformed
       
        try {
            
            AnalyticWindow.setVisible(true);
            AnalyticWindow.setLocationRelativeTo(null);
            
           
            int NumOfCusLength = wh.getIDs().length;
            String noc = String.valueOf(NumOfCusLength);
            
                ATNumberOfCustomersOutput.setText(noc);
            
           
            int NumOfConsLength = wh.getAllContainers().length;
            String noCon = String.valueOf(NumOfConsLength);  
            
                ATNumberOfContainersOutput.setText(noCon);
                
            int emptys = wh.getNumOfEmptyContainers();
            String emps = String.valueOf(emptys);
            
                ATNumberOfEmptyContainersOutput.setText(emps);
                    
        }catch(SQLException ex) {
                        
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }//GEN-LAST:event_AnalyticsBTNActionPerformed

    /**
     * Searches by specified target in analytics window and populates the table
     * @param evt 
     */
    
    private void ATSearchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATSearchBTNActionPerformed
       
        DefaultTableModel tm = (DefaultTableModel)AnalyticTable.getModel();
        
        String command = String.valueOf(ATComboBox.getSelectedItem());

        tm.setRowCount(0);

        if(command.matches("Longest Customer")) {
             
            try{
                 
                Object[][] longest = log.getLongestCustomer();
                 
                for(Object[] o : longest){
                    
                    tm.addRow(new Object[]{o[0], o[1], o[2], o[3]});
                }
                 
                 
             }catch(SQLException ex) {
                 
                 Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
             } 
        }  
        if(command.matches("Most Containers")){
             
            try {
                 
                Object[][] most = log.getMostContainers();
                 
                for(Object[] o : most){
                    
                    tm.addRow(new Object[]{o[0], o[1], o[2], o[3]});
                }

            }catch(SQLException ex) {
                
                 Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            }                        
        }       
    }//GEN-LAST:event_ATSearchBTNActionPerformed

    /**
     * Search by container search button in SEARCH CUSTOMER WINDOW
     * @param evt 
     */
    
    private void SCSearchContainerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCSearchContainerBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)SCConLocTable.getModel();
        
        try {
            container = Short.parseShort(SCContainerInput.getText());
            
            ID = customer.getIdFromContainer(container);
            
            String details = customer.getCustomerDetailsByID(ID).toString();
            
            String[] split = details.split("/");
            
            SCIDOutput.setText(split[0]);
            SCNameOutput.setText(split[1]);
            
            String[] tidyAddress = split[2].split(",");
               
            address = "";

            for(String s : tidyAddress){

                address += s + "\n";
            }
            
            SCAddressOutput.setText(address.trim());
            SCTelephoneOutput.setText(split[3]);

            ID = Integer.parseInt(split[0]);

            Object[][] conLocs = wh.getConainersAndLocations(ID);

            tm.setRowCount(0);

            for(int i = 0; i < conLocs.length; i++) {

                tm.addRow(new Object[]{conLocs[i][0], conLocs[i][1]});
            }
            
            
        } catch (SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SCSearchContainerBTNActionPerformed

    /**
     * opens the chosen container in the pick list window
     * @param evt 
     */
    
    private void PLOpenContainerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PLOpenContainerBTNActionPerformed
       
        try {
            
            DefaultTableModel tm = (DefaultTableModel)PLItemTable.getModel();
            DefaultTableModel tm2 = (DefaultTableModel)PLContainerTable.getModel();
            
            tm2.setRowCount(0);
            
            tm.setRowCount(0);
            
            name = customer.getNameByContainer(container);
            
            PLNameLabel.setText(name);
            
            container = Short.parseShort(PLContainerInput.getText());
            
            Object[][] inventory = wh.findInventory(container);
            
            for(Object[] o : inventory){
                
                tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});
            }
            
            PLContainerChosenLabel.setText(PLContainerInput.getText());
            
        } catch (IOException | SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }catch(NumberFormatException e){
            
            JOptionPane.showMessageDialog(null, "Please Enter Legal Digits");
            
        }
    }//GEN-LAST:event_PLOpenContainerBTNActionPerformed

    /**
     * UPDATES THE TABLE IN EDIT INVENTORY WINDOW
     * HAD TO RE DO AS LOST BUTTON;
     * @param evt 
     */
    
    private void EIUpdateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EIUpdateBTNActionPerformed
        
        DefaultTableModel tm = (DefaultTableModel)EIInventoryTable.getModel();
        DefaultTableModel tm2 = (DefaultTableModel)EIConLocTable.getModel();
        
        int choice = EIConLocTable.getSelectedRow();

        container = Short.valueOf(EIOpenedContainerLabel.getText());
        
        int rows = tm.getRowCount();
        int cols = tm.getColumnCount();
        
        Object[][] newRows = new Object[rows][cols];
           
        for(int i = 0; i < rows; i++) {
            
            for(int j = 0; j < cols; j++) {                
                    
                if(tm.getValueAt(i, j) != null){

                    newRows[i][j] = tm.getValueAt(i, j);
                }
            }
        }

        try{
            
            wh.updateInventory(container, newRows);
            
            edited = false;

        }catch(IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }//GEN-LAST:event_EIUpdateBTNActionPerformed

    private void AUReEnterPasswordInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AUReEnterPasswordInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AUReEnterPasswordInputActionPerformed

    /**
     * button to reset the search results in the view log window
     * @param evt 
     */
    
    private void VLResetBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VLResetBTNActionPerformed
        
        try {
            
            VLRadioDate.setSelected(false);
            VLRadioAction.setSelected(false);
            VLRadioUser.setSelected(false);
            
            DefaultTableModel tm = (DefaultTableModel)VLLogTable.getModel();
            
            tm.setRowCount(0);
            
            Object[][] details = log.getAllLogDetails();
            
            for(Object[] o : details){
                
                tm.addRow(new Object[] {o[0],o[1],o[2],o[3]});
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }//GEN-LAST:event_VLResetBTNActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            
            java.util.logging.Logger.getLogger(StoreMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    
                    createDirectories();
                    
                    LogIn li = new LogIn();
                    boolean admin = li.checkAdmin();
                        
                    if(admin){
                         
                        StoreMain sm = new StoreMain();
                        
                        sm.LogInWindowMain.setVisible(true);
                        sm.LogInWindowMain.setLocationRelativeTo(null);
                        
                    }else{
                        
                        StoreMain sm = new StoreMain();
                        
                        sm.LogInWindowFirst.setVisible(true);
                        sm.LogInWindowFirst.setLocationRelativeTo(null);
                        
                    }  
                    
                } catch (SQLException ex) {
                    Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ACAddBTN;
    private javax.swing.JLabel ACBackgroudLabel;
    private javax.swing.JScrollPane ACConLocScroll;
    private javax.swing.JTable ACConLocTable;
    private javax.swing.JTextField ACContainerInput;
    private javax.swing.JLabel ACContainerLabel;
    private javax.swing.JLabel ACCustomerLabel;
    private javax.swing.JLabel ACIDLabel;
    private javax.swing.JLabel ACIDOutput;
    private javax.swing.JLabel ACIDOutputLabel;
    private javax.swing.JTextField ACIDSearchInput;
    private javax.swing.JTextField ACLocationInput;
    private javax.swing.JLabel ACLocationLabel;
    private javax.swing.JLabel ACNameLabel;
    private javax.swing.JLabel ACNameOutput;
    private javax.swing.JLabel ACNameOutputLabel;
    private javax.swing.JTextField ACNameSearchInput;
    private javax.swing.JButton ACRemoveBTN;
    private javax.swing.JButton ACSearchBTN;
    private javax.swing.JButton ACUpdateBTN;
    private javax.swing.JPanel ADMINPanel;
    private javax.swing.JButton ANCAddConLocBTN;
    private javax.swing.JTextArea ANCAddressInput;
    private javax.swing.JLabel ANCAddressLabel;
    private javax.swing.JLabel ANCBackgroundLabel;
    private javax.swing.JButton ANCClearBTN;
    private javax.swing.JScrollPane ANCConLocScroll;
    private javax.swing.JTable ANCConLocTable;
    private javax.swing.JButton ANCConfirmBTN;
    private javax.swing.JTextField ANCContainerInput;
    private javax.swing.JLabel ANCContainerLabel;
    private javax.swing.JTextField ANCLocationInput;
    private javax.swing.JLabel ANCLocationLabel;
    private javax.swing.JTextField ANCNameInput;
    private javax.swing.JLabel ANCNameLabel;
    private javax.swing.JPanel ANCPanel;
    private javax.swing.JButton ANCRemoveBTN;
    private javax.swing.JTextField ANCTelePhoneNumberInput;
    private javax.swing.JLabel ANCTelePhoneNumberLabel;
    private javax.swing.JLabel ANLBackgroundLabel;
    private javax.swing.JComboBox<String> ATComboBox;
    private javax.swing.JLabel ATNumberOfContainersLabel;
    private javax.swing.JLabel ATNumberOfContainersOutput;
    private javax.swing.JLabel ATNumberOfCustomersLabel;
    private javax.swing.JLabel ATNumberOfCustomersOutput;
    private javax.swing.JLabel ATNumberOfEmptyContainersLabel;
    private javax.swing.JLabel ATNumberOfEmptyContainersOutput;
    private javax.swing.JButton ATSearchBTN;
    private javax.swing.JLabel AUBackgroundLabel;
    private javax.swing.JLabel AUClearanceLabel;
    private javax.swing.JComboBox<String> AUComboBox;
    private javax.swing.JButton AUEnterBTN;
    private javax.swing.JLabel AUMainLabel;
    private javax.swing.JPasswordField AUPasswordInput;
    private javax.swing.JLabel AUPasswordLabel;
    private javax.swing.JPasswordField AUReEnterPasswordInput;
    private javax.swing.JTextField AUUserNameInput;
    private javax.swing.JLabel AUUserNameLabel;
    private javax.swing.JLabel AUreEnterPasswordLabel;
    private javax.swing.JFrame AddContainerToExistingCustomer;
    private javax.swing.JButton AddContainerToExistingCustomerBTN;
    private javax.swing.JFrame AddNewCustomerWindow;
    private javax.swing.JButton AddNewCustomerWindowBTN;
    private javax.swing.JFrame AddUserWindow;
    private javax.swing.JScrollPane AnalyticScroll;
    private javax.swing.JTable AnalyticTable;
    private javax.swing.JFrame AnalyticWindow;
    private javax.swing.JButton AnalyticsBTN;
    private javax.swing.JTextArea ECAddressInput;
    private javax.swing.JTextArea ECAddressOutput;
    private javax.swing.JLabel ECAdressLabel;
    private javax.swing.JLabel ECBackgroudLabel;
    private javax.swing.JButton ECClearBTN;
    private javax.swing.JTextField ECContainerInput;
    private javax.swing.JLabel ECContainerLabel;
    private javax.swing.JLabel ECDateAddedLabel;
    private javax.swing.JLabel ECDateOutput;
    private javax.swing.JButton ECEmptyBTN;
    private javax.swing.JTextField ECIDSearchInput;
    private javax.swing.JLabel ECIDSearchLabel;
    private javax.swing.JTextField ECNameInput;
    private javax.swing.JLabel ECNameLabel;
    private javax.swing.JLabel ECNameOutput;
    private javax.swing.JTextField ECNameSearchInput;
    private javax.swing.JLabel ECNameSearchLabel;
    private javax.swing.JButton ECSearchBTN;
    private javax.swing.JTextField ECTelephoneInput;
    private javax.swing.JLabel ECTelephoneLabel;
    private javax.swing.JLabel ECTelephoneOutput;
    private javax.swing.JButton ECUpdateBTN;
    private javax.swing.JButton EIAddNewBTN;
    private javax.swing.JTextArea EIAddressOutput;
    private javax.swing.JButton EIClearBTN;
    private javax.swing.JScrollPane EIConLocScroll;
    private javax.swing.JTable EIConLocTable;
    private javax.swing.JLabel EIDateOutput;
    private javax.swing.JButton EIEmptyTableBTN;
    private javax.swing.JTextField EIIDInput;
    private javax.swing.JLabel EIIDLabel;
    private javax.swing.JScrollPane EIInventoryScroll;
    private javax.swing.JTable EIInventoryTable;
    private javax.swing.JScrollPane EIItemRemovedScroll;
    private javax.swing.JTable EIItemRemovedTable;
    private javax.swing.JTextField EINameInput;
    private javax.swing.JLabel EINameLabel;
    private javax.swing.JLabel EINameOutput;
    private javax.swing.JLabel EINumberOutput;
    private javax.swing.JButton EIOpenBTN;
    private javax.swing.JLabel EIOpenedContainerLabel;
    private javax.swing.JButton EIRemoveBTN;
    private javax.swing.JButton EISearchBTN;
    private javax.swing.JButton EIUndoRelocateBTN;
    private javax.swing.JButton EIUpdateBTN;
    private javax.swing.JFrame EditCustomerWindow;
    private javax.swing.JButton EditCustomerWindowBTN;
    private javax.swing.JButton EditInventorysBTN;
    private javax.swing.JFrame EditInventorysWindow;
    private javax.swing.JFrame EmptyContainerWindow;
    private javax.swing.JButton EmptyContainerWindowBTN;
    private javax.swing.JComboBox<String> FLComboBox;
    private javax.swing.JButton FLFinalizeBTN;
    private javax.swing.JButton FLNoActionTakenBTN;
    private javax.swing.JButton FLOpenBTN;
    private javax.swing.JLabel FLPickListLable;
    private javax.swing.JScrollPane FLPickListScroll;
    private javax.swing.JTable FLPickListTable;
    private javax.swing.JLabel FPBackgroundLabel;
    private javax.swing.JButton FinalizePickListBTN;
    private javax.swing.JFrame FinalizePickListWindow;
    private javax.swing.JTextArea INAddressLabelOutput;
    private javax.swing.JLabel INBackgroundLabel;
    private javax.swing.JScrollPane INConLocScroll;
    private javax.swing.JTable INConLocTable;
    private javax.swing.JTextField INContainerInput;
    private javax.swing.JLabel INContainerLabel;
    private javax.swing.JLabel INDateLabelOutput;
    private javax.swing.JTextField INIDInput;
    private javax.swing.JLabel INIDLabel;
    private javax.swing.JLabel INIDLabelOutput;
    private javax.swing.JScrollPane INInventoryScroll;
    private javax.swing.JTable INInventoryTable;
    private javax.swing.JTextField INNameInput;
    private javax.swing.JLabel INNameLabel;
    private javax.swing.JLabel INNameLabelOutput;
    private javax.swing.JLabel INNumberLabelOutput;
    private javax.swing.JButton INOpenBTN;
    private javax.swing.JButton INPrintBTN;
    private javax.swing.JButton INSearchConBTN;
    private javax.swing.JButton INSearchNameIdBTN;
    private javax.swing.JButton InventoryListBTN;
    private javax.swing.JFrame InventoryWindow;
    private javax.swing.JLabel LIEnterLabel;
    private javax.swing.JButton LIEnterMainBTN;
    private javax.swing.JLabel LIFBackgroundLabel;
    private javax.swing.JLabel LIFailedLabel;
    private javax.swing.JLabel LIMBackgroundLabel;
    private javax.swing.JButton LogInFirstEnterBTN;
    private javax.swing.JPasswordField LogInPasswordInput;
    private javax.swing.JPasswordField LogInPasswordInputMain;
    private javax.swing.JLabel LogInPasswordLabel;
    private javax.swing.JLabel LogInPasswordLabel1;
    private javax.swing.JLabel LogInPasswordLabelMain;
    private javax.swing.JPasswordField LogInReEnterPasswordInput;
    private javax.swing.JTextField LogInUserInput;
    private javax.swing.JTextField LogInUserInputMain;
    private javax.swing.JLabel LogInUserLabel;
    private javax.swing.JLabel LogInUserLabelMain;
    private javax.swing.JFrame LogInWindowFirst;
    private javax.swing.JFrame LogInWindowMain;
    private javax.swing.JButton LogOutBTN;
    private javax.swing.JLabel MCBackgroundLabel;
    private javax.swing.JTextField MCContainerInput;
    private javax.swing.JLabel MCContainerLabel;
    private javax.swing.JButton MCMoveBTN;
    private javax.swing.JTextField MCMoveToInput;
    private javax.swing.JLabel MCMoveToLabel;
    private javax.swing.JButton MainAddUserBTN;
    private javax.swing.JScrollPane MainConLocScroll;
    private javax.swing.JTable MainConLocTable;
    private javax.swing.JLabel MainContainersLabel;
    private javax.swing.JScrollPane MainCustomerScroll;
    private javax.swing.JTable MainCustomerTable;
    private javax.swing.JLabel MainCustomersLabel;
    private javax.swing.JButton MainExploreBTN;
    private javax.swing.JLabel MainInventoryLabel;
    private javax.swing.JScrollPane MainInventoryScroll;
    private javax.swing.JTable MainInventoryTable;
    private javax.swing.JLabel MainLable;
    private javax.swing.JButton MainOpenBTN;
    private javax.swing.JButton MainResetTable;
    private javax.swing.JFrame MoveContainerWindow;
    private javax.swing.JButton MoveContainerWindowBTN;
    private javax.swing.JButton PLAddAllContainersBTN;
    private javax.swing.JButton PLAddBTN;
    private javax.swing.JButton PLAddCustomBTN;
    private javax.swing.JButton PLAddItemBTN;
    private javax.swing.JLabel PLBackgroundLabel;
    private javax.swing.JButton PLClearBTN;
    private javax.swing.JLabel PLContainerChosenLabel;
    private javax.swing.JTextField PLContainerInput;
    private javax.swing.JLabel PLContainerLabel;
    private javax.swing.JScrollPane PLContainerScroll;
    private javax.swing.JTable PLContainerTable;
    private javax.swing.JButton PLCreateBTN;
    private javax.swing.JTextField PLIDInput;
    private javax.swing.JLabel PLIDLable;
    private javax.swing.JScrollPane PLItemScroll;
    private javax.swing.JTable PLItemTable;
    private javax.swing.JLabel PLNameLabel;
    private javax.swing.JButton PLOpenBTN;
    private javax.swing.JButton PLOpenContainerBTN;
    private javax.swing.JScrollPane PLPickListScroll;
    private javax.swing.JTable PLPickListTable;
    private javax.swing.JButton PLRemoveBTN;
    private javax.swing.JButton PLSearchBTN;
    private javax.swing.JButton PickListBTN;
    private javax.swing.JFrame PickListWindow;
    private javax.swing.JLabel RCBackgroundLabel;
    private javax.swing.JTextArea RCUSAddressOutput;
    private javax.swing.JLabel RCUSAdressLabel;
    private javax.swing.JButton RCUSClearBTN;
    private javax.swing.JLabel RCUSDateAddedLabel;
    private javax.swing.JLabel RCUSDateOutput;
    private javax.swing.JLabel RCUSNameLabel;
    private javax.swing.JLabel RCUSNameOutput;
    private javax.swing.JButton RCUSRemoveBTN;
    private javax.swing.JButton RCUSSearchBTN;
    private javax.swing.JTextField RCUSSearchInput;
    private javax.swing.JLabel RCUSSearchLabel;
    private javax.swing.JLabel RCUSTelephoneLabel;
    private javax.swing.JLabel RCUSTelephoneOutput;
    private javax.swing.JFrame RemoveCustomerWindow;
    private javax.swing.JButton RemoveCustomerWindowBTN;
    private javax.swing.JTextArea SCAddressOutput;
    private javax.swing.JLabel SCAddressOutputLabel;
    private javax.swing.JLabel SCBackgroudLabel;
    private javax.swing.JLabel SCBackgroundLabel;
    private javax.swing.JButton SCClearBTN;
    private javax.swing.JScrollPane SCConLocScroll;
    private javax.swing.JTable SCConLocTable;
    private javax.swing.JTextField SCContainerInput;
    private javax.swing.JLabel SCContainerSearchLabel;
    private javax.swing.JLabel SCIDOutput;
    private javax.swing.JLabel SCIDOutputLabel;
    private javax.swing.JTextField SCIDSearchInput;
    private javax.swing.JLabel SCIDSearchLabel;
    private javax.swing.JLabel SCNameOutput;
    private javax.swing.JLabel SCNameOutputLabel;
    private javax.swing.JTextField SCNameSearchInput;
    private javax.swing.JLabel SCNameSearchLabel;
    private javax.swing.JTextField SCONContainerInput;
    private javax.swing.JLabel SCONContainerLabel;
    private javax.swing.JLabel SCONLocationLabel;
    private javax.swing.JLabel SCONLocationOutput;
    private javax.swing.JButton SCONSearchBTN;
    private javax.swing.JButton SCSearchBTN;
    private javax.swing.JButton SCSearchContainerBTN;
    private javax.swing.JLabel SCTelephoneOutput;
    private javax.swing.JLabel SCTelephoneOutputLabel;
    private javax.swing.JLabel SIBackgroundLabel;
    private javax.swing.JButton SIClearBTN;
    private javax.swing.JTextField SIDescriptionInput;
    private javax.swing.JLabel SIIDescriptionLabel;
    private javax.swing.JScrollPane SIInventorySearchedScroll;
    private javax.swing.JTable SIInventorySearchedTable;
    private javax.swing.JTextField SIItemInput;
    private javax.swing.JLabel SIItemLabel;
    private javax.swing.JTextField SINameInput;
    private javax.swing.JLabel SINameLabel;
    private javax.swing.JButton SIPrintTableBTN;
    private javax.swing.JButton SISearchBTN;
    private javax.swing.JFrame SearchContainerWindow;
    private javax.swing.JButton SearchContainerWindowBTN;
    private javax.swing.JFrame SearchCustomerWindow;
    private javax.swing.JButton SearchCustomerWindowBTN;
    private javax.swing.JFrame SearchInventoryWindow;
    private javax.swing.JButton SearchInventorysBTN;
    private javax.swing.JComboBox<String> VLActionCombo;
    private javax.swing.JLabel VLBackgroundLabel;
    private datechooser.beans.DateChooserCombo VLDateChooser;
    private javax.swing.JScrollPane VLLogScroll;
    private javax.swing.JTable VLLogTable;
    private javax.swing.JButton VLPrintBTN;
    private javax.swing.JRadioButton VLRadioAction;
    private javax.swing.JRadioButton VLRadioDate;
    private javax.swing.JRadioButton VLRadioUser;
    private javax.swing.JButton VLResetBTN;
    private javax.swing.JButton VLSearchBTN;
    private javax.swing.JComboBox<String> VLUserCombo;
    private javax.swing.JLabel VLUserLable;
    private javax.swing.JFrame ViewLogWindow;
    private javax.swing.JButton ViewLogWindowBTN;
    private javax.swing.JLabel backgroundlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables


    public final void checkFinalized() {
    
        try {
            
            ArrayList<String> lists = new ArrayList<>();

            lists = wh.getLists();
            
            if(!lists.isEmpty()){
                
                FinalizePickListBTN.setBackground(Color.RED);
                
            }else{
                
                FinalizePickListBTN.setBackground(new java.awt.Color(204, 204, 204));
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * creates the directories for the data
     */
    
    public static void createDirectories(){
        
        try {
            

            Path d2 = Paths.get("C:\\ContainerStoreData");
           
            Files.createTempDirectory(d2,"Inventorys");
            Files.createTempDirectory(d2,"PickLists");
            Files.createTempDirectory(d2,"EmptiedContainers");
            
            DatabaseConnect dbc = new DatabaseConnect();
            
            dbc.DbCon();
            
        } catch (IOException | SQLException ex) {
            
            Logger.getLogger(StoreMain.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    /**
     * method to take an address from database and tidy it up for the text area
     * @param address
     * @return 
     */
    
    public String tidyAddress(String address){
        
        String[] makeTidy = address.split(",");
        
        String tidy = "";
        
        for(String s : makeTidy){
            
            tidy += s + "\n";
            
        }
        
        return tidy.trim();
    }
    
    /**
     * tidies the entered address to go into the database
     * @param address
     * @return 
     */
    
    public String tidyAddressForDatabase(String address){
        
        String tidy = "";
        
        String[] add = address.split("\n");
        
        for(String s : add){

            tidy += s + ", ";
        }
        
        return tidy.trim().substring(0, tidy.length() -2);
    }
    
}
