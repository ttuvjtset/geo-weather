package model.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeZoneByZipRequest {
    private String zipCode;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "TimeZoneByZipRequest{" +
                "zipCode='" + zipCode + '\'' +
                '}';
    }
}
