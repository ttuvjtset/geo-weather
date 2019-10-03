package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import model.geolocation.TimeZoneByZipError;
import model.geolocation.TimeZoneByZipRequest;
import model.geolocation.TimeZoneByZipResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.GeoLocationService;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/api/geolocation"})
public class GeoLocationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LogManager.getLogger(controller.GeoLocationController.class);

    private GeoLocationService geoLocationService = new GeoLocationService();;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        String requestString = new Util().convertRequestToString(request);

        logger.info("Request: " + requestString);

        String errorText = null;
        TimeZoneByZipRequest timezoneRequest = null;

        try {
            timezoneRequest = mapper.readValue(requestString, TimeZoneByZipRequest.class);
        } catch (MismatchedInputException e) {
            errorText = "Request must be in JSON format";
        }

        if (timezoneRequest != null && timezoneRequest.getZipCode() != null) {
            TimeZoneByZipResponse timeZoneResponse = new TimeZoneByZipResponse();
            String timeZoneByZip;

            try {
                timeZoneByZip = geoLocationService.getTimeZoneByZip(timezoneRequest.getZipCode());
                timeZoneResponse.setTimeZone(timeZoneByZip);
                response.setHeader("Content-Type", "application/json");
                response.getWriter().print(new ObjectMapper().writeValueAsString(timeZoneResponse));
                logger.info("Response: " + response.getStatus() + " " + timeZoneResponse);
                return;

            } catch (IllegalArgumentException e) {
                errorText = e.getMessage();
            }
        }

        if (errorText == null) {
            errorText = "Zip code not specified";
        }

        TimeZoneByZipError timeZoneByZipError = new TimeZoneByZipError();
        timeZoneByZipError.setError(errorText);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(new ObjectMapper().writeValueAsString(timeZoneByZipError));
        logger.info("Response: " + response.getStatus() + " " + timeZoneByZipError);

    }

}
