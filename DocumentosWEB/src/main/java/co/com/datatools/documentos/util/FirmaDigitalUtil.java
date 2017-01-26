package co.com.datatools.documentos.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;

import org.apache.commons.io.FileUtils;

import co.com.datatools.documentos.excepcion.DocumentosRuntimeException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Utilitario para firmar digitalmente documentos PDF utilizando iText
 * 
 * @author dixon.alvarez
 */
public class FirmaDigitalUtil {

    public static final String TYPE_PKCS12 = "pkcs12";

    /**
     * Firma un pdf digitalmente
     * 
     * @param keyStore
     *            El key store como arreglo de bytes
     * @param alias
     *            El alias de la llave privada
     * @param password
     *            La contrasena del key store
     * @param pdf
     *            El pdf a firmar
     * @return El pdf firmado
     */
    public static byte[] firmarPdf(byte[] keyStore, String alias, String password, byte[] pdf) {

        return firmarPdf(keyStore, alias, password, pdf, PdfSignatureAppearance.SELF_SIGNED);
    }

    /**
     * Firma un pdf digitalmente
     * 
     * @param keyStore
     *            El key store como arreglo de bytes
     * @param alias
     *            El alias de la llave privada
     * @param password
     *            La contrasena del key store
     * @param pdf
     *            El pdf a firmar
     * @return El pdf firmado
     */
    public static byte[] firmarPdf(byte[] keyStore, String alias, String password, File pdf) {
        byte[] buffer;
        try {
            buffer = FileUtils.readFileToByteArray(pdf);
        } catch (IOException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        }
        return firmarPdf(keyStore, alias, password, buffer);
    }

    /**
     * Firma un pdf digitalmente
     * 
     * @param keyStore
     *            El key store como arreglo de bytes
     * @param alias
     *            El alias de la llave privada
     * @param password
     *            La contrasena del key store
     * @param pdf
     *            El pdf a firmar
     * @param mode
     *            El modo dependiendo del tipo de certificado. Los posibles valores son: <li>
     *            <ul>
     *            PdfSignatureAppearance.SELF_SIGNED-Adobe.PPKLite
     *            </ul>
     *            <ul>
     *            PdfSignatureAppearance.WINCER_SIGNED-Adobe.PPKMS
     *            </ul>
     *            <ul>
     *            PdfSignatureAppearance.VERISIGN_SIGNED-VeriSign.PPKVS
     *            </ul>
     *            </li>
     * 
     * @return El pdf firmado
     */
    public static byte[] firmarPdf(byte[] keyStore, String alias, String password, byte[] pdf, PdfName mode) {
        try {

            /* KeyStore */
            KeyStore ks = KeyStore.getInstance(TYPE_PKCS12);
            InputStream in = new ByteArrayInputStream(keyStore);
            ks.load(in, password.toCharArray());

            /* PrivateKey */
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());

            if (privateKey == null) {
                throw new DocumentosRuntimeException("alias incorrecto");
            }

            /* Certificate */
            Certificate[] certificateChain = ks.getCertificateChain(alias);

            PdfReader pdfReader = new PdfReader(pdf);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            /* Creacion de firma */
            PdfStamper stamper = PdfStamper.createSignature(pdfReader, out, '\0');
            PdfSignatureAppearance signatureAppearance = stamper.getSignatureAppearance();
            signatureAppearance.setCrypto(privateKey, certificateChain, null, mode);
            stamper.close();

            return out.toByteArray();

        } catch (KeyStoreException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        } catch (CertificateException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            if (e.getCause() instanceof BadPaddingException) {
                throw new DocumentosRuntimeException("Carga certificado", e);
            } else {
                throw new DocumentosRuntimeException("Archivo no valido", e);
            }
        } catch (UnrecoverableKeyException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        } catch (DocumentException e) {
            throw new DocumentosRuntimeException(e.getMessage(), e);
        }
    }
}
