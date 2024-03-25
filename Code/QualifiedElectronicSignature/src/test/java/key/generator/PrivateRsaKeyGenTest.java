package key.generator;

import org.junit.jupiter.api.Test;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.key.generator.PrivateRsaKeyGen;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.*;

public class PrivateRsaKeyGenTest {

    private final byte[] keyBytes = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate().getEncoded();

    private final String cipherType = CipherType.RSA.getStrValue();

    private final PrivateRsaKeyGen keyGen = new PrivateRsaKeyGen();

    public PrivateRsaKeyGenTest() throws NoSuchAlgorithmException {
    }

    @Test
    public void should_throwCriticalAppError_when_nullCipherTypePassedToGenerateKey() {
        assertThatThrownBy(() -> keyGen.generateKey(keyBytes, null)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwCriticalAppError_when_nullBytesPassedToGenerateKey() {
        assertThatThrownBy(() -> keyGen.generateKey(null, cipherType)).isInstanceOf(CriticalAppError.class);
    }

    @Test
    public void should_throwBasicAppError_when_invalidCipherTypePassedToGenerateKey() {
        String invalidCipherType = CipherType.AES.getStrValue();
        assertThatThrownBy(() -> keyGen.generateKey(keyBytes, invalidCipherType)).isInstanceOf(BasicAppError.class);
    }

    @Test
    public void should_throwBasicAppError_when_invalidBytesPassedToGenerateKey() {
        byte[] invalidBytes = new byte[]{1, 2, 3};
        assertThatThrownBy(() -> keyGen.generateKey(invalidBytes, cipherType)).isInstanceOf(BasicAppError.class);
    }

    @Test
    public void should_notThrowAnyException_when_validCipherTypeAndBytesPassedToGenerateKey() {
        assertThatCode(() -> keyGen.generateKey(keyBytes, cipherType)).doesNotThrowAnyException();
    }


}
