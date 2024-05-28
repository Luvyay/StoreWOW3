package ru.gb.FigurineStore.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "ru.gb.FigurineStore.proxy")
public class ProjectConfigOpenFeign {
}
