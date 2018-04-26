package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.MiscUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAs;
    private TextView alsoKnownAs_label;
    private TextView placeOfOrigin;
    private TextView placeOfOrigin_label;
    private TextView description;
    private TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        alsoKnownAs_label = (TextView) findViewById((R.id.also_known_label));
        placeOfOrigin = (TextView) findViewById(R.id.origin_tv);
        placeOfOrigin_label = (TextView) findViewById(R.id.origin_label);
        description = (TextView) findViewById(R.id.description_tv);
        ingredients = (TextView) findViewById(R.id.ingredients_tv);


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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(sandwich.getAlsoKnownAs().size()>0) {
            alsoKnownAs.setText(MiscUtils.getDisplayStringFromListArray(sandwich.getAlsoKnownAs()));
        }else {
            alsoKnownAs.setVisibility(View.GONE);
            alsoKnownAs_label.setVisibility(View.GONE);
        }
        if(sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOrigin.setVisibility(View.GONE);
            placeOfOrigin_label.setVisibility(View.GONE);
        } else {
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        description.setText(sandwich.getDescription());
        ingredients.setText(MiscUtils.getDisplayStringFromListArray(sandwich.getIngredients()));
    }
}
