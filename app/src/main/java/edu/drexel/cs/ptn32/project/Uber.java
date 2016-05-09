package edu.drexel.cs.ptn32.project;

/**
 * Created by chris on 9/1/2015.
 */

import android.view.MenuItem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.temboo.Library.Uber.OAuth.*;
import com.temboo.Library.Uber.OAuth.FinalizeOAuth;
import com.temboo.Library.Uber.OAuth.FinalizeOAuth.FinalizeOAuthInputSet;
import com.temboo.Library.Uber.OAuth.FinalizeOAuth.FinalizeOAuthResultSet;
import com.temboo.Library.Uber.OAuth.InitializeOAuth.InitializeOAuthInputSet;
import com.temboo.Library.Uber.OAuth.InitializeOAuth.InitializeOAuthResultSet;
import com.temboo.Library.Uber.Estimates.GetPriceEstimates.GetPriceEstimatesInputSet;
import com.temboo.Library.Uber.Estimates.GetPriceEstimates.GetPriceEstimatesResultSet;
import com.temboo.Library.Uber.Estimates.GetTimeEstimates.GetTimeEstimatesInputSet;
import com.temboo.Library.Uber.Estimates.GetTimeEstimates.GetTimeEstimatesResultSet;
import com.temboo.Library.Uber.Estimates.*;
import com.temboo.Library.Uber.Products.GetProductTypes.GetProductTypesInputSet;
import com.temboo.Library.Uber.Products.GetProductTypes.GetProductTypesResultSet;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.temboo.Library.Uber.Products.GetProductTypes;
import com.temboo.core.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Uber extends AppCompatActivity {

    ArrayList<String> test1 = new ArrayList<String>();
    static public String oauthURL ="https://login.uber.com/oauth/authorize?client_id=rWkWjoVFn_5eOv-oSZ6A00OZbbtSOsU3&response_type=code&redirect_uri=https%3A%2F%2Fcfg26.temboolive.com%2Fcallback%2Fuber&scope=request&state=e73b6e6e-a87d-421d-99c4-3d7e23f6b8d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_uber);

        weather asynctask = new weather();
        asynctask.execute();
        WebView wb = (WebView) findViewById(R.id.webView1);
        System.out.println(oauthURL);



        wb.loadUrl(oauthURL);

    }

    private class weather extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            String URL = null;
            try {

                String key = "396e5e097f22aa05"; //my key, for accessing wunderground
                String sURL = "http://api.wunderground.com/api/" + key + "/conditions/forecast/q/autoip.json"; //url
                double endLatitude = 40.650729;
                double endLongitude = -74.0095369;
                String productID = "653e6788-871e-4c63-a018-d04423f5b2f7";

                URL url = new URL(sURL);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                //for parsing information from the JSON format received from wunderground forecast page
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject rootobj = root.getAsJsonObject();

                String location = rootobj.get("current_observation").getAsJsonObject().get("display_location").getAsJsonObject().get("full").getAsString();
                System.out.println("Location = " + location);
                Log.i("test", location);

                String latitude = rootobj.get("current_observation").getAsJsonObject().get("display_location").getAsJsonObject().get("latitude").getAsString();
                System.out.println("Latitude = " + latitude);
                Log.i("test", latitude);

                double Latitude = Double.parseDouble(latitude.trim());

                String longitude = rootobj.get("current_observation").getAsJsonObject().get("display_location").getAsJsonObject().get("longitude").getAsString();
                System.out.println("Longitude = " + longitude);
                double Longitude = Double.parseDouble(longitude.trim());

                String zip = rootobj.get("current_observation").getAsJsonObject().get("display_location").getAsJsonObject().get("zip").getAsString();
                System.out.println("Zip Code = " + zip);


                String ClientID = "rWkWjoVFn_5eOv-oSZ6A00OZbbtSOsU3";
                String ClientSecret = "Yxab4dppyf7USRdikDiQmA2xC_V_J3B2BIf-hyS4";
                String SToken = "CPL1w6O6RIz-h98OrrsSQKoYmUjFh4qAhyDtcCzz";
                //String test22 = "https://login.uber.com/oauth/authorize";
                //System.out.println(test22);

                //begining of Oauth

                TembooSession session = new TembooSession("cfg26", "UberTest", "OClFI6VhcxGhSe4P2BhCMEgugt1WsHvV");
                InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);
                InitializeOAuthInputSet initializeOAuthInputs = initializeOAuthChoreo.newInputSet();

                initializeOAuthInputs.set_ClientID(ClientID);
                initializeOAuthInputs.set_Scope("request");
                //initializeOAuthInputs.set_ForwardingURL(test22);

                InitializeOAuthResultSet initializeOAuthResults = initializeOAuthChoreo.execute(initializeOAuthInputs);

                URL = initializeOAuthResults.get_AuthorizationURL();


                System.out.println(URL);

                oauthURL = URL;



                String CallBackID = initializeOAuthResults.get_CallbackID();
                System.out.println(CallBackID);


                //finalize Oauth

                FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

                FinalizeOAuthInputSet finalizeOAuthInputs = finalizeOAuthChoreo.newInputSet();

                finalizeOAuthInputs.set_CallbackID(CallBackID);
                finalizeOAuthInputs.set_ClientID(ClientID);
                finalizeOAuthInputs.set_ClientSecret(ClientSecret);

                FinalizeOAuthResultSet finalizeOAuthResults = finalizeOAuthChoreo.execute(finalizeOAuthInputs);

                String AccessToken = finalizeOAuthResults.get_AccessToken();
                System.out.println(AccessToken);
                String RefreshToken = finalizeOAuthResults.get_RefreshToken();

                //price estimates

                GetPriceEstimates getPriceEstimatesChoreo = new GetPriceEstimates(session);

                GetPriceEstimatesInputSet getPriceEstimatesInputs = getPriceEstimatesChoreo.newInputSet();

                getPriceEstimatesInputs.set_ServerToken(SToken);
                getPriceEstimatesInputs.set_EndLatitude(BigDecimal.valueOf(40.650729));
                getPriceEstimatesInputs.set_EndLongitude(BigDecimal.valueOf(-74.0095369));
                getPriceEstimatesInputs.set_StartLatitude(BigDecimal.valueOf(Latitude));
                getPriceEstimatesInputs.set_StartLongitude(BigDecimal.valueOf(Longitude));

                GetPriceEstimatesResultSet getPriceEstimatesResults = getPriceEstimatesChoreo.execute(getPriceEstimatesInputs);

                String PriceEstimates = getPriceEstimatesResults.get_Response();

                System.out.println(PriceEstimates);

                //time estimates

                GetTimeEstimates getTimeEstimatesChoreo = new GetTimeEstimates(session);
                GetTimeEstimatesInputSet getTimeEstimatesInputs = getTimeEstimatesChoreo.newInputSet();

                getTimeEstimatesInputs.set_StartLatitude(BigDecimal.valueOf(Latitude));

                getTimeEstimatesInputs.set_StartLongitude(BigDecimal.valueOf(Longitude));

                getTimeEstimatesInputs.set_ServerToken(SToken);

                GetTimeEstimatesResultSet getTimeEstimatesResults = getTimeEstimatesChoreo.execute(getTimeEstimatesInputs);

                String TimeEstimates = getTimeEstimatesResults.get_Response();

                System.out.println(TimeEstimates);

                //product types

                GetProductTypes getProductTypesChoreo = new GetProductTypes(session);

                GetProductTypesInputSet getProductTypesInputs = getProductTypesChoreo.newInputSet();

                getProductTypesInputs.set_ServerToken(SToken);

                getProductTypesInputs.set_Latitude(BigDecimal.valueOf(Latitude));
                getProductTypesInputs.set_Longitude(BigDecimal.valueOf(Longitude));

                GetProductTypesResultSet getProductTypesResults = getProductTypesChoreo.execute(getProductTypesInputs);

                String ProductTypes = getProductTypesResults.get_Response();

                System.out.println(ProductTypes);

                //request test

                String rURL = "http://sandbox-api.uber.com/v1/requests?access_token=" + AccessToken + "&product_id=" + productID + "&start_latitude=" + latitude + "&start_longitude=" + longitude + "&end_latitude=" + endLatitude + "&end_longitude=" + endLongitude;


                String urlTest = "http://sandbox-api.uber.com/v1/requests?access_token=" + AccessToken;
                String urlParams = "access_token=" + AccessToken + "&product_id=" + productID + "&start_latitude=" + latitude + "&start_longitude=" + longitude + "&end_latitude=" + endLatitude + "&end_longitude=" + endLongitude;

                //executePost(urlTest, urlParams);

                URL url3 = new URL(rURL);
                HttpURLConnection request3 = (HttpURLConnection) url3.openConnection();
                request3.connect();


                JsonParser jp1 = new JsonParser();
                JsonElement root1 = jp1.parse(PriceEstimates);
                JsonObject rootobj1 = root1.getAsJsonObject();

                int i = 0;
                while (i < 4) {
                    String ProductID = rootobj1.get("prices").getAsJsonArray().get(i).getAsJsonObject().get("product_id").getAsString();
                    String displayName = rootobj1.get("prices").getAsJsonArray().get(i).getAsJsonObject().get("display_name").getAsString();
                    String estimate = rootobj1.get("prices").getAsJsonArray().get(i).getAsJsonObject().get("estimate").getAsString();
                    String lowEstimate = rootobj1.get("prices").getAsJsonArray().get(i).getAsJsonObject().get("low_estimate").getAsString();
                    String highEstimate = rootobj1.get("prices").getAsJsonArray().get(i).getAsJsonObject().get("high_estimate").getAsString();
                    System.out.println(" Product ID = " + ProductID + " Product Type = " + displayName + " Estimate = " + estimate + " Low Estimate = " + lowEstimate + " High Estimate = " + highEstimate);
                    i++;
                }

                JsonParser jp2 = new JsonParser();
                JsonElement root2 = jp2.parse(TimeEstimates);
                JsonObject rootobj2 = root2.getAsJsonObject();


                int k = 0;
                while (k < 3) {
                    String ProductID2 = rootobj2.get("times").getAsJsonArray().get(k).getAsJsonObject().get("product_id").getAsString();
                    String displayName = rootobj2.get("times").getAsJsonArray().get(k).getAsJsonObject().get("display_name").getAsString();
                    String TimeEstimate = rootobj2.get("times").getAsJsonArray().get(k).getAsJsonObject().get("estimate").getAsString();
                    System.out.println(" Product ID = " + ProductID2 + " Product Type = " + displayName + " Estimate = " + TimeEstimate);
                    k++;
                }


                JsonParser jp3 = new JsonParser();
                JsonElement root3 = jp3.parse(ProductTypes);
                JsonObject rootobj3 = root3.getAsJsonObject();

                int j = 0;
                while (j < 4) {
                    String ProductID3 = rootobj3.get("products").getAsJsonArray().get(j).getAsJsonObject().get("product_id").getAsString();
                    String description = rootobj3.get("products").getAsJsonArray().get(j).getAsJsonObject().get("description").getAsString();
                    String displayName2 = rootobj3.get("products").getAsJsonArray().get(j).getAsJsonObject().get("display_name").getAsString();
                    String capacity = rootobj3.get("products").getAsJsonArray().get(j).getAsJsonObject().get("capacity").getAsString();
                    String image = rootobj3.get("products").getAsJsonArray().get(j).getAsJsonObject().get("image").getAsString();
                    System.out.println(" Product ID = " + ProductID3 + " Description = " + description + " Product Type = " + displayName2 + " Capacity = " + capacity + " Image = " + image);
                    j++;
                }


                System.out.println(rURL);
                System.out.println(urlTest);


                URL url4 = new URL(urlTest);
                Map<String, Object> params2 = new LinkedHashMap<>();
                //params2.put("access_token", AccessToken);
                params2.put("product_id", productID);
                params2.put("start_latitude", latitude);
                params2.put("start_longitude", longitude);
                params2.put("end_latitude", endLatitude);
                params2.put("end_longitude", endLongitude);
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params2.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url4.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for (int c = in.read(); c != -1; c = in.read())
                    System.out.print((char) c);


                //executePost(urlTest, urlParams);


                // UberClient client = new UberClient("YOUR_OAUTH_ID", "YOUR_OAUTH_SECRET", "YOUR_OAUTH_REDIRECT_URI",RestAdLogLevel.BASIC );


            } catch (TembooException ie) {
                ie.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            System.out.println(test1);
            return URL;

        }

        @Override
        protected void onPostExecute(String result) {

            oauthURL = result;
            System.out.println(oauthURL);

        }

        public String executePost(String targetURL, String urlParameters)
        {
            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(targetURL);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                System.out.println(response.toString()) ;
                return response.toString();


            } catch (Exception e) {

                e.printStackTrace();
                return null;

            } finally {

                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }}












