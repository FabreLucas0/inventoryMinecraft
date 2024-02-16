package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Shapeless_recipesTest {

    Bloc bloc = new Bloc();
    List<String> noms = new ArrayList<>(Arrays.asList("magenta_dye_from_lapis_red_pink", "mossy_cobblestone", "cyan_bed_from_white_bed", "bone_meal_from_block", "writable_book", "lime_bed_from_white_bed", "light_gray_dye_from_ink_bonemeal", "yellow_dye_from_sunflower", "brown_concrete_powder", "yellow_dye_from_dandelion", "light_gray_wool", "light_gray_concrete_powder", "cyan_concrete_powder", "red_wool", "black_wool", "yellow_bed_from_white_bed", "mushroom_stew", "purple_wool", "lime_wool", "orange_wool", "purple_bed_from_white_bed", "light_gray_dye_from_oxeye_daisy", "gray_dye", "light_blue_dye_from_blue_orchid", "light_blue_wool", "magenta_dye_from_lilac", "red_dye_from_tulip", "flint_and_steel", "light_blue_bed_from_white_bed", "light_gray_dye_from_gray_bonemeal", "red_bed_from_white_bed", "green_concrete_powder", "orange_dye_from_orange_tulip", "yellow_concrete_powder", "brown_wool", "pink_dye_from_pink_tulip", "pink_dye_from_red_bonemeal", "black_bed_from_white_bed", "orange_concrete_powder", "green_wool", "magenta_dye_from_lapis_ink_bonemeal", "gray_bed_from_white_bed", "orange_bed_from_white_bed", "pink_bed_from_white_bed", "gray_concrete_powder", "bone_meal_from_bone", "trapped_chest", "white_concrete_powder", "cyan_wool", "andesite", "lime_dye", "ender_eye", "magenta_dye_from_allium", "red_concrete_powder", "light_blue_concrete_powder", "magenta_concrete_powder", "light_blue_dye_from_lapis_bonemeal", "brown_bed_from_white_bed", "red_dye_from_beetroot", "book", "pumpkin_pie", "yellow_wool", "magma_cream", "pink_concrete_powder", "purple_dye", "fire_charge", "mossy_stonebrick", "light_gray_dye_from_white_tulip", "red_dye_from_poppy", "blue_wool", "orange_dye_from_red_yellow", "magenta_wool", "blaze_powder", "purple_concrete_powder", "magenta_dye_from_purple_and_pink", "magenta_bed_from_white_bed", "pink_dye_from_peony", "pink_wool", "lime_concrete_powder", "light_gray_bed_from_white_bed", "light_gray_dye_from_azure_bluet", "red_dye_from_rose_bush", "gray_wool", "fermented_spider_eye", "green_bed_from_white_bed", "cyan_dye", "black_concrete_powder", "blue_bed_from_white_bed", "granite", "blue_concrete_powder")) ;
    Shapeless_recipes shapelessRecipes;
    @BeforeEach
    void setUp() {
        shapelessRecipes = new Shapeless_recipes(bloc);

    }
    @Test
    void getNom() throws Exception {
        assertEquals(shapelessRecipes.getNom(),noms);
    }
}