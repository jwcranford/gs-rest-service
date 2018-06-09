package hello.client.test;

import hello.client.ApiClient;
import hello.client.api.GreetingControllerApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableRetry
public class SpringConfiguration {

    private static final List<Integer> retryableStatusCodes = Arrays.asList(408, 502, 503, 504);

    @Bean
    public static RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
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
    public static Function<HttpStatusCodeException, Boolean> isRetryable() {
        return e -> retryableStatusCodes.contains(e.getRawStatusCode())
                || (e.getResponseHeaders().containsKey("Retry-After"));
    }

}

