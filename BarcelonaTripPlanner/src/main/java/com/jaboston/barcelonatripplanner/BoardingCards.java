package com.jaboston.barcelonatripplanner;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;



/**
 * Created by josephboston on 12/10/2013.
 */
public class BoardingCards {

    Utils utils = new Utils();

    public JSONObject getBoardingCardAtIndex(Activity activity, int index){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = null;
        String sJsonData = utils.getInternalData("boardingCards.txt", activity.getApplicationContext());

        try {
            jsonArray = new JSONArray(sJsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            try {
                jsonObject = jsonArray.getJSONObject(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public JSONArray getAllCards(Activity activity){

        JSONArray jsonArray = null;
        try {
            String theString;
            theString = utils.getInternalData("boardingCards.txt", activity.getApplicationContext());
            jsonArray = new JSONArray(theString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error: ", "no internal data stored");
        }

        return jsonArray;
    }

    public void addBoardingCard(Activity activity, String cardType, String price, String startLocation, String endLocation, String seatCode, String extraInfo){

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String sJsonData = utils.getInternalData("boardingCards.txt", activity.getApplicationContext());



        try {
            jsonArray = new JSONArray(sJsonData);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("jsonArrayFailure: ", e.getMessage());
        }

        try {
            jsonObject.put("cardType", cardType);
            jsonObject.put("price", price);
            jsonObject.put("startLocation", startLocation);
            jsonObject.put("endLocation", endLocation);
            jsonObject.put("seatCode", seatCode);
            jsonObject.put("extraInfo", extraInfo);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("jsonObject.put failed: ", e.getMessage());
        }

            jsonArray.put(jsonObject);
            utils.saveToInternal(jsonArray.toString(), "boardingCards.txt", activity.getApplicationContext());



    }




}
