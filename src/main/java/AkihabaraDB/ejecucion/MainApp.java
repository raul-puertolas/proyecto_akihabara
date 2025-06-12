package AkihabaraDB.ejecucion;

import java.util.List;
import java.util.Scanner;

import AkihabaraDB.dao.ProductoDAO_Impl;
import AkihabaraDB.model.ProductoOtaku;
import AkihabaraDB.service.LlmService;
import AkihabaraDB.view.InterfazConsola;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InterfazConsola consola = new InterfazConsola();       // Interfaz para interacción por consola
        ProductoDAO_Impl programa = new ProductoDAO_Impl();     // Implementación DAO para productos

        // Productos iniciales agregados al sistema
        ProductoOtaku p1 = new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 9);
        ProductoOtaku p2 = new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20);
        ProductoOtaku p3 = new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15);

        programa.agregarProducto(p1);
        programa.agregarProducto(p2);
        programa.agregarProducto(p3);

        LlmService iaService;
        try {
            iaService = new LlmService();  // Inicializa el servicio para interactuar con IA
        } catch (Exception e) {
            System.out.println("Error al inicializar el servicio de IA: " + e.getMessage());
            sc.close();
            return;
        }

        int opcion = 0;

        // Bucle principal que muestra el menú y gestiona las opciones
        while (opcion != 10) {
            consola.mostrarMenu();
            opcion = consola.pedirOpcion();

            switch (opcion) {
                case 1:  // Añadir un producto nuevo
                    String nombre = consola.pedirNombre();
                    String categoria = consola.pedirCategoria();
                    double precio = consola.pedirPrecio();
                    int stock = consola.pedirStock();

                    ProductoOtaku producto = new ProductoOtaku(nombre, categoria, precio, stock);
                    boolean seHaAgregado = programa.agregarProducto(producto);
                    if (seHaAgregado) {
                        consola.agregarProducto_true();
                    } else {
                        consola.agregarProducto_false();
                    }
                    break;

                case 2:  // Buscar producto por ID
                    int idBuscar = consola.pedirId_obtenerProductoPorId();
                    ProductoOtaku productoBuscado = programa.obtenerProductoPorId(idBuscar);
                    if (productoBuscado == null) {
                        consola.obtenerProductoPorId_null();
                    } else {
                        System.out.println(productoBuscado);
                    }
                    break;

                case 3:  // Mostrar todos los productos
                    List<ProductoOtaku> lista = programa.obtenerTodosLosProductos();
                    if (lista.isEmpty()) {
                        consola.obtenerTodosLosProductos_listaVacia();
                    } else {
                        consola.mostrarObjetosFormateados(lista);
                    }
                    break;

                case 4:  // Actualizar un producto
                    int idActualizar = consola.pedirID_actualizarProducto();
                    String nuevoNombre = consola.pedirNombre_actualizarProducto();
                    String nuevaCategoria = consola.pedirCategoria_actualizarProducto();
                    double nuevoPrecio = consola.pedirPrecio_actualizarProducto();
                    int nuevoStock = consola.pedirStock_actualizarProducto();

                    ProductoOtaku productoActualizado = new ProductoOtaku(idActualizar, nuevoNombre, nuevaCategoria,
                            nuevoPrecio, nuevoStock);
                    boolean actualizado = programa.actualizarProducto(productoActualizado);
                    if (actualizado) {
                        consola.actualizarProducto_true();
                    } else {
                        consola.actualizarProducto_false();
                    }
                    break;

                case 5:  // Eliminar un producto
                    int idEliminar = consola.pedirId_eliminarProducto();
                    boolean eliminado = programa.eliminarProducto(idEliminar);
                    if (eliminado) {
                        consola.eliminarProducto_true();
                    } else {
                        consola.eliminarProducto_false();
                    }
                    break;

                case 6:  // Buscar productos por nombre (búsqueda parcial)
                    String nombreBuscar = consola.pedirNombre_buscarProductosPorNombre();
                    List<ProductoOtaku> resultados = programa.buscarProductosPorNombre(nombreBuscar);
                    if (resultados.isEmpty()) {
                        consola.buscarProductosPorNombre_vacia();
                    } else {
                        consola.mostrarObjetosFormateados(resultados);
                    }
                    break;

                case 7:  // Preguntar a la IA (interacción con servicio IA)
                    consola.preguntarAI(iaService);
                    break;

                case 8:  // Generar descripción de producto con IA
                    consola.generarDescripcionProducto(programa, iaService);
                    break;

                case 9:  // Sugerir categoría para un producto usando IA
                    consola.sugerirCategoriaProducto(iaService);
                    break;

                case 10: // Salir del programa
                    consola.salidaPrograma();
                    break;

                default: // Opción inválida
                    consola.opcionIcorrecta();
            }
        }

        sc.close();
    }
}
