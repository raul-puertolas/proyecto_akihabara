package AkihabaraDB.dao;

import java.util.List;

import AkihabaraDB.model.ProductoOtaku;
// Interfaz con  los mentodos necesarios para ProductoDAO_iml
public interface ProductoDAO {
	boolean agregarProducto(ProductoOtaku producto);
	ProductoOtaku obtenerProductoPorId(int id);
	List<ProductoOtaku> obtenerTodosLosProductos();
	boolean actualizarProducto(ProductoOtaku producto);
	boolean eliminarProducto(int id);
	List<ProductoOtaku> buscarProductosPorNombre(String nombre);
}
