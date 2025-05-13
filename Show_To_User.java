package demo;

import java.util.Scanner;

import demo.SigningController.Authentication;
import demo.SigningController.Sign_In;
import demo.SigningController.Sign_Up;
import demo.Budgeting_Services.Expenses;
import demo.Budgeting_Services.Income;
import demo.Budgeting_Services.Budgeting_Analysis;


public class Show_To_User {

    Scanner scanner = new Scanner(System.in);
    Authentication Auth = new Authentication();

    public void Show_Signing_Page(){

        while(true){
            System.out.println("=================================================");
            System.out.println("        Welcome To Personal Budegt App ");
            System.out.println("=================================================");
            System.out.println("(1) Sign In");
            System.out.println("(2) Sign Up");
            System.out.println("=================================================");
            System.out.print("Choose an option: ");

            String choice ;
            choice = scanner.next();

            switch (choice) {

                case "1":
                    Sign_In S = new Sign_In();
                    String UN_Or_Email;

                    while(true){
                        System.out.print("=> Username OR Email : ");
                        UN_Or_Email = scanner.next();
                        if (S.Check_Null_input(UN_Or_Email) ==  true ){
                            System.out.println("[ Null Input !] ");
                            continue;
                        }
                        if (S.Detect_Email_Or_User_In_Db(UN_Or_Email) == false ){ 
                            continue;
                        }
                        break;
                    }
                    
                    while(true){
                    
                        System.out.print("=> Password : ");
                        String Pass = scanner.next();
                        if(S.Check_Null_input(Pass) == true ){
                            System.out.println("[ Null Input !] ");
                            continue;
                        }
                        if (S.Check_Pass_In_Db(UN_Or_Email,Pass) == false){
                            System.out.println("[ This Pass Is Wrong !] ");
                            System.out.println("[ Are You Want To Reset Your Pass ? (1) Yes / (2) No ] ");
                            System.out.print("Enter Your Choise :  ");
                            String  choise = scanner.next();
                            if(choise.equals( "1")){
                                Auth.Reset_Pass();
                                continue;
                            }
                            continue;
                        }else{
                            Show_Methods_Page();
                        }
                        break;
                    }

                    break;

                case "2":

                    String User , email , phone , pass , confairmed_pass ;            
                    Sign_Up S_U = new Sign_Up();
                    
                    while(true){
                        System.out.print("=> Username : ");
                        User = scanner.next();
                        if(S_U.Check_User_In_Db(User) == true){ //  the username we just check if it exist in db no any wrong name 
                            System.out.println("[This User Is Already Exists !]");
                            continue;
                        }
                        break;
                    }

                    while(true){
                        System.out.print("=> Email : ");
                        email = scanner.next();
                        if(S_U.Email_Input_Check(email) == false){ // check the email 
                            System.out.println("[The Email Format Is Wrong !]");
                            continue;
                        } 
                        if(S_U.Check_Email_In_Db(email)==true ){
                            System.out.println("[This Email Is Already Exists !]");
                            continue;
                        }
                        else{break;}
                    }
                    

                    while(true){
                        System.out.println("[The Phone Format Is [+] - optional , and digits between 10 - 15 !]");
                        System.out.print("=> Phone : ");
                        phone = scanner.next();  // check the phone 
                        if(S_U.Phone_Input_Check(phone)==false){
                            System.out.println("[The Phone Format Is Wrong !]");
                            continue;
                        }
                        else{break;}
                    }

                    while(true){
                        System.out.println("[ The Pass Must Be :- \n"+
                        "- Minimum 7 char\n" +
                        "- At least 1 uppercase letter\n"+
                        "- At least 1 digit (0-9)\n"+
                        "- At least 1 special character (@#$%^&+=) ]");

                        System.out.print("=> Pass : ");
                        pass = scanner.next();  // check the Pass Strenght
                        if(Auth.Check_Pass_Strenght(pass) == false ){
                            System.out.println("[ The Pass Is So Weak - Read Instructions ]");
                            continue;
                        }

                        System.out.print("=> Confairmed Pass : ");
                        confairmed_pass = scanner.next();  // check the Confairmed Pass 
                        if(S_U.Check_confirmed_pass(pass,confairmed_pass) == false){
                            System.out.println("[ The Confairmed Pass Doesn't Match ! ]");
                            continue;
                        }
                        else{break;}
                    }


                    if ( S_U.addUser(User, pass, confairmed_pass, email, phone) == true ){
                        System.out.println("[ The Account Added Successfully ! ]");
                        System.out.println("[ Direct to Dashboard! ]");
                        Show_Methods_Page();

                        break;
                    }
                    else{
                        System.out.println("[ Error Happened ,please Try Agail ]");
                        continue;
                    }

                    default:
                        System.out.println("[ Please Enter A Valid Input ]");
                        continue;
            }
        }
        
    }


    


