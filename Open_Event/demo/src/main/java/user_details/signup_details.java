package user_details;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bouncycastle.util.encoders.Hex;

public class signup_details {
	
    String id;
    String FirstName;
    String LastName;
    String Email;
    String Address;
    String City;
    String Phone;
    String password;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
    public void entry() {
    	System.out.println(Address+" "+City+" "+Phone+" "+password);
    	String str="insert into user values (?,?,?,?,?,?,?,?)";
    	
    	String str2="insert into login values(?,?,?)";
    	
    	String str3="select count(*) from login";
    	
    	 try (
    			 Connection conn = DriverManager.getConnection(
               		  "jdbc:mysql://localhost:3306/openevent?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                         "myuser", "xxxx");
                 
                   Statement stmt = conn.createStatement();
                 ){
    		 
    		 ResultSet rset=stmt.executeQuery(str3);
    		 rset.next();
    		 String res=rset.getString("count(*)");
    		 Integer cnt= new Integer(res);
    		 cnt++;
    		 
    		 
    		 PreparedStatement pre=conn.prepareStatement(str); 
    		 
    		 MessageDigest digest = MessageDigest.getInstance("SHA-256");
    		 byte[] hash = digest.digest(
    		   password.getBytes(StandardCharsets.UTF_8));
    		 String sha256hex = new String(Hex.encode(hash));
    		 
    		
    		 pre.setInt(1, cnt);
    		 pre.setString(2, FirstName);
    		 pre.setString(3, LastName);
    		 pre.setString(4, Email);
    		 pre.setString(5, Address);
    		 pre.setString(6, City);
    		 pre.setString(7, Phone);
    		 pre.setString(8, sha256hex);
    		 pre.execute(); 
    		 
    		PreparedStatement pre2=conn.prepareStatement(str2);
    		pre2.setInt(1, cnt);
    		pre2.setString(2, sha256hex);
    		pre2.setString(3, Email);
    		pre2.execute();
    		 
    		 System.out.println("Reached");
    		 System.out.println(sha256hex);
    	 } catch(Exception e) {
    		 e.printStackTrace();
    	 }
    }
}
