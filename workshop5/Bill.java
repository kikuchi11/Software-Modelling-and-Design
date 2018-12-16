import java.util.Calendar;
import java.util.Date;


public class Bill {
    private int billNumber;
    private int price;
    private Calendar dueDate;
    private BillState state;

    public enum BillState {ONSCHEDULE, LATE};

    public Bill (int price, Calendar dueDate, Calendar todaysDate) {
        this.price = price;
        this.dueDate = dueDate;
        checkStatus (todaysDate);
    }

    public int getPrice() {
        return price;
    }

    public Calendar getDueDate(){

        return dueDate;
    }

    public void checkStatus(Calendar todaysDate) {

        if(dueDate.after ( todaysDate )) {
            this.state = BillState.ONSCHEDULE;
        } else {
            this.state = BillState.LATE;
        }
    }

    public BillState getState() {
        return state;
    }
}
