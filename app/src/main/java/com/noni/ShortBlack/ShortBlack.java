package com.noni.ShortBlack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortBlack extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button submitButton;
    private EditText nameText;
    private String nameStringEntered;
    private String TAG = "ShortBlack.java";
    private JSONObject orderObject = new JSONObject();
    String choice;
    private Spinner mGenderChoicesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_black);
        //Define spinners, set adapters, apply adapters to arrays for Spinner choices.

        submitButton = (Button) findViewById(R.id.submitButton);
        nameText = (EditText) findViewById(R.id.Name);



        Spinner mAdditiveChoicesSpinner = (Spinner) findViewById(R.id.additiveChoicesSpinner);
        Spinner mOrderSizesSpinner = (Spinner) findViewById(R.id.orderSizesSpinner);
        Spinner mCoffeeTypeSpinner = (Spinner) findViewById(R.id.coffeeTypeSpinner);
        Spinner mMilkChoicesSpinner = (Spinner) findViewById(R.id.milkChoicesSpinner);




        ArrayAdapter<CharSequence> mAdditiveAdapter = ArrayAdapter.createFromResource(this, R.array.additiveArray, android.R.layout.simple_spinner_item);
        mAdditiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdditiveChoicesSpinner.setAdapter(mAdditiveAdapter);

        ArrayAdapter<CharSequence> mOrderSizeAdapter = ArrayAdapter.createFromResource(this, R.array.orderSizesArray, android.R.layout.simple_spinner_item);
        mOrderSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOrderSizesSpinner.setAdapter(mOrderSizeAdapter);

        ArrayAdapter<CharSequence> mCoffeeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.cofeeTypeArray, android.R.layout.simple_spinner_item);
        mCoffeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoffeeTypeSpinner.setAdapter(mCoffeeTypeAdapter);

        ArrayAdapter<CharSequence> mMilkChoicesAdapter = ArrayAdapter.createFromResource(this, R.array.milkChoicesArray, android.R.layout.simple_spinner_item);
        mMilkChoicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMilkChoicesSpinner.setAdapter(mMilkChoicesAdapter);


        //initialise listeners for every single spinner
        mAdditiveChoicesSpinner.setOnItemSelectedListener(this);
        mCoffeeTypeSpinner.setOnItemSelectedListener(this);
        mMilkChoicesSpinner.setOnItemSelectedListener(this);
        mOrderSizesSpinner.setOnItemSelectedListener(this);

        //Initialise submit button
        submitButton.setOnClickListener(this);
    }

    //validate all spinners are populated. If not create a toast to let the user know what's missing.
    @Override
    public void onClick(View v) {

        Context c = this.getApplicationContext();
        String name;

        try {
            Log.v(TAG, "Order object is " + orderObject.getString("coffeeType"));
            Log.v(TAG, "Order size is " + orderObject.getString("orderSize"));
            Log.v(TAG, "additive choices is " + orderObject.getString("additiveChoices"));
            Log.v(TAG, "milk Choices is " + orderObject.getString("milkChoices"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        switch (v.getId()) {
            case R.id.submitButton: {
                try {

                    if (
                            (orderObject.getString("coffeeType") != null) &&
                                    (orderObject.getString("orderSize") != null) &&
                                    (orderObject.getString("additiveChoices") != null) &&
                                    (orderObject.getString("milkChoices") != null)
                            ) {
                        Log.v(TAG, "submit button clicked!");

                        if (nameText.getText().toString().matches("")) {
                            name = generateName(c);
                        }
                        else {
                            name = nameText.getText().toString();
                        }

                        Log.v(TAG, "name entered is " + name);

                     Intent i = new Intent(this, FindIt.class);
                        startActivity(i);
                        finish();


                    }

                    else if (orderObject.getString("coffeeType") == null) {
                        Toast.makeText(getApplicationContext(), "What kinda coffee ya like?", Toast.LENGTH_SHORT).show();

                    }
                    else if (orderObject.getString("orderSize") == null) {
                        Toast.makeText(getApplicationContext(), "What's ya order size?", Toast.LENGTH_SHORT).show();
                    }
                    else if (orderObject.getString("genderChoices") == null) {
                        Toast.makeText(getApplicationContext(), "What's your name?", Toast.LENGTH_SHORT).show();
                    }
                    else if (orderObject.getString("additiveChoices") == null) {
                        Toast.makeText(getApplicationContext(), "Would you like milk or do you prefer black coffee?", Toast.LENGTH_SHORT).show();
                    }
                    else if (orderObject.getString("milkChoices") == null) {
                        Toast.makeText(getApplicationContext(), "What kinda milk eh?", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {
            case R.id.milkChoicesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    try {
                        orderObject.put("milkChoices", parent.getItemAtPosition(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case R.id.additiveChoicesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    try {
                        orderObject.put("additiveChoices", parent.getItemAtPosition(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case R.id.coffeeTypeSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    try {
                        orderObject.put("coffeeType", parent.getItemAtPosition(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            case R.id.orderSizesSpinner: {
                if (position != 0) {
                    //pop this into the json object;
                    try {
                        orderObject.put("orderSize", parent.getItemAtPosition(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Log.v(TAG, "nothing selected");

    }

    public String generateName(Context c) {
        String namesListB = "Jackson Aiden Liam Lucas Noah Mason Ethan Caden Jacob Logan Jayden Elijah Jack Luke Michael Benjamin Alexander James Jayce Caleb Connor William Carter Ryan Oliver Matthew Daniel Gabriel Henry Owen Grayson Dylan Landon Isaac Nicholas Wyatt Nathan Andrew Cameron Dominic Joshua Eli Sebastian Hunter Brayden David Samuel Evan Gavin Christian Max Anthony Joseph Julian John Colton Levi Muhammad Isaiah Aaron Tyler Charlie Adam Parker Austin Thomas Zachary Nolan Alex Ian Jonathan Christopher Cooper Hudson Miles Adrian Leo Blake Lincoln Jordan Tristan Jason Josiah Xavier Camden Chase Declan Carson Colin Brody Asher Jeremiah Micah Easton Xander Ryder Nathaniel Elliot Sean Cole";
        String namesListG = "Sophia Emma Olivia Ava Isabella Mia Zoe Lily Emily Madelyn Madison Chloe Charlotte Aubrey Avery Abigail Kaylee Layla Harper Ella Amelia Arianna Riley Aria Hailey Hannah Aaliyah Evelyn Addison Mackenzie Adalyn Ellie Brooklyn Nora Scarlett Grace Anna Isabelle Natalie Kaitlyn Lillian Sarah Audrey Elizabeth Leah Annabelle Kylie Mila Claire Victoria Maya Lila Elena Lucy Savannah Gabriella Callie Alaina Sophie Makayla Kennedy Sadie Skyler Allison Caroline Charlie Penelope Alyssa Peyton Samantha Liliana Bailey Maria Reagan Violet Eliana Adeline Eva Stella Keira Katherine Vivian Alice Alexandra Camilla Kayla Alexis Sydney Kaelyn Jasmine Julia Cora Lauren Piper Gianna Paisley Bella London Clara Cadence";
        ArrayList namesListBoys = new ArrayList<String>();
        ArrayList namesListGirls = new ArrayList<String>();
        WriteToFile w = new WriteToFile();


        String name = "";
        String selectionList;
        Matcher m1;
        Matcher m2;

        Pattern p = Pattern.compile("\\w+");

        m1 = p.matcher(namesListB);
        m2 = p.matcher(namesListG);


           while (m1.find()) {
               namesListBoys.add(m1.group());
           }

           while (m2.find()) {
               namesListBoys.add(m2.group());
           }

        //w.writeToFile(c, namesListBoys, namesListGirls);
        name = randomiseName(namesListBoys);

        return name;
    }
    public String randomiseName (ArrayList<String> namesList) {
        String name = "";


        Random nameGen = new Random();

        name = namesList.get(nameGen.nextInt(198));

        return name;

    }

}
