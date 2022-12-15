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


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class IutCraftController {
    @FXML
    private Label error;

    @FXML
    private TextField txt;

    @FXML
    private CheckBox alpha;

    @FXML
    private CheckBox reverse;

    @FXML
    private CheckBox Shaped;

    @FXML
    private CheckBox Shapeless;

    @FXML
    private ListView<String> ingredientsList;

    @FXML
    private Label pattern;

    @FXML
    private Label blockName;

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


    @FXML
    protected void ClickButton() throws Exception {


        Object obj = new JSONParser().parse(new FileReader(new File("src/main/resources/com/example/demo/merged_recipes.json")));
        JSONObject jo = (JSONObject) obj;
        String text = txt.getText().replaceAll(" ", "_");
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
            bwiki.setVisible(false);
            error.setVisible(true);
            list_craft.getItems().clear();
        } else if (researchNames.contains(txt.getText())) {
            error.setVisible(false);
            blockName = new Label(txt.getText());
            ingredientsList = new ListView<>();
            FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/com/example/demo/RecipesViewer.fxml").toURL());
            GridPane root = fxmlLoader.load();
            Scene scene = new Scene(root, 710, 600);
            Stage recipe = new Stage();
            recipe.setTitle("Votre Craft");
            scene.getStylesheets().add(String.valueOf(this.getClass().getResource("style.css")));
            HashMap<String, Object> item = new HashMap<>((HashMap<String, Object>)jo.get(txt.getText().replaceAll(" ", "_")));
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
                // Creating a label for the recipe
                Label pattern = new Label("");
                ArrayList<String> blockPattern = (ArrayList<String>)item.get("pattern");
                int j = 0;
                for (int i = 0; i < blockPattern.size(); i++) {
                    j = 3 - blockPattern.get(i).length();
                    pattern.setText(pattern.getText() + blockPattern.get(i) + " ".repeat(j) + "\n");
                    if(blockPattern.size() < 3){
                        blockPattern.add("   ");
                    }
                }
                pattern.setText(pattern.getText().replaceAll(" ", "▢"));

                HashMap<String, Object> ingredient = (HashMap<String, Object>) item.get("key");
                ObservableList<String> temp = FXCollections.observableArrayList(ingredient.keySet());
                for(int i=0; i < temp.size(); i++){
                    if((ingredient.get(temp.get(i)) instanceof ArrayList<?>)){
                        temp.set(i, temp.get(i) + " = any " + ((HashMap<String,Object>)((ArrayList<Object>) ingredient.get(temp.get(i))).get(0)).get("item").toString().replaceAll("minecraft:","").replaceAll("_", " "));
                    }
                    else {
                        HashMap<String, Object> itemData = (HashMap<String, Object>) ingredient.get(temp.get(i));
                        temp.set(i, temp.get(i) + " = " + itemData.get("item").toString().replaceAll("minecraft:", "").replaceAll("_", " "));
                    }
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
        } else {
            bwiki.setVisible(false);
            error.setVisible(false);
            list_craft.getItems().clear();
            list_craft.getItems().addAll(researchNames);
            list_craft.setOnMouseClicked(event -> {
                txt.setText((String) list_craft.getSelectionModel().getSelectedItem());
            });
        }

        System.out.println();
    }

    private void element(String text, ArrayList<String> researchNames, String element) {
        if (element.contains(text)) {
            researchNames.add(element.replaceAll("_", " "));
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