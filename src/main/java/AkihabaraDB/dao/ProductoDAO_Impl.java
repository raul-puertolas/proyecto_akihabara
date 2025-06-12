package AkihabaraDB.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AkihabaraDB.model.ProductoOtaku;

public class ProductoDAO_Impl implements ProductoDAO {

    // Inserta un producto en la base de datos
    @Override
    public boolean agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        boolean resultado = false;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            a.setString(1, producto.getNombre());
            a.setString(2, producto.getCategoria());
            a.setDouble(3, producto.getPrecio());
            a.setInt(4, producto.getStock());

            int filasAfectadas = a.executeUpdate();

            if (filasAfectadas > 0) {
                // Obtener el ID generado automáticamente y asignarlo al objeto producto
                try (ResultSet keys = a.getGeneratedKeys()) {
                    if (keys.next()) {
                        producto.setId(keys.getInt(1));
                    }
                }
                resultado = true;
            }

        } catch (SQLException e) {
            resultado = false;  // En caso de error devuelve false
        }
        return resultado;
    }

    // Recupera un producto de la base de datos por su ID
    @Override
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql)) {

            a.setInt(1, id);
            ResultSet b = a.executeQuery();

            if (b.next()) {
                // Crear y devolver el producto con los datos obtenidos
                return new ProductoOtaku(
                        b.getInt("id"),
                        b.getString("nombre"),
                        b.getString("categoria"),
                        b.getDouble("precio"),
                        b.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no encuentra nada o hay error devuelve null
    }

    // Obtiene todos los productos de la base de datos
    @Override
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql);
             ResultSet b = a.executeQuery()) {

            while (b.next()) {
                // Añade cada producto recuperado a la lista
                productos.add(new ProductoOtaku(
                        b.getInt("id"),
                        b.getString("nombre"),
                        b.getString("categoria"),
                        b.getDouble("precio"),
                        b.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    // Actualiza un producto existente
    @Override
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql)) {

            a.setString(1, producto.getNombre());
            a.setString(2, producto.getCategoria());
            a.setDouble(3, producto.getPrecio());
            a.setInt(4, producto.getStock());
            a.setInt(5, producto.getId());

            return a.executeUpdate() > 0; // Retorna true si actualizó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Elimina un producto por su ID
    @Override
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql)) {

            a.setInt(1, id);
            return a.executeUpdate() > 0; // Retorna true si eliminó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Busca productos cuyo nombre contiene el texto dado (búsqueda parcial)
    @Override
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement a = conn.prepareStatement(sql)) {

            a.setString(1, "%" + nombre + "%"); // Uso de comodines para búsqueda parcial
            ResultSet b = a.executeQuery();

            while (b.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                        b.getInt("id"),
                        b.getString("nombre"),
                        b.getString("categoria"),
                        b.getDouble("precio"),
                        b.getInt("stock")
                );
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        }

        return productos;
    }
}
