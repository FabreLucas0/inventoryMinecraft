
///home/lucas/moi/an2/qualite_de_dev/projet/inventoryMinecraft/inventoryMinecraft/src/main/resources/test.json
package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("/home/lucas/moi/an2/qualite_de_dev/projet/inventoryMinecraft/inventoryMinecraft/src/main/resources/test.json"));
            JSONObject jsonObject = (JSONObject)obj;
            String name = (String)jsonObject.get("nom");
            System.out.println("Name: " + name);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
