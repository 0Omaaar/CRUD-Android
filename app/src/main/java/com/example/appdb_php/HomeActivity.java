package com.example.appdb_php;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeActivity extends AppCompatActivity {
    private EditText etServerUrl;
    private Button btnConnect;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etServerUrl = findViewById(R.id.etServerUrl);
        btnConnect = findViewById(R.id.btnConnect);
        tvStatus = findViewById(R.id.tvStatus);

        String defaultUrl = "http://192.168.0.173:9999/crud/viewList.php";
        etServerUrl.setText(defaultUrl);

        btnConnect.setOnClickListener(v -> testConnection());
    }

    private void testConnection() {
        String url = etServerUrl.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        tvStatus.setText("Connection successful!\nRecords found: " + jsonArray.length());
                    } catch (JSONException e) {
                        tvStatus.setText("Connection successful!\n" + response);
                    }
                },
                error -> tvStatus.setText("Connection failed: " + error.getMessage())
        );

        queue.add(request);
    }
}