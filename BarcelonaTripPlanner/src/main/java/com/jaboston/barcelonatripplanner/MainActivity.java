package com.jaboston.barcelonatripplanner;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.*;
import java.util.*;
import android.graphics.Color;
import java.util.logging.Handler;

public class MainActivity extends Activity {

    ArrayList<JSONObject> removedBoardingCardsArray;
    ArrayList<JSONObject> jsonCardsList;
    RelativeLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BoardingCards boardingCards = new BoardingCards();

        Utils utils = new Utils();

        SharedPreferences prefs = utils.getSharedPrefs(this.getApplicationContext());
        String firstRun = prefs.getString("first_run", "yes");

        if(firstRun.contains("yes")){
            boardingCards.addBoardingCard(this, "car", "$1.50", "airport", "restaurant", "3", "Sitting on the back seat");
            boardingCards.addBoardingCard(this, "helicopter", "$500", "restaurant", "hospital", "1", "You are driving. Goodluck!");
            boardingCards.addBoardingCard(this, "train", "$100", "restaurant", "ditch", "STANDONHEAD", "No Seats on this train. Gotta stand on your head.");
            boardingCards.addBoardingCard(this, "Fire Engine", "$10", "restaurant", "burning house", "HOSE", "Take this hose and put out the fire.");
            boardingCards.addBoardingCard(this, "Nimbus", "$300", "burning house", "popo floating island", "LEVITATION", "Dont forget your flying nimbus.");
            boardingCards.addBoardingCard(this, "Sky Dive", "$1020", "popo floating island", "home", "GRAVITY", "Ohhh the adrenaline is pumping");
            boardingCards.addBoardingCard(this, "Legs", "$50", "ditch", "park bench hotel", "VODKA", "Time to get a good nights rest and sobre up");
            boardingCards.addBoardingCard(this, "Bicycle", "$5", "park bench hotel", "home", "BIKESEAT", "They see me rollin' they hatin'");
            boardingCards.addBoardingCard(this, "Ambulance", "$40", "hospital", "home", "WHEELCHAIR", "Well that was a bad idea!");
            Log.e("boarding cards: ", "adding boardingCards");
            prefs.edit().putString("first_run", "no").commit();
        } else {
            Log.e("bording cards: ", "boarding cards already added.");
        }

        JSONArray cards = boardingCards.getAllCards(this);

        Log.e("JSONArray cards: ", cards.toString());


        JSONObject[] jsonCards = new JSONObject[cards.length()];

        for(int i = 0; i < jsonCards.length; i++){
            try {
                jsonCards[i] = cards.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("jsonCards Error in MainActivity OnCreate: ", e.getMessage());
            }
        }


        jsonCardsList = new ArrayList();
        for(int i = 0; i < jsonCards.length; i++){
            jsonCardsList.add(i, jsonCards[i]);
        }

        BoardingCardListAdapter adapter;
        adapter = new BoardingCardListAdapter(this, R.layout.boarding_cards, jsonCardsList);

        ListView boardingCardsListView = (ListView)findViewById(R.id.listView);
        boardingCardsListView.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.tripButton);
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                createFirstRoute();
            }
        });


        //could make this loop and have an awesome 80's disco (inspired by iOS7 of course).
        //specialEffectsFromEighties();

    }

    public void specialEffectsFromEighties(){

        screen = (RelativeLayout)findViewById(R.id.backgroundLayout);
        ObjectAnimator colorFade = ObjectAnimator.ofObject(screen, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 0, 0, 127), Color.argb(0, 255, 0, 127));
        colorFade.setDuration(7000);
        colorFade.start();
    }


    public void createFirstRoute(){

        removedBoardingCardsArray = new ArrayList();

        //add the first array value to begin the route (could be specified by searching through the arraylist for a matching string for example.
        removedBoardingCardsArray.add(jsonCardsList.get(0));

        for(int i = 0; i < jsonCardsList.size(); i++){
            JSONObject jObj = jsonCardsList.get(i);

            int endOfremovedArray = (int)removedBoardingCardsArray.size()-1;
            try {
                if(removedBoardingCardsArray.get(endOfremovedArray).getString("endLocation").equals(jsonCardsList.get(i).getString("startLocation"))){
                    removedBoardingCardsArray.add(jsonCardsList.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        BoardingCardListAdapter adapter;
        adapter = new BoardingCardListAdapter(this, R.layout.boarding_cards, removedBoardingCardsArray);

        ListView boardingCardsListView = (ListView)findViewById(R.id.listView);
        boardingCardsListView.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
