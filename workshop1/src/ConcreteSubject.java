import java.util.ArrayList;


public class ConcreteSubject implements Subject {

	private ArrayList<Observer> ObserverList;
	
	public ConcreteSubject() {
		ObserverList = new ArrayList<Observer>();
	}
	
	@Override
	public void registerObserver(Observer observer) {
		ObserverList.add(observer);
	}

	@Override
	public void deregisterObserver(Observer observer) {
		ObserverList.remove(observer);
	}

	@Override
	public void notifyAllObservers() {
		for (Observer observer: ObserverList) {
			observer.notify();
		}
		
	}

	@Override
	public void printAllObservers() {
		for (Observer observer: ObserverList) {
			System.out.println(observer.getObserverName());
		}
		
	}

}
