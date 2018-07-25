package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG_NAME = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json))
            return null;
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            // mainName
            String mainName = sandwichName.getString("mainName");
            JSONArray alsoKnownAsJSON = sandwichName.getJSONArray("alsoKnownAs");
            // nicknames
            List<String> alsoKnownAs = jsonArrayToList(alsoKnownAsJSON);
            // placeOfOrigin
            String placeOfOrigin = sandwichJson.optString("placeOfOrigin");
            //description
            String description = sandwichJson.getString("description");
            // image url
            String image = sandwichJson.getString("image");
            // ingredients
            JSONArray ingredientsJSON = sandwichJson.getJSONArray("ingredients");
            List<String> ingredients = jsonArrayToList(ingredientsJSON);
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.v(TAG_NAME, "An error occurred: ", e);
        }

        return sandwich;
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++)
                list.add(jsonArray.getString(i));
        } catch (JSONException e) {
            Log.e(TAG_NAME, "An error occured", e);
        }
        return list;
    }
}
