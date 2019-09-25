package aula28.pkg08java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDataBase {

    // string URL padrão
    // endereço: localhost
    // base de dados: mtp
    private String url = "jdbc:postgresql://localhost/pessoa";

    // usuário do postgres
    private String usuario = "postgres";

    // senha do postgres
    private String senha = "ifg";

    // variável que guarda a conexão
    private Connection conn;

    /**
     * Método construtor.
     *
     * Toda vez que instanciar essa classe, a conexão é automaticamente feita
     */
    public ConexaoDataBase() {
        conectar();
    }

    /**
     * Método para conexão com o banco de dados.
     *
     * Carrega o driver e cria a conexão com o BD.
     */
    public void conectar() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        Properties props = new Properties();
        props.setProperty("user", this.usuario);
        props.setProperty("password", this.senha);

        try {
            this.conn = DriverManager.getConnection(this.url, props);
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    /**
     * Método que retorna a conexão feita com o BD
     *
     * @return um objeto Connection que é a conexão feita com o BD
     */
    public Usuario Login(String Email, String Senha) {
        try {
            PreparedStatement ps = this.conn.prepareStatement("SELECT id, nome, cidade_estado, email, senha FROM pessoa where email = ? AND senha = ?");
            ps.setString(1, Email);

            ps.setString(2, Senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Usuario us = new Usuario();
                us.setId(rs.getInt(1));
                us.setNome(rs.getString(2));
                us.setCidade(rs.getString(3));
                us.setEmail(rs.getString(4));
                us.setSenha(rs.getString(5));
                
                
                return us;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void conectarCadastro(String email, String nome, String cidade, String senha) throws SQLException {
        PreparedStatement consulta = this.conn.prepareStatement("INSERT INTO pessoa(nome, email, cidade_estado, senha) VALUES ( ?, ?, ?, ?)");
        consulta.setString(1, nome);
        consulta.setString(2, email);

        consulta.setString(3, cidade);
        consulta.setString(4, senha);
        consulta.executeUpdate();
        consulta.close();

    }

    public Connection getConnection() {
        return this.conn;
    }
}
