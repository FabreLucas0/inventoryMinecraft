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
    private ListView<String> list_craft;

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

        // parsing JSON of all the recipes
        Object obj = new JSONParser().parse(new FileReader(new File("demo/src/main/resources/com/example/demo/merged_recipes.json")));
        JSONObject jo = (JSONObject) obj;

        // setting up variables
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

        // selecting the corresponding elements to search into depending on the selected filter
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


        // bubble sort for researchNames
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < researchNames.size() - 1; i++) {
                // alphabetical order
                if (researchNames.get(i).compareTo(researchNames.get(i + 1)) > 0 && alpha.isSelected()) {
                    String temp = researchNames.get(i);
                    researchNames.set(i, researchNames.get(i + 1));
                    researchNames.set(i + 1, temp);
                    sorted = false;
                }
                // reversed alphabetical order
                else if (researchNames.get(i + 1).compareTo(researchNames.get(i)) > 0 && reverse.isSelected()) {
                    String temp = researchNames.get(i + 1);
                    researchNames.set(i + 1, researchNames.get(i));
                    researchNames.set(i, temp);
                    sorted = false;
                }
            }
        }

        // if nothing is found
        if (researchNames.isEmpty()) {
            // displaying an error message and clearing the research
            bwiki.setVisible(false);
            error.setVisible(true);
            list_craft.getItems().clear();
        }

        // when a block is found, opens a new page
        else if (researchNames.contains(txt.getText())) {
            error.setVisible(false);
            blockName = new Label(txt.getText());
            ingredientsList = new ListView<>();

            //creating the new page
            FXMLLoader fxmlLoader = new FXMLLoader(new File("demo/src/main/resources/com/example/demo/RecipesViewer.fxml").toURL());
            GridPane root = fxmlLoader.load();
            Scene scene = new Scene(root, 710, 600);
            Stage recipe = new Stage();
            recipe.setTitle("Votre Craft");
            scene.getStylesheets().add(String.valueOf(this.getClass().getResource("style.css")));

            // getting the necessary data for displaying
            HashMap<String, Object> item = new HashMap<>((HashMap<String, Object>)jo.get(txt.getText().replaceAll(" ", "_")));
            // for shapeless recipes
            if(item.get("type").equals("crafting_shapeless")){

                // getting the ingredients
                ArrayList<Object> ingredient = (ArrayList<Object>) item.get("ingredients");
                ObservableList<String> ingredientsShapeless = FXCollections.observableArrayList();
                for (int i=0; i < ingredient.size(); i++){
                    ingredientsShapeless.add(((HashMap) ingredient.get(i)).get("item").toString().replaceAll("minecraft:", "").replaceAll("_", " "));
                }

                //placing everything on the scene (on the window)
                ingredientsList.setItems(ingredientsShapeless);
                root.add(ingredientsList, 1, 0);
                blockName.setText(blockName.getText() + " (Shapeless)");
            }
            // for shaped recipes
            else{
                // Creating a label for the recipe
                Label pattern = new Label("");
                ArrayList<String> blockPattern = (ArrayList<String>)item.get("pattern");

                // getting the 3x3 recipe
                int j = 0;
                for (int i = 0; i < blockPattern.size(); i++) {
                    j = 3 - blockPattern.get(i).length();
                    pattern.setText(pattern.getText() + blockPattern.get(i) + " ".repeat(j) + "\n");
                    if(blockPattern.size() < 3){
                        blockPattern.add("   ");
                    }
                }
                // recplacing empty spaces with a random character to keep the 3x3 format
                pattern.setText(pattern.getText().replaceAll(" ", "▢"));

                // getting ingredients
                HashMap<String, Object> ingredient = (HashMap<String, Object>) item.get("key");
                ObservableList<String> temp = FXCollections.observableArrayList(ingredient.keySet());
                for(int i=0; i < temp.size(); i++){
                    // if there is multiple kind of the same block
                    if((ingredient.get(temp.get(i)) instanceof ArrayList<?>)){
                        temp.set(i, temp.get(i) + " = any " + ((HashMap<String,Object>)((ArrayList<Object>) ingredient.get(temp.get(i))).get(0)).get("item").toString().replaceAll("minecraft:","").replaceAll("_", " "));
                    }
                    else {
                        HashMap<String, Object> itemData = (HashMap<String, Object>) ingredient.get(temp.get(i));
                        temp.set(i, temp.get(i) + " = " + itemData.get("item").toString().replaceAll("minecraft:", "").replaceAll("_", " "));
                    }
                }

                //placing everything on the scene (on the window)
                ingredientsList.setItems(temp);
                root.add(ingredientsList, 1,1);
                root.add(pattern, 0, 1);
                pattern.setFont(new Font(60));
                pattern.setPadding(new Insets(0,0,0,70));
                blockName.setText(blockName.getText() + " (Shaped)");
            }

            // placing the block's name and launching the scene
            blockName.setFont(new Font(17));
            root.add(blockName, 0, 0);
            recipe.setScene(scene);
            recipe.showAndWait();
        }
        // when nothing was found
        else {
            bwiki.setVisible(false);
            error.setVisible(false);

            // clear previous researches and display new ones
            list_craft.getItems().clear();
            list_craft.getItems().addAll(researchNames);

            // when clicking in an element in the list, it automatically fills the search bar
            list_craft.setOnMouseClicked(event -> {
                txt.setText(list_craft.getSelectionModel().getSelectedItem());
            });
        }

        System.out.println();
    }

    // fill the researchNames list with found blocks from the research
    private void element(String text, ArrayList<String> researchNames, String element) {
        if (element.contains(text)) {
            researchNames.add(element.replaceAll("_", " "));
        }
        // display wiki button
        if (element.equals(text)) {
            bwiki.setVisible(true);
            bwiki.setOnAction(event -> {
                Recherche find = new Recherche("https://minecraft.fandom.com/wiki/" + element);
                find.start();
            });
        }
    }

    // changing the researches to only "lshaped" type (shaped or shapeless)
    private void element(String text, ArrayList<String> researchNames, Recipies lshaped) throws Exception {
        for (String element : lshaped.getNom()) {

            element(text, researchNames, element);
        }
    }
}






