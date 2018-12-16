
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;



public class Driver {
    public static void main (String args[]) {

        int userInput;
        int i=1;

        Scanner keyboard = new Scanner(System.in);

        Customer cust1 = new Customer ( "Bob", "peterson" );

        cust1.createAccount ("Mastercard", 432543523);

        SimpleDateFormat sdf = new SimpleDateFormat ( "dd/MMM/yyyy" );
        Calendar todaysDate = Calendar.getInstance ();
        System.out.println ("\nTodays date: " + sdf.format ( todaysDate.getTime () ));


        System.out.println ("\nWELCOME: " + cust1.getFirstname () + " " + cust1.getLastname ());

        System.out.println ("\nAVAILABLE FUNCTIONS");
        System.out.println ("1. Check details");
        System.out.println ("2. Get product");
        System.out.println ("3. Make complaint");
        System.out.println ("4. Add bill");
        System.out.println ("5. View all bills");
        System.out.println ("6. View specific bill");
        System.out.println ("7. Change date");
        System.out.println ("8. Pay Bill");
        System.out.println ("9. Resolve Complaint");


        while(i > 0) {

            //checks status of bills

            cust1.getBankAcc ().checkAllBillStatus ( todaysDate );
            System.out.println ("\nCURRENT DATE: " + sdf.format ( todaysDate.getTime () ));

            System.out.println ("\nWhat do you want to do?");

            userInput = keyboard.nextInt ();

            switch(userInput) {
                case 1:
                    cust1.getDetails ();
                    break;
                case 2:
                    cust1.getProduct ();
                    break;
                case 3:
                    cust1.getBankAcc ().setPriorityState ();
                case 4:
                    System.out.println ("\nEnter amount due: ");
                    int amountDue = keyboard.nextInt ();


                    System.out.println ("\nEnter due date (dd-MM-yyyy): ");

                    String dateInput = keyboard.next();

                    String[] dateInputSplit = dateInput.split ( "-" );

                    int day = Integer.parseInt ( dateInputSplit[0]);
                    int month = Integer.parseInt ( dateInputSplit[1]);
                    int year = Integer.parseInt ( dateInputSplit[2]);


                    Calendar dueDate = new GregorianCalendar ();

                    dueDate.set (Calendar.MONTH, month-1  );
                    dueDate.set ( Calendar.YEAR, year );
                    dueDate.set ( Calendar.DATE, day );

                    cust1.getBankAcc ().createBill ( amountDue, dueDate, todaysDate );
                    break;
                case 5:
                    cust1.getBankAcc ().viewAllbills ();
                    break;
                case 6:
                    System.out.println ("\nWhich bill do you want to view?");
                    int billInput = keyboard.nextInt ();
                    cust1.getBankAcc ().viewBillNumber ( billInput );
                    break;
                case 7:
                    System.out.println ("Enter number of days: ");
                    int days = keyboard.nextInt ();


                    todaysDate.add ( Calendar.DAY_OF_MONTH, days );


                    System.out.println ("Date changed!\n");


                case 8:
                    cust1.getBankAcc ().viewAllbills ();

                    System.out.println ("\nWhich bill do you want to pay?");
                    int payBillNumber = keyboard.nextInt ();

                    cust1.getBankAcc ().payBill ( payBillNumber );
                case 9:
                    cust1.getBankAcc ().setActiveState ();

            }

        }

    }
}
