package com.changenode.frisson;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class SupabaseDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    /* Reduce Spring build noise */
    @Configuration
    class Config {
    }

}
