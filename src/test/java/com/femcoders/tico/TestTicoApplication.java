package com.femcoders.tico;

import org.springframework.boot.SpringApplication;

public class TestTicoApplication {
     public static void main(String[] args) {
        SpringApplication
            .from(TicoApplication::main)
            .with(DevContainers.class)
            .run(args);
    }
    
}
