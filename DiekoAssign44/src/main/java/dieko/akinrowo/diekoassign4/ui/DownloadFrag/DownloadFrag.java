//Dieko Akinrowo N01343651 Section D
package dieko.akinrowo.diekoassign4.ui.DownloadFrag;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dieko.akinrowo.diekoassign4.R;

public class DownloadFrag extends Fragment {

    URL url = null;
    String link;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView = null;
    Button button;
    ProgressDialog pd;
    Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_download, container, false);

            String[] img_choice = {"Tomato", "Moon", "Pizza"};
            imageView = (ImageView) root.findViewById(R.id.Diekoimg);
            button = (Button) root.findViewById(R.id.diekobtn);
            spinner = (Spinner) root.findViewById(R.id.dieko_spinner);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, img_choice);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        link = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRglzQZBIyx3Zmd3CX3aeN-f_pBHvrhkdP5BQ&usqp=CAU";
                        break;

                    case 1:
                        link = "https://scitechdaily.com/images/Lunar-Reconnaissance-Orbiter-Moon-2048x1536.jpg";
                        break;

                    case 0:
                        link = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Tomato_je.jpg/440px-Tomato_je.jpg";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Select one", Toast.LENGTH_SHORT).show();
            }
        });

            button.setOnClickListener(v -> {

                Download task = new Download();
                task.execute(link);

            });

                    return root;
                }

    private class Download extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(getContext());
            pd.setMessage("Please wait...It is downloading");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmImg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                pd.hide();
                imageView.setImageBitmap(bitmap);
            } else {
                pd.show();
            }
        }
    }

}
