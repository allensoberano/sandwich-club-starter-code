package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {

            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject sandwhichName = sandwichDetails.getJSONObject("name");

            String mainName = sandwhichName.getString("mainName");
            JSONArray alsoKnownAs = sandwhichName.getJSONArray("alsoKnownAs");

            String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");
            String description = sandwichDetails.getString("description");
            String imageLink = sandwichDetails.getString("image");
            JSONArray ingredients = sandwichDetails.getJSONArray("ingredients");

            //Start sandwich making
            sandwich = new Sandwich();

            //using setters from Sandwich object to create the sandwhich information
            sandwich.setMainName(mainName);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(imageLink);

            //Converts to List<string> before setting
            sandwich.setAlsoKnownAs(convertJSONArray(alsoKnownAs));
            sandwich.setIngredients(convertJSONArray(ingredients));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    //Converts JSONArray to List<String>
    private static List<String> convertJSONArray(JSONArray jArr) throws JSONException {
        List<String> arrList = null;

        if (jArr.length() > 0){
            arrList = new ArrayList<>();
            for(int i=0; i<jArr.length(); i++) {
                arrList.add(jArr.getString(i));
            }

            return arrList;
        }

        return arrList;
    }

}

