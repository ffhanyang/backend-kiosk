package com.ffhanyang.kiosk.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    private PooledPBEStringEncryptor encryptor;

    private SimpleStringPBEConfig config;

    @BeforeEach
    void setUp() {
        encryptor = new PooledPBEStringEncryptor();
        config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize("1");
        encryptor.setConfig(config);
    }

    @Test
    @DisplayName("Jasypt Encrypt성공")
    void jasypt_encrypt_success() {
        // given
        String plainText = "plainText";

        // when
        String encryptedText = encryptor.encrypt(plainText);
        String decryptedText = encryptor.decrypt(encryptedText);
        log.info("encrypted: {}, decrypted: {}", encryptedText, decryptedText);

        // then
        Assertions.assertThat(decryptedText).isEqualTo(plainText);
    }

    @Test
    @DisplayName("Jasypt Decrypt성공")
    void jasypt_decrypt_success() {
        // given
        String plainText = "plainText";
        String provided = "RqLcOyk8PJepxvdPcESQSJR1+GHmlr6x";

        // when
        String decryptedText = encryptor.decrypt(provided);
        log.info("decrypted: {}", decryptedText);

        // then
        Assertions.assertThat(decryptedText).isEqualTo(plainText);

    }


}
