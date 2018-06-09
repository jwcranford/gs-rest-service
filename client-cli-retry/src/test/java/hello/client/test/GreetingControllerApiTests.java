package hello.client.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Integration tests for client-cli-retry.
 * Adapted from complete/.../GreetingControllerTests.java and
 * automatically-generated client/.../GreetingControllerApiTest.java.
 */
// ignored because these tests require the service to be running - they're
// integration tests, not unit tests
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingControllerApiTests {

    @Autowired
    private GreetingService greetingService;


    @Test
    public void noParamGreetingShouldReturnDefaultMessage() {
        Assert.assertEquals("Hello, World!", greetingService.greeting(null).getContent());
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() {
        Assert.assertEquals("Hello, Bob!", greetingService.greeting("Bob").getContent());
    }

    @Test(expected = HttpServerErrorException.class)
    public void errorShouldThrowException() {
        greetingService.greeting("503");
    }

}
