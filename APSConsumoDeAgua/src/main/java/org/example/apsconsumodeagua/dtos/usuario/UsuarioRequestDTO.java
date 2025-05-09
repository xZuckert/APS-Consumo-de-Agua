package org.example.apsconsumodeagua.dtos.usuario;

//Classes DTO usadas para entrada de dados do usuário entre as camadas da aplicação-------------------------------------
public class UsuarioRequestDTO {
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String password;
    private int pessoasNaCasa;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
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
    public int getPessoasNaCasa() {
        return pessoasNaCasa;
    }
    public void setPessoasNaCasa(int pessoasNaCasa) {
        this.pessoasNaCasa = pessoasNaCasa;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    //------------------------------------------------------------------------------------------------------------------
}