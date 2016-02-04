package com.company.sample.client.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class RestClient {

    public static final String ENC = "UTF-8";

    private String sessionId;

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
     * Logs in with a user name and password and saves the session id for subsequent REST API invocations.
     */
    private void login() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String user = "admin";
            String password = "admin";
            HttpGet httpGet = new HttpGet("http://localhost:8080/app-portal/api/login?u=" + user + "&p=" + password);

            System.out.println("Executing request " + httpGet.getRequestLine());

            sessionId = httpclient.execute(httpGet, new StringResponseHandler());
            System.out.println("Logged in, session id: " + sessionId);
        }
    }

    /**
     * Creates a Customer by sending JSON to the standard REST API method "commit".
     */
    private void createCustomer() throws IOException {
        String json = readResource("/com/company/sample/client/rest/createCustomer.json");

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/app-portal/api/commit?s=" + sessionId);
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            System.out.println("Executing request " + httpPost.getRequestLine());

            String response = httpclient.execute(httpPost, new StringResponseHandler());
            System.out.println("Created Customer: " + response);
        }
    }

    /**
     * Creates a Customer by sending its attributes to the "sample_CustomerService" application service.
     */
    private void createCustomerViaService() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String customerName = URLEncoder.encode("Ford Prefect", ENC);
            String customerEmail = URLEncoder.encode("ford.prefect@mail.com", ENC);
            String customerGrade = URLEncoder.encode("GOLD", ENC);
            HttpGet httpGet = new HttpGet("http://localhost:8080/app-portal/api/service.json?s=" + sessionId
                    + "&service=sample_CustomerService"
                    + "&method=createCustomer"
                    + "&param0=" + customerName
                    + "&param1=" + customerEmail
                    + "&param2=" + customerGrade);

            System.out.println("Executing request " + httpGet.getRequestLine());

            String customerId = httpclient.execute(httpGet, new StringResponseHandler());
            System.out.println("Created Customer: " + customerId);
        }
    }

    private String readResource(String name) throws IOException {
        InputStream is = getClass().getResourceAsStream(name);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
        }
        return new String(os.toByteArray(), ENC);
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