    public void Show_Methods_Page(){
        while (true) {
            System.out.println("=================================================");
            System.out.println("              Budegting Services  ");
            System.out.println("=================================================");
            System.out.println("1) Tracking Income");
            System.out.println("2) Expense Tracking");
            System.out.println("3) Budgeting & Analysis");
            System.out.println("4) Reminders");
            System.out.println("\n=================================================");
            System.out.print("Choose an option: ");

            String choice ;
            choice = scanner.next();
        
            switch (choice) {

                case "1":
                    Income I = new Income();
                    String Source;
                    String Date;
                    int Amount;
                    String User_Id;
                    
                    while(true){
                        System.out.println("[ The Income Source Must Be One Of These :- \n"+
                            "- salary\n" +
                            "- freelance\n"+
                            "- part-time\n"+
                            "- gift ]"); 
                        System.out.println("-------------------------------------------");
                        System.out.print("=> Enter Your Income Source Type : ");
                        Source = scanner.next();
                        if (I.Check_Valid_Source(Source) == true){
                            break ;
                        }
                        else{continue;}
                    }

                    System.out.print("=> Enter Your Income Amount : ");
                    Amount = scanner.nextInt();


                    while(true){
                        System.out.println("[ The Date Format Must Be YYYY.MM.DD  ]");  
                        System.out.print("=> Enter Your Date : ");
                        Date = scanner.next().trim();
                        if( I.isNotFutureDate(Date) == false){
                            continue;
                        }
                        if (I.Check_Valid_Date(Date) == false){
                            continue ;
                        }
               
                        else{break;}
                    }

                    while(true){
                        System.out.print("=> Enter Your User_id To Save Your Data : "); 
                        User_Id = scanner.next();

                        if (Auth.Check_UserId_In_Db(User_Id) == true){
                            break ;
                        }
                        else{continue;}
                    }

                    if(I.Add_IncomeData_ToDb(Source, Amount, Date, User_Id) == true){
                        I.Print_User_data(User_Id);
                    }

                break;   // supposed to repeat the main while     

                case "2":

                    Expenses Ex = new Expenses();
                    String Category;
                    String Payment_date;
                    int amount;
                    String user_Id;
                    
                    while(true){
                        System.out.println("[ The Expense Category Must Be One Of These :- \n"+
                            "- Main-Food\n" +
                            "- Entertainment\n"+
                            "- Travelling\n"+
                            "- Shopping\n"+
                            "- Club ]");  // we can add another categories after
                        System.out.println("-------------------------------------------");
                        System.out.print("=> Enter Your Expense Category  : ");
                        Category = scanner.next();
                        if (Ex.Check_Valid_Category(Category) == true){
                            break ;
                        }
                        else{continue;}
                    }

                    System.out.print("=> Enter Your Expense Amount : ");
                    amount = scanner.nextInt();


                    while(true){
                        System.out.println("[ The Date Format Must Be YYYY.MM.DD  ]");  
                        System.out.print("=> Enter Your Date : ");
                        Payment_date = scanner.next().trim();
                        if( Ex.isNotFutureDate(Payment_date) == false){
                            continue;
                        }
                        if (Ex.Check_Valid_Date(Payment_date) == false){
                            continue ;
                        }
               
                        else{break;}
                    }

                    while(true){
                        System.out.print("=> Enter Your User_id To Save Your Data : "); 
                        user_Id = scanner.next();

                        if (Auth.Check_UserId_In_Db(user_Id) == true){
                            break ;
                        }
                        else{continue;}
                    }

                    if(Ex.Add_ExpenseData_ToDb(Category, amount, Payment_date, user_Id) == true){
                        Ex.Print_User_data(user_Id);
                    }

                break;   // supposed to repeat the main while 


                case "3":

                    System.out.println("-------------------------------------------------");
                    System.out.println("                Budegting Page    ");
                    System.out.println("-------------------------------------------------");
                    System.out.println("Waht You Want To Do : - ");
                    System.out.println("1) Add New Budget ");
                    System.out.println("2) Edit Existing One"); 
                    System.out.println("-------------------------------------------------");
                    System.out.print("Choose an option: ");

                    String input ;
                    input = scanner.next();
                    Budgeting_Analysis B_A = new Budgeting_Analysis();

                    if(input.equals("1")){
                        
                        String category;
                        String user__id;
                        int AMount ;
                        
                        while(true){
                            System.out.print("=> Enter Your User_id To Access Your Budgets : "); 
                            user__id = scanner.next();

                            if (Auth.Check_UserId_In_Db(user__id) == true){
                                break ;
                            }
                            else{continue;}
                        }

                        while(true){
                            System.out.println("[ (Remind )The Categories Available Is :- \n"+
                                "- Main-Food\n" +
                                "- Entertainment\n"+
                                "- Travelling\n"+
                                "- Shopping\n"+
                                "- Club ]");
                            System.out.print("=> Enter Category You Want To Set Budget For : ");
                            category = scanner.next();

                            if (B_A.Check_valid_Category(category , user__id) == true){
                                break ;
                            }
                            else{continue;}
                        }

                        while(true){
                            System.out.print("=> Enter Your Budget Amount : ");
                            AMount = scanner.nextInt();
                            if (B_A.Check_valid_Bidget_Amount(AMount , user__id , category) == true){
                                break ;
                            }
                            else{continue;}
                        }
                        if (B_A.Add_Budget_to_db(AMount, user__id, category) == true){
                            break;
                        }
                        else{continue;}
                    }
                    else if (input.equals("2")){

                        // access to corecct budget using userid and category

                        String uuserid;
                        String cat;
                        while(true){
                            System.out.print("=> Enter Your User_id To Access Your Budgets : "); 
                            uuserid = scanner.next();

                            if (Auth.Check_UserId_In_Db(uuserid) == true){
                                break ;
                            }
                            else{continue;}
                        }

                        while(true){
                            System.out.print("=> Enter The Category : ");
                            cat = scanner.next();
                            if (B_A.Check_valid_BudgetCategory(cat, uuserid)){
                                break ;
                            }
                            else{continue;}
                        }
                        


                        // let him add his spending in it and alert him with it 

                        int New_Spending ;
                        int Total_Current_Spending = 0; 
                        while(true){
                            System.out.print("=> Enter Your New Spending in this Category : ");
                            New_Spending = scanner.nextInt();
                            Total_Current_Spending = New_Spending + Total_Current_Spending; 
                            // ensure the spending col contain total spending

                            if(B_A.Check_Exceeding_Budget(Total_Current_Spending,uuserid,cat) == true){
                                continue;
                            }
                            

                            if (B_A.Add_Spending_To_Category(Total_Current_Spending, cat, uuserid) == true){
                                System.out.println("[Spending Updated Successfully]");
                                break ;
                            }
                            else{
                                System.out.println("[Error !]");
                                continue;
                            }
                        }



                        //  if he exceed the bugget amount he will reject last update and warn him 



                    }
                    else{break;}

                    





                case "4":



            default:
            
            }
        
        }

    }

}
