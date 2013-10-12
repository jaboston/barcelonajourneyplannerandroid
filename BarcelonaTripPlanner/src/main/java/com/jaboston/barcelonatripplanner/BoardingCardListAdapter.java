package com.jaboston.barcelonatripplanner;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by josephboston on 12/10/2013.
 */
public class BoardingCardListAdapter extends ArrayAdapter<JSONObject> {

    Context context;
    int layoutResourceId;
    private ArrayList<JSONObject> objects;

    public BoardingCardListAdapter(Context context, int layoutResourceId, ArrayList<JSONObject> objects) {
        super(context, layoutResourceId, objects);
        this.objects = objects;
        this.layoutResourceId = layoutResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable.
        View v = convertView;

        // if the view is null inflate it
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.boarding_cards, null);
        }

        JSONObject jObj = objects.get(position);




        if(jObj != null){

            TextView topt = (TextView) v.findViewById(R.id.textView);
            TextView bottomt = (TextView) v.findViewById(R.id.textView2);

            if(topt != null){
                try {
                    topt.setText(jObj.getString("startLocation") + " - " + jObj.getString("endLocation") + "(" + jObj.getString("price") + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(bottomt != null){
                try {
                    bottomt.setText(jObj.getString("cardType") + " - " + jObj.getString("seatCode") + " - " + jObj.getString("extraInfo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }
        return v;
    }


}
