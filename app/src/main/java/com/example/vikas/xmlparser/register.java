package com.example.vikas.xmlparser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class register extends Activity {

     Button btn;
    EditText Name ,Email , Password,Conpass;
    String name , email,password,conpass;
    AlertDialog.Builder builder;
    TextView login_wala;
    String url = "http://192.168.0.103/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = (Button)findViewById(R.id.btnRegister);
        Name = (EditText)findViewById(R.id.reg_fullname);
        Email = (EditText)findViewById(R.id.reg_email);
        Password = (EditText)findViewById(R.id.reg_password);
        Conpass = (EditText)findViewById(R.id.conpa);
        login_wala = (TextView)findViewById(R.id.ltol);
        builder = new AlertDialog.Builder(register.this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = Name.getText().toString();
                email = Email.getText().toString();
                password = Password.getText().toString();
                conpass = Conpass.getText().toString();

                if(name.equals("")|| email.equals("") || password.equals("") || conpass.equals(""))

                {
                    builder.setTitle("something wrong");
                    builder.setMessage("fill all entries");
                    displayAlert("input_error");

                }

//                else
//
//                    if(!(password.equals(conpass)))
//                    {
//                        builder.setTitle("something wrong....");
//                        builder.setMessage("Password doesn't Match");
//                        displayAlert("input_error");
//
//
//                    }

                    else {



                            StringRequest stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonarray = new JSONArray(response);
                                        JSONObject jsonobject = jsonarray.getJSONObject(0);
                                        String code = jsonobject.getString("code");
                                        String message = jsonobject.getString("message");

                                        //Toast.makeText(getApplicationContext(), "Error: " + code , Toast.LENGTH_LONG).show();
                                        builder.setTitle("server response");
                                        builder.setMessage(message);
                                        displayAlert(code);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("name", name);
                                    params.put("email", email);
                                    params.put("password", password);
                                    params.put("conpass", conpass);


                                    return params;
                                }
                            };

                            MySigleton.getInstance(register.this).addToRequestQueue(stringrequest);

                        }
                }



        });

        login_wala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(register.this,MainActivity.class));
            }
        });


    }



    public void displayAlert(final String code) {

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                 if(code.equals("input_error"))
                {
                    Password.setText(" ");
                    Conpass.setText(" ");
                }

                else if(code.equals("reg_success"))
                {
                    finish();
                }

                else if(code.equals("reg_failed"))
                {
                    Name.setText("");
                    Email.setText("");
                    Password.setText("");
                    Conpass.setText(" ");
                }
            }
        }) ;

        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }
}
