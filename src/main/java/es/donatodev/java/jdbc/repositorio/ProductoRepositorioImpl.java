package es.donatodev.java.jdbc.repositorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.util.ConexionBaseDatos;

public class ProductoRepositorioImpl implements Repositorio<Producto> {
    private final Connection conn;
    
    public ProductoRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {

            while (rs.next()) {
                Producto producto = crearProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    
    @Override
    public Producto porId(Long id) {
        Producto producto=null;
        try(PreparedStatement stmt=this.conn.prepareStatement("select * from productos where id=?")) {
            stmt.setLong(1,id);
            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()) {
                    producto=crearProducto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    @Override
    public void guardar(Producto producto) {
        String sql;
        if(producto.getId()!=null && producto.getId()>0) {
            sql="update productos set nombre=?, precio=? where id=?";
        } else {
            sql="insert into productos(nombre, precio,fecha_registro) values(?,?,?)";
        }
        try(PreparedStatement stmt=this.conn.prepareStatement(sql)) {
            stmt.setString(1,producto.getNombre());
            stmt.setLong(2, producto.getPrecio());

            if(producto.getId()!=null && producto.getId()>0) {
                stmt.setLong(3, producto.getId());
            } else {
                stmt.setDate(3, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminar(Long id) {
        try(PreparedStatement stmt=this.conn.prepareStatement("delete from productos where id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getInt("precio"));
        producto.setFechaRegistro(rs.getDate("fecha_registro"));
        return producto;
    }

}
