package org.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.FormEncoder;
public class FeignSimpleEncoderConfig {
    @Bean
    public Encoder encoder() {
        return new FormEncoder();
    }
}