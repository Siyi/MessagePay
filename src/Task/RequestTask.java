package Task;


import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.messagepay.RequestParam;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import android.os.AsyncTask;

public class RequestTask extends AsyncTask<RequestParam, Void, Void>{

	@Override
	protected Void doInBackground(RequestParam... params) {
		RequestParam requestParam= params[0];
		 HttpClient httpclient = new DefaultHttpClient();	 
		 String url = "http://messagepay.herokuapp.com/Pay?amount="+ requestParam.getTotal()+"&facebookid="+requestParam.getFacebookIds();		 
		 HttpPost httppost = new HttpPost(url);	 	 
		 try {
				HttpResponse response = httpclient.execute(httppost);
				String jsonString = EntityUtils.toString(response.getEntity());
				if(!jsonString.equals("SUCCESS")){
					System.out.println(jsonString);
			}
					
			} catch (ClientProtocolException e) {
			} catch (IOException e) {
			}
		return null;
		
	}
	
	/*@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		new Request(
			    Session.getActiveSession(),
			    "/me/notifications",
			    1379064282388125/notifications?access_token=308190689370512|pMvOlstfRQeN1_eqboQL5ZqZPMw&template=SocialPay&href=Claim?claimReq=123456
			    null,
			    HttpMethod.POST,
			    new Request.Callback() {
			        public void onCompleted(Response response) {
			             handle the result 
			        }
			    }
			).executeAsync();
	}*/
}
