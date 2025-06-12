package AkihabaraDB.view;
import AkihabaraDB.service.LlmService;
import java.util.List;
import java.util.Scanner;
import AkihabaraDB.dao.ProductoDAO_Impl;
import AkihabaraDB.model.ProductoOtaku;

// Esta clase se encarga de todo lo que se muestra y lee por consola
public class InterfazConsola {
    // Creamos un scanner para leer lo que escribe el usuario
    private Scanner sc = new Scanner(System.in);

    // Esto solo muestra el menú principal con las opciones disponibles
    public void mostrarMenu() {
        System.out.println("[||| MENU PRINCIPAL |||]");
        System.out.println("1. Agregar producto");
        System.out.println("2. Obtener producto por ID");
        System.out.println("3. Mostrar todos los productos");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Buscar productos por nombre");
        System.out.println("7. Preguntar algo a la IA");
        System.out.println("8. Generar descripción con IA para un producto");
        System.out.println("9. Sugerir categoría con IA para un nuevo producto");
        System.out.println("10. Salir");
    }

    // Aquí pedimos que el usuario elija una opción válida del menú (del 1 al 10)
    public int pedirOpcion() {
        int opcion = 0;
        boolean valida = false;

        while (!valida) {
            System.out.print("Seleccione una opción (1-10): ");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine(); // Limpiar el salto de línea pendiente
                if (opcion >= 1 && opcion <= 10) {
                    valida = true;  
                } else {
                    System.out.println("Opción no válida. Debe ser un número entre 1 y 10.");
                }
            } else {
                System.out.println("Entrada no válida. Debe ser un número.");
                sc.next(); 
            }
        }

