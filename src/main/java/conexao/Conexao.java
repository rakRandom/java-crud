package conexao;

import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Fellipe Leonardo
 */
public class Conexao {
    final private String driver = "com.mysql.cj.jdbc.Driver";
    final private String url = "jdbc:mysql://localhost/clientes";
    final private String usuario = "root";
    final private String senha = "";
    private Connection conexao;
    public Statement statement;
    public ResultSet resultset;
    
    public boolean conectar() {
        boolean result = true;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            JOptionPane.showMessageDialog(
                    null, 
                    "Conexão estabelecida", 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (ClassNotFoundException driver) {
            JOptionPane.showMessageDialog(
                    null,
                    "Driver não localizado: " + driver, 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
            result = false;
        }
        catch (SQLException fonte) {
            JOptionPane.showMessageDialog(
                    null,
                    "Fonte de dados não localizada: " + fonte, 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
            result = false;
        }
        return result;
    }
    
    public void desconectar() {
        try {
            conexao.close();
            JOptionPane.showMessageDialog(
                    null, 
                    "Conexão com o banco fechado", 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException fechar) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro ao fechar o banco", 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void executarSQL(String sql) {
        try {
            statement = conexao.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            resultset = statement.executeQuery(sql);
        }
        catch (SQLException excecao) {
            JOptionPane.showMessageDialog(
                    null, 
                    "Erro no comando SQL!\nErro: " + excecao, 
                    "Banco de Dados", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
