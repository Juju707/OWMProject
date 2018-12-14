package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import net.aksingh.owmjapis.model.CurrentWeather;


import java.io.*;

/**
 * Controller class that controls GUI's behavior
 */
public class Controller {
    /**
     * List for current weather list for reading  list of cities
     */
    private CurrentWeather[] list;
    /**
     * String for name of city
     */
    private String city;
    /**
     * Variable for city's ID
     */
    private Integer cityID;
    /**
     * Thread for reading weather
     */
    private WeatherStation station;
    /**
     *Thread for progress bar
     */
    private Thread progress;
    /**
     *Observable list for all weather data
     */
    private final ObservableList<WeatherParameters> data =
            FXCollections.observableArrayList(
                    new WeatherParameters());
    /**
     *Actual weather parameters
     */
    private WeatherParameters weatherParameters = new WeatherParameters();
    /**
     * Chart series for loaded from file temperature
     */
    private XYChart.Series<Number, Number> loadtempSeries;
    /**
     * Chart series for loaded from file pressure
     */
    private XYChart.Series<Number, Number> loadpressSeries;
    /**
     * Chart series for loaded from file humidity
     */
    private XYChart.Series<Number, Number> loadhumSeries;
    /**
     * Object to display temperature
     */
    private DisplayTemperatureChart temperatureChart;
    /**
     * Object to display humidity
     */
    private DisplayHumidityChart humidityChart;
    /**
     * Object to display pressure
     */
    private DisplayPressureChart pressureChart;
    /**
     * Variable for stroing the interval value
     */
    private int interval;

    /**
     * X axis for current weather chart
     */
    @FXML
    private NumberAxis xAxis;
    /**
     * Y axis for current weather chart
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * X axis for loaded weather chart
     */
    @FXML
    private NumberAxis xAxis1;
    /**
     * Y axis for loaded weather chart
     */
    @FXML
    private NumberAxis yAxis1;
    /**
     * Chart for current weather
     */
    @FXML
    private ScatterChart<Number, Number> currentWeatherChart;
    /**
     * Text field for choosing city
     */
    @FXML
    private TextField chooseCityField;
    /**
     * Progress bar for displaying measure progress
     */
    @FXML
    private ProgressBar progressBar;
    /**
     * Table view for the current weather values
     */
    @FXML
    private TableView<WeatherParameters> valuesTable;
    /**
     * Time table column for current weather
     */
    @FXML
    private TableColumn<WeatherParameters, String> timeColumn;
    /**
     * Temperature table column for current weather
     */
    @FXML
    private TableColumn<WeatherParameters, Number> tempColumn;
    /**
     * Humidity table column for current weather
     */
    @FXML
    private TableColumn<WeatherParameters, Number> humidityColumn;
    /**
     * Pressure table column for current weather
     */
    @FXML
    private TableColumn<WeatherParameters, Number> pressureColumn;
    /**
     * Choice box to choose units for current weather
     */
    @FXML
    private ChoiceBox<String> chooseUnitsCbox;
    /**
     * Choice box to choose units for forcast weather
     */
    @FXML
    private ChoiceBox<String> forecastChooseUnits;
    /**
     * Label to sygnalize a wrong city
     */
    @FXML
    private Label wrongCity;
    /**
     * Text field for temperature's standard deviation
     */
    @FXML
    private TextField tempStdField;
    /**
     * Text field for humidity's standard deviation
     */
    @FXML
    private TextField humStdField;
    /**
     * Text field for pressure's standard deviation
     */
    @FXML
    private TextField pressStdField;
    /**
     * Text field for maximal temperature value
     */
    @FXML
    private TextField maxTempField;
    /**
     * Text field for maximal humidity value
     */
    @FXML
    private TextField maxHumField;
    /**
     * Text field for maximal pressure value
     */
    @FXML
    private TextField maxPressField;
    /**
     * Text field for minimal temperature value
     */
    @FXML
    private TextField minTempField;
    /**
     * Text field for minimal humidity value
     */
    @FXML
    private TextField minHumField;
    /**
     * Text field for minimal pressure value
     */
    @FXML
    private TextField minPressField;
    /**
     * Text field for input file name
     */
    @FXML
    private TextField fileNameField;
    /**
     * Text field for number of measurement
     */
    @FXML
    private TextField measureNumberField;
    /**
     * Text field for displaying time
     */
    @FXML
    private TextField timeField;
    /**
     * Label to sygnalize incorrectly time value
     */
    @FXML
    private Label timeWarning;
    /**
     * Label to sygnalize that measuring is in progress
     */
    @FXML
    private Label measingProgressLabel;
    /**
     * Table view for loaded values from file
     */
    @FXML
    private TableView<WeatherParameters> loadValuesTable;
    /**
     * Text file for choosing file to be loaded
     */
    @FXML
    private TextField chooseFileField;
    /**
     * Chart for series loaded form a file
     */
    @FXML
    private ScatterChart<Number, Number> loadWeatherChart;
    /**
     * Label to sygnalize success in saving a file
     */
    @FXML
    private Label success;
    /**
     * Label to sygnalize choosing a file name that does not exist
     */
    @FXML
    private Label wrongFile;
    /**
     * Table view for loaded forecast
     */
    @FXML
    private TableView<ForecastParameters> forecastTable;
    /**
     * Time and date table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, String> forecastTime;
    /**
     * Temperature table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastTemperature;
    /**
     * Maximal temperature table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastMaxTemp;
    /**
     * Minimal temperature table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastMinTemp;
    /**
     * Pressure table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastPressure;
    /**
     * Sea level table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastSeaLevel;
    /**
     * Ground level table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastGroundLevel;
    /**
     * Humidity table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastHumidity;
    /**
     * Wind speed table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, Number> forecastWindSpeed;
    /**
     * Weather additional information table column for forecast
     */
    @FXML
    private TableColumn<ForecastParameters, String> forecastExtraInfo;
    /**
     * Label sygnalising choosing wrong city
     */
    @FXML
    private Label forecastWrongCity;
    /**
     * Text field for choosing city to get forecast for
     */
    @FXML
    private TextField forecastCity;

