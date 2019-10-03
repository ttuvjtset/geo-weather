package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.geolocation.TimeZoneByZipError;
import model.geolocation.TimeZoneByZipRequest;
import model.geolocation.TimeZoneByZipResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import util.DelegatingServletInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class GeoLocationControllerTest extends Mockito {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private TimeZoneByZipRequest timeZoneByZipRequest;

    @Before
    public void setUp() throws Exception {
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.timeZoneByZipRequest = new TimeZoneByZipRequest();
    }

    @Test
    public void testZipCodeCorrect() throws Exception {
        timeZoneByZipRequest.setZipCode("11111");
        String json = new ObjectMapper().writeValueAsString(timeZoneByZipRequest);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GeoLocationController().doPost(request, response);

        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        TimeZoneByZipResponse timeZoneByZipResponse = new ObjectMapper().readValue(responseJSON, TimeZoneByZipResponse.class);
        assertEquals("GMT +02:00", timeZoneByZipResponse.getTimeZone());
    }

    @Test
    public void testZipCodeNotFound() throws Exception {
        timeZoneByZipRequest.setZipCode("1111");
        String json = new ObjectMapper().writeValueAsString(timeZoneByZipRequest);

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GeoLocationController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        TimeZoneByZipError timeZoneByZipError = new ObjectMapper().readValue(responseJSON, TimeZoneByZipError.class);
        assertEquals("Zip code not found", timeZoneByZipError.getError());
    }

    @Test
    public void testMissingAttributeInJSON() throws Exception {
        String json = "{\"zipCodee\":\"1\"}";

        when(request.getInputStream())
                .thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new GeoLocationController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        TimeZoneByZipError timeZoneByZipError = new ObjectMapper().readValue(responseJSON, TimeZoneByZipError.class);
        assertEquals("Zip code not specified", timeZoneByZipError.getError());
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

        new GeoLocationController().doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Mockito.verify(response).setHeader("Content-Type", "application/json");

        writer.flush();

        String responseJSON = stringWriter.toString();
        TimeZoneByZipError timeZoneByZipError = new ObjectMapper().readValue(responseJSON, TimeZoneByZipError.class);
        assertEquals("Request must be in JSON format", timeZoneByZipError.getError());
    }
}