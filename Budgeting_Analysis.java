package demo.Budgeting_Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.DatabaseConnector;

public class Budgeting_Analysis {
    public boolean Check_valid_Category(String Category_input , String userid){
        String sql = "SELECT category FROM New_2_Expenses where userid = '" + userid + "'";
        
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery()) {
            
            // Process results - using EXACT column names from SQL query
            while (rs.next()) {
                String category = rs.getString("category");  

                if (Category_input.equals(category)){
                    System.out.println("[ Your Category Exsist In Expenses (Success)]");
                    return true;
                }
            }
            System.out.println("[ Your Category NoT Exsist In Expenses (Failure)]");
                return false;
            
        } catch (SQLException e) {
            // Print full error details for debugging
            e.printStackTrace();  // Shows exception type, message, and stack trace
            return false;
        }

    }


    public boolean Check_valid_BudgetCategory(String Category_input , String userid){
        String sql = "SELECT category FROM New_2_Budgets where userid = '" + userid + "'";
        
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery()) {
            
            // Process results - using EXACT column names from SQL query
            while (rs.next()) {
                String category = rs.getString("category");  

                if (Category_input.equals(category)){
                    System.out.println("[ Your Category Exsist In Budgets (Success)]");
                    return true;
                }
            }
            System.out.println("[ Your Category NoT Exsist In Budgets (Failure)]");
                return false;
            
        } catch (SQLException e) {
            // Print full error details for debugging
            e.printStackTrace();  // Shows exception type, message, and stack trace
            return false;
        }

    }



    public boolean Check_valid_Bidget_Amount(int Amount , String userid , String category){
        String sql = "SELECT amount FROM New_2_Expenses where userid = '" + userid + "' AND category = '" + category + "'";
        
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);



            ResultSet rs = stmt.executeQuery()) {
            
            // Process results - using EXACT column names from SQL query
            while (rs.next()) {
                int Selected_Amount = rs.getInt("amount");  

                if (Amount == Selected_Amount || Amount > Selected_Amount ){
                    System.out.println("[ Your Amount Is Valid (Success)]");
                    return true;
                }
            }
            System.out.println("[ Your Amount Is InValid (Failure)]");
                return false;
            
        } catch (SQLException e) {
            // Print full error details for debugging
            e.printStackTrace();  // Shows exception type, message, and stack trace
            return false;
        }

    }

    public boolean Add_Budget_to_db(int Amount , String userid , String category){
        String sql = "INSERT INTO New_2_Budgets (userid, category, amount ) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userid);
            stmt.setString(2, category);
            stmt.setInt(3, Amount);
            
            stmt.executeUpdate();
            System.out.println("[ Budget Added Successfully !]");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ Connection Error !]");
            return false;
        }
    }



    // public boolean Add_Spending_To_Category(int Spending ,String Categorey , String userid){

    //     String sql = "UPDATE New_2_Budgets SET Spending = ? WHERE  where userid = '" + userid + "' AND category = '" + Categorey + "'";
    
    //     try (Connection conn = DatabaseConnector.getConnection();
    //         PreparedStatement stmt = conn.prepareStatement(sql)) {
            
    //         stmt.setInt(1, Spending);
            
    //         return stmt.executeUpdate() > 0;
            
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }

    // }

    // public boolean Check_Exceeding_Budget(int Toatal_Spending ,String userid , String category ){

    //     String sql = "SELECT Spending FROM New_2_Budgets where userid = '" + userid + "' AND category = '" + category + "'";
        
    //     try (Connection conn = DatabaseConnector.getConnection();
    //         PreparedStatement stmt = conn.prepareStatement(sql);



    //         ResultSet rs = stmt.executeQuery()) {
            
    //         // Process results - using EXACT column names from SQL query
    //         while (rs.next()) {
    //             int Selected_Spending = rs.getInt("amount");  

    //             if (Selected_Spending == Toatal_Spending || Selected_Spending > Toatal_Spending ){
    //                 System.out.println("[ Your Budget Is Exceided - Enter Valid Spending]");
    //                 return true;
    //             }
    //         }
    //         System.out.println("[ Your Budget Is NoT Exceided]");
    //             return false;
            
    //     } catch (SQLException e) {
    //         // Print full error details for debugging
    //         e.printStackTrace();  // Shows exception type, message, and stack trace
    //         return false;
    //     }
    // }



public boolean Add_Spending_To_Category(int Spending, String Category, String userid) {
    // Use parameterized query to prevent SQL injection
    String sql = "UPDATE New_2_Budgets SET Spending = ? WHERE userid = ? AND category = ?";
    
    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, Spending);
        stmt.setString(2, userid);
        stmt.setString(3, Category);
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean Check_Exceeding_Budget(int newSpending, String userid, String category) {
    // Get both amount (budget limit) and current Spending
    String sql = "SELECT amount, Spending FROM New_2_Budgets WHERE userid = ? AND category = ?";
    
    try (Connection conn = DatabaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, userid);
        stmt.setString(2, category);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int budgetAmount = rs.getInt("amount");
            int currentSpending = rs.getInt("Spending");
            
            // Check if new spending would exceed budget
            if (currentSpending + newSpending > budgetAmount) {
                System.out.println("[ Your Budget Is Exceeded - Enter Valid Spending ]");
                return true;
            }
        }
        System.out.println("[ Your Budget Is Not Exceeded ]");
        return false;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
