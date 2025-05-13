package demo.SigningController;

import java.sql.*;
import java.util.regex.Pattern; // to do a pattern to match the email and phone format

import demo.DatabaseConnector;

public class Sign_Up {


    // Check Input Error
    
    public boolean Email_Input_Check(String email){
        final Pattern Email_Regex = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        if(email == null || !Email_Regex.matcher(email).matches() ){ // i should read emails from db and prevent duplicates
            return false;
        }
        return true;
    }

    public boolean Phone_Input_Check(String phone){
        final Pattern Phone_Regex = 
        Pattern.compile("^\\+?[0-9]{10,15}$");

        if(phone == null || !Phone_Regex.matcher(phone).matches() ){ // i should read phones from db and prevent duplicates
            return false;
        }
        return true;
    }


    public boolean Check_confirmed_pass( String pass , String confirm_pass){
        
        if(!confirm_pass.equals(pass) || confirm_pass == null){
            return false;
        }
        return true;
    }



    // Database Checking 


    public boolean Check_Email_In_Db(String email){
        String sql = "select email from User_Credentials where email = '" + email+"'" ;
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                ResultSet rs = stmt.executeQuery();
                String Email = "not found";

                while (rs.next()) {
                    Email =  rs.getString("email");
                }
                if (Email.equals("not found")){
                    return false;
                }
                else{
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public boolean Check_User_In_Db(String user){
        String sql = "select Username from User_Credentials where Username = '" + user +"'" ;
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                ResultSet rs = stmt.executeQuery();
                String Temp_User = "not found";

                while (rs.next()) {
                    Temp_User =  rs.getString("Username");
                }
                if (Temp_User.equals("not found")){
                    return false;
                }
                else{
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public boolean Check_Phone_In_Db(String phone){
        String sql = "select phone from User_Credentials where phone = '" + phone+"'" ;
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                ResultSet rs = stmt.executeQuery();
                String Temp_phone = "not found";

                while (rs.next()) {
                    Temp_phone =  rs.getString("phone");
                }
                if (Temp_phone.equals("not found")){
                    return true ;
                }
                else{
                    System.out.println("[The Phone Is Already Exists !]");;
                    return false;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }


    // Adding The Account


    public boolean addUser(String user , String pass , String confirm_pass, String email , String phone ){

        // User_Id is the pk that connect with the other tables as Fk
        // As user is unique then the user id will be so  
        String User_Id = user + "_" + pass.charAt(0) + pass.charAt(1) + pass.charAt(2);

        String sql = "INSERT INTO User_Credentials (Username, Pass_word, email , phone, UserId) VALUES (?, ?, ?, ?, ?)";
            
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user);
            stmt.setString(2, pass);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, User_Id);

            System.out.print("[ This Is Your User-Id (Save It Please) - (Used In Reseting Pass) ] =>");
            System.out.println("[" + User_Id + "]");
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
