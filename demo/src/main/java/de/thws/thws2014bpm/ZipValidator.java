package de.thws.thws2014bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Named
public class ZipValidator implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("zip code: " + execution.getVariable("zip"));

        String zip = execution.getVariable("zip").toString();
        String city = "";
        Client client = ClientBuilder.newClient();
        Response resp = client.target("http://api.zippopotam.us/de/" + zip)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (200 == resp.getStatus()) {
            JsonObject data = resp.readEntity(JsonObject.class);
            JsonArray places = data.getJsonArray("places");
            JsonObject place = places.getJsonObject(0);
            city = place.getString("place name");
        } else {
            System.out.println("no valid city found for " + zip);
            System.out.println("error: " + resp.getEntity());
        }

       /*  JsonObject resp = client.target("http://api.zippopotam.us/de/" + zip)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        JsonArray places = resp.getJsonArray("places");
        JsonObject place = places.getJsonObject(0);
        String city = place.getString("place name");*/ 

        System.out.println("zip: " + zip);
        System.out.println("city: " + city);

        execution.setVariable("city", city);

    }

}
