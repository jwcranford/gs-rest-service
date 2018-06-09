package hello.client.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    private final GreetingService greetingService;

    @Autowired
    public Main(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run()
    {
        return args -> {
            String name = "Bob"; // String | name
            if (args != null && args.length >= 1) {
                name = args[0];
            }
            System.out.println("Sending " + name);
            System.out.println(greetingService.greeting(name).toString());
        };
    }

}
