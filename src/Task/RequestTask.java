package Task;


import java.io.IOException;
import java.net.URLEncoder;

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

public class RequestTask extends AsyncTask<RequestParam, Void, String>{
	RequestParam requestPara=null;

	@Override
	protected String doInBackground(RequestParam... params) {
		requestPara= params[0];
		 HttpClient httpclient = new DefaultHttpClient();
		 String result = null;
		 String url = "http://messagepay.herokuapp.com/"+requestPara.getActionType()+"?amount="+requestPara.getTotal()+"&facebookid="+requestPara.getFacebookIds()+"&memo="+requestPara.getMemo();		 
		 System.out.println(url);
		 HttpPost httppost = new HttpPost(url);	 	 
		 HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}							
		return result;
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(result.equals("SUCCESS"))return;
		//System.out.println(requestPara.getFacebookIds()+"/notifications?access_token=308190689370512|pMvOlstfRQeN1_eqboQL5ZqZPMw&template=SocialPay&href="+result);
		new FacebookCallTask().execute(result, requestPara.getFacebookIds());
		System.out.println(result);
		System.out.println(requestPara.getFacebookIds());
		
//		new Request(
//			    Session.getActiveSession(),
//			    requestPara.getFacebookIds()+"/notifications?access_token=308190689370512|b954c8ec8a6113477ef4d822133bd99a&template=SocialPay&href="+result,
//			    null,
//			    HttpMethod.POST,
//			    new Request.Callback() {
//			        public void onCompleted(Response response) {
//			             System.out.println(response);
//			        }
//			    }
//			).executeAsync();
	}
}
