package com.example.nasaproject.baseDatos;

import com.example.nasaproject.baseDatos.datosCompartidos.PasswordUtil;
import com.example.nasaproject.modelsMysql.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UsuarioDao extends MySQLConnection implements Dao<Usuario> {

    static Connection conn = getConnection();


    @Override
    public Optional<Usuario> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        ObservableList<Usuario> listUsuarios = FXCollections.observableArrayList();
        String query = "SELECT u.idUsuario, u.cveUsuario, u.email, NombreUsuario, Apellidos, FechaNac, Nacionalidad, r.nombre, contraseña\n" +
                "FROM usuarios u inner join registro r on u.email = r.email";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Usuario u = new Usuario();


                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setEmail(rs.getString("email"));
                u.setNombreUsuario(rs.getString("NombreUsuario"));
                u.setApellidos(rs.getString("Apellidos"));
                u.setFechaNac(rs.getString("FechaNac"));
                u.setNacionalidad(rs.getString("Nacionalidad"));
                u.setCveUsuario(rs.getString("cveUsuario"));
                u.setUsername(rs.getString("nombre"));
                u.setPassword(rs.getString("contraseña"));


                listUsuarios.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsuarios;
    }

    @Override
    public boolean save(Usuario usuario) {

        String query = "insert into usuarios" +
                " (Email, NombreUsuario, Apellidos, FechaNac, Nacionalidad, cveUsuario)" +
                " values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getFechaNac());
            ps.setString(5, usuario.getNacionalidad());
            ps.setString(6, usuario.getCveUsuario());

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Usuario usuario) {
        String query = "UPDATE usuarios SET " +
                "Email=?, NombreUsuario=?, Apellidos=?, FechaNac=?, Nacionalidad=?, cveUsuario=? " +
                "WHERE idUsuario=?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getFechaNac());
            ps.setString(5, usuario.getNacionalidad());
            ps.setString(6, usuario.getCveUsuario());
            ps.setInt(7, usuario.getIdUsuario());

            ps.executeUpdate();
            System.out.println(usuario.getEmail());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean delete(int cveUsuario)
    {

        String query = "delete from usuarios where idUsuario = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, cveUsuario);
            ps.execute();
            System.out.println("Se ha eliminado el usuario con id: "+cveUsuario);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarRegistroTablasUsuarios(Usuario usuario)
    {
        boolean resultado;
        String query = "delete from comentariosapod where email= ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario.getEmail());
            ps.execute();
            System.out.println("Se ha eliminado de la tabla Comentarios el usuario "+usuario.getNombreUsuario());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query2 = "delete from busquedasnasa where email= ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query2);
            ps.setString(1, usuario.getEmail());
            ps.execute();
            System.out.println("Se ha eliminado de la tabla BusquedasNasa el usuario "+usuario.getNombreUsuario());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query3= "delete from accesos where email= ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query3);
            ps.setString(1, usuario.getEmail());
            ps.execute();
            System.out.println("Se ha eliminado de la tabla accesos el usuario "+usuario.getNombreUsuario());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query4= "delete from historialbusqueda where email= ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query4);
            ps.setString(1, usuario.getEmail());
            ps.execute();
            System.out.println("Se ha eliminado de la tabla Historial el usuario "+usuario.getNombreUsuario());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query5= "delete from registro where email= ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query5);
            ps.setString(1, usuario.getEmail());
            ps.execute();
            System.out.println("Se ha eliminado de la tabla Registro el usuario "+usuario.getNombreUsuario());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registerUser(Usuario usuario,String fecha, String rol)
    {
        String query = "insert into registro" +
                " (email, contraseña, nombre, fechaRegistro, rol)" +
                " values (?, ?, ?, ?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(usuario.getPassword());
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, usuario.getEmail());
            ps.setString(2, hashedPassword);
            ps.setString(3, usuario.getUsername());
            ps.setString(4, fecha);
            ps.setString(5, rol);

            ps.execute();
            usuario.setCveUsuario("NRM");
            save(usuario);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRegistro(Usuario usuario) {
        String query = "update registro " +
                "set email=?,nombre=?,contraseña=? " +
                "where email=?";
        String passwordToStore = usuario.getPassword();
        if (passwordToStore != null && !passwordToStore.isBlank()
                && !passwordToStore.startsWith("$2a$")
                && !passwordToStore.startsWith("$2b$")
                && !passwordToStore.startsWith("$2y$")) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
            usuario.setPassword(passwordToStore);
        }
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, passwordToStore);
            ps.setString(4, usuario.getEmail());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT contraseña FROM registro WHERE nombre = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
            {
                String hashedPassword = resultSet.getString("contraseña");
                return PasswordUtil.checkPassword(password, hashedPassword);
            }
            else
                return false; // Autenticación fallida
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario getUsuarioByUsername(String username) {
        String query = "SELECT u.idUsuario, u.cveUsuario, u.email, NombreUsuario, Apellidos, FechaNac, Nacionalidad, r.nombre, contraseña\n" +
                "FROM usuarios u inner join registro r on u.email = r.email\n" +
                "WHERE r.nombre = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Usuario(
                        resultSet.getInt("idUsuario"),
                        resultSet.getString("cveUsuario"),
                        resultSet.getString("email"),
                        resultSet.getString("NombreUsuario"),
                        resultSet.getString("Apellidos"),
                        resultSet.getString("FechaNac"),
                        resultSet.getString("Nacionalidad"),
                        resultSet.getString("nombre"),
                        resultSet.getString("contraseña")
                );
            } else {
                return null; // Usuario no encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void guardarBusqueda(String email, String p_busqueda,String p_fecha)
    {
        String query ="INSERT INTO historialbusqueda (email, busqueda, fechaBusqueda)\n" +
                "VALUES (?, ?, ?);";
        try
        {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,p_busqueda);
            ps.setString(3,p_fecha);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void guardarAcceso(String email)
    {
        String query ="INSERT INTO accesos (email,fechaAcceso)\n" +
                "VALUES (?, ?);";

        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String fecha = LocalDate.now()+" "+horaActual.format(formatoHora);


        try
        {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2, fecha);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
