package hello.client.test;

import hello.client.ApiClient;
import hello.client.api.GreetingControllerApi;
import hello.client.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Main {

    private final GreetingControllerApi greetingControllerApi;

    @Autowired
    public Main(GreetingControllerApi greetingControllerApi) {
        this.greetingControllerApi = greetingControllerApi;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public static RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RetriableResponseInterceptor(408, 502, 503, 504));
        return restTemplate;
    }

    @Bean
    public static ApiClient apiClient(RestTemplate restTemplate) {
        final ApiClient client = new ApiClient(restTemplate);
        client.setBasePath("http://localhost:8080");
        return client;
    }

    @Bean
    public static GreetingControllerApi greetingControllerApi(ApiClient apiClient) {
        return new GreetingControllerApi(apiClient);
    }

    @Bean
    public CommandLineRunner run(GreetingControllerApi greetingControllerApi)
    {
        return args -> {
            String name = "World"; // String | name
            try {
                System.out.println(greeting(name).toString());
            } catch (RetriableResponseInterceptor.RetriableException e) {
                System.out.println("SUCCESS! retriable exception " + e.toString());
            }
        };
    }

    public Greeting greeting(String name) {
        return greetingControllerApi.greetingUsingGET(name);
    }
}
