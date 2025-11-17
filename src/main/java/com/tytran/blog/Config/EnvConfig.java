package com.tytran.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvConfig {

    public EnvConfig(Environment env) {
        String[] profiles = env.getActiveProfiles(); // lấy mảng các profile đang chạy, vd:[prod, test]
        String profile = (profiles.length > 0)
                ? profiles[0]
                : "default"; // nếu có ít nhất 1 phần tử thì lấy phần tử đầu tiên , ngược lại set default

        Dotenv dotenv;

        if ("prod".equals(profile)) {
            dotenv = Dotenv.configure()
                    .filename(".env.prod")
                    .load();
        } else {
            dotenv = Dotenv.configure()
                    .filename(".env")
                    .load();
        }

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
