package AkihabaraDB_test;


import org.junit.jupiter.api.*;

import AkihabaraDB.dao.ProductoDAO;
import AkihabaraDB.dao.ProductoDAO_Impl;
import AkihabaraDB.model.ProductoOtaku;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class testProductoDAO {

    private static ProductoDAO dao;
    private static ProductoOtaku productoTest;

    @BeforeAll
    public static void setup() {
        dao = new ProductoDAO_Impl();
        productoTest = new ProductoOtaku(0, "Figura Luffy", "Figuras", 29.99, 10);
    }

    @Test
    @Order(1)
    public void testAgregarProducto() {
        boolean resultado = dao.agregarProducto(productoTest);
        assertTrue(resultado, "El producto debería agregarse correctamente");
        assertTrue(productoTest.getId() > 0, "El ID del producto debe ser asignado");
    }

    @Test
    @Order(2)
    public void testObtenerProductoPorId() {
        ProductoOtaku producto = dao.obtenerProductoPorId(productoTest.getId());
        assertNotNull(producto, "El producto no debe ser null");
        assertEquals("Figura Luffy", producto.getNombre());
    }

    @Test
    @Order(3)
    public void testActualizarProducto() {
        productoTest.setPrecio(34.99);
        boolean actualizado = dao.actualizarProducto(productoTest);
        assertTrue(actualizado, "El producto debería actualizarse");

        ProductoOtaku actualizadoProducto = dao.obtenerProductoPorId(productoTest.getId());
        assertEquals(34.99, actualizadoProducto.getPrecio());
    }

    @Test
    @Order(4)
    public void testBuscarProductosPorNombre() {
        List<ProductoOtaku> resultados = dao.buscarProductosPorNombre("Luffy");
        assertFalse(resultados.isEmpty(), "Debe encontrar al menos un producto");
        assertTrue(resultados.stream().anyMatch(p -> p.getNombre().contains("Luffy")));
    }

    @Test
    @Order(5)
    public void testObtenerTodosLosProductos() {
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
        assertNotNull(productos, "La lista no debe ser null");
        assertTrue(productos.size() > 0, "Debe haber al menos un producto en la base de datos");
    }

    @Test
    @Order(6)
    public void testEliminarProducto() {
        boolean eliminado = dao.eliminarProducto(productoTest.getId());
        assertTrue(eliminado, "El producto debe eliminarse correctamente");

        ProductoOtaku eliminadoProducto = dao.obtenerProductoPorId(productoTest.getId());
        assertNull(eliminadoProducto, "El producto ya no debe existir");
    }
}
