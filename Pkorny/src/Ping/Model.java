package Ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Model {
	/*public Logger logger=Logger.getLogger(App.class);
	// Model log=new Model();
	 public void runMe(String parameter){
		 
			if(logger.isDebugEnabled()){
				logger.debug("This is debug : " + parameter);
			}
			
			if(logger.isInfoEnabled()){
				logger.info("This is info : " + parameter);
			}
			
			logger.warn("This is warn : " + parameter);
			logger.error("This is error : " + parameter);
			logger.fatal("This is fatal : " + parameter);
			
		} */

	private String ipAdd="IPCIM";
	public long calculate(long number1, long number2, String operator) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                return number1 * number2;
            case "/":
                if (number2 == 0)
                    return 0;

                return number1 / number2;
        }

        System.out.println("Unknown operator - " + operator);
        return 0;
    }
    public String Muvelet(TextField ip1, TextField ip2, TextField ip3, TextField ip4, String Comm)
    {
    	try{
        	if(0<=Integer.parseInt(ip1.getText())&& Integer.parseInt(ip1.getText())<=255
        			&&0<=Integer.parseInt(ip2.getText())&& Integer.parseInt(ip2.getText())<=255
        			&&0<=Integer.parseInt(ip3.getText())&& Integer.parseInt(ip3.getText())<=255
        			&&0<=Integer.parseInt(ip4.getText())&& Integer.parseInt(ip4.getText())<=255
        			){
        	ipAdd=ip1.getText()+ "."+ip2.getText()+ "."+ip3.getText()+ "."+ip4.getText();
        	
        	switch (Comm) {
            case "Ping":
                return ICMP(ipAdd);
            case "TraceRoute":
                return TraceRT(ipAdd);
        	}
        	}
        	else return "Nem megfelelo IP cim!";
        	}
        	catch(Exception e)
        	{
        		return "Nem Integer szamot adtal meg!";
        		}
		return Comm;
    	
    	
    }
    private String ICMP(String ip)
    {
    	 try {
    		 dbsqlconnect con=new dbsqlconnect();
    		 //String ins;
    	      //String ipAddress = "127.0.0.1";
    	      InetAddress inet = InetAddress.getByName(ip);
    	     // System.out.println("Sending Ping Request to " + ip);
    	      if (inet.isReachable(5000)){
    	    	  con.SetData("insert into PingTable (IPcim, Elerhetoe) values ('"+ip+"', '1')" );
    	    	  con.close();
    	        return ("1");
    	      } else {
    	    	  con.SetData("insert into PingTable (IPcim, Elerhetoe) values ('"+ip+"', '0')" );
    	    	  con.close();
    	        return ("0");
    	      }
    	      
    	    } catch ( Exception e ) {
    	      return ("Kivetel: " + e.getMessage());
    	     
    	    }
    	 
    }
    private String TraceRT(String ip)
    {
    	   BufferedReader in;
    	   dbsqlconnect con=new dbsqlconnect();
    	    String outPut="";
           try{
               Runtime r   =   Runtime.getRuntime();
               String command="tracert "+ip;
               
               Process p   =   r.exec(command);

               in  =   new BufferedReader(new InputStreamReader(p.getInputStream()));

               String line;
               
               
               while((line=in.readLine())!=null){
            	   
            	   
            	  
                  outPut = outPut + line + "\n";
            	  
                   
               }

           }catch(IOException e){

           return (e.toString());

           }
           con.SetData("insert into TraceTable (IPcim, Utvonal) values ('"+ip+"', '"+outPut+"')");
		return outPut;
    }
    public void Windows(String button) throws IOException
    {
    	 Parent root;
         Stage primaryStage=new Stage();
          if(button.equals("Lekerdezes")){
          root = FXMLLoader.load(getClass().getResource("ui3.fxml"));
         }
         else root = FXMLLoader.load(getClass().getResource("ui.fxml"));
         primaryStage.setScene(new Scene(root));
         primaryStage.show();
    }
    public String kveri(String Comm) throws SQLException
    {
    	if(Comm.equals("Pingelt cimek"))
    	return Q1();
    	else if(Comm.equals("Valaszolt cimek"))
    		return Q2();
    		else if(Comm.equals("Nem valaszolt"))
    			return Q3();
    		else if(Comm.equals("TraceRoute cimek"))
    			return Q4();
    		else return Q5();
    }
    private String Q1() throws SQLException
    {
    	String querry ="select IPcim from PingTable";
    	String output="";
    	ResultSet rs;
    	dbsqlconnect con=new dbsqlconnect();
    	rs=con.GetData(querry);
    	while (rs.next()) {
            String Ipcim = rs.getString("IPcim");
            output=output + Ipcim + "\n";
            }
    	con.close();
    	return output;
    }
    private String Q2() throws SQLException
    {
    	String querry ="select IPcim from PingTable where elerhetoe='1' ";
    	String output="";
    	ResultSet rs;
    	dbsqlconnect con=new dbsqlconnect();
    	rs=con.GetData(querry);
    	while (rs.next()) {
            String Ipcim = rs.getString("IPcim");
            output=output + Ipcim + "\n";
            }
    	con.close();
    	return output;
    }
    private String Q3() throws SQLException
    {
    	String querry ="select IPcim from PingTable where elerhetoe='0' ";
    	String output="";
    	ResultSet rs;
    	dbsqlconnect con=new dbsqlconnect();
    	rs=con.GetData(querry);
    	while (rs.next()) {
            String Ipcim = rs.getString("IPcim");
            output=output + Ipcim + "\n";
            }
    	con.close();
    	return output;
    }
    private String Q4() throws SQLException
    {
    	String querry ="select IPcim from TraceTable";
    	String output="";
    	ResultSet rs;
    	dbsqlconnect con=new dbsqlconnect();
    	rs=con.GetData(querry);
    	while (rs.next()) {
            String Ipcim = rs.getString("IPcim");
            output=output + Ipcim + "\n";
            }
    	con.close();
    	return output;
    }
    private String Q5() throws SQLException
    {
    	String querry ="select IPcim, Utvonal from TraceTable";
    	String output="";
    	ResultSet rs;
    	dbsqlconnect con=new dbsqlconnect();
    	rs=con.GetData(querry);
    	while (rs.next()) {
            String Ipcim = rs.getString("IPcim");
            String Utvonal = rs.getString("Utvonal");
            
            output=output + Ipcim + "\n" + Utvonal + "\n";
            }
    	con.close();
    	return output;
    }


}