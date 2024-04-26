package com.dev.timetracker.utility.category;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Getter
public class AddressAPI {

    String addrState;
    String addrCity;
    String addrStreet;
    String addrDistrict;
    String addrCountry;

    public AddressAPI(String addrZip) {
        this.getByZip(addrZip);
    }

    public void getByZip(String addrZip) {
        try {
            URI uri = new URI("https", "viacep.com.br", "/ws/" + addrZip + "/json/", null, null);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonParser jsonParser = objectMapper.createParser(String.valueOf(response));

                Map<String, Object> map = objectMapper.readValue(jsonParser, new TypeReference<>() {});
                this.addrState = map.get("uf").toString();
                this.addrCity = map.get("localidade").toString();
                this.addrStreet = map.get("logradouro").toString();
                this.addrDistrict = map.get("bairro").toString();
                this.addrCountry = "Brasil";
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}