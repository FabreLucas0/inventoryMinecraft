import java.awt.Desktop;
import java.net.URI;

public class Recherche extends Thread {
    private String url;

    public Recherche(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}