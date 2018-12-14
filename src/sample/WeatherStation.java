package sample;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Juju
 * @version 1.0
 * A class which creates a connection with the OpenWeatherMap and gets Weather Parameters for given city in set units
 * Class implements 2 interfaces
 */
public class WeatherStation implements Observable, Runnable {
    /**
     * Units attribute that is set as "standard" by default
     */
    private String units = "standard";
    /**
     * City attribute that is set as blank by default
     */
    private String city = ""; //Miasto ktore podane przez uzytkownika zostanie wklejone do adresu
    /**
     * Result attribute which will store result of getting data form the server
     */
    private String result;
    /**
     * ArrayList that will contain all the observers
     */
    private volatile ArrayList<Observer> observers = new ArrayList<>();
    /**
     * Boolean that specifies whether the thread is currently running
     */
    private volatile boolean isRunning = false;
    /**
     * Thread for this class
     */
    private Thread worker;
    /**
     * Time value that specifies how long will the thread sleeps
     */
    private int interval;
    /**
     * Constantly updating parameters while the thread is running
     */
    private WeatherParameters weatherParameters;

    /**
     * Constructor for the class
     *
     * @param city
     * @param units
     * @param interval
     */
    public WeatherStation(String city, String units, int interval) {
        this.interval = interval;
        this.city = city;
        this.units = units;
    }

    /**
     * Get the interval value
     *
     * @return interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * Get the state of thread
     *
     * @return isRunning
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Get the units string
     *
     * @return units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Set units string
     *
     * @param units
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Gets the city string
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city String
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets Weather Parameters
     *
     * @return WeatherParameters
     */
    public WeatherParameters getWeatherParameters() {
        return weatherParameters;
    }

    /**
     * Sets WeatherParameters
     *
     * @param weatherParameters
     */
    public void setWeatherParameters(WeatherParameters weatherParameters) {
        this.weatherParameters = weatherParameters;
    }

    /**
     * A method to get current weather from OpenWeatherMap
     * It creates the url from set parameters in the constructor
     * The method gets a response from server,gets data in for of string buffer
     * After getting a string it maps the main response in form of Gson and sets appropriate values to weather parameters
     */
    private void getWeather() {
        String beginning = "http://api.openweathermap.org/data/2.5/weather?q="; //Początek adresu
        String end = "&APPID="; //Koniec adresu
        String unitsString = "&units=";
        String ID = "953ae9dd6351dce7a117a1d204669199";

        StringBuffer response = new StringBuffer();
        String url;
        if (units.toLowerCase().equals("standard"))
            url = beginning + city + end + ID;
        else
            url = beginning + city + unitsString + units + end + ID;
        //api.openweathermap.org/data/2.5/weather?q=London&APPID=953ae9dd6351dce7a117a1d204669199"
        //"http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&units=metric&APPID=953ae9dd6351dce7a117a1d204669199";
        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Map m = gson.fromJson(result, Map.class);

        String output = m.get("main").toString();

        output = output.replaceAll("[^\\d.\\s \\-]", "");

        String[] parameters = output.split(" ");

        weatherParameters = new WeatherParameters();
        weatherParameters.setTemp(Double.parseDouble(parameters[0]));
        weatherParameters.setPressure(Double.parseDouble(parameters[1]));
        weatherParameters.setHumidity(Double.parseDouble(parameters[2]));
        weatherParameters.setMinTemp(Double.parseDouble(parameters[3]));
        weatherParameters.setMaxTemp(Double.parseDouble(parameters[4]));


    }

    /**
     * Adds observer to list if the observer isn't on the list already
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {

        if (!observers.contains(observer)) observers.add(observer);

    }

    /**
     * Removes observer from the list if the observer is on the list
     *
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {

        if (observers.contains(observer)) observers.remove(observer);

    }

    /**
     * Sends weather parameters update to all the observers on the list
     */
    @Override
    public void notifyObservers() {

        for (Observer o : observers)
            o.update(weatherParameters);

    }

    /**
     * Starts thread
     */
    void start() {
        worker = new Thread(this, "Weather thread");
        worker.start();
    }

    /**
     * Pauses the running thread
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Interrupts the working thread
     */
    void interrupt() {
        isRunning = false;
        worker.interrupt();
    }

    /**
     *The method sets the running value to true and runs the thread
     * It calls the local getWeather method, changes local time to string format and puts the thread to sleep if the connection is successful
     * If running the thread fails, the method throws an exception
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {

            try {
                getWeather();
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                weatherParameters.setTimeStamp(time);
                notifyObservers();
                worker.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }

        }

    }
}
