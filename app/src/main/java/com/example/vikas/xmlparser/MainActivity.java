package com.example.vikas.xmlparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

   TextView tv;
   EditText Email , Password;
    String email , password;
    Button btn;
    String url = "http://192.168.0.103/login.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.reg);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,register.class));
            }
        });

        builder = new AlertDialog.Builder(MainActivity.this);
        btn = (Button)findViewById(R.id.btnLogin);
        Email = (EditText)findViewById(R.id.ET1);
        Password = (EditText)findViewById(R.id.ET2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString();
                password = Password.getText().toString();

                if((email.equals(""))||(password.equals("")))
                {
                    builder.setTitle("something error");
                    displayAlert("Enter A Valid User Name And Password");
                }

                else
                {

                    final ProgressDialog progress = ProgressDialog.show(MainActivity.this,
                            "", "Signing In....", true, false);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                progress.dismiss();
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonobject = jsonArray.getJSONObject(0);
                                String code = jsonobject.getString("code");

                                 if(code.equals("login_failed"))
                                 {
                                     builder.setTitle("login Error");
                                     displayAlert(jsonobject.getString("message"));
                                 }


                                 else
                                 {
                                     Intent i =new Intent(MainActivity.this,success.class);
                                     Bundle bundle = new Bundle();
                                     bundle.putString("name",jsonobject.getString("name"));
                                     bundle.putString("email",jsonobject.getString("email"));
                                     i.putExtras(bundle);
                                     startActivity(i);
                                 }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progress.dismiss();
                            Toast.makeText(MainActivity.this,"Error in Connection",Toast.LENGTH_LONG).show();

                            error.printStackTrace();
                        }
                    })

                    {


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email" , email);
                            params.put("password" , password);

                            return params;
                        }
                    };

                    MySigleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });


    }
    public void displayAlert(String message)
    {
       builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Email.setText("");
                Password.setText("");

            }
        });

        AlertDialog alertdialog = builder.create();
        alertdialog.show();

    }



}
