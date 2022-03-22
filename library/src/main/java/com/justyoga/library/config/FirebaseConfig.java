package com.justyoga.library.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FirebaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public Firestore firebaseDatabase() {
        try {
            // URL resource = getClass().getResource(configPath);
            // InputStream serviceAccount = new FileInputStream(resource.getFile());
            InputStream serviceAccount = getClass().getResourceAsStream(configPath);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options =
                    new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .setStorageBucket("credo-f7d83.appspot.com")
                            .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.error("Error in initializing firebase database", e);
        }
        return FirestoreClient.getFirestore(FirebaseApp.getInstance());
    }

    @Value("${fit.justyoga.firebase.config.path}")
    private String configPath;

    @PostConstruct
    public void init() {}
}
