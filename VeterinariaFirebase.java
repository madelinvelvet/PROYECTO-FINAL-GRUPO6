package com.veterinaria.app.veterinariafirebase;

import com.google.cloud.firestore.Firestore;
import java.util.Scanner;

public class VeterinariaFirebase {

    public static void main(String[] args) {

        ConexionFirebase conexion = new ConexionFirebase();
        Firestore db = conexion.getDb();

        MantenimientoClientes clientes = new MantenimientoClientes(db);
        MantenimientoMascotas mascotas = new MantenimientoMascotas(db);
        MantenimientoVeterinarios vets = new MantenimientoVeterinarios(db);
        MantenimientoCitas citas = new MantenimientoCitas(db);

        Scanner sc = new Scanner(System.in);
        int opcionPrincipal = 0;

        do {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1. Clientes");
            System.out.println("2. Mascotas");
            System.out.println("3. Veterinarios");
            System.out.println("4. Citas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcionPrincipal = Integer.parseInt(sc.nextLine());

            switch (opcionPrincipal) {
                case 1 -> menuClientes(clientes, sc);
                case 2 -> menuMascotas(mascotas, sc);
                case 3 -> menuVeterinarios(vets, sc);
                case 4 -> menuCitas(citas, sc);
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcionPrincipal != 5);
    }

    // ================== CLIENTES ==================
    public static void menuClientes(MantenimientoClientes clientes, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1. Agregar");
            System.out.println("2. Consultar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> clientes.agregarCliente();
                case 2 -> clientes.consultarCliente();
                case 3 -> clientes.actualizarCliente();
                case 4 -> clientes.eliminarCliente();
                case 5 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    // ================== MASCOTAS ==================
    public static void menuMascotas(MantenimientoMascotas mascotas, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n--- MENU MASCOTAS ---");
            System.out.println("1. Agregar");
            System.out.println("2. Consultar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> {
                    System.out.println("→ Agregar nueva mascota");
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Edad: ");
                    String edad = sc.nextLine();
                    System.out.print("Tipo: ");
                    String tipo = sc.nextLine();
                    System.out.print("ID Cliente: ");
                    String idCliente = sc.nextLine();
                    System.out.print("Peso: ");
                    String peso = sc.nextLine();
                    System.out.print("Observaciones: ");
                    String obs = sc.nextLine();
                    mascotas.agregarMascota(id, nombre, edad, tipo, idCliente, peso, obs);
                }
                case 2 -> mascotas.consultarMascota();
                case 3 -> mascotas.actualizarMascota();
                case 4 -> mascotas.eliminarMascota();
                case 5 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    // ================== VETERINARIOS ==================
    public static void menuVeterinarios(MantenimientoVeterinarios vets, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n--- MENU VETERINARIOS ---");
            System.out.println("1. Agregar");
            System.out.println("2. Consultar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> {
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Especialidad: ");
                    String esp = sc.nextLine();
                    System.out.print("Telefono: ");
                    String tel = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    vets.agregarVeterinario(id, nombre, esp, tel, email);
                }
                case 2 -> vets.consultarVeterinario();
                case 3 -> vets.actualizarVeterinario();
                case 4 -> vets.eliminarVeterinario();
                case 5 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }

    // ================== CITAS ==================
    public static void menuCitas(MantenimientoCitas citas, Scanner sc) {
        int opcion;
        do {
            System.out.println("\n--- MENU CITAS ---");
            System.out.println("1. Agregar");
            System.out.println("2. Consultar");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> citas.agregarCita();
                case 2 -> citas.consultarCita();
                case 3 -> citas.actualizarCita();
                case 4 -> citas.eliminarCita();
                case 5 -> System.out.println("Regresando al menu principal...");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 5);
    }
}
