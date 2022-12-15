package com.example.demo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.*;


public class Shaped_recipes extends Bloc_decorateur {

    public Shaped_recipes(Bloc shaped) {
        super(shaped);
    }


    public List<String> getNom() throws Exception {
        Object obj = new JSONParser().parse(new FileReader(new File("src/main/resources/com/example/demo/merged_recipes.json")));
        JSONObject jo = (JSONObject) obj;
        HashMap item;
        for (int i = 0; i < jo.size(); i++) {
            item = (HashMap) jo.get(jo.keySet().toArray()[i]);
            if (item.get("type").equals("crafting_shaped")){
                shaped.ajoute(jo.keySet().toArray()[i].toString());



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