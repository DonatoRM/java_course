package es.donatodev.java.jdbc.repositorio;

import static es.donatodev.java.jdbc.repositorio.SqlQueries.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.donatodev.java.jdbc.modelo.Categoria;
import es.donatodev.java.jdbc.modelo.Producto;

public class ProductoRepositorioImpl implements Repositorio<Producto> {
    private final Connection conn;
    private static final Logger LOGGER=Logger.getLogger(ProductoRepositorioImpl.class.getName());
    // Constantes SQL movidas a SqlQueries.java
    private static final String MSG_LIST_OK="Se ha listado correctamente los productos";

    public ProductoRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
    try (Statement stmt = this.conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL_LISTAR)) {

            while (rs.next()) {
                Producto producto = crearProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO,MSG_LIST_OK);
        return productos;
    }

    
    @Override
    public Producto porId(Long id) {
        Producto producto=null;
    try(PreparedStatement stmt=this.conn.prepareStatement(SQL_POR_ID)) {
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
            sql=SQL_ACTUALIZAR;
        } else {
            sql=SQL_GUARDAR;
        }
        try(PreparedStatement stmt=this.conn.prepareStatement(sql)) {
            stmt.setString(1,producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3,producto.getCategoria().getId());

            if(producto.getId()!=null && producto.getId()>0) {
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminar(Long id) {
    try(PreparedStatement stmt=conn.prepareStatement(SQL_ELIMINAR)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria=new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }

}
