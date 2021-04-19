//Dieko Akinrowo N01343651 Section D
package dieko.akinrowo.diekoassign4.ui.HomeFrag;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.RadialGradient;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dieko.akinrowo.diekoassign4.R;

public class HomeFrag extends Fragment {

    TextView tvDieko, tvN01343651, date;
    Calendar calendar;
    SimpleDateFormat format;
    String _date;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvDieko = (TextView) root.findViewById(R.id.Diekotv1);
        tvDieko.setText(R.string.fullname);

        tvN01343651 = (TextView) root.findViewById(R.id.Diekotv2);
        tvN01343651.setText(R.string.studentid);

        date = (TextView)root.findViewById(R.id.Diekotv3);
        calendar = Calendar.getInstance();

        format = new SimpleDateFormat("EEE MMM d, yyyy hh:mm aa");
        _date = format.format(calendar.getTime());
        date.setText(_date);

        return root;
    }
}