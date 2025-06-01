package com.example.appdb_php;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private EditText etId, etName, etAddress;
    private TextView tvResult;
    private String baseUrl = "http://192.168.0.173:9999/crud/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        tvResult = findViewById(R.id.tvResult);

        findViewById(R.id.btnAdd).setOnClickListener(v -> addData());
        findViewById(R.id.btnUpdate).setOnClickListener(v -> updateData());
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteData());
        findViewById(R.id.btnView).setOnClickListener(v -> viewData());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearFields());
    }

    private void addData() {
        String url = baseUrl + "add.php?name=" + URLEncoder.encode(etName.getText().toString()) +
                "&address=" + URLEncoder.encode(etAddress.getText().toString());
        executeRequest(url, "Data added successfully");
    }

    private void updateData() {
        String url = baseUrl + "update.php?id=" + etId.getText().toString() +
                "&name=" + URLEncoder.encode(etName.getText().toString()) +
                "&address=" + URLEncoder.encode(etAddress.getText().toString());
        executeRequest(url, "Data updated successfully");
    }

    private void deleteData() {
        String url = baseUrl + "delete.php?id=" + etId.getText().toString();
        executeRequest(url, "Data deleted successfully");
    }

    private void viewData() {
        String url = baseUrl + "viewList.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    StringBuilder result = new StringBuilder();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            result.append("ID: ").append(obj.getInt("id"))
                                    .append("\nName: ").append(obj.getString("name"))
                                    .append("\nAddress: ").append(obj.getString("address"))
                                    .append("\n\n");
                        }
                        tvResult.setText(result.toString());
                    } catch (JSONException e) {
                        tvResult.setText("Error parsing data");
                    }
                },
                error -> tvResult.setText("Error: " + error.getMessage())
        );
        queue.add(request);
    }

    private void executeRequest(String url, String successMessage) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    tvResult.setText(successMessage + "\n" + response);
                    viewData(); // Refresh data
                },
                error -> tvResult.setText("Error: " + error.getMessage())
        );
        queue.add(request);
    }

    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etAddress.setText("");
    }
}