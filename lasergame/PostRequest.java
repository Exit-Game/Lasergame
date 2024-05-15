package lasergame;
import java.net.HttpURLConnection;
import java.net.URL;
public class PostRequest
{
    public static void request () //Request an die API senden, um den Parameter des Spiels, wenn es gewonnen wurde, auf True zu setzen
    {
        try
        {
            // URL und Verbindung vorbereiten
            URL url = new URL("http://192.168.213.6:5000/set-game/Lasergame");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Anforderungsmethode auf POST setzen
            conn.setRequestMethod("POST");

            // TimeOut setzen
            conn.setConnectTimeout(10);

            // Zum Senden von Anforderungsparametern
            conn.setDoOutput(true);

            // Antwortcode erhalten
            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            // Verbindung schließen
            conn.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
