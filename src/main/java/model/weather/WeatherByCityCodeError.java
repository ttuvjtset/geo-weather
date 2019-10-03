package model.weather;

public class WeatherByCityCodeError {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "WeatherByCityCodeError{" +
                "error='" + error + '\'' +
                '}';
    }
}
