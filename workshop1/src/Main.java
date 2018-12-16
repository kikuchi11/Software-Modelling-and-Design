
public class Main {
	public static void main(String[] args) {
		Event event1 = new Event(0, 1, "earthquake");
		Event event2 = new Event(0, 1, "draught");
		ConcreteSubject subject1 = new ConcreteSubject();
		ConcreteSubject subject2 = new ConcreteSubject();
		
		ConcreteObserver observer1 = new ConcreteObserver("observer1");
		ConcreteObserver observer2 = new ConcreteObserver("observer2");
		ConcreteObserver observer3 = new ConcreteObserver("observer3");
		ConcreteObserver observer4 = new ConcreteObserver("observer4");
		
		subject1.registerObserver(observer1);
		subject1.registerObserver(observer2);
		subject2.registerObserver(observer3);
		subject2.registerObserver(observer4);
		
		subject1.printAllObservers();
		subject2.printAllObservers();
		
		subject1.deregisterObserver(observer1);
		subject1.printAllObservers();
		
		subject2.deregisterObserver(observer3);
		subject2.printAllObservers();
		
		
		
	}
}
