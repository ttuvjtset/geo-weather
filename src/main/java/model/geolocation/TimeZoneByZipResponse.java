package model.geolocation;

public class TimeZoneByZipResponse {
    private String timeZone;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "TimeZoneByZipResponse{" +
                "timeZone='" + timeZone + '\'' +
                '}';
    }
}
