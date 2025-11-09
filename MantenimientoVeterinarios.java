package com.veterinaria.app.veterinariafirebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MantenimientoVeterinarios {

    private Firestore db;
    private Scanner scanner = new Scanner(System.in);

    public MantenimientoVeterinarios(Firestore db) {
        this.db = db;
    }

    // Metodo para verificar si un veterinario existe
    private boolean existeVeterinario(String id) {
        try {
            DocumentReference docRef = db.collection("veterinarios").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            return document.exists();
        } catch (Exception e) {
            System.out.println("Error al verificar veterinario: " + e.getMessage());
            return false;
        }
    }

    // Metodo que pide un ID valido (que exista en Firebase)
    private String pedirIdExistente() {
        String id;
        while (true) {
            System.out.print("Ingrese el ID del veterinario: ");
            id = scanner.nextLine();
            if (existeVeterinario(id)) {
                break;
            } else {
                System.out.println("El ID ingresado no existe. Intente de nuevo.\n");
            }
        }
        return id;
    }

    // Metodo para agregar veterinario
    public void agregarVeterinario(String id, String nombre, String especialidad, String telefono, String email) {
        try {
            Map<String, Object> vet = new HashMap<>();
            vet.put("nombre", nombre);
            vet.put("especialidad", especialidad);
            vet.put("telefono", telefono);
            vet.put("email", email);

            ApiFuture<WriteResult> resultado = db.collection("veterinarios").document(id).set(vet);
            System.out.println("Veterinario agregado correctamente. Hora de registro: " + resultado.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al agregar veterinario: " + e.getMessage());
        }
    }

    // Metodo para consultar veterinario (verifica ID)
    public void consultarVeterinario() {
        String id = pedirIdExistente();
        try {
            DocumentReference docRef = db.collection("veterinarios").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            System.out.println("Veterinario encontrado: " + document.getData());
        } catch (Exception e) {
            System.out.println("Error al consultar veterinario: " + e.getMessage());
        }
    }

    // Metodo para actualizar un campo del veterinario (verifica ID)
    public void actualizarVeterinario() {
        String id = pedirIdExistente();

        System.out.print("Ingrese el campo a actualizar (nombre, especialidad, telefono, email): ");
        String campo = scanner.nextLine();
        System.out.print("Ingrese el nuevo valor: ");
        String valor = scanner.nextLine();

        try {
            ApiFuture<WriteResult> future = db.collection("veterinarios").document(id).update(campo, valor);
            System.out.println("Veterinario actualizado correctamente. Hora de actualizacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al actualizar veterinario: " + e.getMessage());
        }
    }

    // Metodo para eliminar veterinario (verifica ID)
    public void eliminarVeterinario() {
        String id = pedirIdExistente();
        try {
            ApiFuture<WriteResult> future = db.collection("veterinarios").document(id).delete();
            System.out.println("Veterinario eliminado correctamente. Hora de eliminacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al eliminar veterinario: " + e.getMessage());
        }
    }
}
