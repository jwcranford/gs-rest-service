package hello;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping( path = "/greeting", method = RequestMethod.GET)
    public ResponseEntity<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        final long id = counter.incrementAndGet();
        if (!"Bob".equals(name) || id % 2 == 0) {
            System.out.println("ok");
            return ResponseEntity.ok(new Greeting(id, String.format(template, name)));
        } else {
            System.out.println("503");
            return ResponseEntity.status(503).build();
        }
    }
}
