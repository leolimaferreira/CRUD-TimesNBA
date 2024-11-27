package boundary;

import java.time.LocalDate;

import controller.TimeController;
import controller.TimeException;
import entity.Time;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TimeBoundary extends Application{
	
	private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtCidade = new TextField();
    private TextField txtNomeMascote = new TextField();
    private DatePicker dataCriacao = new DatePicker();

    private TimeController control = null;

    private TableView<Time> tableView = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		try { 
            control = new TimeController();
            ligacoes();
            gerarColunas();
        } catch (TimeException e ) { 
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
		BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction( e -> { 
            try { 
                control.gravar();
            } catch (TimeException err) { 
                new Alert(AlertType.ERROR, "Erro ao gravar o time", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (TimeException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );
        
        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Cidade: "), 0, 2);
        paneForm.add(txtCidade, 1, 2);
        paneForm.add(new Label("Nome Mascote: "), 0, 3);
        paneForm.add(txtNomeMascote, 1, 3);
        paneForm.add(new Label("Criacao: "), 0, 4);
        paneForm.add(dataCriacao, 1, 4);

        paneForm.add(btnGravar, 0, 5);
        paneForm.add(btnPesquisar, 1, 5);

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

        Scene scn = new Scene(panePrincipal, 600, 400);
        stage.setScene(scn);
        stage.setTitle("Times da NBA");
        stage.show();
	}
	
	public void gerarColunas() { 
        TableColumn<Time, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Time, Long>("id") );

        TableColumn<Time, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Time, String>("nome"));

        TableColumn<Time, String> col3 = new TableColumn<>("Cidade");
        col3.setCellValueFactory( new PropertyValueFactory<Time, String>("cidade"));

        TableColumn<Time, String> col4 = new TableColumn<>("Nome Mascote");
        col4.setCellValueFactory( new PropertyValueFactory<Time, String>("nomeMascote"));

        TableColumn<Time, LocalDate> col5 = new TableColumn<>("Criacao");
        col5.setCellValueFactory( new PropertyValueFactory<Time, LocalDate>("dataCriacao"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Nome: " + novo.getNome());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Time, Void>, TableCell<Time, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Time, Void> call(
                    TableColumn<Time, Void> param) {
                    TableCell<Time, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Time time = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( time ); 
                                } catch (TimeException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir o time", ButtonType.OK).showAndWait();
                                }
                            });
                        }

                        @Override
                        public void updateItem( Void item, boolean empty) {                             
                            if (!empty) { 
                                setGraphic(btnApagar);
                            } else { 
                                setGraphic(null);
                            }
                        }
                        
                    };
                    return celula;            
                } 
            };

        TableColumn<Time, Void> col6 = new TableColumn<>("Ação");
        col6.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        tableView.setItems( control.getLista() );
    }
	
	public void ligacoes() { 
    	if (control == null) {
            throw new IllegalStateException("Controle não foi inicializado.");
        }
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.cidadeProperty(), txtCidade.textProperty());
        Bindings.bindBidirectional(control.nomeMascoteProperty(), txtNomeMascote.textProperty());
        Bindings.bindBidirectional(control.dataCriacaoProperty(), dataCriacao.valueProperty());
    }

    public static void main(String[] args) {
        Application.launch(TimeBoundary.class, args);
    }
}
