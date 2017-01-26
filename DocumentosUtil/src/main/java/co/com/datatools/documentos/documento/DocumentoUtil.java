/**
 * 
 */
package co.com.datatools.documentos.documento;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Clase utilizada para generar consecutivo en Thread Safe
 * 
 * @author julio.pinzon
 * 
 */
public class DocumentoUtil {

    private static AtomicLong consecutivo = null;
    static {
        consecutivo = new AtomicLong();
    }

    public static synchronized long obtenerConsecutivoDocumento(long consecutivoDocumento) {
        if (consecutivo.get() == 0l) {
            consecutivo.set(consecutivoDocumento);
            consecutivo.incrementAndGet();
        } else {
            consecutivo.incrementAndGet();
            // EN caso que hayan varias instancias de la aplicacion se debe incrementar hasta encontrar un consecutivo no tomado
            while (consecutivo.get() <= consecutivoDocumento) {
                consecutivo.incrementAndGet();
            }
        }
        return consecutivo.get();
    }
}
