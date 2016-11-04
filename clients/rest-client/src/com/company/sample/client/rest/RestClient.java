package com.company.sample.client.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RestClient {

    public static final String ENC = "UTF-8";

    private String accessToken;

    public static void main(String[] args) {
        RestClient client = new RestClient();
        try {
            client.login();
            client.createCustomer();
            client.createCustomerViaService();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Logs in with a user name and password and saves the access token for subsequent REST API invocations.
     */
    private void login() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:8080/app/rest/v2/oauth/token");

            // see cuba.rest.client.id and cuba.rest.client.secret application properties
            String credentials = Base64.getEncoder().encodeToString("client:secret".getBytes(ENC));
            post.setHeader("Authorization", "Basic " + credentials);

            // user credentials
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "password"));
            params.add(new BasicNameValuePair("username", "admin"));
            params.add(new BasicNameValuePair("password", "admin"));
            post.setEntity(new UrlEncodedFormEntity(params));

            System.out.println("Executing request " + post.getRequestLine());

            String json = httpclient.execute(post, new StringResponseHandler());
            JSONObject jsonObject = new JSONObject(json);
            accessToken = jsonObject.getString("access_token");

            System.out.println("Logged in, session id: " + accessToken);
        }
    }

    /**
     * Creates a Customer by sending JSON to the standard REST API CRUD method.
     */
    private void createCustomer() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Arthur Dent");
        jsonObject.put("email", "arthur.dent@mail.com");
        jsonObject.put("grade", "GOLD");
        String json = jsonObject.toString();

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:8080/app/rest/v2/entities/sample$Customer");
            post.setHeader("Authorization", "Bearer " + accessToken);

            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(stringEntity);

            System.out.println("Executing request " + post.getRequestLine());

            String response = httpclient.execute(post, new StringResponseHandler());

            System.out.println("Created Customer: " + response);
        }
    }

    /**
     * Creates a Customer by sending its attributes to the "sample_CustomerService" middleware service.
     */
    private void createCustomerViaService() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String customerName = URLEncoder.encode("Ford Prefect", ENC);
            String customerEmail = URLEncoder.encode("ford.prefect@mail.com", ENC);
            String customerGrade = URLEncoder.encode("GOLD", ENC);

            HttpGet get = new HttpGet("http://localhost:8080/app/rest/v2/services/sample_CustomerService/createCustomer?"
                    + "name=" + customerName
                    + "&email=" + customerEmail
                    + "&grade=" + customerGrade);
            get.setHeader("Authorization", "Bearer " + accessToken);

            System.out.println("Executing request " + get.getRequestLine());

            String customerId = httpclient.execute(get, new StringResponseHandler());
            System.out.println("Created Customer: " + customerId);
        }
    }

    private static class StringResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }
}
