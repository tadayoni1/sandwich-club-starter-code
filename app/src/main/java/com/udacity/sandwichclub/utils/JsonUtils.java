package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();


    private final static String NAME = "name";  // json object
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJsonObject = new JSONObject(json);
            Sandwich sandwich = new Sandwich();

            sandwich.setMainName(sandwichJsonObject.getJSONObject(NAME).getString(MAIN_NAME));

            JSONArray alsoKnownAsJSONArray = sandwichJsonObject.getJSONObject(NAME).getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAsArray = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJSONArray.length(); i++) {
                alsoKnownAsArray.add((String) alsoKnownAsJSONArray.get(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAsArray);

            sandwich.setPlaceOfOrigin(sandwichJsonObject.getString(PLACE_OF_ORIGIN));

            sandwich.setDescription(sandwichJsonObject.getString(DESCRIPTION));

            sandwich.setImage(sandwichJsonObject.getString(IMAGE));

            JSONArray ingredientsJSONArray = sandwichJsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                ingredients.add((String) ingredientsJSONArray.get(i));
            }
            sandwich.setIngredients(ingredients);

            return sandwich;
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Unable to parse JSON String: " + json.substring(0, 30) + " ....");
            e.printStackTrace();
        }
        return null;
    }
}
