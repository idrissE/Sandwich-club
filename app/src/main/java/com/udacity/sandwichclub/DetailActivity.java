package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ImageView sandwichImg = findViewById(R.id.image_iv);
        TextView mainNameTv = findViewById(R.id.main_name_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView originLabel = findViewById(R.id.origin_label);
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView alsoKnownAsLabel = findViewById(R.id.known_as_label);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView ingredientsLabel = findViewById(R.id.ingredients_label);
        // Image
        Picasso.get()
                .load(sandwich.getImage())
                .into(sandwichImg);
        // Main Name
        mainNameTv.setText(sandwich.getMainName());

        // origin
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin()))
            originTv.setText(sandwich.getPlaceOfOrigin());
        else {
            originTv.setVisibility(View.GONE);
            originLabel.setVisibility(View.GONE);
        }

        // known as
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            for (String nickName : sandwich.getAlsoKnownAs())
                alsoKnownAsTv.append(nickName + "\n");
        } else {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

        // ingredients
        if (!sandwich.getIngredients().isEmpty())
            for (String ingredient : sandwich.getIngredients())
                ingredientsTv.append(ingredient + "\n");
        else {
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabel.setVisibility(View.GONE);
        }

        // description
        descriptionTv.setText(sandwich.getDescription());
    }
}
