package utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {
    public String convertRequestToString(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(request.getInputStream()));
        return reader.lines().map(String :: trim).collect(Collectors.joining("\n"))
                .replace("\n", "").replace("\r", "");
    }
}
