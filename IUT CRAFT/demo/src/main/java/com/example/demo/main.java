package com.example.demo;

import java.io.*;
import java.util.*;

import com.example.demo.Musique;
import com.example.demo.Recherche;
import org.json.simple.*;
import org.json.simple.parser.*;


public class main {




    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Musique musicThread = new Musique("src/main/resources/com/example/demo/Musique.wav");

            musicThread.start();
            Object obj = new JSONParser().parse(new FileReader("src/main/resources/com/example/demo/merged_recipes.json"));
            JSONObject jo = (JSONObject) obj;

            Scanner scanner = new Scanner(System.in);

            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> researchNames = new ArrayList<>();

            for (int i = 0; i < jo.size(); i++) {
                names.add(jo.keySet().toArray()[i].toString());
            }

            /*
            ArrayList<String> collor = (ArrayList<String>) jo.get("pattern");
            System.out.println(collor.get(0));
            */
            while(true) {
                System.out.print("Saisissez le texte à rechercher : ");
                String searchText = scanner.nextLine();
                System.out.print("Ordre (a : alphabetique, !a : alphabétique inverse, n'importe quoi : pas d'ordre) : ");
                String order = scanner.nextLine();
                System.out.println();

                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                int liseur = Integer.parseInt(br.readLine());

                Comparator comp = new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (order.equals("a"))
                            return o1.compareTo(o2);
                        else if (order.equals("!a")) {
                            return o2.compareTo(o1);
                        }
                        else{
                            return 0;
                        }
                    }
                };

                researchNames.clear();
                for (String element : names) {

                    if (element.contains(searchText)) {
                        researchNames.add(element);
                    }
                    if (element.equals(searchText)){

                            break;

                    }
                }
                researchNames.sort(comp);
                if(researchNames.isEmpty()){
                    System.out.println("recherche non trouvée");
                }
                else if(researchNames.size() == 1){
                    //mettre code pour affichage craft ici
                }
                else {
                    for (String name : researchNames) {
                        System.out.println(name);

                    }
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
