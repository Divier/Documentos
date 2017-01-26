/**
 * 
 */
package co.com.datatools.documentos.util;

import java.util.Comparator;

import co.com.datatools.documentos.plantillas.VariableDTO;

/**
 * Comparador de variables
 * 
 * @author julio.pinzon
 * 
 */
public class VariableDTOComparator implements Comparator<VariableDTO> {
    @Override
    public int compare(VariableDTO o1, VariableDTO o2) {
        return o1.getNombreVariable().toLowerCase().compareTo(o2.getNombreVariable().toLowerCase());
    }
}
