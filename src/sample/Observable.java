package sample;

/**
 * Interface for object being observable
 * @author Juju
 * @version 1.0
 */
public interface Observable {
    /**
     * Adds observer
     * @param observer
     */
    public void addObserver(Observer observer);

    /**
     * Removes observer
     * @param observer
     */
    public void removeObserver(Observer observer);

    /**
     * Notifies observers about the updates
     */
    public void notifyObservers();


}