    /**
     * A method that is called upon staring program to:
     * Initialize all the values
     * Hide labels that signalise incorrect usages
     * Adds units to both choiceboxes
     * Sets the color of special labels
     * Sets the default text
     * Configures axises
     */
    @FXML
    public void initialize() {

        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2.add("Metric");
        list2.add("Standard");
        list2.add("Imperial");
        chooseUnitsCbox.setItems(list2);
        forecastChooseUnits.setItems(list2);

        chooseUnitsCbox.setValue("Metric");
        forecastChooseUnits.setValue("Metric");

        chooseCityField.setText("London");
        forecastCity.setText("London");

        timeField.setText("10");

        loadList();

        wrongCity.setTextFill(Color.RED);
        forecastWrongCity.setTextFill(Color.RED);
        wrongFile.setTextFill(Color.RED);
        success.setTextFill(Color.GREEN);

        forecastWrongCity.setVisible(false);
        wrongFile.setVisible(false);
        success.setVisible(false);
        wrongCity.setVisible(false);
        currentWeatherChart.setVisible(false);
        success.setVisible(false);
        timeWarning.setVisible(false);
        measingProgressLabel.setVisible(false);

        //remove null measure
        data.remove(0);

        maxPressField.setText("0");
        minPressField.setText("0");
        maxHumField.setText("0");
        minHumField.setText("0");
        maxTempField.setText("0");
        minTempField.setText("0");
        fileNameField.setText("Measure");
        chooseFileField.setText("Measures");

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        xAxis.setTickUnit(1);
        yAxis.setTickUnit(1);
        xAxis.setLabel("Nr.of measurement");

    }

