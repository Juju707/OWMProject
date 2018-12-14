package sample;

/**
 * Interface for observing
 * @author Juju
 * @version 1.0
 */
public interface Observer {
    /**
     * Method for updatting current weather parameters
     * @param weatherParameters
     */
    public void update(WeatherParameters weatherParameters);

}
