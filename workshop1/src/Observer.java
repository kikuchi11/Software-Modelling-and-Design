
public abstract class Observer {
	
	private String ObserverName;
	
	public String getObserverName() {
		return ObserverName;
	}
	public void setObserverName(String observerName) {
		ObserverName = observerName;
	}
	public abstract void notify(Subject s, Event e);
}


