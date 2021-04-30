package pu.fmi.rainytime.models;

public class WeatherReport {
    private City city;
    private String weather;
    private double temp;
    private String timestamp;

    public WeatherReport() {

    }
    public WeatherReport(City city, String weather, double temp, String timestamp) {
        this.city = city;
        this.weather = weather;
        this.temp =  Math.round(temp * 100.0) / 100.0;;
        this.timestamp = timestamp;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
