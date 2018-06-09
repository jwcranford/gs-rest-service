package hello.client.test;

import hello.client.api.GreetingControllerApi;
import hello.client.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class GreetingService {
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
