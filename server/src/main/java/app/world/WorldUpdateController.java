package app.world;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amindenwebb on 2016-07-07.
 */
@RestController
public class WorldUpdateController {

    @RequestMapping("/world/update")
    public String update() {
        try {
            (new WorldUpdater()).updateWorldData();
        } catch (FailedUpdateException e) {
            return "ERROR";
        }
        return "OK";
    }
}