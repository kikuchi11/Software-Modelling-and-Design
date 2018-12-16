
public class Event {
	private int oldValue;
	private int newValue;
	private String type;
	
	public Event(int oldValue, int newValue, String type) {
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.type = type;
	}
	
	public int getOldValue() {
		return oldValue;
	}
	public void setOldValue(int oldValue) {
		this.oldValue = oldValue;
	}
	public int getNewValue() {
		return newValue;
	}
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
