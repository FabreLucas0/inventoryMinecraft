package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class IutCraftController{
    @FXML
    private Label erreur;

    @FXML
    private Label blockName;

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
    private ListView<String> list_craft;

    @FXML
    private ListView<String> ingredientsList;

    @FXML
    private Label pattern;

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

    @FXML
    protected void onHelloButtonClick() throws IOException, ParseException {
        // Lire la valeur du champ de texte
        Object obj = new JSONParser().parse(new FileReader(new File("src/main/resources/merged_recipes.json")));
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
            element = element.replaceAll("_", " ");
            if (element.contains(text)) {
                researchNames.add(element);
            }
            if (element.equals(text)) {
                if (wiki.selectedProperty().getValue()) {
                    Recherche find = new Recherche("https://minecraft.fandom.com/wiki/" + element);
                    find.start();
                }
            }
        }

        // Algorithme de tri à bulles d'une liste
        boolean trie = false;
        while (!trie) {
            trie = true;
            for (int i = 0; i < researchNames.size() - 1; i++) {
                // par ordre alphabétique
                if (researchNames.get(i).compareTo(researchNames.get(i + 1)) > 0 && alpha.isSelected()) {
                    String temp = researchNames.get(i);
                    researchNames.set(i, researchNames.get(i + 1));
                    researchNames.set(i + 1, temp);
                    trie = false;
                }
                // par ordre alphabétique inverse
                else if (researchNames.get(i + 1).compareTo(researchNames.get(i)) > 0 && reverse.isSelected()) {
                    String temp = researchNames.get(i + 1);
                    researchNames.set(i + 1, researchNames.get(i));
                    researchNames.set(i, temp);
                    trie = false;
                }
            }
        }
        if (researchNames.isEmpty()) {
            erreur.setVisible(true);
            list_craft.getItems().clear();
        }
        else if (researchNames.contains(toto.getText())) {
            erreur.setVisible(false);
            blockName = new Label(toto.getText());
            ingredientsList = new ListView<>();
            FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/RecipesViewer.fxml").toURL());
            GridPane root = fxmlLoader.load();
            Scene scene = new Scene(root, 710, 600);
            Stage recipe = new Stage();
            HashMap<String, Object> item = new HashMap<>((HashMap<String, Object>)jo.get(toto.getText().replaceAll(" ", "_")));
            if(item.get("type").equals("crafting_shapeless")){
                ArrayList<Object> ingredient = (ArrayList<Object>) item.get("ingredients");
                ObservableList<String> ingredientsShapeless = FXCollections.observableArrayList();
                for (int i=0; i < ingredient.size(); i++){
                    ingredientsShapeless.add(((HashMap) ingredient.get(i)).get("item").toString().replaceAll("minecraft:", "").replaceAll("_", " "));
                }
                ingredientsList.setItems(ingredientsShapeless);
                root.add(ingredientsList, 1, 0);
                blockName.setText(blockName.getText() + " (Shapeless)");
            }
            else{

                Label pattern = new Label("");
                for (String line: (ArrayList<String>)item.get("pattern")) {
                    pattern.setText(pattern.getText() + line + "\n");
                }

                HashMap<String, Object> ingredient = (HashMap<String, Object>) item.get("key");
                ObservableList<String> temp = FXCollections.observableArrayList(ingredient.keySet());
                for(int i=0; i < temp.size(); i++){
                    HashMap<String, Object> itemData = (HashMap<String, Object>) ingredient.get(temp.get(i));
                    temp.set(i, temp.get(i) + " = " + itemData.get("item").toString().replaceAll("minecraft:","").replaceAll("_", " "));
                }
                ingredientsList.setItems(temp);
                root.add(ingredientsList, 1,1);
                root.add(pattern, 0, 1);
                pattern.setFont(new Font(60));
                pattern.setPadding(new Insets(0,0,0,70));
                blockName.setText(blockName.getText() + " (Shaped)");
            }
            blockName.setFont(new Font(17));
            root.add(blockName, 0, 0);
            recipe.setScene(scene);
            recipe.showAndWait();

        }
        else {
            System.out.println(toto.getText());
            erreur.setVisible(false);
            list_craft.getItems().clear();
            list_craft.getItems().addAll(researchNames);
            list_craft.setOnMouseClicked(event -> {
                toto.setText(list_craft.getSelectionModel().getSelectedItem());
            });
        }
    }
}






