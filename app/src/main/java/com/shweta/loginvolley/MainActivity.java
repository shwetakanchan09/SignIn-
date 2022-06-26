package com.shweta.loginvolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Context context;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        pBar = findViewById(R.id.progressbar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (email.equals("") || password.equals(""))
                    Toast.makeText(MainActivity.this, "Please fill email & password", Toast.LENGTH_SHORT).show();
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                else if (password.length() < 4)
                    Toast.makeText(MainActivity.this, "Password must be greater or equal to 4 letters", Toast.LENGTH_SHORT).show();
                else
                    Login(email, password);

            }
        });
    }

   /* private void Login(String email, String password) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "http://192.168.0.156/cproject/2022/apponlineVoting/kartik/app/admin_login.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("NIK123123",response);
                    }
                },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })}


}
*/


    private void Login(String email, String password) {
        pBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST,
                "https://myfinalyearproject.in/2022/medicalCenterSiddhesh/app/user/user_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        Log.i("NIK123123", response);
                        //lecture 2
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            JSONObject data =json.getJSONObject("data");
                            Log.i("NIK123123","u_id ==>" + data.getString("u_id"));
                                Log.i("NIK123123","u_name ==>" + data.getString("u_name"));
                                Log.i("NIK123123","u_email ==>" + data.getString("u_email"));
                                Log.i("NIK123123"," phone_number ==>" + data.getString("phone_number"));
                                Log.i("NIK123123"," u_password ==>" + data.getString("u_password"));
                                Log.i("NIK123123"," u_address ==>" + data.getString("u_address"));
                                Log.i("NIK123123"," u_pin_code ==>" + data.getString("u_pin_code"));
                                Log.i("NIK123123"," u_status ==>" + data.getString("u_status"));

                                Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
                                intent.putExtra("u_id", data.getString("u_id"));
                                intent.putExtra("u_name", data.getString("u_name"));
                                intent.putExtra("u_email", data.getString("u_email"));
                                intent.putExtra("phone_number", data.getString("phone_number"));
                                intent.putExtra("u_password", data.getString("u_password"));
                                intent.putExtra("u_address", data.getString("u_address"));
                                intent.putExtra("u_pin_code", data.getString("u_pin_code"));
                                intent.putExtra("u_status", data.getString("u_status"));
                                startActivity(intent);

                            }else {
                                Toast.makeText(MainActivity.this, "Invalid credential", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pBar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("u_email", email);
                params.put("u_password", password);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(
                getApplicationContext());
        queue.add(request);
    }
}