    /**
     * A method that upon pressing the "start button":
     * Removes all previous data
     * Creates new Object to display current weather
     * Starts the Weather Station thread
     * Sets the table columns
     * Adds observers
     * Sets temperature chart as the default chart
     */
    @FXML
    void addToTableAndChart() {
        data.clear();
        data.removeAll();
        currentWeatherChart.getData().removeAll(currentWeatherChart.getData());
        measureNumberField.clear();


        temperatureChart = new DisplayTemperatureChart(currentWeatherChart);
        pressureChart = new DisplayPressureChart(currentWeatherChart);
        humidityChart = new DisplayHumidityChart(currentWeatherChart);

        checkCity();
        String units = chooseUnitsCbox.getSelectionModel().getSelectedItem().toLowerCase();
        checkInterval();
        if (interval != 0) {
            station = new WeatherStation(city, units, interval);
            WeatherDataUpdate dataUpdate = new WeatherDataUpdate(data);
            TextAreaDisplayWeather number = new TextAreaDisplayWeather(weatherParameters, measureNumberField, maxTempField, maxHumField, maxPressField, minTempField, minHumField, minPressField, tempStdField, pressStdField, humStdField);

            station.start();
            progressing();
            measingProgressLabel.setVisible(true);

            timeColumn.setCellValueFactory(
                    new PropertyValueFactory<>("timeStamp")
            );
            tempColumn.setCellValueFactory(
                    new PropertyValueFactory<>("temp")
            );
            pressureColumn.setCellValueFactory(
                    new PropertyValueFactory<>("pressure")
            );
            humidityColumn.setCellValueFactory(
                    new PropertyValueFactory<>("humidity")
            );
            valuesTable.setItems(data);
            station.addObserver(dataUpdate);
            station.addObserver(number);
            station.addObserver(temperatureChart);
            station.addObserver(pressureChart);
            station.addObserver(humidityChart);
            tempChart();
        }
    }

    /**
     * A method that upon pressing the button "interreupt":
     * interrupts all the threads
     */
    @FXML
    void interruptMeasure() {
        if (station != null) {
            station.interrupt();
            progress.interrupt();
            measingProgressLabel.setVisible(false);
        }
    }

    /**
     * A method that upon pressing button "continue":
     * Will start threads anew but will keep the previous data
     */
    @FXML
    void continueMeasure() {
        if (station != null) {
            if (data.size() == 0) throw new IllegalThreadStateException("Thread hasn't been started");
            station.start();
            success.setVisible(false);
            progressing();
            measingProgressLabel.setVisible(true);
        }
    }

