package org.example.apsconsumodeagua.dto.usuario;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String cep;
    private String endereco;
    private String cidade;
    private String estado;
    private int pessoasNaCasa;

    public UsuarioResponseDTO(Long id, String nome, String sobrenome, String email,String cpf, String cep, String endereco, String cidade, String estado, int pessoasNaCasa) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.pessoasNaCasa = pessoasNaCasa;
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
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
