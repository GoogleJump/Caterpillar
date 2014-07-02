package com.travellog;
 
import java.io.IOException;
 
import android.os.AsyncTask;
import android.util.Log;
 
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.travellog.tripendpoint.Tripendpoint;
import com.travellog.userendpoint.Userendpoint;
 
public class GetTripByOwnerKeyTask extends AsyncTask {
 
    @Override
    protected Object doInBackground(Object... params) {
 
        Tripendpoint.Builder endpointBuilder = new Tripendpoint.Builder(
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest httpRequest) { }
                });
//      endpointBuilder.setApplicationName("gjtravellog");
        Tripendpoint endpoint = CloudEndpointUtils.updateBuilder(
                endpointBuilder).build();
         
       /* try {
            Log.w("trip ", endpoint.listTrip((String) params[1]).setPrettyPrint(true).execute().toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
         
        return null;
    }
 
 
}