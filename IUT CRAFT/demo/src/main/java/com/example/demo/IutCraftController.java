package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;


public class IutCraftController {
    @FXML
    private Label erreur;

    @FXML
    private TextField toto;

    @FXML
    private CheckBox alpha;

    @FXML
    private CheckBox reverse;

    @FXML
    private CheckBox Shaped;

    @FXML
    private CheckBox Shapeless;

    @FXML
    private CheckBox wiki;

    @FXML
    private ListView list_craft;

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

    public void clearListView() {
        ListView<String> items = (ListView<String>) list_craft.getItems();
        items.refresh();
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
    protected void onHelloButtonClick() throws IOException, ParseException {
        // Lire la valeur du champ de texte
        Object obj = new JSONParser().parse(new FileReader(new Fwile("demo/src/main/resources/com/example/demo/merged_recipes.json")));
        JSONObject jo = (JSONObject) obj;
        String text = toto.getText();
        erreur.setText("recherche non trouvée");
        //Scene recipes = new Scene(fxmlLoader.load(), 710, 600);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> researchNames = new ArrayList<>();

        for (int i = 0; i < jo.size(); i++) {
            names.add(jo.keySet().toArray()[i].toString());
        }

        researchNames.clear();
        for (String element : names) {

            if (element.contains(text)) {
                researchNames.add(element);
            }
            if (element.equals(text)) {
                if (wiki.selectedProperty().getValue() == true) {
                    Recherche find = new Recherche("https://minecraft.fandom.com/wiki/" + element);
                    find.start();
                }
            }
        }
        researchNames.sort(comp);
        if (researchNames.isEmpty()) {
            erreur.setVisible(true);
            list_craft.getItems().clear();
        } else if (researchNames.size() == 1) {
            erreur.setVisible(false);
            //mettre code pour affichage craft ici
        } else {
            erreur.setVisible(false);
            list_craft.getItems().clear();
            list_craft.getItems().addAll(researchNames);
            list_craft.setOnMouseClicked(event -> {
                toto.setText((String) list_craft.getSelectionModel().getSelectedItem());
            });
        }
        System.out.println();
    }
}






