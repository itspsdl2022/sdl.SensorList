package jp.ac.titech.itpro.sdl.sensorlist;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        ListView sensorsView = findViewById(R.id.main_sensors);
        ArrayAdapter<Sensor> adapter = new ArrayAdapter<Sensor>(this, 0, new ArrayList<Sensor>()) {
            @Override
            public @NonNull
            View getView(int pos, @Nullable View view, @NonNull ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                Sensor sensor = getItem(pos);
                if (sensor != null) {
                    TextView nameView = view.findViewById(android.R.id.text1);
                    TextView typeView = view.findViewById(android.R.id.text2);
                    nameView.setText(sensor.getName());
                    typeView.setText(SensorUtil.getTypeName(sensor));
                }
                return view;
            }
        };
        sensorsView.setAdapter(adapter);
        sensorsView.setOnItemClickListener(this);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (manager == null) {
            Toast.makeText(this, R.string.toast_no_sensor_manager, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        adapter.addAll(SensorUtil.getSensors(manager));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent = new Intent(this, SensorActivity.class);
        intent.putExtra(SensorActivity.EXTRA_SENSOR_POS, pos);
        startActivity(intent);
    }
}