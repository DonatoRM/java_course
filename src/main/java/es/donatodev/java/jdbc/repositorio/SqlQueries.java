package es.donatodev.java.jdbc.repositorio;

public class SqlQueries {
    public static final String SQL_LISTAR = "SELECT p.*,c.nombre AS categoria FROM productos p " +
            "INNER JOIN categorias c ON p.categoria_id = c.id";
    public static final String SQL_POR_ID = "SELECT p.*, c.nombre AS categoria FROM productos p" +
            " INNER JOIN categorias c ON (p.categoria_id = c.id) WHERE p.id = ?";
    public static final String SQL_GUARDAR = "INSERT INTO productos(nombre, precio, categoria_id, fecha_registro) VALUES(?, ?, ?, ?)";
    public static final String SQL_ACTUALIZAR = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id=?";
    public static final String SQL_ELIMINAR = "DELETE FROM productos WHERE id=?";
    // Puedes agregar más consultas aquí si lo necesitas
}
