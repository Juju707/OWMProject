package sample;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class for displaying data in text fields
 * Class implements Observer and DisplayWeather interfaces
 * @author Juju
 * @version 1.0
 */
public class TextAreaDisplayWeather implements Observer,DisplayWeather {
    /**
     *Current weather parameters for comparing with other attributes
     */
    private  WeatherParameters weatherParameters;
    /**
     * Text field with maximal temperature value in all series
     */
    private TextField maxTemp;
    /**
     * Storage variable for maximal temperature value
     */
    private double maxtemp;
    /**
     *Text field with maximal humidity value in all series
     */
    private TextField maxHum;
    /**
     *Storage variable for maximal humidity value
     */
    private double maxhum;
    /**
     *Text field with maximal pressure value in all series
     */
    private TextField maxPress;
    /**
     * Storage variable for maximal pressure value
     */
    private double maxpress;
    /**
     *Text field with minimal temperature value in all series
     */
    private TextField minTemp;
    /**
     * Storage variable for minimal temperature value
     */
    private double mintemp;
    /**
     *Text field with minimal humidity value in all series
     */
    private TextField minHum;
    /**
     * Storage variable for minimal humidity value
     */
    private double minhum;
    /**
     *Text field with minimal pressure value in all series
     */
    private TextField minPress;
    /**
     * Storage variable for minimal pressure value
     */
    private double minpress;
    /**
     *Text field with temperature's standard deviation value
     */
    private TextField stdTemp;
    /**
     *Storage variable for temperature's standard deviation value
     */
    private double stdtemp;
    /**
     *Text field with pressure's standard deviation value
     */
    private TextField stdPress;
    /**
     *Storage variable for pressure's standard deviation value
     */
    private double stdpress;
    /**
     *Text field with humidity's standard deviation value
     */
    private TextField stdHum;
    /**
     * Storage variable for humidity's standard deviation value
     */
    private double stdhum;
    /**
     *Text field with current number of measure
     */
    private TextField number;
    /**
     * Storage variable for current number of measure
     */
    private int measNumber;
    /**
     *Array list with weather parameters
     */
    private ArrayList<WeatherParameters> list=new ArrayList<>();

    /**
     * Constructor with text fields for display as parameters
     * @param weatherParameters
     * @param number
     * @param maxTemp
     * @param maxHum
     * @param maxPress
     * @param minTemp
     * @param minHum
     * @param minPress
     * @param stdTemp
     * @param stdPress
     * @param stdHum
     */
    public TextAreaDisplayWeather(WeatherParameters weatherParameters, TextField number,TextField  maxTemp, TextField  maxHum, TextField maxPress, TextField minTemp, TextField minHum, TextField  minPress, TextField  stdTemp, TextField  stdPress, TextField  stdHum) {
        //
        this.weatherParameters = weatherParameters;
        this.list=list;
        this.maxTemp = maxTemp;
        this.maxHum = maxHum;
        this.maxPress = maxPress;
        this.minTemp = minTemp;
        this.minHum = minHum;
        this.minPress = minPress;
        this.stdTemp = stdTemp;
        this.stdPress = stdPress;
        this.stdHum = stdHum;
        this.number = number;
        measNumber=0;
    }

    /**
     * Method for displaying values in appropriate text fields and checking whether a value is maximal or minimal
     */
    @Override
    public void display() {
        Platform.runLater(()->{
            number.setText(String.valueOf(measNumber));
            DecimalFormat format = new DecimalFormat("##0.###");
            if (measNumber>=1){
                //Min and max
                if(list.size()==1){
                    minPress.setText(String.valueOf(list.get(0).getPressure()));
                    minHum.setText(String.valueOf(list.get(0).getHumidity()));
                    minTemp.setText(String.valueOf(list.get(0).getTemp()));
                    maxPress.setText(String.valueOf(list.get(0).getPressure()));
                    maxHum.setText(String.valueOf(list.get(0).getHumidity()));
                    maxTemp.setText(String.valueOf(list.get(0).getTemp()));
                } else {
                    if (maxtemp < list.get(list.size() - 1).getTemp()) maxTemp.setText(String.valueOf(list.get(list.size() - 1).getTemp()));
                    if (mintemp > list.get(list.size() - 1).getTemp()) minTemp.setText(String.valueOf(list.get(list.size() - 1).getTemp()));
                    if (minpress >list.get(list.size() - 1).getPressure()) maxPress.setText(String.valueOf(list.get(list.size() - 1).getHumidity()));
                    if (maxhum < list.get(list.size() - 1).getHumidity()) maxHum.setText(String.valueOf(list.get(list.size() - 1).getHumidity()));
                    if (maxpress < list.get(list.size() - 1).getPressure()) minPress.setText(String.valueOf(list.get(list.size() - 1).getPressure()));
                    if (minhum > list.get(list.size() - 1).getHumidity()) minHum.setText(String.valueOf(list.get(list.size() - 1).getPressure()));

                    stdTemp.setText(format.format(stdtemp));
                    stdPress.setText(format.format(stdpress));
                    stdHum.setText(format.format(stdhum));

                }
            }
        });
        measNumber++;
    }

    /**
     *Gets the updated weather parameters
     * @param weatherParameters
     */
    @Override
    public void update(WeatherParameters weatherParameters) {

        this.weatherParameters=weatherParameters;
        if (weatherParameters!=null) {
            list.add(weatherParameters);
            standardDeviations();
        }
        display();

    }

    /**
     * Calculates standard deviations
     */
    public void standardDeviations(){
        //Standard deviations
        double meanTemp = 0.0;
        double meanPress = 0.0;
        double meanHum = 0.0;

        for (WeatherParameters o : list) {
            meanTemp += o.getTemp();
            meanPress += o.getPressure();
            meanHum += o.getHumidity();
        }
        meanTemp = meanTemp / list.size();
        meanPress = meanPress / list.size();
        meanHum = meanHum / list.size();

        double varTemp = 0.0;
        double varPress = 0.0;
        double varHum = 0.0;

        for (WeatherParameters o : list) {
            varTemp += Math.pow((o.getTemp() - meanTemp), 2);
            varPress += Math.pow((o.getPressure() - meanPress), 2);
            varHum += Math.pow((o.getHumidity() - meanHum), 2);
        }
        if (list.size() > 1) {
            varTemp = varTemp / (list.size() - 1);
            varPress = varPress / (list.size() - 1);
            varHum = varHum / (list.size() - 1);
        }
        stdhum = Math.sqrt(varHum);
        stdtemp = Math.sqrt(varTemp);
        stdpress = Math.sqrt(varPress);
    }
}
