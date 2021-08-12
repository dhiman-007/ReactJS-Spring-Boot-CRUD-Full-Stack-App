package utility;

import java.util.*;

public class Util {

    public String getBaseURL(){
        final String ACCESS_KEY = "26373c60fe133792361d0a9fbc0c1bec";
        String URL = "http://api.weatherstack.com/current?access_key="+ACCESS_KEY;
        return URL;
    }

    public List<String> quotes(){
        List<String> quotes = new ArrayList<String>(Arrays.asList(
                "“That which does not kill us makes us stronger.”",
                "“Be who you are and say what you feel, because those who mind don’t matter and those who matter don’t mind.”",
                "“We must not allow other people’s limited perceptions to define us.”",
                "“Be yourself; everyone else is already taken.”",
                "“This above all: to thine own self be true.”"
        ));
        return quotes;
    }

}
