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
        Object obj = new JSONParser().parse(new FileReader("/amuhome/t21204574/Projet Qualité/IUT CRAFT/demo/src/main/resources/com/example/demo/merged_recipes.json"));
        JSONObject jo = (JSONObject) obj;
        HashMap item;
        for (int i = 0; i < jo.size(); i++) {
            item = (HashMap) jo.get(jo.keySet().toArray()[i]);
            if (item.get("type").equals("crafting_shaped")){
                System.out.println(item.get("group"));
                shaped.ajoute(jo.keySet().toArray()[i].toString());


                //Collections.sort(shaped);


            }
        }
        return shaped.getNom();
    }
}