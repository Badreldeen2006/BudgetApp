package demo.Budgeting_Services;
import java.util.regex.Pattern;
import demo.DatabaseConnector;

import java.sql.*;
import java.time.LocalDate;

public class Income {

    public boolean Check_Valid_Source(String Source){
        if (!Source.equals("salary") && !Source.equals("freelance") && !Source.equals("part-time")
        && !Source.equals("gift")   ){
            System.out.println("[ Enter Valid Source Type - Read options ]");
            return false;
        }
        return true;
    }

    public boolean Check_Valid_Date(String Date){
        final Pattern Date_Regex = 
        Pattern.compile("^(19|20)\\d{2}[-/.](0?[1-9]|1[0-2])[-/.](0?[1-9]|[12][0-9]|3[01])$");

        if (!Date_Regex.matcher(Date).matches()){ // i must write instructions of format
            System.out.println("[ Enter Valid Date Format  - Read Instructions ]");
            return false;
        }
        return true;
    }

    public boolean isNotFutureDate(String date) {
        try {
            // 1. Split the formatted string
            String[] parts = date.split("\\.");
            
            // 2. Create LocalDate object
            LocalDate inputDate = LocalDate.of(
                Integer.parseInt(parts[0]), 
                Integer.parseInt(parts[1]), 
                Integer.parseInt(parts[2])
            );
            
            if(!inputDate.isAfter(LocalDate.now()) == true){
                return true;
            }
            else{
                System.out.println("[Error The Date Mustn't be In Future ! ]");
                return false;
            }

            // 3. Compare with current date
            //return !inputDate.isAfter(LocalDate.now()); // this expression return true if is a current date or past 
            
        } catch (Exception e) {
            return false; // Invalid format or impossible date
        }
    }


    public boolean Add_IncomeData_ToDb (String Source , int amount,  String rceivingDate , String user_id  ){

        String sql = "INSERT INTO Income (Source, Amount, Receiving_Date ,  userid) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
                  // Convert string date to SQL Date
            String[] parts = rceivingDate.split("\\.");
            LocalDate localDate = LocalDate.of(
                Integer.parseInt(parts[0]), 
                Integer.parseInt(parts[1]), 
                Integer.parseInt(parts[2])
            );
            Date sqlDate = Date.valueOf(localDate);

            stmt.setString(1, Source);
            stmt.setInt   (2, amount);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, user_id);

            stmt.executeUpdate();
            System.out.println("[ Data Added Successfully !]");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ Connection Error !]");
            return false;
        }
    }



    public void Print_User_data(String userid){
        String sql = "SELECT Source, Amount, Receiving_Date  FROM Income  where userid = '" + userid + "'" ;
        
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String Source = rs.getString("Source");  
                int    Amount = rs.getInt("Amount");      
                Date receivingDate = rs.getDate("Receiving_Date");  // Use getDate() for DATE columns
                
                System.out.printf(
                    "Income Source: %s, Amount: %d, Date: %s%n",
                    Source,
                    Amount,
                    receivingDate.toString()  // Formats as YYYY-MM-DD
                );
            }
            
        } catch (SQLException e) {
            // Print full error details for debugging
            e.printStackTrace();  // Shows exception type, message, and stack trace
        }

    }

}
