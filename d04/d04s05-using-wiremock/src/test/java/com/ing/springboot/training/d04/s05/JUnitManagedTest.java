package com.ing.springboot.training.d04.s05;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@WireMockTest(httpPort = JUnitManagedTest.LOCAL_PORT)
public class JUnitManagedTest {

    public static final int LOCAL_PORT = 9999;

    private static final String WIREMOCK_PATH = "/testing/wiremock";
    private static final String APPLICATION_JSON = "application/json";

    private static final String LOCAL_SERVER_URL = "http://localhost:" + LOCAL_PORT + "/testing/wiremock";

    @Test
    public void givenAJUnitManagedWireMockServer_whenReadingAJSONResponse_thenTheResponseIsCorrectlyRead() {
        stubFor(get(urlPathMatching("/testing/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody("{\"library\": \"WireMock\"}")));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(LOCAL_SERVER_URL);
            HttpResponse httpResponse = httpClient.execute(request);

            verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
            assertEquals(200, httpResponse.getStatusLine().getStatusCode());
            assertEquals(APPLICATION_JSON, httpResponse.getFirstHeader("Content-Type").getValue());
            assertEquals("{\"library\": \"WireMock\"}", convertHttpResponseToString(httpResponse));
        } catch (IOException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    private String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
    }

    @Test
    public void givenAJUnitManagedWireMockServer_whenMatchingHeaders_thenTheResponseIsCorrectlyRead() {
        final String contentType = ContentType.TEXT_HTML.getMimeType();
        stubFor(get(urlPathEqualTo(WIREMOCK_PATH))
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", contentType)
                        .withBody("Service Unavailable")));

        HttpResponse httpResponse;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(LOCAL_SERVER_URL);
            request.addHeader("Accept", contentType);
            httpResponse = httpClient.execute(request);

            verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
            assertEquals(503, httpResponse.getStatusLine().getStatusCode());
            assertEquals(contentType, httpResponse.getFirstHeader("Content-Type").getValue());
            assertEquals("Service Unavailable", convertHttpResponseToString(httpResponse));
        } catch (IOException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    @Test
    public void givenAJUnitManagedWireMockServer_whenMatchingBody_thenCorrect() {
        stubFor(post(urlEqualTo(WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON))
                .withRequestBody(containing("\"testing-library\": \"WireMock\""))
                .willReturn(aResponse().withStatus(200)));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = executeRequest(new StringEntity(getJsonString()), httpClient);
            assertEquals(200, response.getStatusLine().getStatusCode());
            verify(postRequestedFor(urlEqualTo(WIREMOCK_PATH))
                    .withHeader("Content-Type", equalTo(APPLICATION_JSON)));
        } catch (IOException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    private static HttpResponse executeRequest(StringEntity stringEntity, CloseableHttpClient httpClient) throws IOException {
        HttpPost request = new HttpPost(LOCAL_SERVER_URL);
        request.addHeader("Content-Type", APPLICATION_JSON);
        request.setEntity(stringEntity);
        return httpClient.execute(request);
    }

    private String getJsonString() throws IOException {
        try (InputStream jsonInputStream = getClass().getClassLoader()
                                                     .getResourceAsStream("wiremock_intro.json")) {
            return convertInputStreamToString(jsonInputStream);
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        if (inputStream == null) return null;
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    @Test
    public void givenAJUnitManagedServer_whenNotUsingPriority_thenTheResponseIsCorrect() throws IOException {
        stubFor(get(urlPathMatching("/testing/.*"))
                .willReturn(aResponse().withStatus(200)));

        stubFor(get(urlPathEqualTo(WIREMOCK_PATH))
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void givenAJUnitManagedServer_whenUsingPriority_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/testing/.*"))
                .atPriority(1)
                .willReturn(aResponse().withStatus(200)));

        stubFor(get(urlPathEqualTo(WIREMOCK_PATH))
                .atPriority(2)
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(WIREMOCK_PATH)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    private HttpResponse generateClientAndReceiveResponseForPriorityTests() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(LOCAL_SERVER_URL);
            request.addHeader("Accept", ContentType.TEXT_HTML.getMimeType());
            return httpClient.execute(request);
        }
    }
}