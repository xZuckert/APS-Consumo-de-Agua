package org.example.apsconsumodeagua.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.example.apsconsumodeagua.controllers.TabGraficosController;
import org.example.apsconsumodeagua.controllers.TabHomeController;
import org.example.apsconsumodeagua.controllers.TabUsuarioController;
import org.example.apsconsumodeagua.database.UsuarioGraficoDAO;
import org.example.apsconsumodeagua.factory.GraficoFactory;
import org.example.apsconsumodeagua.managers.TabManager;
import org.example.apsconsumodeagua.managers.GraficoManager;
import org.example.apsconsumodeagua.services.UsuarioService;
import org.example.apsconsumodeagua.utils.Toast;
import org.example.apsconsumodeagua.utils.constantes.CaminhoFxml;
import org.example.apsconsumodeagua.utils.constantes.AppConstantes;
import org.example.apsconsumodeagua.utils.enums.TipoGrafico;
import org.example.apsconsumodeagua.utils.enums.ToastEnum;

import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

//(Classe criada para comunicação global da aplicação)------------------------------------------------------------------
public class AppModel {

    //(Instancia da classe, ela é criada apenas a primeira vez que é chamada, depois disso sempre chama o mesmo objeto)-
    private static AppModel instance;

    //(Map que guarda o caminho e os dados das páginas)-----------------------------------------------------------------
    private final Map<String, Parent> rotas = new HashMap<>();

    //(Pane principal da aplicação)-------------------------------------------------------------------------------------
    private AnchorPane rootPane;

    private TipoGrafico tipoGrafico;
    private String graficoSelecionadoAtual;
    private final GraficoManager graficoManager;

    //(Instancias das classes de manipulação das views)------------------------------------------------------------------
    private TabManager tabManager;

    private TabUsuarioController tabUsuarioController;
    private TabHomeController tabHomeController;
    private TabGraficosController tabGraficosController;

    //(Construtor da classe, ela está privada para não ser possível criar novas instancias)-----------------------------
    private AppModel() {
        setTipoGrafico(TipoGrafico.AREA);
        //(Instancias das classes de manipulação dos graficos)----------------------------------------------------------
        GraficoFactory graficoFactory = new GraficoFactory();
        graficoManager = new GraficoManager(graficoFactory, tabGraficosController, rootPane);
    }

    //(Primeira função chamada pela aplicação)--------------------------------------------------------------------------
    public void carregarAplicacao(ToggleButton ... tabs) {
        inicializarTabs(tabs);
        inicializarGraficos();
        inicializarBoxAnos();
        inicializarListeners();
    }

    //(Funções de inicialização)----------------------------------------------------------------------------------------
    private void inicializarTabs(ToggleButton ... tabs) {
        tabManager = new TabManager(tabs[0], tabs[1], tabs[2]);
        tabUsuarioController = carregarFXMLComController(CaminhoFxml.TAB_USUARIO);
        tabHomeController = carregarFXMLComController(CaminhoFxml.TAB_HOME);
        tabGraficosController = carregarFXMLComController(CaminhoFxml.TAB_GRAFICOS);
        tabManager.inicializarComTabInicial(CaminhoFxml.TAB_HOME);
        getTabUsuarioController().atualizarQuantidadeMoradores();
    }
    private void inicializarGraficos(){
        String anoAtual = String.valueOf(Year.now().getValue());
        if (graficoManager.getGrafico(anoAtual) == null) {
            UsuarioGraficoDAO.getDadosUsuarioGraficoDB(UsuarioService.getInstance().getUsuarioLogado().getCpf());
            System.out.println(graficoManager.valores);
            graficoManager.valores.forEach((key, value) -> {
                graficoManager.gerarGrafico(key,tipoGrafico);
                tabGraficosController.adicionarGraficoNaTab(key,rootPane);
                tabHomeController.atualizarBoxGraficos();
                tabHomeController.selecionarGrafico(key);
                graficoSelecionadoAtual = key;
            });
            if(graficoManager.valores.isEmpty()) {
                graficoManager.gerarGrafico(anoAtual, tipoGrafico);
                tabGraficosController.adicionarGraficoNaTab(anoAtual,rootPane);
                tabHomeController.selecionarGrafico(anoAtual);
                graficoSelecionadoAtual = anoAtual;
            }
        }
    }
    private void inicializarBoxAnos(){
        for (int i = Year.now().getValue(); i >= Year.now().getValue() - AppConstantes.ANOS_ANTERIORES; i--) {
            tabHomeController.boxAnos.getItems().add(String.valueOf(i));
        }
    }
    private void inicializarListeners(){
        tabHomeController.boxAnos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> tabHomeController.atualizarBoxMeses(valorNovo));
        tabHomeController.boxGraficos.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo != null) {
                tabHomeController.selecionarGrafico(valorNovo);
                graficoSelecionadoAtual = valorNovo;
            }
        });
        tabGraficosController.tipoGrafico.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            setTipoGrafico(valorNovo);
            graficoManager.trocarTipoGrafico(valorNovo);
            tabGraficosController.atualizarGraficoNaTab();
            tabHomeController.selecionarGrafico(graficoSelecionadoAtual);
        });
    }

    //(Função que carrega as telas e armazena no map rotas)-------------------------------------------------------------
    public <T> T carregarFXMLComController(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            rotas.put(fxmlPath, root);

            return loader.getController();

        } catch (IOException e) {
            Toast.mostrarToast(rootPane,"Não foi possível localizar a nova página", ToastEnum.ERRO);
        }
        return null;
    }

    public void trocarTela(String fxmlPath) {
        getRootPane().getChildren().set(0,getTela(fxmlPath));
    }

    //(Funções para pegar instancias)-----------------------------------------------------------------------------------
    public GraficoManager getGraficoManager() {
        return this.graficoManager;
    }
    public TabUsuarioController getTabUsuarioController() {
        return this.tabUsuarioController;
    }
    public TabHomeController getTabHomeController() {
        return this.tabHomeController;
    }
    public TabGraficosController getTabGraficosController() {
        return this.tabGraficosController;
    }
    public Parent getTela(String key) {
        return rotas.get(key);
    }
    public AnchorPane getRootPane() {
        return rootPane;
    }
    public TabManager getTabManager() {
        return tabManager;
    }

    //(Função que garante a instancia unica da classe)------------------------------------------------------------------
    public static synchronized AppModel getInstance() {
        if (instance == null) {
            instance = new AppModel();
        }
        return instance;
    }

    //(Função que seta o pane principal da aplicação)-------------------------------------------------------------------
    public void setRootPane(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public TipoGrafico getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(TipoGrafico tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
}
