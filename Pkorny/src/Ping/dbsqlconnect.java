package Ping;



import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Karcsi
 */
public class dbsqlconnect {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    private String loch2="jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g";
    private String felhasz="HL_IG8QMT";
    private String jelszo="kassai";
    // @Kapcsolodas az adatbazishoz az osztaly peldanyositasakor a konstruktorbol.
    public dbsqlconnect()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
          
            con = DriverManager.getConnection(loch2,felhasz,jelszo);
            st=con.createStatement();
            
            
          
        }
        catch(Exception e){System.out.println("Hiba az adatbazis kapcsolatban!");};
    }
    /** Adatok lekerese.*/
    public ResultSet GetData(String utasitas)
    {
        try
        {
          
            rs=st.executeQuery(utasitas);
          
            
        }catch(Exception e)
        {
            System.out.println(e+"getdata");
        }
        return rs;
        
    }
    /** 
     *  Adatok rogzitese az adatbazisban.
     */
    public void SetData(String kveri)
    {
        try{
        st.executeUpdate(kveri);
        
        }
        catch(Exception ex2)
        {System.out.println(ex2+"Setdata");}
    }
    /**
     *  Tábla létrehozása!
     */
    public void create() throws SQLException 
    {
        String cr="create table PingTable(IPcim VARCHAR(30), Elerhetoe VARCHAR(3))";
        String cr2="create table TraceTable(IPcim VARCHAR(30), Utvonal VARCHAR(4000))";
        
         
        
        try {
            st.executeUpdate(cr);
            
        } catch (SQLException ex) {
            System.out.println(" Pingtabla mar letezik:"+ ex);
        }
        try {
            
            st.executeUpdate(cr2);
        } catch (SQLException ex) {
            System.out.println(" tracetabla mar letezik :"+ ex);
        }
    }
    public void drop()
    {
    	 String cr="drop table PingTable";
         String cr2="drop table TraceTable";
         
          
         
         
         //System.out.println("siker");
         
         
         try {
             st.executeUpdate(cr);
             
         } catch (SQLException ex) {
             System.out.println(" Pingtabla nem letezik:"+ ex);
         }
         try {
             
             st.executeUpdate(cr2);
         } catch (SQLException ex) {
             System.out.println(" tracetabla nem letezik :"+ ex);
         }
    }
    public void close() throws SQLException
    {
        st.close();
        con.close();
    }
   
}
