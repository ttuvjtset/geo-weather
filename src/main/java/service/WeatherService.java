package service;

import model.citydata.WeatherInCity;
import model.citydata.Wind;

import java.util.HashMap;

public class WeatherService {

    private HashMap<Integer, WeatherInCity> internalSimpleDB = new HashMap<>();

    public WeatherService() {
        internalSimpleDB.put(1, new WeatherInCity(5.5, 80.5, 801.3,
                new Wind("East", "2-3")));
    }

    public WeatherInCity getTemperatureByCityCode(String cityCode) {
        Integer cityCodeInt;

        try {
            cityCodeInt = Integer.valueOf(cityCode);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("City code must be numeric");
        }

        if (internalSimpleDB.containsKey(cityCodeInt)) {
            return internalSimpleDB.get(cityCodeInt);
        } else {
            throw new IllegalArgumentException("City code not found");
        }
    }
}
