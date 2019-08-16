import java.sql.*;  
import java.util.Scanner;

import com.sun.javadoc.Type;

public class Assignment2 {

	//get a connection to database 
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/q2";
			String  userName = "root";
			String password = "1234";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url, userName, password);
			System.out.println("connected...");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//////q1
	public static void Question1(String a1) {
		
		//if entered word flights calculate.else do nothing
		if(a1.equals("flights")) {
			String s = "select flight_no,arr_loc,arr_time,dep_time from flights where dep_date='9/8/19'";
			try {
				
				//get connection
				Connection con = getConnection();
				
				PreparedStatement statement = con.prepareStatement(s);
				
				ResultSet result = statement.executeQuery();
				
				//print all data
				while(result.next()) {
					System.out.print(result.getInt("flight_no"));
					System.out.print("     ");
					System.out.print(result.getString("arr_loc"));
					System.out.print("     ");
					String t = ConvertHours(result.getString("arr_time"),result.getString("dep_time"));
					System.out.println(t);

				}
			}catch(Exception e) {System.out.println(e);}
		}
	}
	///convert for calculate the diff without the colon
	public static String ConvertHours(String out,String end) {
		String[]Out = out.split(":");
		String[]End = end.split(":");
		int divBeforePoint = Math.abs(Integer.parseInt(Out[0]) - Integer.parseInt(End[0]));
		int divAfterPoint = Math.abs(Integer.parseInt(Out[1]) - Integer.parseInt(End[1]));
		return divBeforePoint +":"+divAfterPoint;
	}
	
	//q3
	public static void Question3(int a1) {
		try {
			//get connection
			Connection con = getConnection();
		
			//Prepare the stored procedure call 
			CallableStatement myStmt = con.prepareCall("{CALL `q2`.`numofvisitcountries`(?,?)}");
			
			//Set the parameters
			myStmt.setInt(1,a1);
			myStmt.registerOutParameter(2, Types.INTEGER);
			
			//call stored procedure
			myStmt.execute();
			
			//print the value of the out parameter
			System.out.println("Number of visited countries : "+myStmt.getInt(2)); 

		}catch(Exception e) {System.out.println(e);}
	}
	
	//q4
	public static void Question4(String a1) {	
		try {
			//get connection
			Connection con = getConnection();
			
			String s = "select flight_no,arr_loc,arr_date,arr_time from flights where dep_loc = 'PortEilat'";
			
			PreparedStatement statement = con.prepareStatement(s);
			
			ResultSet result = statement.executeQuery();
			
			//print like json object data
			while(result.next()) {
				System.out.print("{\"flight_no\":"+"\""+result.getInt("flight_no")+"\",");
				System.out.print("\"arr_loc\":"+"\""+result.getString("arr_loc")+"\",");
				System.out.print("\"arr_date\":"+"\""+result.getString("arr_date")+"\",");
				System.out.print("\"arr_time\":"+"\""+result.getString("arr_time")+"\"}");
				System.out.println();
			}
		}catch(Exception e) {System.out.println(e);}

	}

	
	public static void main(String[] args) {
		//insert to question number 1
		System.out.println("For question 1. Enter the word 'flights' : ");
		Scanner a = new Scanner(System.in);
		String a1 = a.next();
		Question1(a1);
		
		//insert to question number 3
		System.out.println("For question 3. Enter Passenger id : ");
		Scanner a2 = new Scanner(System.in);
		int a3 = a.nextInt();
		Question3(a3);
		
		//insert to question number 4
		System.out.println("For question 4. Enter the word 'airports' : ");
		Scanner a4 = new Scanner(System.in);
		String a5 = a4.next();
		Question4(a5);
	}
	
	

}

