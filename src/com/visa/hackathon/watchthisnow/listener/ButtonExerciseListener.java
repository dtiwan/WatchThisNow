package com.visa.hackathon.watchthisnow.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.visa.hackathon.watchthisnow.R;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ButtonExerciseListener implements OnClickListener{
	

    private static String API_KEY = "07IGBWES2FU6LUR927Z1212NrsYSGhofvfWfrqzfiWBEoAHns";
    private static String SHARED_SECRET = "9xTF}194l4utVx4Ce@8HUEkzYr0q{7/NammxuN++";
    

	@Override
	public void onClick(View v) {
		
		StringBuilder result = new StringBuilder();
		try {
			RestCall restCall = (RestCall) new RestCall().execute("");
			result.append(restCall.get());
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Exception", e);
			result = result.append(e.getStackTrace());
		}
        
		TextView txtHelloWorld = (TextView) ((View)v.getParent()).findViewById(R.id.text_log);
		txtHelloWorld.setText(result);
	}

    private static String getXPayToken(String apiNameURI, String queryString, String requestBody) throws SignatureException {
        String timestamp = getTimestamp();
        String sourceString = SHARED_SECRET + timestamp + apiNameURI + queryString + requestBody;
        String hash = sha256Digest(sourceString);
        String token = "x:" + timestamp + ":" + hash;
        return token;
    }

    private static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis()/ 1000L);
    }

    private static String sha256Digest(String data) throws SignatureException {
        return getDigest("SHA-256", data, true);
    }

    private static String getDigest(String algorithm, String data, boolean toLower) throws
            SignatureException {
        try {
            MessageDigest mac = MessageDigest.getInstance(algorithm);
            mac.update(data.getBytes("UTF-8"));
            return toLower ?
                    new String(toHex(mac.digest())).toLowerCase() : new String(toHex(mac.digest()));
        } catch (Exception e) {
            throw new SignatureException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
    
    private class RestCall extends AsyncTask<String, Void, String>{
    	
		@Override
		protected String doInBackground(String... params) {
			String baseUri = "cybersource/";
	        String resourcePath = "payments/v1/authorizations";
	        String url = "https://sandbox.api.visa.com/" + baseUri + resourcePath + "?apikey=" + API_KEY;

	        HttpClient client = new DefaultHttpClient();
	        HttpPost httpPost = new HttpPost(url);

	        // Load the body for the post request
	        String body = "{\"amount\": \"20\", \"currency\": \"USD\", \"payment\": { \"cardNumber\": \"4111111111111111\", \"cardExpirationMonth\": \"10\", \"cardExpirationYear\": \"2016\" }}";
	        StringBuilder result;
			try {
				StringEntity postBodyEntity = new StringEntity(body);
				httpPost.setEntity(postBodyEntity);

				// Add headers
				httpPost.setHeader("content-type", "application/json");
				String xPayToken = getXPayToken(
				        resourcePath,
				        "apikey=" + API_KEY,
				        body);
				httpPost.setHeader("x-pay-token", xPayToken);

				// Make the call
				HttpResponse response = client.execute(httpPost);

				// Read and print the response
				BufferedReader rd = new BufferedReader(
				        new InputStreamReader(response.getEntity().getContent()));

				result = new StringBuilder();
				String line = null;
				while ((line = rd.readLine()) != null) {
				    result.append(line);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return result.toString();
		}
    	
    }

}
