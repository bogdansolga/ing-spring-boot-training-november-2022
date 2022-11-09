package com.ing.springboot.training.d04.s05;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ProgrammaticallyManagedTest {

    private static final String TESTED_PATH = "/spring-boot";


    @Test
    public void givenAProgrammaticallyManagedWireMockServer_whenUsingSimpleStubbing_thenTheResponseIsCorrect() throws IOException {
        final WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();

        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo(TESTED_PATH)).willReturn(aResponse().withBody("Welcome to WireMock!")));

        HttpGet request = new HttpGet("http://localhost:8080/spring-boot");
        HttpResponse httpResponse;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            httpResponse = httpClient.execute(request);
            String stringResponse = convertResponseToString(httpResponse);

            verify(getRequestedFor(urlEqualTo(TESTED_PATH)));
            assertEquals("Welcome to WireMock!", stringResponse);
        } catch (IOException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }

        wireMockServer.stop();
    }

    private static String convertResponseToString(HttpResponse httpResponse) throws IOException {
        return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
    }
}