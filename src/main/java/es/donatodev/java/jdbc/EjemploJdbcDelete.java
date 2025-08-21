package es.donatodev.java.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import es.donatodev.java.jdbc.modelo.Producto;
import es.donatodev.java.jdbc.repositorio.ProductoRepositorioImpl;
import es.donatodev.java.jdbc.repositorio.Repositorio;
import es.donatodev.java.jdbc.util.ConexionBaseDatos;

public class EjemploJdbcDelete {
    public static void main(String[] args) {
        try(Connection conn=ConexionBaseDatos.getInstance()) {

        Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
        System.out.println("============ listar ============ ");
        repositorio.listar().forEach(System.out::println);

        System.out.println("============ obtener por id ============ ");
        
        System.out.println(repositorio.porId(1L));
        System.out.println("============ eliminar nuevo producto ============ ");
        repositorio.eliminar(3L);
        System.out.println("Producto eliminado con éxito!!!");
        repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
