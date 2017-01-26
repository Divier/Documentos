package co.com.datatools.documentos.entidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para generar los helper por la perdedera de tiempo tan h********
 * 
 * @author pedro.moncada
 * 
 */
public class UtilsHelpers extends JFrame implements ActionListener {

	/**
	 * @author pedro.moncada
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtDireccion;
	private JTextField txtNombreHelper;

	public UtilsHelpers() {
		// frame
		setSize(780, 120);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Creador de helpers automatico");
		// campo de texto para la direccion de creacion del helper
		JLabel labDireccion = new JLabel("Direccion:");
		labDireccion.setBounds(5, 5, 80, 30);
		add(labDireccion);
		txtDireccion = new JTextField(
				"D:\\circulemos2\\Proyecto\\WorkspaceMaven\\DocumentosJPA\\src\\main\\java\\co\\com\\datatools\\documentos\\entidades\\");
		txtDireccion.setBounds(90, 5, 650, 30);
		add(txtDireccion);
		// campo que recibe el nombre del helper
		JLabel labNombreHelper = new JLabel("Entidad:");
		labNombreHelper.setBounds(5, 40, 80, 30);
		add(labNombreHelper);
		txtNombreHelper = new JTextField();
		txtNombreHelper.setBounds(90, 40, 350, 30);
		add(txtNombreHelper);
		// boton que genera el helper si todo sale bien
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.setBounds(490, 40, 250, 30);
		btnGenerar.addActionListener(this);
		add(btnGenerar);

	}

	/**
	 * @author pedro.moncada
	 * @param typeName
	 *            , parametro que recibe el tipo de dato del objeto que se esta
	 *            intentando ecribir en el archivo .java
	 * @return, true si no es ni de tipo primitivo ni ancestro y false si es
	 *          alguno de ellos.
	 */
	public boolean isNotTypePrimitiveOrAncestor(String typeName) {
		if (typeName.toLowerCase().trim().equals("string")
				|| typeName.toLowerCase().trim().equals("string[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("integer")
				|| typeName.toLowerCase().trim().equals("integer[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("int")
				|| typeName.toLowerCase().trim().equals("int[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("byte")
				|| typeName.toLowerCase().trim().equals("byte[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("long")
				|| typeName.toLowerCase().trim().equals("long[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("date")
				|| typeName.toLowerCase().trim().equals("date[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("bigdecimal")
				|| typeName.toLowerCase().trim().equals("bigdecimal[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("short")
				|| typeName.toLowerCase().trim().equals("short[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("float")
				|| typeName.toLowerCase().trim().equals("float[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("double")
				|| typeName.toLowerCase().trim().equals("double[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("char")
				|| typeName.toLowerCase().trim().equals("char[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("boolean")
				|| typeName.toLowerCase().trim().equals("boolean[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("time")
				|| typeName.toLowerCase().trim().equals("time[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("biginteger")
				|| typeName.toLowerCase().trim().equals("biginteger[]")) {
			return false;
		} else if (typeName.toLowerCase().trim().equals("void")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author pedro.moncada
	 * @param baseClase
	 * @return
	 */
	public boolean existeEntidad(String baseClase) {
		ClassPool pool = ClassPool.getDefault();
		// String baseClase = "co.datatools.pruebas.todo.Actividad";
		try {
			String dir = "co.com.datatools.documentos.entidades." + baseClase;
			@SuppressWarnings("unused")
			CtClass cc = pool.get(dir);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	/**
	 * This method is a filter for JPA reserve Words please complement
	 * 
	 * @param clase
	 * @return
	 */
	public boolean containsEntity(String clase) {
		if (clase.contains("Id") || clase.contains("Entity")
				|| clase.contains("ManyToOne") || clase.contains("OneToMany"))
			return true;

		return false;
	}

	/**
	 * @author pedro.moncada
	 * @param value
	 *            a integer represent the modifiers of class, metod o attribute
	 * @return a String for create a class.
	 */
	public static String getInfoModifiers(int value) {
		String strReturn = "";
		switch (value) {
		case Modifier.ABSTRACT:
			strReturn += " abstract";
			break;
		case Modifier.PUBLIC:
			strReturn += " public";
			break;
		case Modifier.FINAL:
			strReturn += " final";
			break;
		case Modifier.PRIVATE:
			strReturn += " private";
			break;
		case Modifier.PROTECTED:
			strReturn += " protected";
			break;
		case Modifier.STATIC:
			strReturn += " static";
			break;
		// case Modifier.ENUM:
		// strReturn += " enum";
		// break;
		case 25: // Equals to private static final;
			strReturn = " public static final";
			break;
		case 26: // Equals to private static final;
			strReturn = " private static final";
			break;
		}
		// System.out.println("Valor es " + value);
		return strReturn;

	}

	/**
	 * @author pedro.moncada
	 * @param cadena
	 * @return
	 */
	public String stringFirstUp(String cadena) {
		if (!cadena.isEmpty()) {
			String nuevaCadena = cadena.substring(0, 1).toUpperCase()
					+ cadena.substring(1, cadena.length());
			return nuevaCadena;
		} else {
			return cadena;
		}
	}

	/**
	 * @author pedro.moncada
	 * @param baseClase
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public void createHelper(String direccion, String baseClase)
			throws NotFoundException, IOException {

		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(baseClase);
		CtField[] fields = cc.getDeclaredFields();

		// Creacion de la clase y sus modificadores
		File myFile = new File(direccion + cc.getSimpleName() + "Helper.java");

		String nameClassLower = myFile.getName().toString().substring(0, 1)
				.toLowerCase()
				+ myFile.getName()
						.toString()
						.substring(1, myFile.getName().toString().length() - 11);

		String nameClass = myFile.getName().toString().substring(0, 1)
				.toUpperCase()
				+ myFile.getName()
						.toString()
						.substring(1, myFile.getName().toString().length() - 11);

		// buffer de escritura
		BufferedWriter out = new BufferedWriter(new FileWriter(myFile));
		// class package
		// you can change this package location by another
		out.write("package co.com.datatools.documentos.negocio.helpers;\n\n");
		out.write("import co.com.datatools.documentos.entidades." + cc.getSimpleName()
				+ ";\n\n");
		out.write("import co.com.datatools.documentos.dto." + cc.getSimpleName()
				+ "DTO;\n\n");

		out.write("\n\n");
		out.write("/** \n *\n * @author Pedro.Moncada\n * @version 2.0\n **/ \n");
		// Write the class declaration with interface implements
		out.write(getInfoModifiers(cc.getModifiers()).trim() + " class "
				+ nameClass + "Helper" + " {\n\n");

		// METODO A DTO
		out.write("\tpublic static " + nameClass + "DTO to" + nameClass
				+ "DTO(" + nameClass + " " + nameClassLower + "){\n");

		String nameClassDTO = nameClass + "DTO";
		String nameClassLowerDTO = nameClassLower + "DTO";

		out.write("\t\t" + nameClassDTO + " " + nameClassLowerDTO + " = new "
				+ nameClassDTO + "();\n");

		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!fields[i].getType().getSimpleName().toLowerCase()
					.equals("list")
					&& !isNotTypePrimitiveOrAncestor(fields[i].getType()
							.getSimpleName())
					&& !name.toLowerCase().contains("serialversionuid")) {
				out.write("\t\t" + nameClassLowerDTO + ".set"
						+ stringFirstUp(name) + "(" + nameClassLower + ".get"
						+ stringFirstUp(name) + "());");
				out.write("\n");
			}
		}
		out.write("\t\treturn " + nameClassLowerDTO + ";");
		out.write("\n\t}\n\n");

		// METODO A ENTIDAD
		out.write("\tpublic static " + nameClass + " to" + nameClass + "("
				+ nameClassDTO + " " + nameClassLowerDTO + ", " + nameClass
				+ " " + nameClassLower + "){\n");
		out.write("\t\tif(null==" + nameClassLower + "){\n");
		out.write("\t\t\t" + nameClassLower + " = new " + nameClass + "();\n");
		out.write("\t\t}\n");
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!fields[i].getType().getSimpleName().toLowerCase()
					.equals("list")
					&& !isNotTypePrimitiveOrAncestor(fields[i].getType()
							.getSimpleName())
					&& !name.toLowerCase().contains("serialversionuid")) {
				out.write("\t\t" + nameClassLower + ".set"
						+ stringFirstUp(name) + "(" + nameClassLowerDTO
						+ ".get" + stringFirstUp(name) + "());");
				out.write("\n");
			}
		}
		out.write("\t\treturn " + nameClassLower + ";");
		out.write("\n\t}\n\n");

		out.write("}");
		out.close();
	}

	/**
	 * @author pedro.moncada
	 * @param args
	 */
	public static void main(String[] args) {
		UtilsHelpers utilsHelpers = new UtilsHelpers();
		utilsHelpers.setVisible(true);
	}

	/**
	 * @author pedro.moncada
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (existeEntidad(txtNombreHelper.getText().trim())) {
			try {
				createHelper(
						txtDireccion.getText().trim(),
						"co.com.datatools.documentos.entidades."
								+ txtNombreHelper.getText());
				JOptionPane.showMessageDialog(null, "Helper Creado!", "Error",
						JOptionPane.INFORMATION_MESSAGE, null);
				txtNombreHelper.setText("");
			} catch (NotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "La entidad no existe",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}

	}
}
