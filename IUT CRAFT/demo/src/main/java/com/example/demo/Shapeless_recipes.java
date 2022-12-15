package com.example.demo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class Shapeless_recipes extends Bloc_decorateur {


    public Shapeless_recipes(Bloc shaped) {
        super(shaped);
    }


    public List<String> getNom() throws Exception {
        Object obj = new JSONParser().parse(new FileReader("demo/src/main/resources/com/example/demo/merged_recipes.json"));
        JSONObject jo = (JSONObject) obj;
        HashMap item = new HashMap<>();
        for (int i = 0; i < jo.size(); i++) {
            item = (HashMap) jo.get(jo.keySet().toArray()[i]);
            if (item.get("type").equals("crafting_shapeless")){
                shaped.ajoute(jo.keySet().toArray()[i].toString());
                //Collections.sort(nom);
            }
        }
        return shaped.getNom();
    }

    @Override
    public String toString() {
        try {
            return ""+getNom();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}