package project.healthcare.phone.util;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

public class JSONRequest extends Request<JSONObject> {

  public JSONRequest(String url, JSONObject params, Listener<JSONObject> listener, ErrorListener errorListener) {
    super(Method.POST, url, errorListener);
    mParams = params;
    mListener = listener;
  }

  @Override
  public String getBodyContentType() {
    return "text/plain; charset=" + getParamsEncoding();
  }

  @Override
  public byte[] getBody() throws AuthFailureError {
    JSONObject params = mParams;
    if (params != null) {
      return encodeParameters(mParams, getParamsEncoding());
    }
    return null;
  }

  private byte[] encodeParameters(JSONObject params, String encoding) {
    try {
      return params.toString().getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
    String parsed;
    try {
        parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
    } catch (UnsupportedEncodingException e) {
        parsed = new String(response.data);
    }
    try {
      return Response.success(new JSONObject(parsed), HttpHeaderParser.parseCacheHeaders(response));
    } catch (JSONException e) {
      e.printStackTrace();
      return Response.error(new VolleyError(e.getMessage()));
    }
  }

  @Override
  protected void deliverResponse(JSONObject response) {
    mListener.onResponse(response);
  }

  private JSONObject mParams;
  private Listener<JSONObject> mListener;

}
