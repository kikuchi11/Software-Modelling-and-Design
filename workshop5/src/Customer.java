public class Customer {
    private String firstname;
    private String lastname;
    private Account bankAcc;


    public Customer (String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void createAccount (String type, int accNum) {
        Account bankAcc = new Account ( getFirstname (), getLastname (), type , accNum );
        this.bankAcc = bankAcc;
    }

    public String getFirstname () {
        return firstname;
    }

    public Account getBankAcc() {
        return bankAcc;
    }

    public String getLastname() {
        return lastname;
    }

    public void getProduct() {
        System.out.println ("Received Approval.");
        if(bankAcc.getState () == Account.State.PENDING) {
            bankAcc.setActiveState ();
        }
    }

    public void getDetails() {
        System.out.println ("\n");
        System.out.println ("This account belongs to:  " + getFirstname () + " " + getLastname () );
        System.out.println ("Account Type: " + bankAcc.getType ());
        System.out.println ("Account Number: " + bankAcc.getAccNum ());
        System.out.println ("STATE: " + bankAcc.getState ());
        System.out.println ("Amount Owing: " + bankAcc.amountOwing ());
    }
}
