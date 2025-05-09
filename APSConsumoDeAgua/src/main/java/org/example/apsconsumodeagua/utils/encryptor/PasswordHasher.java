package org.example.apsconsumodeagua.utils.encryptor;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    private static final int WORK_FACTOR = 12;
    // O "work factor" controla quão custoso é gerar o hash.
    // Valores mais altos são mais seguros, mas mais lentos.
    // O valor padrão do gensalt() costuma ser 10 ou 12.
    // Você pode ajustar se necessário, mas 12 é um bom ponto de partida.

    public String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }
        // BCrypt.gensalt() gera um salt aleatório com o work factor especificado.
        // BCrypt.hashpw() combina a senha e o salt para gerar o hash final.
        String salt = BCrypt.gensalt(WORK_FACTOR);
        return BCrypt.hashpw(plainPassword, salt);
    }
    public String hashPasswordWithDefaults(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }
        // Usa BCrypt.gensalt() sem argumentos para o work factor padrão (geralmente 10)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    //------------------------------------------------------------------------------------------------------------------
}