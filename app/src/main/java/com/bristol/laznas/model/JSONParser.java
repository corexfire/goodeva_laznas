package com.bristol.laznas.model;

import android.util.Log;
import android.webkit.CookieManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
    private HttpsURLConnection urlConnection;
    private CookieManager cookieManager;

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method,
									  List<NameValuePair> params) {

		// Making HTTP request
		try {
			
			// check for request method
			if(method == "POST"){
				// request method is POST
				// defaultHttpClient
				HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

				DefaultHttpClient client = new DefaultHttpClient();

				SchemeRegistry registry = new SchemeRegistry();
				SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
				socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
				registry.register(new Scheme("https", socketFactory, 443));
				SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
				DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

                // Set verifier
				HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);


				//DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
                //HttpResponse httpResponse = httpClient.execute(httpPost);
                //HttpEntity httpEntity = httpResponse.getEntity();
                //is = httpEntity.getContent();

                try {
                    DataLoader dl = new DataLoader();
                    //String url1 = "https://IpAddress";
                    HttpResponse response = dl.secureLoadData(url, params);

                    StringBuilder sb = new StringBuilder();
                    sb.append("HEADERS:\n\n");

                    Header[] headers = response.getAllHeaders();
                    for (int i = 0; i < headers.length; i++) {
                        Header h = headers[i];
                        sb.append(h.getName()).append(":\t").append(h.getValue()).append("\n");
                    }

                    InputStream is = response.getEntity().getContent();
                    StringBuilder out = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    for (String line = br.readLine(); line != null; line = br.readLine())
                        out.append(line);
                    br.close();

                    sb.append("\n\nCONTENT:\n\n").append(out.toString());

                    Log.i("response", sb.toString());
                    json = out.toString();
                    Log.i("json", json);
                    try {
                        jObj = new JSONObject(json);
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }

                    // Return the JSON Object.
                    return jObj;
                    //text.setText(sb.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
				//Log.d("Login attempt 2", Integer.toString(is.read()));




				
			}else if(method == "GET"){
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				//String paramString = URLEncodedUtils.format(params, "utf-8");
				//url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				//HttpResponse httpResponse = httpClient.execute(httpGet);
				//HttpEntity httpEntity = httpResponse.getEntity();
				//is = httpEntity.getContent();

				////
//
//				int length = 500;
//
//				try {
//					URL inside = new URL(url);
//					HttpURLConnection conn = (HttpURLConnection) inside.openConnection();
//					conn.setReadTimeout(10000 /* milliseconds */);
//					conn.setConnectTimeout(15000 /* milliseconds */);
//					conn.setRequestMethod("GET");
//					conn.setDoInput(true);
//					conn.connect();
//					int response = conn.getResponseCode();
//					Log.d("JSON", "The response is: " + response);
//					is = conn.getInputStream();
//
//					// Convert the InputStream into a string
//					String contentAsString = convertInputStreamToString(is, length);
//					//return contentAsString;
//				} finally {
//					if (is != null) {
//						is.close();
//					}
//				}

				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget= new HttpGet(url);

				HttpResponse response = httpclient.execute(httpget);

				if(response.getStatusLine().getStatusCode()==200){
					String server_response = EntityUtils.toString(response.getEntity());
					Log.i("Server response", server_response );
				} else {
					Log.i("Server response", "Failed to get server response" );
				}
			}			
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			Log.d("Login attempt inside", sb.toString());

			json = sb.toString();

			//getTempFile(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);

		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			Log.d("Login attempt", "Failed");
			//return null;
		}

		// return JSON String
		return jObj;

	}

	public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[length];
		reader.read(buffer);
		return new String(buffer);
	}
	
    public JSONObject getJSONFromUrl(final String url) {

        // Making HTTP request
        try {
            // Construct the client and the HTTP request.
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);


            // Execute the POST request and store the response locally.
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // Extract data from the response.
            HttpEntity httpEntity = httpResponse.getEntity();

            // Open an inputStream with the data content.
            is = httpEntity.getContent();



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Create a BufferedReader to parse through the inputStream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            // Declare a string builder to help with the parsing.
            StringBuilder sb = new StringBuilder();
            // Declare a string to store the JSON object data in string form.
            String line = null;
            
            // Build the string until null.
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            
            // Close the input stream.
            is.close();
            // Convert the string builder data to an actual string.
            json = sb.toString();
			Log.d("Login attempt 2", sb.toString());

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // Try to parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // Return the JSON Object.
        return jObj;

    }

    public class CustomSSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public CustomSSLSocketFactory(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new CustomX509TrustManager();

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        public CustomSSLSocketFactory(SSLContext context)
                throws KeyManagementException, NoSuchAlgorithmException,
                KeyStoreException, UnrecoverableKeyException {
            super(null);
            sslContext = context;
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                                   boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port,
                    autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    public class CustomX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) throws CertificateException {

            // Here you can verify the servers certificate. (e.g. against one which is stored on mobile device)

            // InputStream inStream = null;
            // try {
            // inStream = MeaApplication.loadCertAsInputStream();
            // CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // X509Certificate ca = (X509Certificate)
            // cf.generateCertificate(inStream);
            // inStream.close();
            //
            // for (X509Certificate cert : certs) {
            // // Verifing by public key
            // cert.verify(ca.getPublicKey());
            // }
            // } catch (Exception e) {
            // throw new IllegalArgumentException("Untrusted Certificate!");
            // } finally {
            // try {
            // inStream.close();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            // }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    public class DataLoader {

        public HttpResponse secureLoadData(String url,
                                           List<NameValuePair> params)
                throws ClientProtocolException, IOException,
                NoSuchAlgorithmException, KeyManagementException,
                URISyntaxException, KeyStoreException, UnrecoverableKeyException {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                    new SecureRandom());

            HttpClient client = new DefaultHttpClient();

            SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = client.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            DefaultHttpClient sslClient = new DefaultHttpClient(ccm,
                    client.getParams());

            //HttpGet get = new HttpGet(new URI(url));
            //HttpResponse response = sslClient.execute(get);
            HttpPost post = new HttpPost(new URI(url));
            post.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = sslClient.execute(post);

            return response;
        }

    }


}
