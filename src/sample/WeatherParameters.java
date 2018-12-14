package sample;

/**
 * @author Juju
 * @version 1.0
 * Class constructed for storing measured values of parameters
 */
public class WeatherParameters {

    /**
     * Private temperature attribute of double type
     */
    private double temp;
    /**
     * Private pressure attribute of double type
     */
    private double pressure;
    /**
     *Private humidity attribute of double type
     */
    private double humidity;
    /**
     * Private minimal temperature of double type
     */
    private double minTemp;
    /**
     * Private maximal attribute of double type
     */
    private double maxTemp;
    /**
     * Private time attribute of String type
     */
    private String timeStamp;

    /**
     * Gets time stamp string
     * @return timeStamp
     */
    public String getTimeStamp() { return timeStamp; }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets temperature value
     * @return temperature
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Sets temperature value
     * @param temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * Gets pressure value
     * @return pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Sets pressure value
     * @param pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Gets humidity value
     * @return humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Sets humidity value
     * @param humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets minimal temperature value
     * @return
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * Sets minimal temperature value
     * @param minTemp
     */
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    /**
     * Gets maximal temperature value
     * @return
     */
    public double getMaxTemp() {
        return maxTemp;
    }

    /**
     * Sets maximal temperature value
     * @param maxTemp
     */
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    /**
     * Returns all values in one row in String type
     * @return values String
     */
    @Override
    public String toString() {
        return temp + "  " + pressure + "  " + humidity + "  " + minTemp + "  " + maxTemp + "   " + timeStamp;
    }
}
