package AkihabaraDB.gui;

import AkihabaraDB.*;
import AkihabaraDB.dao.ProductoDAO;
import AkihabaraDB.dao.ProductoDAO_Impl;
import AkihabaraDB.model.ProductoOtaku;
import AkihabaraDB.service.LlmService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// Interfaz gráfica principal para gestionar productos en Akihabara Market
public class AkihabaraGUI extends JFrame {

    private ProductoDAO dao = new ProductoDAO_Impl(); // Acceso a datos de productos
    private LlmService iaService; // Servicio de IA para consultas inteligentes
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtCategoria, txtPrecio, txtStock;

    public AkihabaraGUI() {
        try {
            iaService = new LlmService(); // Inicializa el servicio de IA
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Error inicializando el servicio de IA: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            iaService = null;
        }
        setTitle("Akihabara Market");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tabla para mostrar productos
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Panel de entradas de datos
        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new GridLayout(2, 4, 10, 10));
        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        panelInputs.add(new JLabel("Nombre:"));
        panelInputs.add(txtNombre);
        panelInputs.add(new JLabel("Categoría:"));
        panelInputs.add(txtCategoria);
        panelInputs.add(new JLabel("Precio:"));
        panelInputs.add(txtPrecio);
        panelInputs.add(new JLabel("Stock:"));
        panelInputs.add(txtStock);

        // Panel de botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRefrescar = new JButton("Refrescar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnRefrescar);

        // Panel de botones para funciones de IA
        JPanel panelIA = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnIAPregunta = new JButton("Preguntar a la IA");
        JButton btnIADescripcion = new JButton("Descripción IA");
        JButton btnIASugerirCategoria = new JButton("Sugerir categoría IA");

        panelIA.add(btnIAPregunta);
        panelIA.add(btnIADescripcion);
        panelIA.add(btnIASugerirCategoria);

        // Panel inferior que agrupa entradas y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInferior.add(panelInputs);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(panelBotones);
        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(panelIA);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos iniciales en la tabla
        cargarTabla();

        // Eventos de botones CRUD
        btnAgregar.addActionListener(e -> agregarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnBuscar.addActionListener(e -> buscarProducto());
        btnRefrescar.addActionListener(e -> cargarTabla());

        // Evento para seleccionar fila de la tabla y mostrar datos en los campos
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtNombre.setText((String) tabla.getValueAt(fila, 1));
                    txtCategoria.setText((String) tabla.getValueAt(fila, 2));
                    txtPrecio.setText(tabla.getValueAt(fila, 3).toString());
                    txtStock.setText(tabla.getValueAt(fila, 4).toString());
                }
            }
        });

        // Evento: Preguntar a la IA
        btnIAPregunta.addActionListener(e -> {
            String pregunta = JOptionPane.showInputDialog(this, "Escribe tu pregunta para la IA:");
            if (pregunta != null && !pregunta.trim().isEmpty()) {
                try {
                    String respuesta = iaService.consultarIA(pregunta);
                    JOptionPane.showMessageDialog(this, respuesta, "Respuesta de la IA", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al consultar la IA: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento: Generar descripción de producto con IA
        btnIADescripcion.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingresa el ID del producto:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(input);
                    ProductoOtaku producto = dao.obtenerProductoPorId(id);
                    if (producto != null) {
                        String prompt = "Genera una descripción profesional y atractiva para este producto: " + producto.getNombre();
                        try {
                            String descripcion = iaService.consultarIA(prompt);
                            JOptionPane.showMessageDialog(this, descripcion, "Descripción IA", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Error al consultar la IA: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Producto no encontrado con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento: Sugerir categoría con IA
        btnIASugerirCategoria.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del producto para sugerir categoría:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                try {
                    String prompt = "Sugiere una categoría para el producto: " + nombre;
                    String sugerencia = iaService.consultarIA(prompt);
                    JOptionPane.showMessageDialog(this, sugerencia, "Categoría sugerida", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al consultar la IA: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Carga todos los productos en la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (ProductoOtaku p : dao.obtenerTodosLosProductos()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    // Agrega un nuevo producto usando los datos de los campos de texto
    private void agregarProducto() {
        try {
            ProductoOtaku p = new ProductoOtaku(
                txtNombre.getText(),
                txtCategoria.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtStock.getText())
            );
            if (dao.agregarProducto(p)) {
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar producto.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de ingresar números válidos en Precio y Stock.");
        }
    }

    // Actualiza el producto seleccionado en la tabla
    private void actualizarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = (int) tabla.getValueAt(fila, 0);
                ProductoOtaku p = new ProductoOtaku(id,
                    txtNombre.getText(),
                    txtCategoria.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText())
                );
                if (dao.actualizarProducto(p)) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado.");
                    cargarTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
        }
    }

    // Elimina el producto seleccionado en la tabla
    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tabla.getValueAt(fila, 0);
            if (dao.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.");
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }

    // Busca productos por nombre y muestra los resultados en la tabla
    private void buscarProducto() {
        String nombre = txtNombre.getText();
        List<ProductoOtaku> resultados = dao.buscarProductosPorNombre(nombre);
        modeloTabla.setRowCount(0);
        for (ProductoOtaku p : resultados) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    // Limpia los campos de entrada de texto
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }

    // Método principal para lanzar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AkihabaraGUI().setVisible(true));
    }
}
