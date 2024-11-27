package controller;

import java.time.LocalDate;

import entity.Time;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TimeController {
	
    private ObservableList<Time> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cidade = new SimpleStringProperty("");
    private StringProperty nomeMascote = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataCriacao = new SimpleObjectProperty<>(LocalDate.now());

    private TimeDAO timeDAO = null;
    
	public TimeController() throws TimeException {
		timeDAO = new TimeDAOImpl();
	}
	
	public Time paraEntidade() { 
		Time t = new Time();
		t.setId(id.get());
		t.setNome(nome.get());
		t.setCidade(cidade.get());
		t.setNomeMascote(nomeMascote.get());
		t.setDataCriacao(dataCriacao.get());
		return t;
	}
	
	public void excluir( Time t ) throws TimeException { 
        timeDAO.remover( t );
        pesquisarTodos();
    }

    public void limparTudo() { 
        id.set( 0 );
        nome.set( "" );
       	cidade.set( "");
        nomeMascote.set("");
        dataCriacao.set(LocalDate.now());
    }

    public void paraTela(Time t) { 
        if (t != null) {
            id.set( t.getId() );
            nome.set( t.getNome() );
            cidade.set( t.getCidade() );
            nomeMascote.set( t.getNomeMascote() );
            dataCriacao.set( t.getDataCriacao() );
        }
    }
    
    public void gravar() throws TimeException { 
        Time t = paraEntidade();
        if (t.getId() == 0 ) { 
            this.contador += 1;
            t.setId( this.contador );
            timeDAO.inserir( t );
        } else { 
            timeDAO.atualizar( t );
        }
        pesquisarTodos();
    }
    
    public void pesquisar() throws TimeException { 
        lista.clear();
        lista.addAll( timeDAO.pesquisarPorNome( nome.get() ) );
    }

    public void pesquisarTodos() throws TimeException { 
        lista.clear();
        lista.addAll( timeDAO.pesquisarPorNome( "" ) );
    }
    
    public LongProperty idProperty() { 
        return this.id;
    }
    public StringProperty nomeProperty() { 
        return this.nome;
    }
    public StringProperty cidadeProperty() { 
        return this.cidade;
    }
    public StringProperty nomeMascoteProperty() { 
        return this.nomeMascote;
    }
    public ObjectProperty<LocalDate> dataCriacaoProperty() { 
        return this.dataCriacao;
    }

    public ObservableList<Time> getLista() { 
        return this.lista;
    }
}
