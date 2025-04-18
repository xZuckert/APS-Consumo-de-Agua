package org.example.apsconsumodeagua.dto.usuario;

public class UsuarioRequestDTO {
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String cep;
    private String endereco;
    private String cidade;
    private String estado;
    private String password;
    private int pessoasNaCasa;

    public UsuarioRequestDTO(int pessoasNaCasa, String password, String estado, String cidade, String endereco, String cep,String cpf, String email, String sobrenome, String nome) {
        this.pessoasNaCasa = pessoasNaCasa;
        this.password = password;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.cep = cep;
        this.cpf = cpf;
        this.email = email;
        this.sobrenome = sobrenome;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPessoasNaCasa(int pessoasNaCasa) {
        this.pessoasNaCasa = pessoasNaCasa;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
