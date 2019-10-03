package service;

import java.util.HashMap;

public class GeoLocationService {

    private HashMap<String, String> internalSimpleDB = new HashMap<>();

    public GeoLocationService() {
        internalSimpleDB.put("11111", "GMT +02:00");
    }

    public String getTimeZoneByZip(String zipCode) throws IllegalArgumentException {
        if (internalSimpleDB.containsKey(zipCode)) {
            return internalSimpleDB.get(zipCode);
        } else {
            throw new IllegalArgumentException("Zip code not found");
        }
    }

}
