package controller;

import java.util.List;
import entity.Time;

public interface TimeDAO {
	void inserir( Time t ) throws TimeException;
    void atualizar( Time  t ) throws TimeException;
    void remover( Time t ) throws TimeException;
    List<Time> pesquisarPorNome( String nome ) throws TimeException;
}
