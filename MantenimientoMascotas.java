package com.veterinaria.app.veterinariafirebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MantenimientoMascotas {

    private Firestore db;
    private Scanner scanner = new Scanner(System.in);

    public MantenimientoMascotas(Firestore db) {
        this.db = db;
    }

    // Metodo para verificar si una mascota existe
    private boolean existeMascota(String id) {
        try {
            DocumentReference docRef = db.collection("mascotas").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            return document.exists();
        } catch (Exception e) {
            System.out.println("Error al verificar mascota: " + e.getMessage());
            return false;
        }
    }

    // Metodo que pide un ID existente
    private String pedirIdExistente() {
        String id;
        while (true) {
            System.out.print("Ingrese el ID de la mascota: ");
            id = scanner.nextLine();
            if (existeMascota(id)) {
                break;
            } else {
                System.out.println("El ID ingresado no existe. Intente de nuevo.\n");
            }
        }
        return id;
    }

    // Metodo para agregar una mascota
    public void agregarMascota(String id, String nombre, String edad, String tipo, String idCliente, String peso, String observaciones) {
        try {
            Map<String, Object> mascota = new HashMap<>();
            mascota.put("nombre", nombre);
            mascota.put("edad", edad);
            mascota.put("tipo", tipo);
            mascota.put("idCliente", idCliente);
            mascota.put("peso", peso);
            mascota.put("observaciones", observaciones);

            ApiFuture<WriteResult> resultado = db.collection("mascotas").document(id).set(mascota);
            System.out.println("Mascota agregada correctamente. Hora de registro: " + resultado.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al agregar mascota: " + e.getMessage());
        }
    }

    // Metodo para consultar mascota
    public void consultarMascota() {
        String id = pedirIdExistente();
        try {
            DocumentReference docRef = db.collection("mascotas").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            System.out.println("Mascota encontrada: " + document.getData());
        } catch (Exception e) {
            System.out.println("Error al consultar mascota: " + e.getMessage());
        }
    }

    // Metodo para actualizar una mascota
    public void actualizarMascota() {
        String id = pedirIdExistente();

        System.out.print("Ingrese el campo a actualizar (nombre, edad, tipo, idCliente, peso, observaciones): ");
        String campo = scanner.nextLine();
        System.out.print("Ingrese el nuevo valor: ");
        String valor = scanner.nextLine();

        try {
            ApiFuture<WriteResult> future = db.collection("mascotas").document(id).update(campo, valor);
            System.out.println("Mascota actualizada correctamente. Hora de actualizacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al actualizar mascota: " + e.getMessage());
        }
    }

    // Metodo para eliminar mascota
    public void eliminarMascota() {
        String id = pedirIdExistente();
        try {
            ApiFuture<WriteResult> future = db.collection("mascotas").document(id).delete();
            System.out.println("Mascota eliminada correctamente. Hora de eliminacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al eliminar mascota: " + e.getMessage());
        }
    }
}
