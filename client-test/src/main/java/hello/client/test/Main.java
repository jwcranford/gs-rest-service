package hello.client.test;

import hello.client.ApiClient;
import hello.client.api.GreetingControllerApi;
import hello.client.model.Greeting;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner run()
    {
        return args -> {
            final ApiClient client = new ApiClient();
            client.setBasePath("http://localhost:8080");
            GreetingControllerApi apiInstance = new GreetingControllerApi(client);
            String name = "World"; // String | name
            try {
                Greeting result = apiInstance.greetingUsingGET(name);
                System.out.println(result);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 408) {
                    System.out.println("408 - this is retriable");
                } else {
                    throw e;
                }
            } catch (HttpServerErrorException e) {
                final int status = e.getStatusCode().value();
                switch (status) {
                    case 502:
                    case 503:
                    case 504:
                        System.out.format("%d - retriable", status).println();
                        break;
                    case 500:
                        // check Retry-After and X-Retriable
                        throw e;
                    default:
                        throw e;
                }
            } catch (RuntimeException e) {
                System.err.println("Exception when calling GreetingControllerApi#greetingUsingGET");
                e.printStackTrace();
            }
        };
    }
}
