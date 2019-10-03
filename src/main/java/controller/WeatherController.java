package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import model.citydata.WeatherInCity;
import model.weather.WeatherByCityCodeError;
import model.weather.WeatherByCityCodeRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.WeatherService;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/api/weather"})
public class WeatherController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LogManager.getLogger(controller.WeatherController.class);

    private WeatherService weatherService = new WeatherService();;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        String requestString = new Util().convertRequestToString(request);

        logger.info("Request: " + requestString);

        String errorText = null;
        WeatherByCityCodeRequest weatherRequest = null;

        try {
            weatherRequest = mapper.readValue(requestString, WeatherByCityCodeRequest.class);
        } catch (MismatchedInputException e) {
            errorText = "Request must be in JSON format";
        }

        if (weatherRequest != null && weatherRequest.getCityCode() != null) {
            WeatherInCity weatherInCity;

            try {
                weatherInCity = weatherService.getTemperatureByCityCode(weatherRequest.getCityCode());
                response.setHeader("Content-Type", "application/json");
                response.getWriter().print(new ObjectMapper().writeValueAsString(weatherInCity));
                logger.info("Response: " + response.getStatus() + " " + weatherInCity);
                return;

            } catch (IllegalArgumentException e) {
                errorText = e.getMessage();
            }
        }

        if (errorText == null) {
            errorText = "City code not specified";
        }

        WeatherByCityCodeError weatherByCityCodeError = new WeatherByCityCodeError();
        weatherByCityCodeError.setError(errorText);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(new ObjectMapper().writeValueAsString(weatherByCityCodeError));
        logger.info("Response: " + response.getStatus() + " " + weatherByCityCodeError);
    }

}