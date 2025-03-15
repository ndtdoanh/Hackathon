package com.hacof.submission.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initialize() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("D:/Download/Ki8/hacof-7d63e-firebase-adminsdk-fbsvc-b004f29c7f.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://hacof-7d63e.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
