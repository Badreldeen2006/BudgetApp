package demo.SigningController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import demo.DatabaseConnector;

public class Sign_In {


    // user can sign in with user or email and pass [both email and pass is unique so it will be correct in shaa allah]

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
                    System.out.println("[ This Email Doesn't Exist !] ");
                    return false;
                }
                else{
                    System.out.println("[ The Account Is Exist ]");
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean Check_User_In_Db(String user){
        String sql = "select Username from User_Credentials where Username = '" + user+"'" ;
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                ResultSet rs = stmt.executeQuery();
                String Temp_User = "not found";

                while (rs.next()) {
                    Temp_User =  rs.getString("Username");
                }
                if (Temp_User.equals("not found")){
                    System.out.println("[ This User Doesn't Exist !] ");
                    return false;
                }
                else{
                    System.out.println("[ The Account Is Exist ]");
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // will be called when sign in to check the input is empty or not 
    public boolean Check_Null_input(String input){
        if(input == null){
            return true;
        }
        return false;
    }

    // detect user input usename or email
    public boolean Detect_Email_Or_User_In_Db(String input){
        final Pattern Email_Regex = 
        Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        if (Email_Regex.matcher(input).matches()){
            System.out.println("[ Your Email Input Is Added - Checking If Exist ]");
            return Check_Email_In_Db(input);
        }
        else{
            System.out.println("[ Your User Input Is Added - Checking If Exist ]");
            return Check_User_In_Db(input);
        }
    }


    public boolean Check_Pass_In_Db(String  Un_Or_Email , String pass ){ // this fun will get hte user or email and check its pass
        String sql = "select Pass_word from User_Credentials where email = ? or Username = ? " ;
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, Un_Or_Email);
                stmt.setString(2, Un_Or_Email);

                ResultSet rs = stmt.executeQuery();
                String Temp_Pass = "not found";

                while (rs.next()) {
                    Temp_Pass =  rs.getString("Pass_word");
                }
                if (!Temp_Pass.equals(pass) ){
                    return false;
                }
                else{
                    System.out.println("[ Direct To Dashboard ]");
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
