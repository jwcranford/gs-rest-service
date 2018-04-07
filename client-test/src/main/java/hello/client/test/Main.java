package hello.client.test;

import hello.client.ApiClient;
import hello.client.api.GreetingControllerApi;
import hello.client.model.Greeting;

public class Main {

    public static void main(String[] args) {
        final ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:8080");
        GreetingControllerApi apiInstance = new GreetingControllerApi(client);
        String name = "World"; // String | name
        try {
            Greeting result = apiInstance.greetingUsingGET(name);
            System.out.println(result);
        } catch (RuntimeException e) {
            System.err.println("Exception when calling GreetingControllerApi#greetingUsingGET");
            e.printStackTrace();
        }
    }
}
