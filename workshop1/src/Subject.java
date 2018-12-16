
public interface Subject {
	public void registerObserver(Observer observer);
	public void deregisterObserver(Observer observer);
	public void notifyAllObservers();
	public void printAllObservers();
}
