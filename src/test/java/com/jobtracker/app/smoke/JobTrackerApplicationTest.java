package com.jobtracker.app.smoke;

import com.jobtracker.app.JobTrackerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobTrackerApplicationTests {
    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodRuns() {
        JobTrackerApplication.main(new String[]{});
    }
}