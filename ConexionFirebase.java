    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.veterinaria.app.veterinariafirebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class ConexionFirebase {

    private Firestore db;

    // Constructor que inicializa la conexión
    public ConexionFirebase() {
        try {
            // Ruta al archivo JSON de credenciales
            FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\Jermi\\Desktop\\VeterinariaFirebase\\VeterinariaFirebase\\veterinariabd-ce301-firebase-adminsdk-fbsvc-86b3615ca5.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

            System.out.println("Conexion a Firebase exitosa.");

            // Prueba rápida: lista las colecciones existentes
            db.listCollections().forEach(col -> System.out.println(" Coleccion: " + col.getId()));

        } catch (IOException e) {
            System.err.println(" Error al conectar con Firebase: " + e.getMessage());
        }
    }

    // Getter para Firestore
    public Firestore getDb() {
        return db;
    }

    // Main temporal para probar la conexión directamente
    public static void main(String[] args) {
        new ConexionFirebase();
    }
}

