/**
 * 
 */
package co.com.datatools.documentos.documento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Clase utilitaria para manejo de numeros
 * 
 * @author julio.pinzon
 *
 */
public class NumeroUtil {

    private static final String[] UNIDADES = { "", "UN ", "DOS ", "TRES ", "CUATRO ", "CINCO ", "SEIS ", "SIETE ",
            "OCHO ", "NUEVE ", "DIEZ ", "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ", "DIECISIETE ",
            "DIECIOCHO ", "DIECINUEVE ", "VEINTE " };

    private static final String[] DECENAS = { "VENTI", "TREINTA ", "CUARENTA ", "CINCUENTA ", "SESENTA ", "SETENTA ",
            "OCHENTA ", "NOVENTA ", "CIEN " };

    private static final String[] CENTENAS = { "CIENTO ", "DOSCIENTOS ", "TRESCIENTOS ", "CUATROCIENTOS ",
            "QUINIENTOS ", "SEISCIENTOS ", "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS " };

    private static final String[] CENTENAS_DECIMALES = { "CIENTO ", "DOSCIENTAS ", "TRESCIENTAS ", "CUATROCIENTAS ",
            "QUINIENTAS ", "SEISCIENTAS ", "SETECIENTAS ", "OCHOCIENTAS ", "NOVECIENTAS " };

    private static final String[] UNIDADES_DECIMALES = { "", "DÉCIMAS", "CENTÉSIMAS", "MILÉSIMAS", "DIEZMILÉSIMAS",
            "CIENMILÉSIMAS", "MILLONÉSIMAS", "DIEZMILLONÉSIMAS", "CIENMILLONÉSIMAS", "MILMILLONÉSIMAS", "BILLONÉSIMAS",
            "DIEZBILLONÉSIMAS", "CIENBILLONÉSIMAS", "MILBILLONÉSIMAS", "TRILLONÉSIMAS", "DIEZTRILLONÉSIMAS",
            "CIENTRILLONÉSIMAS", "MILTRILLONÉSIMAS" };

    private static final String[] MILLONES = { "CIENTO ", "MIL ", "MILLÓN ", "BILLÓN ", "TRILLÓN " };

    private static final String[] MILLONES_PLURAL = { "CIENTOS ", "MIL ", "MILLONES ", "BILLONES ", "TRILLONES " };

    private static final String MENOS = "MENOS ";
    private static final String UNO = "UNO ";
    private static final String UNA = "UNA ";
    private static final String CERO = "CERO ";
    private static final String SEPARADOR_DECIMALES = "CON ";
    private static int total = 0;
    private static final int INDEX_MIL = 1;

    private static final String TEXTO_DECIMAL = "/100";

