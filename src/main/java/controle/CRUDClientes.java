package controle;

import conexao.Conexao;

import java.awt.*;
import java.awt.event.*;

import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

/**
 *
 * @author Fellipe Leonardo
 */
public class CRUDClientes extends JFrame {
    Conexao con_cliente;
    
    JLabel rCodigo, rNome, rEmail, rTel, rData, rPesquisarTitulo, rPesquisar;
    JTextField tCodigo, tNome, tEmail, tPesquisar;
    JFormattedTextField tTel, tData;
    MaskFormatter mTel, mData;
    JButton bPrimeiro, bAnterior, bProximo, bUltimo,
            bNovo, bGravar, bAlterar, bExcluir, bPesquisar, bSair;
    
    JTable tblClientes;
    JScrollPane scpTabela;
    
    String opcoesPesquisaLista[] = {
        "Começa com",
        "Termina com",
        "Contém"
    };
    JComboBox cbPesquisa;
    
    public CRUDClientes(Conexao conn) {
        super("Tabela - Clientes");
        
        // Passando a referencia da conexão já feita para um novo objeto
        con_cliente = conn;

        Container tela = getContentPane();
        setLayout(null);
        
        // ============
        
        rCodigo          = new JLabel("Código: ");
        rNome            = new JLabel("Nome: ");
        rEmail           = new JLabel("Email: ");
        rTel             = new JLabel("Telefone: ");
        rData            = new JLabel("Data: ");
        rPesquisarTitulo = new JLabel("Pesquisar");
        rPesquisar       = new JLabel("Nome do cliente");
        
        tCodigo    = new JTextField(3);
        tNome      = new JTextField(50);
        tEmail     = new JTextField(50);
        tPesquisar = new JTextField(50);
        
        bPrimeiro  = new JButton("Primeiro");
        bAnterior  = new JButton("Anterior");
        bProximo   = new JButton("Próximo");
        bUltimo    = new JButton("Ultimo");
        bNovo      = new JButton("Novo Registro");
        bGravar    = new JButton("Gravar");
        bAlterar   = new JButton("Alterar");
        bExcluir   = new JButton("Excluir");
        bPesquisar = new JButton("Pesquisar");
        bSair      = new JButton("Sair");
        
        cbPesquisa = new JComboBox(opcoesPesquisaLista);
        
        try {
            mTel = new MaskFormatter("(##)####-####");
            mData = new MaskFormatter("##/##/####");
        }
        catch (ParseException e) {}
        tTel = new JFormattedTextField(mTel);
        tData = new JFormattedTextField(mData);
        
        // Definições de Font
        rPesquisarTitulo.setFont(new Font("", Font.BOLD, 14));
        
        // Set Bounds
        rCodigo         .setBounds(30,  20, 100, 20);
        rNome           .setBounds(30,  50, 100, 20);
        rEmail          .setBounds(30,  80, 100, 20);
        rTel            .setBounds(30, 110, 100, 20);
        rData           .setBounds(30, 140, 100, 20);
        rPesquisarTitulo.setBounds(30, 430, 100, 20);
        rPesquisar      .setBounds(30, 450, 100, 20);
        
        tCodigo   .setBounds(200,  20,  50, 20);
        tNome     .setBounds(200,  50, 200, 20);
        tEmail    .setBounds(200,  80, 200, 20);
        tTel      .setBounds(200, 110, 200, 20);
        tData     .setBounds(200, 140, 200, 20);
        tPesquisar.setBounds(220, 450, 300, 20);
        
        bPrimeiro .setBounds( 30, 180, 100, 25);
        bAnterior .setBounds(150, 180, 100, 25);
        bProximo  .setBounds(270, 180, 100, 25);
        bUltimo   .setBounds(390, 180, 100, 25);
        bNovo     .setBounds(550,  30, 100, 25);
        bGravar   .setBounds(550,  80, 100, 25);
        bAlterar  .setBounds(550, 130, 100, 25);
        bExcluir  .setBounds(550, 180, 100, 25);
        bPesquisar.setBounds(550, 448, 100, 24);
        bSair     .setBounds(550, 510, 100, 25);
        
        cbPesquisa.setBounds(130, 450, 90, 20);
        
        // Funções dos botões
        bPrimeiro .setMnemonic(KeyEvent.VK_P);
        bAnterior .setMnemonic(KeyEvent.VK_A);
        bProximo  .setMnemonic(KeyEvent.VK_O);
        bUltimo   .setMnemonic(KeyEvent.VK_U);
        bNovo     .setMnemonic(KeyEvent.VK_N);
        bGravar   .setMnemonic(KeyEvent.VK_G);
        bAlterar  .setMnemonic(KeyEvent.VK_L);
        bExcluir  .setMnemonic(KeyEvent.VK_E);
        bPesquisar.setMnemonic(KeyEvent.VK_S);
        bSair     .setMnemonic(KeyEvent.VK_R);
        
        bPrimeiro.addActionListener((ActionEvent e) -> {
            try {
                con_cliente.resultset.first();
                mostrar_dados();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível acessar o primeiro registro",
                        "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        bAnterior.addActionListener((ActionEvent e) -> {
            try {
                if (!con_cliente.resultset.previous()) {
                    con_cliente.resultset.next();
                    return;
                }
                mostrar_dados();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível acessar o registro anterior",
                        "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        bProximo.addActionListener((ActionEvent e) -> {
            try {
                if (!con_cliente.resultset.next()) {
                    con_cliente.resultset.previous();
                    return;
                }
                mostrar_dados();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível acessar o próximo registro",
                        "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        bUltimo.addActionListener((ActionEvent e) -> {
            try {
                con_cliente.resultset.last();
                mostrar_dados();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível acessar o último registro",
                        "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        bNovo.addActionListener((ActionEvent e) -> {
            limpar();
        });
        bGravar.addActionListener((ActionEvent e) -> {
            String nome = tNome.getText();
            String data = tData.getText();
            String tel = tTel.getText();
            String email = tEmail.getText();
            
            try {
                String insert_sql="insert into tbclientes (nome,telefone, email, dt_nasc) values ('" + nome + "','" + tel + "','" + email + "','" + data + "')";
                con_cliente.statement.executeUpdate(insert_sql);
                JOptionPane.showMessageDialog(null, 
                        "Gravação realizada com sucesso", 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                
                con_cliente.executarSQL("select * from tbclientes order by cod");
                preencherTabela();
                posicionarRegistro();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, 
                        "Erro na gravação:\n" + erro, 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bAlterar.addActionListener((ActionEvent e) -> {
            String nome = tNome.getText();
            String data = tData.getText();
            String tel = tTel.getText();
            String email = tEmail.getText();
            String sql;
            String msg;
            
            try {
                if(tCodigo.getText().equals("")) {
                    sql = "insert into tbclientes (nome, telefone, email, dt_nasc) values ('"+ nome +", '"+ tel +", '"+ email +", '"+ data +"')";
                    msg = "Gravação";
                } else {
                    sql="update tbclientes set nome='" + nome + "',telefone='" + tel + "', email='" + email + "', dt_nasc='" + data + "' where cod = " + tCodigo.getText();
                    msg = "Alteração";
                }
                
                con_cliente.statement.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, 
                        msg + " realizada com sucesso", 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                
                con_cliente.executarSQL("select * from tbclientes order by cod");
                preencherTabela();
                posicionarRegistro();
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, 
                        "Erro na gravação:\n" + erro, 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bExcluir.addActionListener((ActionEvent e) -> {
            String sql;
            try {
                int resposta = JOptionPane.showConfirmDialog(rootPane, 
                        "Deseja excluir o registro?", 
                        "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.OK_OPTION) {
                    sql = "delete from tbclientes where cod = " + tCodigo.getText();
                    int excluir = con_cliente.statement.executeUpdate(sql);
                    if (excluir == 1) {
                        JOptionPane.showMessageDialog(null, 
                            "Exclusão realizada com sucesso",
                            "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                        con_cliente.executarSQL("select * from tbclientes order by cod");
                        con_cliente.resultset.first();
                        preencherTabela();
                        posicionarRegistro();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, 
                            "Erro na exclusão do registro",
                            "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, 
                            "Operação cancelada pelo usuário",
                            "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, 
                        "Erro na exclusão:\n" + erro, 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bPesquisar.addActionListener((ActionEvent e) -> {
            try {
                String pesquisa;
                
                switch (cbPesquisa.getSelectedIndex()) {
                    case 0 -> pesquisa = "select * from tbclientes where nome like '" + tPesquisar.getText() + "%'";
                    case 1 -> pesquisa = "select * from tbclientes where nome like '%" + tPesquisar.getText() + "'";
                    case 2 -> pesquisa = "select * from tbclientes where nome like '%" + tPesquisar.getText() + "%'";
                    default -> throw new SQLException();
                }
                con_cliente.executarSQL(pesquisa);
                
                if (con_cliente.resultset.first()) {
                    preencherTabela();
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Não existem dados com este paramêtro",
                            "Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE); 
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, 
                        "Os dados digitados não foram localizados:\n" + erro, 
                        "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bSair.addActionListener((ActionEvent e) -> {
            dispose();
            var login = new Login();
            login.setVisible(true);
        });
        
        // Add
        tela.add(rCodigo);  // Labels
        tela.add(rNome);
        tela.add(rEmail);
        tela.add(rTel);
        tela.add(rData);
        tela.add(rPesquisarTitulo);
        tela.add(rPesquisar);
        tela.add(tCodigo);  // Text fields
        tela.add(tNome);
        tela.add(tEmail);
        tela.add(tTel);
        tela.add(tData);
        tela.add(tPesquisar);
        tela.add(bPrimeiro);  // Botões
        tela.add(bAnterior);
        tela.add(bProximo);
        tela.add(bUltimo);
        tela.add(bNovo);
        tela.add(bGravar);
        tela.add(bAlterar);
        tela.add(bExcluir);
        tela.add(bPesquisar);
        tela.add(bSair);
        tela.add(cbPesquisa); // ComboBox
        
        // ============
        
        tblClientes = new JTable();
        scpTabela = new JScrollPane();
        
        tblClientes.setBounds(30, 220, 620, 200);
        scpTabela.setBounds(30, 220, 620, 200);
        
        tela.add(tblClientes);
        tela.add(scpTabela);
        
        tblClientes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tblClientes.setFont(new Font("Arial", 1, 12));
        
        tblClientes.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[] {
                    "Código", 
                    "Nome", 
                    "Data Nasc", 
                    "Telefone", 
                    "Email"
                }
        ) {
            boolean[] canEdit = new boolean[] {
                false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        scpTabela.setViewportView(tblClientes);
        tblClientes.setAutoCreateRowSorter(true);
        
        setSize(700, 600);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        con_cliente.executarSQL("select * from tbclientes order by cod");
        preencherTabela();
        posicionarRegistro();
    }
    
    private void preencherTabela() {
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(4);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(145);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(30);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(90);
        
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setNumRows(0);
        
        try {
            con_cliente.resultset.beforeFirst();
            while(con_cliente.resultset.next()) {
                modelo.addRow(new Object[] {
                    con_cliente.resultset.getString("cod"),
                    con_cliente.resultset.getString("nome"),
                    con_cliente.resultset.getString("dt_nasc"),
                    con_cliente.resultset.getString("telefone"), 
                    con_cliente.resultset.getString("email")
                });
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao listar dados da tabela!\nErro: " + erro
            );
        }
    }
    
    private void posicionarRegistro() {
        try {
            con_cliente.resultset.first();
            mostrar_dados();
        } catch(SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível posicionar no primeiro registro: "+erro,
                    "Mensagem do Programa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private void mostrar_dados() {
        try {
            tCodigo.setText(con_cliente.resultset.getString("cod"));
            tNome  .setText(con_cliente.resultset.getString("nome"));
            tData  .setText(con_cliente.resultset.getString("dt_nasc"));
            tTel   .setText(con_cliente.resultset.getString("telefone"));
            tEmail .setText(con_cliente.resultset.getString("email"));
        } catch(SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não localizou dados: "+erro,
                    "Mensagem do Programa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
    }
    
    private void limpar() {
        tCodigo.setText("");
        tNome  .setText("");
        tData  .setText("");
        tTel   .setText("");
        tEmail .setText("");
        tCodigo.requestFocus();
    }
}
