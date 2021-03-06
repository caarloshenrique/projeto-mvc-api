package br.com.dao;

import br.com.connection.Conexao;
import br.com.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDaoImpl implements UsuarioDao {

    Connection conexao = null;
    PreparedStatement pstm = null;

    @Override
    public void salvarUsuario(Usuario usuario) {
        conexao = new Conexao().getConnection();
        String sqlinsert = "INSERT INTO tb_usuario(email, senha, profissional_id) VALUES(?,?,?)";
        try {
            pstm = conexao.prepareStatement(sqlinsert);
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, usuario.getSenha());
            pstm.setInt(3, usuario.getProfissionalId());
            pstm.execute();
            pstm.close();
        } catch (SQLException insert) {
            System.out.println("Erro: " + insert);
        } finally {
            try {
                conexao.close();
            } catch (SQLException insertclose) {
                System.out.println("Erro: " + insertclose);
            }
        }
    }

    @Override
    public void alterarUsuario(Usuario usuario) {
        conexao = new Conexao().getConnection();
        String query = "UPDATE tb_usuario SET email=?, senha=?, profissional_id=? WHERE id=?";
        try {
            pstm = conexao.prepareStatement(query);
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, usuario.getSenha());
            pstm.setInt(3, usuario.getProfissionalId());
            pstm.setInt(4, usuario.getId());
            pstm.execute();
            pstm.close();
        } catch (SQLException update) {
            System.out.println("Erro: " + update);
        } finally {
            try {
                conexao.close();
            } catch (SQLException updateclose) {
                System.out.println("Erro: " + updateclose);
            }
        }
    }

    @Override
    public void excluirUsuario(int id) {
        conexao = new Conexao().getConnection();
        String query = "DELETE FROM tb_usuario WHERE id=?";
        try {
            pstm = conexao.prepareStatement(query);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException delete) {
            System.out.println("Erro: " + delete);
        } finally {
            try {
                conexao.close();
            } catch (SQLException deleteclose) {
                System.out.println("Erro: " + deleteclose);
            }
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        ResultSet rs = null;
        List<Usuario> lista = new ArrayList<Usuario>();
        conexao = new Conexao().getConnection();
        String query = "SELECT* FROM tb_usuario";
        try {
            pstm = conexao.prepareStatement(query);
            rs = pstm.executeQuery();
            rs.first();
            do {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setProfissionalId(rs.getInt("profissional_id"));

                lista.add(usuario);

            } while (rs.next());
        } catch (SQLException list) {
            System.out.println("Erro: " + list);
        } finally {
            try {
                conexao.close();
            } catch (SQLException listclose) {
                System.out.println("Erro: " + listclose);
            }
        }
        return lista;
    }

    @Override
    public boolean login(String email, String senha) {
        String sql = "SELECT * FROM tb_usuario WHERE email=? AND senha=?";
        ResultSet rs;
        try {
            conexao = new Conexao().getConnection();
            pstm = conexao.prepareStatement(sql);
            this.pstm.setString(1, email);
            this.pstm.setString(2, senha);

            rs = pstm.executeQuery();

            if (rs.first()) {
                this.pstm.close();
                conexao.close();
                return true;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Usuário inválido!");
        }
        return false;
    }

}