    /**
     * Convierte los trios de numeros que componen las unidades, las decenas y las centenas del numero.
     * 
     * @param number
     *            Numero a convertir en digitos
     * @return Numero convertido en letras
     */
    private static String convertNumber(String number, boolean decimales) {

        // Caso especial con el 100
        if (number.equals("100")) {
            return "CIEN";
        }

        StringBuilder output = new StringBuilder();
        if (getDigitAt(number, 2) != 0) {
            if (!decimales) {
                output.append(CENTENAS[getDigitAt(number, 2) - 1]);
            } else {
                output.append(CENTENAS_DECIMALES[getDigitAt(number, 2) - 1]);
            }
        }

        int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1)) + String.valueOf(getDigitAt(number, 0)));

        if (k <= 20) {
            output.append(UNIDADES[k]);
        } else {
            if (k > 30 && getDigitAt(number, 0) != 0) {
                output.append(DECENAS[getDigitAt(number, 1) - 2] + "Y " + UNIDADES[getDigitAt(number, 0)]);
            } else {
                output.append(DECENAS[getDigitAt(number, 1) - 2] + UNIDADES[getDigitAt(number, 0)]);
            }
        }

        return output.toString();
    }

    /**
     * Convierte un numero en representacion numerica a uno en representacion de texto. El numero es valido si esta entre 0 y 999'999.999
     * 
     * @param patternDecimalPoints
     *            Patron para dar formato al numero
     * 
     * @param number
     *            Numero a convertir
     * @return Numero convertido a texto
     * @throws NumberFormatException
     *             Si no es un numero
     */
    public static String convertNumberToLetter(String doubleNumber, String patternDecimalPoints)
            throws NumberFormatException {

        // String patternThreeDecimalPoints = "#.###";
        String formatedDouble = Math.abs(Double.valueOf(doubleNumber)) + "";
        if (patternDecimalPoints != null) {

            DecimalFormat format = new DecimalFormat(patternDecimalPoints);
            format.setRoundingMode(RoundingMode.DOWN);
            // formateamos el numero, para ajustarlo a el formato de tres puntos
            // decimales
            formatedDouble = format.format(Math.abs(Double.valueOf(doubleNumber)));
        }
        return convertNumberToLetter(formatedDouble);
    }

    /**
     * Convierte un numero en representacion numerica a uno en representacion de texto. El numero es valido si esta entre 0 y 999'999.999
     * 
     * @param number
     *            Numero a convertir
     * @return Numero convertido a texto
     * @throws NumberFormatException
     *             Si no es un numero
     */
    public static String convertNumberToLetter(String doubleNumber) throws NumberFormatException {

        doubleNumber = new BigDecimal(doubleNumber.replaceAll(",", ".")).toPlainString();
        // Utilizado para concatenar las palabras del numero
        StringBuilder converted = new StringBuilder();

        // Valor negativo
        if (doubleNumber.charAt(0) == '-') {
            converted.append(MENOS);
            doubleNumber = doubleNumber.replaceAll("-", "");
        }

        // Parte la cadena en decimales y enteros
        String[] splitNumber = doubleNumber.replace(',', '#').replace('.', '#').split("#");

        // Convierte los enteros
        converted.append(convertInteger(splitNumber[0], false));

        // Decimales
        if (splitNumber.length > 1) {

            // Convierte los decimales
            // String decimales = convertInteger(splitNumber[1], true);
            String decimales = splitNumber[1] + TEXTO_DECIMAL;

            if (!decimales.isEmpty()) {
                converted.append(SEPARADOR_DECIMALES);
                converted.append(decimales);
            }

        }

        return converted.toString();
    }

    /**
     * Convierte a letras un numero entero
     * 
     * @param textNumber
     *            Cadena de texto del numero
     * @param isDecimal
     *            Indica si se esta haciendo sobre un decimal
     * @return Numero convertido a palabras
     */
    private static String convertInteger(String textNumber, boolean isDecimal) {
        StringBuilder converted = new StringBuilder();
        total = 0;
        boolean isMil = true;
        // Indice del arreglo de unidades de millones
        int millionUnit = MILLONES.length - 1;
        // Maximo 24 digitos
        for (int i = 23; i >= 0; i -= 3) {
            // Unidades
            if (i == 2) {
                // Descompone el ultimo trio de unidades
                int cientos = Integer.parseInt(String.valueOf(getDigitAt(textNumber, 2))
                        + String.valueOf(getDigitAt(textNumber, 1)) + String.valueOf(getDigitAt(textNumber, 0)));
                if (cientos == 1) {
                    if (!isDecimal) {
                        converted.append(UNO);
                    } else {
                        converted.append(UNA);
                    }
                } else if (cientos > 1) {
                    converted.append(convertNumber(String.valueOf(cientos), isDecimal));
                }
                total += cientos;
                // Si no convirtio nada es 0
                if (!isDecimal && total == 0) {
                    converted.append(CERO);
                }
            }
            // Miles
            else if (isMil) {
                converted.append(convertWithUnits(i, INDEX_MIL, textNumber, isDecimal));
                isMil = false;
            }
            // Unidad millones
            else {
                converted.append(convertWithUnits(i, millionUnit, textNumber, isDecimal));
                millionUnit--;
                isMil = true;
            }
        }

        // En caso que sea mil

        if (Double.valueOf(textNumber) == 1000) {
            converted = new StringBuilder(MILLONES[INDEX_MIL]);
        }
        if (isDecimal) {
            // Unidades
            if (Double.valueOf(textNumber) == 1) {
                converted.append("DECIMA");
            } else if (Double.valueOf(textNumber) > 1) {
                converted.append(CENTENAS_DECIMALES[textNumber.length()]);
            }
        }
        return converted.toString();
    }

    /**
     * Convierte una serie de tres digitos a palabras segun su unidad
     * 
     * @param index
     *            Posicion del numero que se esta evaluando
     * @param millionUnit
     *            Posicion de la unidad de millones
     * @param textNumber
     *            Numero evaluado
     * @param decimals
     * @return Numero en palabras con su unidad
     */
    private static String convertWithUnits(int index, int millionUnit, String textNumber, boolean decimals) {
        String converted = "";

        int number = Integer.parseInt(
                String.valueOf(getDigitAt(textNumber, index)) + String.valueOf(getDigitAt(textNumber, index - 1))
                        + String.valueOf(getDigitAt(textNumber, index - 2)));
        if (number == 1 && millionUnit == INDEX_MIL) {
            // MIL
            converted = MILLONES[INDEX_MIL];
        } else if (number == 1) {
            converted = UNIDADES[1] + MILLONES[millionUnit];
        } else if (number > 1) {
            converted = convertNumber(String.valueOf(number), decimals) + MILLONES_PLURAL[millionUnit];
        } else {
            // En caso que sean solo ceros
            if (textNumber.length() > (index - 2) && millionUnit != INDEX_MIL) {
                converted = MILLONES_PLURAL[millionUnit];
            }
        }
        total += number;
        return converted;
    }

    /**
     * Retorna el digito numerico en la posicion indicada de derecha a izquierda
     * 
     * @param origin
     *            Cadena en la cual se busca el digito
     * @param position
     *            Posicion de derecha a izquierda a retornar
     * @return Digito ubicado en la posicion indicada
     */
    private static int getDigitAt(String origin, int position) {
        if (origin.length() > position && position >= 0) {
            return origin.charAt(origin.length() - position - 1) - 48;
        }
        return 0;
    }
}
