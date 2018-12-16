
public class ConcreteObserver extends Observer {
	
	public ConcreteObserver(String ObserverName) {
		this.setObserverName(ObserverName);
	}
	@Override
	public void notify(Subject s, Event e) {
		System.out.format("The event is %s, and it's notified, The subject is \n", e.getType());
	}
}
