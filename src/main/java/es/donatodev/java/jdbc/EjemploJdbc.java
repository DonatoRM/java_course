package es.donatodev.java.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import es.donatodev.java.jdbc.modelo.Categoria;
import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.repositorio.ProductoRepositorioImpl;
import es.donatodev.java.jdbc.repositorio.Repositorio;
import es.donatodev.java.jdbc.util.ConexionBaseDatos;

public class EjemploJdbc {
    public static void main(String[] args) {
        try(Connection conn=ConexionBaseDatos.getInstance()) {

        Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
        System.out.println("============ listar ============ ");
        repositorio.listar().forEach(System.out::println);

        System.out.println("============ obtener por id ============ ");
        
        System.out.println(repositorio.porId(1L));
        System.out.println("============ insertar nuevo producto ============ ");
        Producto producto=new Producto();
        producto.setNombre("Teclado Razer mecánico");
        producto.setPrecio(550);
        producto.setFechaRegistro(new Date());
        Categoria categoria=new Categoria();
        categoria.setId(3L);
        producto.setCategoria(categoria);
        repositorio.guardar(producto);
        System.out.println("Producto guardado con éxito!!!");
        repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
