package AkihabaraDB.model;

public class ProductoOtaku {
    // Atributos privados que representan las propiedades de un producto
	private int id;          // Identificador único del producto
	private String nombre;   // Nombre del producto
	private String categoria;// Categoría o tipo del producto
	private double precio;   // Precio del producto
	private int stock;       // Cantidad disponible en inventario

    // Constructor completo con todos los atributos, incluido el id
	public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}
	
    // Constructor sin id, útil para crear productos nuevos antes de asignarles un id
	public ProductoOtaku(String nombre, String categoria, double precio, int stock) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}

    // Métodos getters y setters para acceder y modificar los atributos privados

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

    // Método sobrescrito toString para mostrar la información del producto en formato texto
	@Override
	public String toString() {
		return "ProductoOtaku id = " + id + " , nombre = " + nombre + " , categoria = " + categoria + " , precio = " + precio
				+ " , stock = " + stock;
	}
}
