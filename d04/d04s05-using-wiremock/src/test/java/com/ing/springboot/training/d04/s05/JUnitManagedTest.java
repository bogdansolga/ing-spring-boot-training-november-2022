package com.ing.springboot.training.d04.s05;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class JUnitManagedTest {

    private static final String WIREMOCK_PATH = "/testing/wiremock";
    private static final String APPLICATION_JSON = "application/json";
    static int port;
    
    static {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            // No OPS
        }
    }
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);

    @Test
    public void givenJUnitManagedServer_whenMatchingURL_thenCorrect() throws IOException {
        
        stubFor(get(urlPathMatching("/testing/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody("\"testing-library\": \"WireMock\"")));

        HttpResponse httpResponse;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(String.format("http://localhost:%s/testing/wiremock", port));
            httpResponse = httpClient.execute(request);
        }
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals(APPLICATION_JSON, httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("\"testing-library\": \"WireMock\"", stringResponse);
    }

    @Test
    public void givenJUnitManagedServer_whenMatchingHeaders_thenCorrect() throws IOException {
        stubFor(get(urlPathEqualTo(WIREMOCK_PATH))
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "text/html")
                        .withBody("!!! Service Unavailable !!!")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/testing/wiremock", port));
        request.addHeader("Accept", "text/html");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
        assertEquals("text/html", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("Service Unavailable", stringResponse);
    }

    @Test
    public void givenJUnitManagedServer_whenMatchingBody_thenCorrect() throws IOException {
        stubFor(post(urlEqualTo(WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON))
                .withRequestBody(containing("\"testing-library\": \"WireMock\""))
                .withRequestBody(containing("\"creator\": \"Tom Akehurst\""))
                .withRequestBody(containing("\"website\": \"wiremock.org\""))
                .willReturn(aResponse().withStatus(200)));

        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("wiremock_intro.json");
        String jsonString = convertInputStreamToString(jsonInputStream);
        StringEntity entity = new StringEntity(jsonString);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(String.format("http://localhost:%s/testing/wiremock", port));
            request.addHeader("Content-Type", APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            assertEquals(200, response.getStatusLine().getStatusCode());
        }

        verify(postRequestedFor(urlEqualTo(WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON)));
    }

    @Test
    public void givenJUnitManagedServer_whenNotUsingPriority_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/testing/.*")).willReturn(aResponse().withStatus(200)));
        stubFor(get(urlPathEqualTo(WIREMOCK_PATH)).withHeader("Accept", matching("text/.*"))
                                                  .willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void givenJUnitManagedServer_whenUsingPriority_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/testing/.*")).atPriority(1).willReturn(aResponse().withStatus(200)));
        stubFor(get(urlPathEqualTo(WIREMOCK_PATH)).atPriority(2).withHeader("Accept", matching("text/.*")).willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    private static String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }

    private HttpResponse generateClientAndReceiveResponseForPriorityTests() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(String.format("http://localhost:%s/testing/wiremock", port));
            request.addHeader("Accept", "text/xml");
            return httpClient.execute(request);
        }
    }
}