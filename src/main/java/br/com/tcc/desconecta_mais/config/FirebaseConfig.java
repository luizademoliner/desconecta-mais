package br.com.tcc.desconecta_mais.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseConfig {

    // aponta pro arquivo definido em application.yaml (firebase.credentials-path)
    @Value("${firebase.credentials-path}")
    private Resource credentialsResource;

    @PostConstruct
    public void initialize() {
        try {
            // evita inicializar de novo se a aplicação já tiver um FirebaseApp ativo
            // (acontece em testes ou em hot-reload do Spring DevTools)
            if (!FirebaseApp.getApps().isEmpty()) {
                return;
            }

            try (InputStream serviceAccount = credentialsResource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível inicializar o Firebase Admin SDK. " +
                    "Verifique se o arquivo de credenciais existe no caminho configurado em firebase.credentials-path.", e);
        }
    }
}