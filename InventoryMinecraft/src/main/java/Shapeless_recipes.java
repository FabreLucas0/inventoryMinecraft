import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.*;
import java.util.Collections;

public class Shapeless_recipes implements Bloc{
    public List<String> nom;

    public Shapeless_recipes() {
        this.nom = new ArrayList<>();
    }

    public List<String> getNom() {
        return nom;
    }

    @Override
    public void cherche_nom() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("src/main/resources/merged_recipes.json"));
        JSONObject jo = (JSONObject) obj;
        HashMap item = new HashMap<>();
        for (int i = 0; i < jo.size(); i++) {
            item = (HashMap) jo.get(jo.keySet().toArray()[i]);
            if (item.get("type").equals("crafting_shapeless")){
                nom.add(jo.keySet().toArray()[i].toString());
                Collections.sort(nom);
            }
        }



    }
}
