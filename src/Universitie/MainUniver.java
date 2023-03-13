package Universitie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class MainUniver {

	public static void main(String[] args) {
		
		String url = "jdbc:sqlserver://localhost:1433;databaseName=Univer;"
        		+ "encrypt=true;"
        		+ "trustServerCertificate=true";

        Scanner scanner = new Scanner(System.in);
       	System.out.println("enter user");
       	 String user = scanner.nextLine();
       	 System.out.println(user);
       	 System.out.println("enter pass");
       	 String pass = scanner.nextLine();
       	 System.out.println(pass);

       	 if (user.equals(user) && pass.equals(pass)) {}else {
       	 System.out.println("worng username and password ");
       	 }
       	 Connection con = null;
       	 System.out.println("System is in prograss:");
    	try {
    		
	       	 // create a new table
	       	 Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	       	 DriverManager.registerDriver(driver);
	       	 con = DriverManager.getConnection(url, user, pass);
	       	 Statement st = con.createStatement();
	        

	    	//create table setting
			 String sql1="CREATE TABLE universite (" 
					 + "domain text,"
					 + "country text,"
					 + "web_pages text,"
					 + "name text, "
					 + "alpha_two_code text"
					 + ");";
			 
	    	 System.out.println("craeted to SQL database");
	    		//st.execute(sql1);
	
    		
	    		
	    		// api
	    		
    			String apiUrl = "http://universities.hipolabs.com/search?country=Oman";
    	        try {
    	            URL url1 = new URL(apiUrl);
    	            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
    	            conn.setRequestMethod("GET");
    	            conn.setRequestProperty("Accept", "application/json");
    	            
    	            if (conn.getResponseCode() != 200) {
    	                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
    	            }
    	            
    	            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    	            String output;
    	            StringBuilder json = new StringBuilder();
    	            
    	            while ((output = br.readLine()) != null) {
    	                json.append(output);
    	            }
    	            
    	            conn.disconnect();
    	            
    	            Gson gson = new Gson();
    	           //List<MyObject> myObj = gson.fromJson(json.toString(), ArrayList.class);
   	            
    	            

    	            
   	      java.lang.reflect.Type listType = new TypeToken<ArrayList<MyObject>>() {}.getType();
    	      List<MyObject> myObj = gson.fromJson(json.toString(), listType);   
    	      
    	      String insertSql = "INSERT INTO Universities (domains, country, web_pages, name, alpha_two_code) VALUES (?, ?, ?, ?, ?)";
    	      PreparedStatement ps = con.prepareStatement(insertSql);
    	      for (MyObject obj : myObj) {
    	      List<String> domain = obj.getDomain();
    	      String country = obj.getCountry();
    	      List<String> webPages = obj.getWeb_pages();
    	      String webPagesString = String.join(",", webPages);
    	      String name = obj.getName();
    	      String alpha_two_code = obj.getAlpha_two_code();
    	      ps.setString(1, String.join(",", domain));
    	      ps.setString(2, country);
    	      ps.setString(3, webPagesString);
    	      ps.setString(4, name);
    	      ps.setString(5, alpha_two_code);
    	      ps.executeUpdate();
    	      }

    		 
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}catch (Exception e) {
		System.err.println(e);
}
}   
}

