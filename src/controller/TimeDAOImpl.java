package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Time;

public class TimeDAOImpl implements TimeDAO{
	
	private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3307/nbadb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

	public TimeDAOImpl() throws TimeException {
		try {
			Class.forName(DB_CLASS);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new TimeException(e);
		}
		
	}

	@Override
	public void inserir(Time t) throws TimeException {
		try { 
            String SQL = """
                    INSERT INTO timesnba (id, nome, cidade, nomeMascote, dataCriacao)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, t.getId());
            stm.setString(2, t.getNome());
            stm.setString(3, t.getCidade());
            stm.setString(4, t.getNomeMascote());
            java.sql.Date dt = java.sql.Date.valueOf(t.getDataCriacao());
            stm.setDate(5, dt);
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new TimeException( e );
        }
		
	}

	@Override
	public void atualizar(Time t) throws TimeException {
		try { 
            String SQL = """
                    UPDATE timesnba SET nome = ?, cidade = ?, nomeMascote = ?, dataCriacao = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, t.getNome());
            stm.setString(2, t.getCidade());
            stm.setString(3, t.getNomeMascote());
            java.sql.Date dt = java.sql.Date.valueOf(t.getDataCriacao());
            stm.setDate(4, dt);
            stm.setLong(5, t.getId());
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new TimeException( e );
        }  
		
	}

	@Override
	public void remover(Time t) throws TimeException {
		try { 
            String SQL = """
                    DELETE FROM timesnba WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, t.getId() );
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new TimeException( e );
        }
	}

	@Override
	public List<Time> pesquisarPorNome(String nome) throws TimeException {
		List<Time> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM timesnba WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Time t = new Time();
                t.setId( rs.getLong("id") );
                t.setNome( rs.getString("nome") );
                t.setCidade(rs.getString("cidade") );
                t.setNomeMascote( rs.getString("nomeMascote") );
                t.setDataCriacao( rs.getDate("dataCriacao").toLocalDate());

                lista.add( t );
            }
        } catch (SQLException e) { 
            throw new TimeException( e );
        }
        return lista;
	}

}
