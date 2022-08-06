package api.async.app;

import api.async.app.model.User;
import api.async.app.services.GitHubLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookUpService gitHubLookUpService;

    @Autowired
    public AppRunner(GitHubLookUpService gitHubLookUpService) {
        this.gitHubLookUpService = gitHubLookUpService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        //Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = gitHubLookUpService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookUpService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookUpService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: {}", (System.currentTimeMillis() - start));
        logger.info("--> {}", page1.get());
        logger.info("--> {}", page2.get());
        logger.info("--> {}", page3.get());
    }
}
