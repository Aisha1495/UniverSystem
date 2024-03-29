package Universitie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class MainUniver {
	
	MyObject myobject= new MyObject();
	 private static String url = "jdbc:sqlserver://localhost:1433;databaseName=Univer;encrypt=true;trustServerCertificate=true";
	 private static Connection con = null;
	 private static Scanner scanner = new Scanner(System.in);
	 private static boolean isLoggedIn = false;
	 
		public static void main(String[] args) {
	 System.out.println("Welcome to the Universities Database System");
	 showMenu();
	 }
	 private static void showMenu() {
	 while (true) {
	 System.out.println("Select an option:");
	 System.out.println("1. Create table");
	 System.out.println("2. Insert data");
	 System.out.println("3. Login");
	 System.out.println("4. Logout");
	 System.out.println("5. Remove university");
	 System.out.println("6. Print universities");
	 System.out.println("7. Backup database");
	 System.out.println("8. Exit");
	 int option = scanner.nextInt();
	 scanner.nextLine(); // consume the new line character
	 switch (option) {
	 case 1:
	 createTable();
	 break;
	 case 2:
	 insertData();
	 break;
	 case 3:
	 login();
	 break;
	 case 4:
	 logout();
	 break;
	 case 5:
	 removeData();
	 break;
	 case 6:
	 printUniversities();
	 break;
	 case 7:
	 backupDatabase();
	 break;
	 case 8:
	 System.out.println("Goodbye!");
	 System.exit(0);
	 break;
	 default:
	 System.out.println("Invalid option. Please select again.");
	 break;
	 }
	 }
	}
	 private static void createTable() {
	 try {
	 con = DriverManager.getConnection(url, "sa", "root");
	 Statement st = con.createStatement();
	 String sql= "Create table Universities1("
	// + " state_province text ,"
	 + " domains text ,"
	 + " country text ,"
	 + " web_pages text ,"
	 + " name text , "
	 + "alpha_two_code text "
	 + ");";
	 System.out.println("databas craeted");
	 st.executeUpdate(sql);
	 System.out.println("Table created successfully");
	 } catch (Exception e) {
	 System.err.println(e.getMessage());
	 }
	 }
	 
	 private static void insertData() {
		 
		   	 System.out.println("Enter the name of the country to search for universities:");
			 String countryName = scanner.nextLine();

//			 String apiUrl = "http://universities.hipolabs.com/search?country=" + countryName;
		     try {
			 String apiUrl = "http://universities.hipolabs.com/search?country=" + countryName;        
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
//	            List<MyObject> myObj =  gson.fromJson(json.toString(), ArrayList.class);
	            java.lang.reflect.Type listType = new TypeToken<ArrayList<MyObject>>() {}.getType();
	            List<MyObject> myObj = gson.fromJson(json.toString(), listType);
	            // Use myObj for further processing
	            String insertSql = "INSERT INTO Universities1 (domains, country, web_pages, name, alpha_two_code) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement ps = con.prepareStatement(insertSql);
	            
	            for (MyObject obj : myObj) {
	                List<String> domains = obj.getDomains();
	                String country = obj.getCountry();
	                List<String> webPages = obj.getWeb_pages();
	                String webPagesString = String.join(",", webPages);
	                String name = obj.getName();
	                String alpha_two_code = obj.getAlpha_two_code();
	                ps.setString(1, String.join(",", domains));
	                ps.setString(2, country);
	                ps.setString(3, webPagesString);
	                ps.setString(4, name);
	                ps.setString(5, alpha_two_code);
//	                System.out.println(insertSql);
	                ps.executeUpdate();
	                System.out.println("done");
	            } 
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
	 }
//	   if (!isLoggedIn) {
//	 System.out.println("You must be logged in to perform this action");
//	 return;
//	 }
//	 try {
//	 System.out.println("Enter domains:");
//	 String domains = scanner.nextLine();
//	 System.out.println("Enter country:");
//	 String country = scanner.nextLine();
//	 System.out.println("Enter web_pages:");
//	 String web_pages = scanner.nextLine();
//	 System.out.println("Enter name:");
//	 String name = scanner.nextLine();
//	 System.out.println("Enter alpha_two_code:");
//	 String alpha_two_code = scanner.nextLine();
//	 con = DriverManager.getConnection(url, "sa", "password");
//	 String insertSql = "INSERT INTO UniversitiesCanada (domains, country, web_pages, name, alpha_two_code) VALUES (?, ?, ?, ?, ?)";
//	 PreparedStatement ps = con.prepareStatement(insertSql);
//	 ps.setString(1, domains);
//	 ps.setString(2, country);
//	 ps.setString(3, web_pages);
//	 ps.setString(4, name);
//	 ps.setString(5, alpha_two_code);
//	 int rowsAffected = ps.executeUpdate();
//	 if (rowsAffected > 0) {
//	 System.out.println("Data inserted successfully");
//	 } else {
//	 System.out.println("Data insertion failed");
//	 }
//	 } catch (Exception e) {
//	 e.printStackTrace();
//	 }
//	 }
	 
	 
	 private static void removeData() {
	 if (!isLoggedIn) {
	 System.out.println("You must be logged in to perform this action");
	 return;
	 }
	 try {
	 System.out.println("Enter university name to delete:");
	 String name = scanner.nextLine();
	 con = DriverManager.getConnection(url, "sa", "password");
	 String sql = "DELETE FROM Universities WHERE Name = ?";
	 PreparedStatement ps = con.prepareStatement(sql);
	 ps.setString(1, name);
	 int rowsAffected = ps.executeUpdate();
	 if (rowsAffected > 0) {
	 System.out.println("University removed successfully");
	 } else {
	 System.out.println("Failed to remove university");
	 }
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 }
	 
	 
	 private static void printUniversities() {
	 if (!isLoggedIn) {
	 System.out.println("You must be logged in to perform this action");
	 return;
	 }
	 try {
	 con = DriverManager.getConnection(url, "sa", "password");
	 String sql = "SELECT * FROM Universities1";
	 Statement st = con.createStatement();
	 ResultSet rs = st.executeQuery(sql);
	 while (rs.next()) {
	 String name = rs.getString("Name");
	 String country = rs.getString("Country");
	 String city = rs.getString("City");
	 String website = rs.getString("Website");
	 System.out.println("Name: " + name + ", Country: " + country + ", City: " + city + ", Website: " + website);
	 }
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 }
	 
	 
	 private static void backupDatabase() {
		 
		 if (!isLoggedIn) {
	            System.out.println("You must be logged in to perform this action");
	            return;
	        }

	        File defaultBackupFile = new File("./backups.bak");
	        
	        try {
	            con = DriverManager.getConnection(url, "sa", "root");
	            String sql = "BACKUP DATABASE Universities TO DISK = '" + defaultBackupFile.getAbsolutePath() + "'";
	            Statement st = con.createStatement();
	            st.execute(sql);
	            System.out.println("Database backup created at " + defaultBackupFile.getAbsolutePath());
	            System.out.println("Done");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Done");
	    }
//	 if (!isLoggedIn) {
//	 System.out.println("You must be logged in to perform this action");
//	 return;
//	 }
//	 try {
//	 JFileChooser fileChooser = new JFileChooser();
//	 fileChooser.setDialogTitle("Choose backup file location");
//	 int userSelection = fileChooser.showSaveDialog(null);
//	 if (userSelection == JFileChooser.APPROVE_OPTION) {
//	 File backupFile = fileChooser.getSelectedFile();
//	 String backupPath = backupFile.getAbsolutePath();
//	 con = DriverManager.getConnection(url, "sa", "password");
//	 String sql = "BACKUP DATABASE Universities TO DISK = '" + backupPath + "'";
//	 Statement st = con.createStatement();
//	 st.execute(sql);
//	 System.out.println("Database backup created at " + backupPath);
//	 }
//	 } catch (SQLException e) {
//	 e.printStackTrace();
//	 }
//	 }
	 
	 
	 private static void login() {
	 if (isLoggedIn) {
	 System.out.println("You are already logged in");
	 return;
	 }
	 // Establish a connection to the SQL server
	 String url = "jdbc:sqlserver://localhost:1433;databaseName=Univer;encrypt=true;trustServerCertificate=true";
	 String username = "sa";
	 String password = "root";
	 try {
	 Connection con = DriverManager.getConnection(url, username, password);
	 System.out.println("Connected to SQL server");
	 } catch (SQLException e) {
	 e.printStackTrace();
	 return;
	 }
	 System.out.println("Enter username:");
	 String usernameInput = scanner.nextLine();
	 System.out.println("Enter password:");
	 String passwordInput = scanner.nextLine();
	 if (usernameInput.equals("sa") && passwordInput.equals("root")) {
	 System.out.println("Login successful");
	 isLoggedIn = true;
	 } else {
	 System.out.println("Invalid username or password");
	 }
	 }
	 private static void logout() {
	 if (!isLoggedIn) {
	 System.out.println("You are already logged out");
	 return;
	 }
	 System.out.println("Logging out...");
	 isLoggedIn = false;
	 }
}