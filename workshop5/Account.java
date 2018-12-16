import java.text.SimpleDateFormat;
import java.util.*;

public class Account {
    private String type;
    private int accNum;
    private String ownerFirstname;
    private String ownerLastname;
    private State state;
    private List<Bill> bills;
    private int lateBillCount = 0;
    private int billCounter =0;

    public enum State {PENDING, ACTIVE, PRIORITY, INACTIVE, CLOSED,
        SECONDCHANCE, IN_DEFAULT, ADMINISTRATION, HEALTHY_DEBT, BAD_DEBT, COLLECTIONS }


    public Account (String firstname, String lastname, String type, int accNum) {
        this.ownerFirstname = firstname;
        this.ownerLastname = lastname ;
        this.type = type;
        this.accNum = accNum;
        this.state = State.PENDING;
        List<Bill> bills = new ArrayList <Bill> (  );
        this.bills = bills;
    }

    public void setActiveState () {
        this.state = State.ACTIVE;
    }

    public void setPriorityState() {
        this.state = State.PRIORITY;
    }


    public String getType () {
        return type;
    }

    public int getAccNum() {
        return accNum;
    }

    public State getState() {
        return state;
    }

    public int getBillCounter() {
        return billCounter;
    }

    public void setBillCounter(int value ) {
        this.billCounter = value;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public int getLateBillCount() {
        return lateBillCount;
    }

    public void createBill(int amount, Calendar date, Calendar todaysDate) {

        bills.add ( new Bill ( amount, date, todaysDate ) );
        int billNumber = getBillCounter () + 1;
        setBillCounter ( billNumber );
        System.out.println ("\nBill Added");
    }

    public int amountOwing() {
        int totalBills= getBillCounter ();
        int sum =0;
        for(int i=0; i < totalBills; i++) {
            sum = sum + bills.get ( i ).getPrice ();
        }
        return sum;
    }

    public void viewAllbills() {
        for(int i=0; i < getBillCounter (); i++) {
            billPrinter ( i );
            System.out.println ("\n");
        }
    }

    public void checkAllBillStatus(Calendar todaysDate) {
            for(int i=0; i < getBillCounter (); i++) {
                bills.get ( i ).checkStatus (todaysDate);
                if(bills.get ( i ).getState () == Bill.BillState.LATE) {
                    this.state = State.IN_DEFAULT;
                    this.lateBillCount = lateBillCount + 1;
                    checkHistory();
                }
            }
    }
    

    public void checkHistory() {
        if(getLateBillCount () == 0) {
            this.state = State.SECONDCHANCE;
        } else {
            this.state = State.ADMINISTRATION;
            paymentPlan();
        }
    }

    public void paymentPlan() {
        String yes = "y", no = "n";

        Scanner keyboard = new Scanner ( System.in );
        System.out.println ("\n*** YOU ARE IN ADMINISTRATION *** ");
        System.out.println ("Do you want to accept the payment plan? (y/n)");
        String userResponse = keyboard.next ();

        if(userResponse.equalsIgnoreCase ( yes )) {
            this.state = State.HEALTHY_DEBT;
        } else if (userResponse.equalsIgnoreCase ( no )) {
            this.state = State.BAD_DEBT;
        }
    }

    public void payBill(int billNumber) {
        if(getBills ().remove ( billNumber ).getState () == Bill.BillState.LATE  && this.state ==
                State.IN_DEFAULT) {
            setActiveState ();
        }
        getBills ().remove ( billNumber );
        setBillCounter (getBillCounter ()-1 );

    }

    public void viewBillNumber(int number) {
        billPrinter ( number );
    }

    public void billPrinter(int billNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat ( "dd/MMM/yyyy" );

        System.out.println ("Bill Information");
        System.out.println ("Bill owner: " + this.ownerFirstname);
        System.out.println ("Bill Number: " + billNumber);
        System.out.println ("Amount: " + bills.get ( billNumber ).getPrice ());
        System.out.println ("Due date: " + sdf.format ( bills.get ( billNumber ).getDueDate ().getTime ()
        ) );
        System.out.println ("Bill state: " + bills.get ( billNumber ).getState ());
    }
}
