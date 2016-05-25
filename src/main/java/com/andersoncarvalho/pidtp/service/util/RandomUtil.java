package com.andersoncarvalho.pidtp.service.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Classe para geracao de strings aleatorias.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Gera uma senha.
     *
     * @return a senha gerada
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Gera uma chava de ativacao.
     *
     * @return a chavede ativacao
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
    * Gera uma chave para resetar.
    *
    * @return a chave para resetar
    */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }
}
