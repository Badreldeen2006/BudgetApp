package demo.SigningController;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.*;

import demo.DatabaseConnector;

public class Authentication {

    
    Scanner scanner = new Scanner(System.in);

    // for Reset Pass
    public boolean Check_UserId_In_Db(String UserId){
        String sql = "select UserId from User_Credentials where UserId = ?";
        try (Connection conn = DatabaseConnector.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1, UserId);
                ResultSet rs = stmt.executeQuery();
                String Temp_UserId = "not found";

                while (rs.next()) {
                    Temp_UserId =  rs.getString("UserId");
                }
                if (Temp_UserId.equals("not found")){
                    System.out.println("[ Enter Your Correct User_Id !] ");
                    return false;
                }
                else{
                    return true;
                }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // for Reset Pass
    public void Update_Pass(String userid){
        String sql = "UPDATE User_Credentials SET Pass_word = ? WHERE UserId = ?";
    
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            String New_Pass ;

            while(true){
                System.out.println("[ The Pass Must Be :- \n"+
                            "- Minimum 7 char\n" +
                            "- At least 1 uppercase letter\n"+
                            "- At least 1 digit (0-9)\n"+
                            "- At least 1 special character (@#$%^&+=) ]");    

                System.out.print("=> Enter your New Pass :");
                
                New_Pass = scanner.next();
                if (!Check_Pass_Strenght(New_Pass)){
                    System.out.println("[ The Pass Is So Weak - Read Instructions ]");
                    continue;
                }
                else{
                    stmt.setString(1, New_Pass);
                    stmt.setString(2, userid);
                    stmt.executeUpdate();
                    System.out.println("[ Your New Password Added Successfully ]");
                    return ; 
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ;
        }
    }


    public void Reset_Pass(){
        while(true){
            System.out.print("=> Enter your User-Id :");
            String User_id = scanner.nextLine();
            if (Check_UserId_In_Db(User_id) == true){
                Update_Pass(User_id);
                return;
            }
            else{
                System.out.println("[ The User_Id Is Wrong ]");

            }
        }
    }


    public boolean Check_Pass_Strenght(String pass){

        final Pattern Pass_Regex = 
        Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*-/+=]).{7,}$");

        if (pass == null || !Pass_Regex.matcher(pass).matches()){
            return false;
        }
        return true;
    }

    
}
