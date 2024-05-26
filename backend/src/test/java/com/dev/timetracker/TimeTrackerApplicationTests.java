package com.dev.timetracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeTrackerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void timeTrackerMainTest() {
        TimeTrackerApplication.main(new String[]{});
    }
}