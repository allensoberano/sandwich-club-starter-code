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

        ImageView sandwichImageIV = findViewById(R.id.image_iv);


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
                .into(sandwichImageIV);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView mAlsoKnownTV = (TextView) findViewById(R.id.also_known_tv);
        TextView mAKAlabel = (TextView) findViewById(R.id.also_known_label);

        TextView mIngredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        TextView mIngLabel = (TextView) findViewById(R.id.ingredients_label);

        TextView mOriginTV = (TextView) findViewById(R.id.origin_tv);
        TextView mOrginLabel = (TextView) findViewById(R.id.place_of_origin_label);

        TextView mDescriptionTV = (TextView) findViewById(R.id.description_tv);
        TextView mDescLabel  = (TextView) findViewById(R.id.description_label);


        //** Hide labels when null else show Label and Text
        //using TextUtils.join because String.join needed api26

        if (sandwich.getAlsoKnownAs() != null) {
            mAlsoKnownTV.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            mAKAlabel.setVisibility(View.GONE);
        }

        if (sandwich.getIngredients() != null) {
            mIngredientsTV.setText(TextUtils.join(", ", sandwich.getIngredients()));
        } else {
            mIngLabel.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrginLabel.setVisibility(View.GONE);
        } else {
            mOriginTV.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()) {
            mDescLabel.setVisibility(View.GONE);
        } else {
            mDescriptionTV.setText(sandwich.getDescription());
        }


    }
}
