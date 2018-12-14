package sample;

import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
/**
 * Class for displaying humidity chart
 * @author Juju
 * @version 1.0
 */
public class DisplayHumidityChart implements Observer,DisplayWeather{
    /**
     * Weather Parameters for updating data series
     */
    private WeatherParameters weatherParameters;
    /**
     * XY Series to allow adding data to chart
     */
    private XYChart.Series<Number, Number> series=new XYChart.Series<>();
    /**
     * Chart to display
     */
    private ScatterChart<Number, Number> scatterChart;
    /**
     * Variable for number of measurement
     */
    private int number;
    /**
     * Constructor for the object to display chart
     * @param scatterChart
     */
    public DisplayHumidityChart(ScatterChart<Number, Number> scatterChart) {
        this.scatterChart = scatterChart;
        series.setName("Humidity");
        number=0;
    }
    /**
     * Update data series with new weather parameters and increase number of measurement by one
     * @param weatherParameters
     */
    @Override
    public void update(WeatherParameters weatherParameters) {

        Platform.runLater(() -> {
            if (weatherParameters!=null) {
                series.getData().add(new XYChart.Data<>(number, weatherParameters.getHumidity()));
                number++;}
        });
    }
    /**
     * Method to display updated chart
     * The data needs to be removed every time otherwise an exception will be thrown for double data series
     */
    @Override
    public void display() {
        scatterChart.setAnimated(false);
        update(weatherParameters);
        scatterChart.getData().removeAll(scatterChart.getData());
        scatterChart.getData().addAll(series);
    }

}
