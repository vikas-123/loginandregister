package com.example.vikas.xmlparser;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by vikas on 15-06-2017.
 */

public class MySigleton {

    private static MySigleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;


    private MySigleton(Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()

    {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized MySigleton getInstance(Context context)

    {

        if (mInstance == null)
        {
            mInstance = new MySigleton(context);
        }
        return  mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request)

    {
       getRequestQueue().add(request);
    }
}
