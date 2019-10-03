package model.citydata;

public class WeatherInCity {
    private double temperature;
    private double humidity;
    private double pressure;
    private Wind wind;

    public WeatherInCity() {
    }

    public WeatherInCity(double temperature, double humidity, double pressure, Wind wind) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherInCity{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", wind=" + wind +
                '}';
    }
}
