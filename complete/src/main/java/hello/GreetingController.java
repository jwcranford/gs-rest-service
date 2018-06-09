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
        switch(name) {
            case "503":
                System.out.println("replying with 503...");
                return ResponseEntity.status(503).build();
            case "Retry-After":
                System.out.println("replying with 500 with Retry-After header...");
                return ResponseEntity.status(500).header("Retry-After", "30").build();
            case "even":
                if (id % 2 == 0) {
                    System.out.println("even - ok");
                    return ResponseEntity.ok(new Greeting(id, String.format(template, name)));
                } else {
                    System.out.println("odd - replying with 503...");
                    return ResponseEntity.status(503).build();
                }
            default:
                return ResponseEntity.ok(new Greeting(id, String.format(template, name)));
        }
    }
}
