package controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import model.citydata.WeatherInCity;
import model.weather.WeatherByCityCodeError;
import model.weather.WeatherByCityCodeRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import util.DelegatingServletInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private WeatherByCityCodeRequest weatherByCityCodeRequest;

    @Before
    public void setUp() throws Exception {
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.weatherByCityCodeRequest = new WeatherByCityCodeRequest();
    }

    @Test
    public void testCityCodeCorrect() throws Exception {
        weatherByCityCodeRequest.setCityCode("1");
        String json = new ObjectMapper().writeValueAsString(weatherByCityCodeRequest);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new WeatherController().doPost(request, response);

        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        WeatherInCity weatherInCity = new ObjectMapper().readValue(responseJSON, WeatherInCity.class);
        assertEquals(5.5, weatherInCity.getTemperature(), 0.001);
        assertEquals(80.5, weatherInCity.getHumidity(), 0.001);
        assertEquals(801.3, weatherInCity.getPressure(), 0.001);
        assertEquals("East", weatherInCity.getWind().getDirection());
        assertEquals("2-3", weatherInCity.getWind().getStrength());
    }

    @Test
    public void testCityCodeNotNumeric() throws Exception {
        String json = "{\"cityCode\":\"a\"}";

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new WeatherController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        WeatherByCityCodeError weatherByCityCodeError = new ObjectMapper().readValue(responseJSON, WeatherByCityCodeError.class);
        assertEquals("City code must be numeric", weatherByCityCodeError.getError());
    }

    @Test
    public void testMissingAttributeInJSON() throws Exception {
        String json = "{\"cityCodee\":\"1\"}";

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new WeatherController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        WeatherByCityCodeError weatherByCityCodeError = new ObjectMapper().readValue(responseJSON, WeatherByCityCodeError.class);
        assertEquals("City code not specified", weatherByCityCodeError.getError());
    }

    @Test
    public void testCityCodeNotFound() throws Exception {
        weatherByCityCodeRequest.setCityCode("11");
        String json = new ObjectMapper().writeValueAsString(weatherByCityCodeRequest);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new WeatherController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        WeatherByCityCodeError weatherByCityCodeError = new ObjectMapper().readValue(responseJSON, WeatherByCityCodeError.class);
        assertEquals("City code not found", weatherByCityCodeError.getError());
    }

    @Test
    public void testEmptyJSON() throws Exception {
        String json = "";

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new WeatherController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        WeatherByCityCodeError weatherByCityCodeError = new ObjectMapper().readValue(responseJSON, WeatherByCityCodeError.class);
        assertEquals("Request must be in JSON format", weatherByCityCodeError.getError());
    }
}