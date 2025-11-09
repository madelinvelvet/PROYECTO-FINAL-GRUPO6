package com.veterinaria.app.veterinariafirebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MantenimientoCitas {

    private Firestore db;
    private Scanner scanner = new Scanner(System.in);

    public MantenimientoCitas(Firestore db) {
        this.db = db;
    }

    // Metodo para verificar si un documento existe en una coleccion
    private boolean existeDocumento(String coleccion, String id) {
        try {
            DocumentReference docRef = db.collection(coleccion).document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            return document.exists();
        } catch (Exception e) {
            System.out.println("Error al verificar en " + coleccion + ": " + e.getMessage());
            return false;
        }
    }

    // Metodo que pide un ID existente de una coleccion especifica
    private String pedirIdExistente(String coleccion, String tipo) {
        String id;
        while (true) {
            System.out.print("Ingrese el ID de " + tipo + ": ");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("El ID no puede estar vacio. Intente de nuevo.\n");
                continue;
            }
            if (existeDocumento(coleccion, id)) {
                break;
            } else {
                System.out.println("El ID ingresado no existe en " + coleccion + ". Intente de nuevo.\n");
            }
        }
        return id;
    }

    // Metodo para agregar cita
    public void agregarCita() {
        System.out.print("Ingrese el ID de la cita: ");
        String id = scanner.nextLine().trim();

        String idMascota = pedirIdExistente("mascotas", "la mascota");
        String idVeterinario = pedirIdExistente("veterinarios", "el veterinario");

        System.out.print("Ingrese la fecha: ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese el motivo: ");
        String motivo = scanner.nextLine();
        System.out.print("Ingrese observaciones: ");
        String observaciones = scanner.nextLine();

        try {
            Map<String, Object> cita = new HashMap<>();
            cita.put("idMascota", idMascota);
            cita.put("idVeterinario", idVeterinario);
            cita.put("fecha", fecha);
            cita.put("motivo", motivo);
            cita.put("observaciones", observaciones);

            ApiFuture<WriteResult> resultado = db.collection("citas").document(id).set(cita);
            System.out.println("Cita agregada correctamente. Hora de registro: " + resultado.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al agregar cita: " + e.getMessage());
        }
    }

    // Metodo para consultar cita
    public void consultarCita() {
        String id = pedirIdExistente("citas", "la cita");
        try {
            DocumentReference docRef = db.collection("citas").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            System.out.println("Cita encontrada: " + document.getData());
        } catch (Exception e) {
            System.out.println("Error al consultar cita: " + e.getMessage());
        }
    }

    // Metodo para actualizar cita
    public void actualizarCita() {
        String id = pedirIdExistente("citas", "la cita");

        System.out.print("Ingrese el campo a actualizar (idMascota, idVeterinario, fecha, motivo, observaciones): ");
        String campo = scanner.nextLine();
        System.out.print("Ingrese el nuevo valor: ");
        String valor = scanner.nextLine();

        try {
            ApiFuture<WriteResult> future = db.collection("citas").document(id).update(campo, valor);
            System.out.println("Cita actualizada correctamente. Hora de actualizacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al actualizar cita: " + e.getMessage());
        }
    }

    // Metodo para eliminar cita
    public void eliminarCita() {
        String id = pedirIdExistente("citas", "la cita");
        try {
            ApiFuture<WriteResult> future = db.collection("citas").document(id).delete();
            System.out.println("Cita eliminada correctamente. Hora de eliminacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al eliminar cita: " + e.getMessage());
        }
    }
}