    /**
     * A method that upon pressing button "humidity chart":
     * Will remove all the previous data form chart and display a humidity chart for current weather
     */
    @FXML
    void humChart() {
        if (station != null) {
            currentWeatherChart.setVisible(true);
            currentWeatherChart.getData().removeAll(currentWeatherChart.getData());
            yAxis.setLabel("Humidity");
            humidityChart.display();
        }

    }
    /**
     * A method that upon pressing button "pressure chart":
     * Will remove all the previous data form chart and display a pressure chart for current weather
     */
    @FXML
    void pressChart() {
        if (station != null) {
            currentWeatherChart.setVisible(true);
            currentWeatherChart.getData().removeAll(currentWeatherChart.getData());
            yAxis.setLabel("Pressure");
            pressureChart.display();
        }
    }
    /**
     * A method that upon pressing button "temperature chart":
     * Will remove all the previous data form chart and display a temperature chart for current weather
     */
    @FXML
    void tempChart() {
        if (station != null) {
            currentWeatherChart.setVisible(true);
            currentWeatherChart.getData().removeAll(currentWeatherChart.getData());
            yAxis.setLabel("Temperature");
            temperatureChart.display();
        }
    }
    /**
     * A method that upon pressing button "humidity chart":
     * Will remove all the previous data form chart and display a humidity chart for weather loaded from file
     */
    @FXML
    void loadHumChart() {
        if (loadhumSeries != null) {
            loadWeatherChart.getData().removeAll(loadWeatherChart.getData());
            loadWeatherChart.getData().addAll(loadhumSeries);
            yAxis1.setLabel("Humidity");
            xAxis1.setLabel("Nr. of measurement");
        }
    }
    /**
     * A method that upon pressing button "pressure chart":
     * Will remove all the previous data form chart and display a pressure chart for weather loaded from file
     */
    @FXML
    void loadPressChart() {
        if (loadpressSeries != null) {
            loadWeatherChart.getData().removeAll(loadWeatherChart.getData());
            loadWeatherChart.getData().addAll(loadpressSeries);
            yAxis1.setLabel("Pressure");
            xAxis1.setLabel("Nr. of measurement");
        }
    }
    /**
     * A method that upon pressing button "temperature chart":
     * Will remove all the previous data form chart and display a temperature chart for weather loaded from file
     */
    @FXML
    void loadTempChart() {
        if (loadtempSeries != null) {
            loadWeatherChart.getData().removeAll(loadWeatherChart.getData());
            loadWeatherChart.getData().addAll(loadtempSeries);
            yAxis1.setLabel("Temperature");
            xAxis1.setLabel("Nr. of measurement");
        }
    }
    /**
     * A method that upon pressing button "load data":
     * Will create a new Gson Object to map all of the objects inside the file
     * Will load all the objects
     * Will put the loaded values into proper columns in table
     * Will load data for charts
     * Will adjust the axises settings
     * Will throw an exception if the chosen file does not exist
     */
    @FXML
    void loadData() {

        WeatherParameters[] loadData;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        File file = new File((chooseFileField.getText()) + ".json");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            loadData = gson.fromJson(bufferedReader, WeatherParameters[].class);
            wrongFile.setVisible(false);
            loadValuesTable.getColumns().removeAll(loadValuesTable.getColumns());
            ObservableList<WeatherParameters> loadedData = FXCollections.observableArrayList(loadData);
            loadValuesTable.setItems(loadedData);
            TableColumn<WeatherParameters, String> loadTimeColumn = new TableColumn<>("Time");
            loadTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
            TableColumn<WeatherParameters, Number> loadTempColumn = new TableColumn<>("Temperature");
            loadTempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));
            TableColumn<WeatherParameters, Number> loadPressureColumn = new TableColumn<>("Pressure");
            loadPressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            TableColumn<WeatherParameters, Number> loadHumColumn = new TableColumn<>("Humidity");
            loadHumColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            loadValuesTable.getColumns().addAll(loadTimeColumn, loadTempColumn, loadPressureColumn, loadHumColumn);


            loadtempSeries = new XYChart.Series<>();
            loadtempSeries.setName("Temperature");
            loadpressSeries = new XYChart.Series<>();
            loadpressSeries.setName("Pressure");
            loadhumSeries = new XYChart.Series<>();
            loadhumSeries.setName("Humidity");


            if (loadData != null) {
                for (int i = 0; i < loadData.length; i++) {
                    loadtempSeries.getData().add(new XYChart.Data<>(i, loadData[i].getTemp()));
                    loadpressSeries.getData().add(new XYChart.Data<>(i, loadData[i].getPressure()));
                    loadhumSeries.getData().add(new XYChart.Data<>(i, loadData[i].getHumidity()));
                }
            }
        } catch (IOException e) {
            wrongFile.setVisible(true);
        }


        xAxis1.setAutoRanging(true);
        yAxis1.setAutoRanging(true);
        xAxis1.setTickUnit(1);
        yAxis1.setTickUnit(1);

    }
    /**
     * A method that upon pressing button "save file":
     * Will create a new Gson object
     * Will map all the stored data into a json file
     * Will save the file under given by the user name
     * Will throw exceptions if certain conditions are not met
     */
    @FXML
    private void saveFile() {
        if (station != null) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            if (!fileNameField.getText().equals("")) {
                success.setVisible(true);
                station.interrupt();
                File file = new File((fileNameField.getText()) + ".json");
                try (FileWriter fileWriter = new FileWriter(file)) {
                    gson.toJson(data, fileWriter);
                } catch (IOException e) {
                    System.out.println("I/O error");
                }
            } else throw new IllegalArgumentException("File name must exist");
            progress.interrupt();
        }
    }
    /**
     * A method that upon pressing button "get forecast":
     * Will set columns in the table
     * Will check whether the city name was chosen correctly
     * Will create a new Forecast station object
     * Will load acquired values in the table
     */
    @FXML
    void getForecast() {
        checkForecastCityID();
        ForecastStation fs = new ForecastStation(cityID, forecastChooseUnits.getSelectionModel().getSelectedItem());
        fs.getForecast();
        forecastTime.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        forecastTemperature.setCellValueFactory(new PropertyValueFactory<>("temp"));
        forecastMaxTemp.setCellValueFactory(new PropertyValueFactory<>("maxTemp"));
        forecastMinTemp.setCellValueFactory(new PropertyValueFactory<>("minTemp"));
        forecastPressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        forecastGroundLevel.setCellValueFactory(new PropertyValueFactory<>("groundLevel"));
        forecastSeaLevel.setCellValueFactory(new PropertyValueFactory<>("seaLevel"));
        forecastHumidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        forecastWindSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        forecastExtraInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
        forecastTable.setItems(fs.getData());
    }
    /**
     * A method that:
     * Will get the user's City name
     * Will check whether that name figures on the official map of cities from OpenWeatherMap
     * Will assign the proper city name or nothing,depending on the result of the search
     * Will trhow an exception if the city was picked incorrectly
     */
    private void checkCity() {
        //Sprawdzanie
        String writtenCity = chooseCityField.getText();
        city = "";
        wrongCity.setVisible(false);
        for (CurrentWeather aList : list) {
            if (writtenCity.equals(aList.getCityName())) {
                city = aList.getCityName();
                break;
            }
        }
        if (city.equals("")) {
            wrongCity.setVisible(true);
            throw new IllegalArgumentException("Incorrectly picked city");
        }
    }
    /**
     * A method that:
     * Will get the user's City name
     * Will check whether that name figures on the official map of cities from OpenWeatherMap
     * Will assign the proper city ID for city or nothing,depending on the result of the search
     * Will trhow an exception if the city was picked incorrectly
     */
    private void checkForecastCityID() {
        //Sprawdzanie
        String forecast = forecastCity.getText();
        city = "";
        forecastWrongCity.setVisible(false);
        for (CurrentWeather cw : list) {
            if (forecast.equals(cw.getCityName())) {
                cityID = cw.getCityId();
                break;
            } else {
                cityID = 0;
            }
        }
        if (cityID.equals(0)) {
            forecastWrongCity.setVisible(true);
            throw new IllegalArgumentException("Incorrectly picked city");
        }
    }
    /**
     * A method that:
     * Will load the city list from Gson file
     * Will trhow an exception if there was an input/output error
     */
    private void loadList() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        File file = new File("D:/Studia/5 semestr/ZPO/citylist.json");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            list = gson.fromJson(bufferedReader, CurrentWeather[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * A method that:
     * Will create a new thread for observing measurement progress
     * Will set proper labels visible
     */
    private void progressing() {
        Task<Void> task = new Task<>() {
            @Override
            public Void call() {
                for (int i = 0; i < 21; i++) {
                    try {
                        Thread.sleep(interval / 20);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        break;
                    }
                    updateProgress(i + 1, 20);
                    if (i == 20) i = 0;
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());
        progress = new Thread(task);
        progress.setDaemon(true);
        progress.start();
    }
    /**
     * A method that:
     * Will get the user's interval value
     * Will assign the proper time value in milliseconds if the written interval's value was above 1
     * Will throw an exception if the condition is not met
     */
    private void checkInterval() {
        double time = Double.valueOf(timeField.getText());
        if (time >= 1) {
            interval = (int) time * 1000;
            if (timeWarning.isVisible()) timeWarning.setVisible(false);
        } else {
            timeWarning.setVisible(true);
            throw new IllegalArgumentException("Interval is too small");
        }
    }

}





