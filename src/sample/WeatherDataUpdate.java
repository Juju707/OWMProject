package sample;

import javafx.collections.ObservableList;

/**
 * Class for updating the observable list with data
 * Implements the Observer interface
 * @author Juju
 * @version 1.0
 */
public class WeatherDataUpdate implements Observer {
    /**
     *Observable list to be updaated
     */
    private ObservableList<WeatherParameters> data;

    /**
     * Constructor for the class
     * @param data
     */
    public WeatherDataUpdate(ObservableList<WeatherParameters> data) {
        this.data = data;
    }

    /**
     * Gets the updated Weather Parameters and puts them in a list
     * @param weatherParameters
     */
    @Override
    public void update(WeatherParameters weatherParameters) {

        data.add(weatherParameters);
    }
}
