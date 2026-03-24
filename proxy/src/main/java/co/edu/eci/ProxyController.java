package co.edu.eci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ProxyController {

    private static final String USER_AGENT = "Mozilla/5.0";
    
    private final String server1 = System.getenv("SERVER1_URL");
    private final String server2 = System.getenv("SERVER2_URL");

     private final AtomicInteger counter = new AtomicInteger(0);

    private String callServer(String serverUrl, String endpoint, String paramName, String value) {
        try {

            String fullUrl = serverUrl + endpoint + "?" + paramName + "=" + value;

            URL obj = new URL(fullUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
                );
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }

            return null; 

        } catch (Exception e) {
            return null;
        }
    }

    private String roundRobin(String endpoint, String value) {

        int index = counter.getAndIncrement() % 2;

        String currentServer;
        String otherServer;
        String currentName;
        String otherName;

        if (index == 0) {
            currentServer = server1;
            otherServer   = server2;
            currentName   = "SERVER1";
            otherName     = "SERVER2";
        } else {
            currentServer = server2;
            otherServer   = server1;
            currentName   = "SERVER2";
            otherName     = "SERVER1";
        }

        String response = callServer(currentServer, endpoint, value);
        if (response != null) {
            return currentName + ": " + response;
        }

        response = callServer(otherServer, endpoint, value);
        if (response != null) {
            return otherName + ": " + response;
        }

        return "Error: ambos servidores están caídos";
    }

    @GetMapping("/fibwin")
    public String fibwin(@RequestParam("value") String value) {
        return roundRobin("/fibwin", value);
    }
}
    