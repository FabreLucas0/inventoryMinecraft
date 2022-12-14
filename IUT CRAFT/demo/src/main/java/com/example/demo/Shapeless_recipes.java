package com.example.demo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class Shapeless_recipes extends Bloc_decorateur {


    public Shapeless_recipes(Bloc shapeless) {
        super(shapeless);
    }


    public List<String> getNom() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("src/main/resources/com/example/demo/merged_recipes.json"));
        JSONObject jo = (JSONObject) obj;
        HashMap item = new HashMap<>();
        for (int i = 0; i < jo.size(); i++) {
            item = (HashMap) jo.get(jo.keySet().toArray()[i]);
            if (item.get("type").equals("crafting_shapeless")){
                shapeless.ajoute(jo.keySet().toArray()[i].toString());
                //Collections.sort(nom);
            }
        }
        return shapeless.getNom();
    }
}