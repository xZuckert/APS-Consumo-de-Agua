package org.example.apsconsumodeagua.utils.encryptor;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordVerifier {

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || plainPassword.isEmpty() || hashedPassword == null || hashedPassword.isEmpty()) {
            // Ou lançar uma exceção, dependendo da sua lógica de tratamento de erros.
            System.err.println("Senha ou hash inválidos para verificação.");
            return false;
        }

        try {
            // BCrypt.checkpw extrai automaticamente o salt do hashedPassword,
            // hasheia o plainPassword com esse salt e compara os resultados.
            return BCrypt.checkpw(plainPassword, hashedPassword); // Retorna True a senha esteja correta e Retorna false caso a senha seja incorreta
        } catch (IllegalArgumentException e) {
            // Ocorre se o hashedPassword não for um hash BCrypt válido.
            System.err.println("Erro ao verificar a senha: O hash fornecido não é válido. " + e.getMessage());
            return false;
        }
    }
}