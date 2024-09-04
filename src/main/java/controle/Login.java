package controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import conexao.Conexao;
import java.sql.SQLException;
import javax.swing.JOptionPane; 

/**
 *
 * @author Admin
 */
public class Login extends JFrame {
    Conexao con_cliente;
    
    JLabel rTitulo, rUsuario, rSenha;
    JTextField cUsuario;
    JPasswordField cSenha;
    JButton bLogar, bLimpar;
    
    public Login() {
        super("Login");
        
        con_cliente = new Conexao(); // inicialização do objeto
        con_cliente.conectar(); // chama o método que conecta

        Container tela = getContentPane();
        setLayout(null);
        
        //
        rTitulo  = new JLabel("Acesso ao Sistema");
        rUsuario = new JLabel("Usuário: ");
        rSenha   = new JLabel("Senha: ");
        
        cUsuario = new JTextField(3);
        cSenha   = new JPasswordField(50);
        
        bLogar  = new JButton("Logar");
        bLimpar = new JButton("Limpar");
        
        // Set Bounds
        rTitulo .setBounds(  0,  30, 460, 20);
        rUsuario.setBounds(100, 110, 100, 20);
        rSenha  .setBounds(100, 160, 100, 20);
        
        cUsuario.setBounds(200, 108, 160, 24);
        cSenha  .setBounds(200, 158, 160, 24);
        
        bLogar .setBounds(100, 260, 100, 25);
        bLimpar.setBounds(260, 260, 100, 25);
        
        // Definições do Texto
        rTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        rTitulo .setFont(new Font("",  Font.BOLD, 24));
        rUsuario.setFont(new Font("", Font.PLAIN, 16));
        rSenha  .setFont(new Font("", Font.PLAIN, 16));
        
        cUsuario.setFont(new Font("", Font.PLAIN, 16));
        cSenha  .setFont(new Font("", Font.PLAIN, 16));
        
        // Funções dos botões
        bLogar .setMnemonic(KeyEvent.VK_L);
        bLimpar .setMnemonic(KeyEvent.VK_I);
        
        bLogar.addActionListener((ActionEvent e) -> {
            try{
                String pesquisa = "select * from tblusuario where usuario like '"+ cUsuario.getText() + "' and senha = " + cSenha.getText() + ""; 
                con_cliente.executarSQL(pesquisa);
                if (con_cliente.resultset.first()) { 
                    // acesso ao form de cadastro
                    dispose();
                    var crudClientes = new CRUDClientes(con_cliente);
                    crudClientes.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "\n Usuário não cadastrado", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
                    con_cliente.desconectar();
                    System.exit(0);
                }
            } catch (SQLException errosql) {
                JOptionPane.showMessageDialog(null, "\n Os dados digitados não foram localizados\n "+errosql, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        bLimpar.addActionListener((ActionEvent e) -> {
            cUsuario.setText("");
            cSenha.setText("");
        });
        
        // Add
        tela.add(rTitulo);  // Labels
        tela.add(rUsuario);
        tela.add(rSenha);
        tela.add(cUsuario);  // Text fields
        tela.add(cSenha);
        tela.add(bLogar);  // Botões
        tela.add(bLimpar);
        
        //
        setSize(480, 360);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException | 
                ClassNotFoundException | 
                InstantiationException | 
                IllegalAccessException e) {}
        
        var login = new Login();
        login.setVisible(true);
    }
}
