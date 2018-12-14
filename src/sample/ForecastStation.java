package sample;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;

/**
 * Class for getting forecast for 5 next days,every 3 hours
 * @author Juju
 * @version 1.0
 */
public class ForecastStation {
    /**
     * Observable List for storing all of the Forecast Parameters
     */
    private ObservableList<ForecastParameters> data =
        FXCollections.observableArrayList(
                new ForecastParameters());
    /**
     * Variable for containing city's ID for calling the waether forecast method
     */
    private int cityID;
    /**
     * Variable for getting chosen units
     */
    private String units;

    /**
     * Gets list with forecast parameters
     * @return
     */
    public ObservableList<ForecastParameters> getData() {
        return data;
    }

    /**
     * Constructor for the object with city ID and chosen units as parameters
     * @param cityID
     * @param units
     */
    public ForecastStation(int cityID,String units) {
        this.cityID=cityID;
        this.units=units;
    }

    /**
     * Method that uses owm-japis for skipping the connection and mapping steps
     * Calls a new OpenWeatherMap object (owm) by users ID,sets proper unit and calls hourlyForecast
     * In a loop forecast parameters are being called out for all object where each is 3 hours apart from another
     * The parameters are added to the list
     * If no forecast is found, throws an exception
     */
    public void getForecast(){
        String ID = "953ae9dd6351dce7a117a1d204669199";
        OWM owm = new OWM(ID);
        if (units.equals("Metric")) owm.setUnit(OWM.Unit.METRIC);
        else if(units.equals("Imperial"))owm.setUnit(OWM.Unit.IMPERIAL);
        else owm.setUnit(OWM.Unit.STANDARD);
        try {
            HourlyWeatherForecast hwf= owm.hourlyWeatherForecastByCityId(cityID);

            for(int i=0;i<hwf.getDataList().size();i++){
                ForecastParameters forecastParameters = new ForecastParameters();
                forecastParameters.setTimeStamp(hwf.getDataList().get(i).getDateTimeText());
                forecastParameters.setTemp(hwf.getDataList().get(i).getMainData().getTemp());
                forecastParameters.setMaxTemp(hwf.getDataList().get(i).getMainData().getTempMax());
                forecastParameters.setMinTemp(hwf.getDataList().get(i).getMainData().getTempMin());
                forecastParameters.setPressure(hwf.getDataList().get(i).getMainData().getPressure());
                forecastParameters.setSeaLevel(hwf.getDataList().get(i).getMainData().getSeaLevel());
                forecastParameters.setGroundLevel(hwf.getDataList().get(i).getMainData().getGroundLevel());
                forecastParameters.setHumidity(hwf.getDataList().get(i).getMainData().getHumidity());
                forecastParameters.setInfo(hwf.getDataList().get(i).getWeatherList().get(0).getMoreInfo());
                forecastParameters.setWindSpeed(hwf.getDataList().get(i).getWindData().getSpeed());
                data.add(forecastParameters);
            }
            data.remove(0);

        } catch (APIException e) {
            throw new IllegalArgumentException("Incorrect City ID");
        }


    }

}
