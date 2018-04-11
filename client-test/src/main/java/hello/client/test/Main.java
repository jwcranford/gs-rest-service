package hello.client.test;

import hello.client.api.GreetingControllerApi;
import hello.client.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@SpringBootApplication
public class Main {

    private final GreetingService greetingService;

    @Autowired
    public Main(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner run()
    {
        return args -> {
            String name = "Bob"; // String | name
            System.out.println(greetingService.greeting(name).toString());
        };
    }

}

@Service
class GreetingService {
    private final GreetingControllerApi greetingControllerApi;

    @Autowired
    public GreetingService(GreetingControllerApi greetingControllerApi) {
        this.greetingControllerApi = greetingControllerApi;
    }

    @Retryable(value={HttpStatusCodeException.class},
            exceptionExpression = "#{@isRetryable.apply(#root)}")
    public Greeting greeting(String name) {
        return greetingControllerApi.greetingUsingGET(name);
    }
}
