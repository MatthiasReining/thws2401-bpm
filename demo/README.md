# Camunda Explained
https://camunda.com/


# Start Server & Deploy

    mvn package && copy /y target\*.war ..\..\srv\camunda-bpm-wildfly-7.20.0\server\wildfly-29.0.0.Final\standalone\deployments\.

	
# Install

Pre-Condition: Java 21 

Tested with:

    openjdk version "17.0.5" 2022-10-18
    OpenJDK Runtime Environment GraalVM CE 22.3.0 (build 17.0.5+8-jvmci-22.3-b08)
    OpenJDK 64-Bit Server VM GraalVM CE 22.3.0 (build 17.0.5+8-jvmci-22.3-b08, mixed mode, sharing)


* Wildfly 29 / Camunda 
* Jakarta EE 10

* https://camunda.com/de/download/
* https://camunda.com/de/download/platform-7/
* https://downloads.camunda.cloud/release/camunda-bpm/
* https://downloads.camunda.cloud/release/camunda-bpm/wildfly/7.20/


# Show Around

Tasklist, Cockpit, Admin

# Setup Project

mvn archetype Jakarta EE 10

* camunda Dependencies: https://docs.camunda.org/manual/7.20/user-guide/cdi-java-ee-integration/

* beans.xml `META-INF/beans.xml`  `bean-discovery-mode="all"`
* processes.xml `META-INF/processes.xml`


# Key Java Elements

## Service Task: 

```
@RequestScoped 
@Named 
public class ZipTask implements JavaDelegate {
```


# Free Webservices: 

* https://documenter.getpostman.com/view/8854915/Szf7znEe
* https://publicapis.io/
* https://www.zippopotam.us/


# Java Example Webservice Call

```
String zip = execution.getVariable("zip").toString();
 // https://jakarta.ee/learn/docs/jakartaee-tutorial/current/websvcs/jaxrs-client/jaxrs-client.html
   
Client client = ClientBuilder.newClient();

// JsonObject data = client.target("http://api.zippopotam.us/de/" + zip)
// .request(MediaType.APPLICATION_JSON)
// .get(JsonObject.class);

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

System.out.println("zip: " + zip);
System.out.println("city: " + city);

execution.setVariable("city", city);


