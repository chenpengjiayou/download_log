package com.example.log.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientWithBasicAuth {

    private HttpClientBuilder httpClientBuilder;
    private CloseableHttpClient closeableHttpClient;

    public String doGetMethod(String host, int port, String URI) {
        if (closeableHttpClient == null) {
            //createCloseableHttpClient();
            createCloseableHttpClientWithBasicAuth();
        }
        String result = "";
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet("http://" + host + URI);
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public void closeHttpClient() {
        if (closeableHttpClient != null) {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCloseableHttpClient() {
        if (closeableHttpClient == null) {
            // 创建HttpClientBuilder
            httpClientBuilder = HttpClientBuilder.create();
            // HttpClient
            closeableHttpClient = httpClientBuilder.build();
        }
    }

    public void createCloseableHttpClientWithBasicAuth() {
        if (closeableHttpClient == null) {
            // 创建HttpClientBuilder
            httpClientBuilder = HttpClientBuilder.create();
            // 设置BasicAuth
            CredentialsProvider provider = new BasicCredentialsProvider();
            // Create the authentication scope
            AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
            // Create credential pair，在此处填写用户名和密码
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ylfood", "ffovwqg2hSiqyuLetz");
            // Inject the credentials
            provider.setCredentials(scope, credentials);
            // Set the default credentials provider
            httpClientBuilder.setDefaultCredentialsProvider(provider);
            // HttpClient
            closeableHttpClient = httpClientBuilder.build();
        }
    }

	/*private HttpClientContext createBasicAuthContext(HttpHost host,String username, String password) {
	    CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    Credentials defaultCreds = new UsernamePasswordCredentials(username, password);
	    credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), defaultCreds);
	    AuthCache authCache = new BasicAuthCache();
	    BasicScheme basicAuth = new BasicScheme();
	    authCache.put(host, basicAuth);
	    HttpClientContext context = HttpClientContext.create();
	    context.setCredentialsProvider(credsProvider);
	    context.setAuthCache(authCache);
	    return context;
	}*/

    public static void main(String args[]) {
        String host = "39.108.148.225";
        int port = 8080;
        String URI = "/2019/03/26/tomcat-b2cnew/";

        HttpClientWithBasicAuth httpClient = new HttpClientWithBasicAuth();
        String result = httpClient.doGetMethod(host, port, URI);
        System.out.println(result);
        result = httpClient.doGetMethod(host, port, URI);
        System.out.println(result);
    }
}