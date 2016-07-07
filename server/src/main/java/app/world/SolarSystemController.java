package app.world;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by amindenwebb on 2016-07-07.
 */
@RestController
public class SolarSystemController {

    @RequestMapping("/world/solarsystem")
    public String solarsystem() throws IOException {
        return "{ status: OK }";
    }

}
