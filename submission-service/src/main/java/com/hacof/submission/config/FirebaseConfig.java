package com.hacof.submission.config;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import com.google.firebase.storage.FirebaseStorage;
// import com.google.firebase.storage.StorageReference;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    //    private static final String FIREBASE_CONFIG_PATH = "path/to/your/firebase-service-account.json"; // Adjust
    // this path
    //
    //    @Bean
    //    public FirebaseApp initializeFirebase() throws IOException {
    //        // Create a file input stream to read the service account key
    //        FileInputStream serviceAccount = new FileInputStream(FIREBASE_CONFIG_PATH);
    //
    //        // Initialize Firebase with the service account credentials
    //        FirebaseOptions options = new FirebaseOptions.Builder()
    //                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //                .build();
    //
    //        // Initialize Firebase app
    //        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
    //
    //        // Return the FirebaseApp instance
    //        return firebaseApp;
    //    }
    //
    //    @Bean
    //    public FirebaseStorage getFirebaseStorage() throws IOException {
    //        // Return FirebaseStorage instance
    //        return FirebaseStorage.getInstance();
    //    }
    //
    //    @Bean
    //    public StorageReference getStorageReference() throws IOException {
    //        // Get and return the default storage reference
    //        return getFirebaseStorage().getReference();
    //    }
}