        return opcion;
    }

    // Esto pide un ID y se asegura de que sea un número entero
    public int pedirId() {
        while (true) {
            System.out.print("Introduce el ID del producto: ");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Debe ser un número entero.");
            }
        }
    }

    // Pide un nombre (cualquier texto que escriba el usuario)
    public String pedirNombre() {
        System.out.print("Introduce el nombre del producto: ");
        return sc.nextLine();
    }

    // Pide la categoría del producto, texto simple también
    public String pedirCategoria() {
        System.out.print("Introduce la categoría del producto: ");
        return sc.nextLine();
    }

    // Pide el precio y valida que sea un número y no negativo
    public double pedirPrecio() {
        while (true) {
            try {
                System.out.print("Introduce el precio del producto: ");
                double precio = Double.parseDouble(sc.nextLine());
                if (precio < 0) {
                    System.out.println("El precio no puede ser negativo.");
                } else {
                    return precio;
                }
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido. Debe ser un número.");
            }
        }
    }

    // Pide el stock y se asegura que sea un entero
    public int pedirStock() {
        while (true) {
            System.out.print("Introduce el stock del producto: ");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido. Debe ser un número entero.");
            }
        }
    }

    // Pedir id para obtener producto por id, simplemente usa pedirId()
    public int pedirId_obtenerProductoPorId() {
        return pedirId();
    }

    // Mensaje cuando no se encuentra el producto
    public void obtenerProductoPorId_null() {
        System.out.println("Producto no encontrado.");
    }

    // Mensaje cuando no hay productos en la lista
    public void obtenerTodosLosProductos_listaVacia() {
        System.out.println("No hay productos registrados.");
    }

    // Pedir id para actualizar producto
    public int pedirID_actualizarProducto() {
        return pedirId();
    }

    // Pedir nombre para actualizar
    public String pedirNombre_actualizarProducto() {
        return pedirNombre();
    }

    // Pedir categoría para actualizar
    public String pedirCategoria_actualizarProducto() {
        return pedirCategoria();
    }

    // Pedir precio para actualizar
    public double pedirPrecio_actualizarProducto() {
        return pedirPrecio();
    }

    // Pedir stock para actualizar
    public int pedirStock_actualizarProducto() {
        return pedirStock();
    }

    // Mensaje cuando la actualización salió bien
    public void actualizarProducto_true() {
        System.out.println("Producto actualizado correctamente.");
    }

    // Mensaje cuando la actualización falla
    public void actualizarProducto_false() {
        System.out.println("Error al actualizar el producto. Verifica que el ID exista.");
    }

    // Pedir id para eliminar producto
    public int pedirId_eliminarProducto() {
        return pedirId();
    }

    // Mensaje cuando eliminación fue correcta
    public void eliminarProducto_true() {
        System.out.println("Producto eliminado correctamente.");
    }

    // Mensaje cuando eliminación falla
    public void eliminarProducto_false() {
        System.out.println("Error al eliminar el producto. Verifica que el ID exista.");
    }

    // Pide un nombre para buscar productos por nombre
    public String pedirNombre_buscarProductosPorNombre() {
        System.out.print("Introduce el nombre a buscar: ");
        return sc.nextLine();
    }

    // Mensaje cuando no se encuentra ningún producto con ese nombre
    public void buscarProductosPorNombre_vacia() {
        System.out.println("No se encontraron productos con ese nombre.");
    }

    // Mensaje para despedirse cuando sales del programa
    public void salidaPrograma() {
        System.out.println("Saliendo del programa. ¡Hasta pronto!");
    }

    // Mensaje para cuando el usuario mete una opción que no está en el menú
    public void opcionIcorrecta() {
        System.out.println("Opción incorrecta. Por favor, elige una opción válida del menú.");
    }

    // Mensaje cuando agregas un producto sin problemas
    public void agregarProducto_true() {
        System.out.println("Producto agregado correctamente.");
    }

    // Mensaje cuando no puedes agregar porque ya existe o el ID está repetido
    public void agregarProducto_false() {
        System.out.println("Error: el producto ya existe o el ID está repetido.");
    }

    // Muestra la lista de productos formateada (imprime uno por uno)
    public void mostrarObjetosFormateados(List<ProductoOtaku> productos) {
        System.out.println("\n=== Lista de Productos ===");
        for (ProductoOtaku p : productos) {
            System.out.println(p);
        }
    }

    // Pide una pregunta para la IA
    public String pedirPreguntaIA() {
        System.out.print("Escribe tu pregunta para la IA: ");
        return sc.nextLine();
    }

    // Muestra la respuesta que devuelve la IA
    public void mostrarRespuestaIA(String respuesta) {
        System.out.println(" Respuesta de la IA:");
        System.out.println(respuesta);
    }

    // Pide un ID para generar descripción con IA (para un producto)
    public int pedirId_generarDescripcion() {
        System.out.print("Ingresa el ID del producto para generar una descripción: ");
        return sc.nextInt();
    }

    // Pide el nombre para sugerir categoría con IA
    public String pedirNombre_sugerirCategoria() {
        System.out.print("Ingresa el nombre del nuevo producto para sugerir categoría: ");
        return sc.nextLine();
    }

    // Muestra la descripción generada por la IA
    public void mostrarDescripcionGenerada(String descripcion) {
        System.out.println("Descripción generada por la IA:");
        System.out.println(descripcion);
    }

    // Muestra la categoría sugerida por la IA
    public void mostrarCategoriaSugerida(String categoria) {
        System.out.println("Categoría sugerida por la IA:");
        System.out.println(categoria);
    }

    // Función que usa la IA para responder a una pregunta que el usuario haga
    public void preguntarAI(LlmService iaService) {
        String pregunta = pedirPreguntaIA();
        try {
            String respuesta = iaService.consultarIA(pregunta);
            mostrarRespuestaIA(respuesta);
        } catch (Exception e) {
            System.out.println("Error al consultar la IA: " + e.getMessage());
        }
    }

    // Función que genera descripción para un producto usando la IA
    public void generarDescripcionProducto(ProductoDAO_Impl programa, LlmService iaService) {
        int id = pedirId();
        ProductoOtaku producto = programa.obtenerProductoPorId(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        } else {
            String prompt = "Genera una descripción llamativa y profesional para el siguiente producto: " + producto;
            try {
                String descripcion = iaService.consultarIA(prompt);
                mostrarDescripcionGenerada(descripcion);
            } catch (Exception e) {
                System.out.println("Error al consultar la IA: " + e.getMessage());
            }
        }
    }

    // Función que sugiere una categoría para un producto nuevo con ayuda de la IA
    public void sugerirCategoriaProducto(LlmService iaService) {
        sc.nextLine(); 
        String nombre = pedirNombre_sugerirCategoria();
        String prompt = "Sugiere una categoría para el producto: " + nombre;
        try {
            String categoria = iaService.consultarIA(prompt);
            mostrarCategoriaSugerida(categoria);
        } catch (Exception e) {
            System.out.println("Error al consultar la IA: " + e.getMessage());
        }
    }

}
