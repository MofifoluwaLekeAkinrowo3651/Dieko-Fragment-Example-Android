//Dieko Akinrowo N01343651 Section D
package dieko.akinrowo.diekoassign4.ui.WebServiceFrag;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dieko.akinrowo.diekoassign4.R;

public class WebServiceFrag extends Fragment {

    TextView display;
    EditText zipcode;
    String zip;
    AlertDialog.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_service, container, false);
        display = (TextView) root.findViewById(R.id.dieko_display);

        new ReadJSONFeedTask().execute(
                "http://extjs.org.cn/extjs/examples/grid/survey.html");

        Button button = (Button) root.findViewById(R.id.button);
        zipcode = (EditText) root.findViewById(R.id.dieko_zip);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            zip = zipcode.getText().toString();

            if (zip != "" && zip.length() == 5) {

                String url = "https://api.openweathermap.org/data/2.5/weather?";
                url += "zip=" + zip;
                url += ",US";
                url += "&appid=0b014c03b8d86401a294d8644195a145";
                Log.d("URL", url);
                new ReadJSONFeedTask().execute(url);

            } else {

                builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                AlertDialog alert = builder.create();
                alert.show();

            }
            }
        });

        return root;
    }

    public String readJSONFeed(String address) {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        };
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream content = new BufferedInputStream(
                    urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return stringBuilder.toString();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                //JSONArray jsonArray = new JSONArray(result);
                //Uncomment the two rows below to parse weather data from OpenWeatherMap
                JSONObject weatherJson = new JSONObject(result);

                String strResults="Weather\n";
                JSONObject data2= weatherJson.getJSONObject("sys");
                strResults += "\nLocation: " +weatherJson.getString("name") +", "+data2.getString("country") +", " +zip;

                JSONObject coord = weatherJson.getJSONObject("coord");
                strResults += "\nLongitude: " +coord.getString("lon");
                strResults += "\nLatitude: " +coord.getString("lat");

                JSONObject dataObject= weatherJson.getJSONObject("main");
                strResults +="\nHumidity: "+dataObject.getString("humidity");
                strResults +="\nTemperature: "+dataObject.getString("temp");

                display.setText(strResults);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}