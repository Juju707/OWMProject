package sample;

/**
 * @author Juju
 * @version 1.0
 * Class constructed for storing measured values of parameters
 */
public class ForecastParameters {
    /**
     *Private time stamp values as String
     */
    private String timeStamp;
    /**
     *Private temperature variable of double type
     */
    private double temp;
    /**
     *Private temperature minimal variable of double type
     */
    private double minTemp;
    /**
     *Private temperature maximal variable of double type
     */
    private double maxTemp;

    /**
     *Private pressure variable of double type
     */
    private double pressure;
    /**
     *Private sea level variable of double type
     */
    private double seaLevel;
    /**
     *Private ground level variable of double type
     */
    private double groundLevel;
    /**
     * Private humidity variable of double type
     */
    private double humidity;
    /**
     *Private extra info variable of String type
     */
    private String info;
    /**
     *Private wind speed variable of double type
     */
    private double windSpeed;

    /**
     * Gets time stamp
     * @return timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets time stamp
     * @param timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets temperature
     * @return temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Sets temperature
     * @param temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * Gets minimal temperature
     * @return minTemp
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * Sets minimal temperature
     * @param minTemp
     */
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    /**
     * Gets maximal temperature
     * @return maxTemp
     */
    public double getMaxTemp() {
        return maxTemp;
    }

    /**
     * Sets minimal temperature
     * @param maxTemp
     */
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    /**
     * Gets pressure
     * @return pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Sets pressure
     * @param pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Gets sea level
     * @return seaLevel
     */
    public double getSeaLevel() {
        return seaLevel;
    }

    /**
     * Sets sea level
     * @param seaLevel
     */
    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     * Gets ground level
     * @return groundLevel
     */
    public double getGroundLevel() {
        return groundLevel;
    }

    /**
     * Sets ground level
     * @param groundLevel
     */
    public void setGroundLevel(double groundLevel) {
        this.groundLevel = groundLevel;
    }

    /**
     * Gets humidity
     * @return humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Sets humidity
     * @param humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets extra info about weather
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets extra info about weather
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets wind speed
     * @return windSpeed
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets wind speed
     * @param windSpeed
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Returns String values of all parameters in one row
     * @return string
     */
    @Override
    public String toString() {
        return timeStamp+"   "+temp+"   "+maxTemp+"   "+minTemp+"   "+humidity+"   "+pressure+"   "+seaLevel+"   "+groundLevel+"   "+windSpeed+"   "+info;
    }
}
