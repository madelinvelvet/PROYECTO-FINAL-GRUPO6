package com.veterinaria.app.veterinariafirebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MantenimientoClientes {

    private Firestore db;
    private Scanner scanner = new Scanner(System.in);

    public MantenimientoClientes(Firestore db) {
        this.db = db;
    }

    // Metodo para verificar si un cliente existe
    private boolean existeCliente(String id) {
        try {
            DocumentReference docRef = db.collection("clientes").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            return document.exists();
        } catch (Exception e) {
            System.out.println("Error al verificar cliente: " + e.getMessage());
            return false;
        }
    }

    // Metodo que pide un ID existente
    private String pedirIdExistente() {
        String id;
        while (true) {
            System.out.print("Ingrese el ID del cliente: ");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("El ID no puede estar vacio. Intente de nuevo.\n");
                continue;
            }
            if (existeCliente(id)) {
                break;
            } else {
                System.out.println("El ID ingresado no existe. Intente de nuevo.\n");
            }
        }
        return id;
    }

    // Metodo para agregar cliente
    public void agregarCliente() {
        System.out.print("Ingrese el ID del cliente: ");
        String id = scanner.nextLine().trim();

        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese el email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese la direccion: ");
        String direccion = scanner.nextLine();

        try {
            Map<String, Object> cliente = new HashMap<>();
            cliente.put("nombre", nombre);
            cliente.put("telefono", telefono);
            cliente.put("email", email);
            cliente.put("direccion", direccion);

            ApiFuture<WriteResult> resultado = db.collection("clientes").document(id).set(cliente);
            System.out.println("Cliente agregado correctamente. Hora de registro: " + resultado.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
    }

    // Metodo para consultar cliente
    public void consultarCliente() {
        String id = pedirIdExistente();
        try {
            DocumentReference docRef = db.collection("clientes").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            System.out.println("Cliente encontrado: " + document.getData());
        } catch (Exception e) {
            System.out.println("Error al consultar cliente: " + e.getMessage());
        }
    }

    // Metodo para actualizar un campo del cliente
    public void actualizarCliente() {
        String id = pedirIdExistente();

        System.out.print("Ingrese el campo a actualizar (nombre, telefono, email, direccion): ");
        String campo = scanner.nextLine();
        System.out.print("Ingrese el nuevo valor: ");
        String valor = scanner.nextLine();

        try {
            ApiFuture<WriteResult> future = db.collection("clientes").document(id).update(campo, valor);
            System.out.println("Cliente actualizado correctamente. Hora de actualizacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    // Metodo para eliminar cliente
    public void eliminarCliente() {
        String id = pedirIdExistente();
        try {
            ApiFuture<WriteResult> future = db.collection("clientes").document(id).delete();
            System.out.println("Cliente eliminado correctamente. Hora de eliminacion: " + future.get().getUpdateTime());
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
