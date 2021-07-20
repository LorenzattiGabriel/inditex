package com.example.Inditex.config;


import com.example.Inditex.config.factory.YamlPropertySourceFactory;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource(value = "classpath:prices.yml", factory = YamlPropertySourceFactory.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceConfig {

    @Setter
    @Getter
    private String curr;

    @Setter
    @Getter
    private Float taxes;

}
