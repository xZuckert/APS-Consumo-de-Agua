package org.example.apsconsumodeagua.models.usuario;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//Classe modelo de usuario----------------------------------------------------------------------------------------------
public class UsuarioModel {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String bairro;
    private String rua;
    private String numero;
    private String cep;
    private String cidade;
    private String estado;
    private String password;
    private int pessoasNaCasa;
    private final DoubleProperty consumoIdeal = new SimpleDoubleProperty();
    //Construtor da classe usuario--------------------------------------------------------------------------------------
    public UsuarioModel(String nome, String sobrenome, String email, String cpf, String cep, String endereco, String rua, String numero, String cidade, String estado, String password, int pessoasNaCasa) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.cep = cep;
        this.bairro = endereco;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.password = password;
        this.pessoasNaCasa = pessoasNaCasa;
    }
    //Funções para manipular os dados do usuario------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String cep) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String cep) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPessoasNaCasa() {
        return pessoasNaCasa;
    }

    public void setPessoasNaCasa(int pessoasNaCasa) {
        this.pessoasNaCasa = pessoasNaCasa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getConsumoIdeal() {
        return consumoIdeal.get();
    }

    public void setConsumoIdeal(double consumoIdeal) {
        this.consumoIdeal.set(consumoIdeal);
    }

    public DoubleProperty consumoIdealProperty() {
        return consumoIdeal;
    }
    //------------------------------------------------------------------------------------------------------------------
}