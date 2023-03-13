package Universitie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;




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
					 + "state_province text, "
					 + "domain text,"
					 + "country text,"
					 + "web_pages text,"
					 + "name text, "
					 + "alpha_two_code text"
					 + ");";
			 
	    	 System.out.println("craeted to SQL database");
	    		//st.execute(sql1);
	
    		
	    		
	    		// api
	    		
    			String apiUrl = "http://universities.hipolabs.com/search?country=%3ccountry";
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
    	            MyObject myObj = gson.fromJson(json.toString(), MyObject.class);
    	            
    	            // Use myObj for further processing
    	            

    	            
    	            
    	            
    	            
//    	            String sql = "INSERT INTO Univer (state_province,domain,country,web_pages,name,alpha_two_code)"+
//   						 "VALUES ("+"'"+myObj.getState_province()+"','"+myObj.getDomain()+"','"+myObj.getName()+"','"+myObj.getWeb_pages()+"','"++"','"++ "')";
//   		System.out.println(sql);
//   	        	st.execute(sql);    
//    		
//    		 BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\UniversitiesProject\\src\\Universitie\\univ.txt"));
//    		 StringBuilder jsonString = new StringBuilder();
//    		 String line;
//    		 while ((line = reader.readLine()) != null) {
//    		 jsonString.append(line);
//    		 }
//    		 reader.close();
//    		 JSONArray universities = new JSONArray(jsonString.toString());
//    		 for (int i = 0; i < universities.length(); i++) {
//    		 JSONObject university = universities.getJSONObject(i);
//    		 String name = university.getString("name");
//    		 String country = university.getString("country");
//    		 System.out.println(name + " - " + country);
    	      //  }
    		 
    	}catch (Exception e) {
			System.out.println(e);
		}
	}catch (Exception e) {
		System.out.println(e);
}
}   
}

