package com.jaboston.barcelonatripplanner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Surface;

public class Utils{

private String filepath = "alldata";
File myInternalFile;
Bitmap bMap;
int orientation;
SharedPreferences prefs;

	public String getBaseUrl(){
		return "http://api.prowd.com/";
	}
	
	public SharedPreferences getSharedPrefs(Context ctx){
		SharedPreferences settings = ctx.getSharedPreferences("settings", 0);
		return settings;
	}
	
	public boolean isNetworkAvailable(Context ctx) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public String getAuthenticateToken(Context ctx){
		prefs = getSharedPrefs(ctx);
		return prefs.getString("authentication_token", "");
	}
	
	public void saveToInternal(String data, String name, Context context){
		  ContextWrapper contextWrapper = new ContextWrapper(context);
		  File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
		  myInternalFile = new File(directory , name);
		  try {
			    FileOutputStream fos = new FileOutputStream(myInternalFile);
			    fos.write(data.toString().getBytes());
			    fos.close();
			   } catch (IOException e) {
			    e.printStackTrace();
			   }
		  Log.i("data saved", "data named " + name + "saved to directory.");
	}
	
	 public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	 }
	
	public String getInternalData(String filename, Context context){
		  ContextWrapper contextWrapper = new ContextWrapper(context);
		  File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
		  myInternalFile = new File(directory , filename);
		String myData = "";
		 try {
			    FileInputStream fis = new FileInputStream(myInternalFile);
			    DataInputStream in = new DataInputStream(fis);
			    BufferedReader br = 
			     new BufferedReader(new InputStreamReader(in));
			    String strLine;
			    while ((strLine = br.readLine()) != null) {
			     myData = myData + strLine;
			    }
			    in.close();
			   } catch (IOException e) {
			    e.printStackTrace();
			    
			    return "";
			   }
		Log.i("returning data", myData+" <-");
		myData = myData + "";
		return myData;
		
	}
	
	
	
}
