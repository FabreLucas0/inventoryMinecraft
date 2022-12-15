package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;



public class IutCraftController {
    @FXML
    private Label error;

    @FXML
    private TextField texte;

    @FXML
    private CheckBox alpha;

    @FXML
    private CheckBox reverse;

    @FXML
    private CheckBox Shaped;

    @FXML
    private CheckBox Shapeless;


    @FXML
    private ListView list_craft;

    @FXML
    private Button bwiki;



    public void unCheckAlph(){
        alpha.setSelected(false);
    }

    public void unCheckReve(){
        reverse.setSelected(false);
    }

    public void unCheckSh(){
        Shaped.setSelected(false);
    }

    public void unCheckShl(){
        Shapeless.setSelected(false);
    }



    Comparator comp = new Comparator<String>() {
        public int compare(String o1, String o2) {
            if (alpha.selectedProperty().getValue() == true)
                return o1.compareTo(o2);
            else if (reverse.selectedProperty().getValue() == true) {
                return o2.compareTo(o1);
            } else {
                return 0;
            }
        }
    };


    @FXML
    protected void ClickButton() throws Exception {


        Object obj = new JSONParser().parse(new FileReader(new File("src/main/resources/com/example/demo/merged_recipes.json")));
        JSONObject jo = (JSONObject) obj;
        String text = texte.getText();
        error.setText("recherche non trouvée");


        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> researchNames = new ArrayList<>();
        Recipies lshaped = new Shaped_recipes(new Bloc());
        Recipies shapeless = new Shapeless_recipes(new Bloc());


        for (int i = 0; i < jo.size(); i++) {
            names.add(jo.keySet().toArray()[i].toString());

        }


        researchNames.clear();

        if(Shaped.selectedProperty().getValue() == true) {

            element(text, researchNames, lshaped);
        }
        else if(Shapeless.selectedProperty().getValue() == true) {
            element(text, researchNames, shapeless);
        }
        else {
            for (String element : names) {

                element(text, researchNames, element);
            }
        }


        researchNames.sort(comp);
        if (researchNames.isEmpty()) {
            bwiki.setVisible(false);
            error.setVisible(true);
            list_craft.getItems().clear();
        } else if (researchNames.size() == 1) {
            error.setVisible(false);
            //mettre code pour affichage craft ici
        } else {
            bwiki.setVisible(false);
            error.setVisible(false);
            list_craft.getItems().clear();
            list_craft.getItems().addAll(researchNames);
            list_craft.setOnMouseClicked(event -> {
                texte.setText((String) list_craft.getSelectionModel().getSelectedItem());
            });
        }

        System.out.println();
    }

    private void element(String text, ArrayList<String> researchNames, String element) {
        if (element.contains(text)) {
            researchNames.add(element);
        }
        if (element.equals(text)) {
            bwiki.setVisible(true);
            bwiki.setOnAction(event -> {
                Recherche find = new Recherche("https://minecraft.fandom.com/wiki/" + element);
                find.start();
            });
        }
    }

    private void element(String text, ArrayList<String> researchNames, Recipies lshaped) throws Exception {
        for (String element : lshaped.getNom()) {

            element(text, researchNames, element);
        }
    }
}






