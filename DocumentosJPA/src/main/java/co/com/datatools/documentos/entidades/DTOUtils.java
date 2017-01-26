package co.com.datatools.documentos.entidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collection;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Esteban Hernandez Esta clase ha sido creada para ayudar a los
 *         desarrolladores a crear sus DTOS de manera automatica a partir de los
 *         Entities de JPA Requiere la libreria Javassist 3.1.8
 * 
 *         Csddlabs 2013.
 * 
 */
public class DTOUtils {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		/*
		 * if (args.length < 2) { System.out .println(
		 * "Se requiere pasar el nombre de la clase de la cual se desea generar el DTO"
		 * ); return; }
		 */
		DTOUtils myUtils = new DTOUtils();
		while (true) {

			try {

				String nombreEntidad = JOptionPane
						.showInputDialog("Ingrese el nombre de la entidad que desea generar");
				myUtils.createDTO("co.com.datatools.documentos.entidades."
						+ nombreEntidad);
				JOptionPane.showMessageDialog(null,
						"La entidad fue creada con exito", "OK",
						JOptionPane.INFORMATION_MESSAGE);
				// myUtils.createDTO("co.com.datatools.c2.entidades.Comparendo");

			} catch (NotFoundException e) {
				JOptionPane.showMessageDialog(null, "La entidad no existe",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
			    e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Error con la comunicacion",
						"Error con la comunicacion", JOptionPane.ERROR_MESSAGE);
			}
			// break;
			int opcion = JOptionPane.showConfirmDialog(null,
					"Desea generar otro DTO?");
			if (opcion > 0) {
				break;
			}
		}

	}

	/**
	 * 
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
	 * 
	 * @param nameOfParameter
	 *            change to lowerCase the firts letter of parameter
	 * @return
	 */
	public static String getReturnParameterUpper(String nameOfParameter) {
		String base = nameOfParameter.substring(3);
		StringBuilder baseConvention = new StringBuilder(base);
		char first = base.charAt(0);
		if (Character.isUpperCase(first))
			baseConvention.setCharAt(0, Character.toLowerCase(first));

		return baseConvention.toString();
	}

	/**
	 * 
	 * @return the base path of the current project
	 */
	// public static String getBasePath() {
	// DTOUtils myUtils = new DTOUtils();
	// String lastPart = "\\src\\";
	// ClassLoader cl = myUtils.getClass().getClassLoader();
	// URL url = cl.getResource(".");
	// File theFile = new File(url.getFile());
	// System.out.println("el path es" + theFile.getParent());
	// //return theFile.getParent() + lastPart;
	// String u = "D:\\producto\\proyecto\\workspace\\Circulemos2JPA\\src\\";
	// return u;
	// }

	public static String getBasePath() throws IOException {
		String lastPart = "\\src\\";
		String path = System.getProperty("user.dir");
		return path + lastPart;
	}

	/**
	 * 
	 * @param packageName
	 *            the F.Q.N of package
	 * @return a path base on character / of package F.Q.N
	 */
	public static String packageToFolder(String packageName) {
		String folderName = packageName.replace('.', '\\');
		return folderName;
	}

	/**
	 * 
	 * @param path
	 *            Full path including the name with extension
	 * @return the path without extension file
	 */
	public static String removeExtension(String path) {
		String pathWithoutExtension = path;
		if (!path.contains("."))
			return path;
		else
			pathWithoutExtension = path.substring(0, path.lastIndexOf("."));
		return pathWithoutExtension;
	}

	/**
	 * 
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

	@SuppressWarnings("unused")
	public boolean existeEntidad(String baseClase) {
		ClassPool pool = ClassPool.getDefault();
		// String baseClase = "co.datatools.pruebas.todo.Actividad";
		try {
			String dir = "co.com.datatools.documentos.entidades." + baseClase;
			CtClass cc = pool.get(dir);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param baseClase
	 *            is the F.Q.N of class without extension of initial class to be
	 *            transformed
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public void createDTO(String baseClase) throws NotFoundException,
			IOException {

		ClassPool pool = ClassPool.getDefault();
		// String baseClase = "co.datatools.pruebas.todo.Actividad";
		CtClass cc = pool.get(baseClase);
		// pool.makeClass("" + cc.getPackageName() + "." + cc.getName());
		CtField[] fields = cc.getDeclaredFields();
		CtMethod[] methods = cc.getDeclaredMethods();

		// se crean nuevas clases a partir de entidades encontradas
		for (int i = 0; i < fields.length; i++) {
			// se pregunta si el tipo contenido es de tipo primitivo o de su
			// ancestro
			if (isNotTypePrimitiveOrAncestor(fields[i].getType()
					.getSimpleName())) {
				// se pregunta si es una lista, ya que esta tiene un tratamiento
				// especial
				if (fields[i].getType().getSimpleName().toLowerCase()
						.equals("list")) {
					// se coloca la incial del nombre en mayuscula
					String newName = fields[i].getName();
					newName = newName.substring(0, 1).toUpperCase()
							+ newName.substring(1, newName.length());
					String newName1 = newName;
					// para controlar plurales se busca que exista la entidad
					if (existeEntidad(newName
							.substring(0, newName.length() - 1))) {
						newName = newName.substring(0, newName.length() - 1)
								+ "DTO";
					} else if (existeEntidad(newName.substring(0,
							newName.length() - 2))) {
						newName = newName.substring(0, newName.length() - 2)
								+ "DTO";
					} else if (existeEntidad(newName)) {
						newName = newName + "DTO";
					}
					// se crea el nuevo tipo de clase
					CtClass newClass = pool.makeClass(newName);
					newClass.defrost();
					newClass.setName(newName);

					fields[i].setType(newClass);
					fields[i].setName("list" + newName1 + "DTO");

				} else {
					// se verifica que la entidad exista
					if (existeEntidad(fields[i].getName().substring(0, 1)
							.toUpperCase()
							+ fields[i].getName().substring(1,
									fields[i].getName().length()))) {
						String newName = fields[i].getName() + "DTO";

						CtClass newClass = pool.makeClass(newName);
						newClass.defrost();
						newClass.setName(newName);

						fields[i].setType(newClass);
						fields[i].setName(newName);
					}
					// si no existe se intenta agregar una 's' al final
					else if (existeEntidad(fields[i].getName().substring(0, 1)
							.toUpperCase()
							+ fields[i].getName().substring(1,
									fields[i].getName().length()) + "s")) {
						String newName = fields[i].getName() + "sDTO";

						CtClass newClass = pool.makeClass(newName);
						newClass.defrost();
						newClass.setName(newName);

						fields[i].setType(newClass);
						fields[i].setName(newName);
					} else {
						// si no se encuentra se deja como esta
						String newName = fields[i].getName() + "DTO";

						CtClass newClass = pool.makeClass(newName);
						newClass.defrost();
						newClass.setName(newName);

						fields[i].setType(newClass);
						fields[i].setName(newName);
					}

				}
			}
		}

		// Creacion de la clase y sus modificadores
		File myFile = new File(
				"D:\\circulemos2\\Proyecto\\WorkspaceMaven\\DocumentosDTO\\src\\main\\java\\co\\com\\datatools\\documentos\\plantillas\\"
						+ cc.getSimpleName() + "DTO.java");
		// File myFile = new File(getBasePath() +
		// packageToFolder(cc.getPackageName()) + "\\"
		// + cc.getSimpleName() + "DTO.java");
		// System.out.println("El path de ejecucion es " +
		// myFile.getPath());
		BufferedWriter out = new BufferedWriter(new FileWriter(myFile));

		// class package
		// you can change this package location by another
		out.write("package co.com.datatools.documentos.plantillas;\n\n");
		out.write("import co.com.datatools.documentos.entidades." + cc.getSimpleName()
				+ ";\n\n");
		// import section of class, including all elements with filter for
		// java.lang and annotations;
		@SuppressWarnings("unchecked")
		Collection<String> col = cc.getRefClasses();
		for (String clase : col) {
			if (!clase.startsWith("java.lang")
					&& !(cc.getName().contains(clase))
					&& !containsEntity(clase)
					&& !clase.toLowerCase().contains("dto")
					&& !clase.contains("javax") && !clase.contains("entidades")) {
				out.write("import " + clase + ";\n");
			}
		}
		out.write("\n\n");
		out.write("/** \n *\n * @author Generated\n * @version 2.0\n **/ \n");
		// Write the class declaration with interface implements
		out.write(getInfoModifiers(cc.getModifiers()).trim() + " class "
				+ removeExtension(myFile.getName()).trim() + " implements ");
		for (int i = 0; i < (cc.getInterfaces().length) - 1; i++) {
			out.write(cc.getInterfaces()[i].getSimpleName() + ",");
		}
		out.write(cc.getInterfaces()[(cc.getInterfaces().length) - 1]
				.getSimpleName());

		out.write("{");

		out.write("\n\n\t// Attributes Declaration \n\n");
		// class body with attributes
		for (int i = 0; i < fields.length; i++) {

			int modifiers = fields[i].getModifiers();
			String simpleName = fields[i].getType().getSimpleName();
			String name = fields[i].getName();
			// se pregunta si el tipo de dato que se ingresa es de tipo lista
			if (name.toLowerCase().contains("list")) {
				// si es tipo lista
				name = name.substring(0, name.length() - 3) + "DTO";
				out.write("\t" + getInfoModifiers(modifiers).trim() + " List<"
						+ simpleName + "> " + name);
			} else {
				// no es de tipo lista
				if (isNotTypePrimitiveOrAncestor(simpleName)) {
					simpleName = simpleName.substring(0, 1).toUpperCase()
							+ simpleName.substring(1, simpleName.length());
				}
				out.write("\t" + getInfoModifiers(modifiers).trim() + " "
						+ simpleName + " " + name);
			}
			// case of modifiers static final
			if (fields[i].getModifiers() == 25
					|| fields[i].getModifiers() == 26) {
				out.write("=" + fields[i].getConstantValue().toString()
						+ "L;\n");
			} else {
				out.write(";\n");
			}
		}
		out.write("\n\t// Constructors Declaration \n");
		/*
		 * CONSTRUCTOR 0 - constructor basico de la clase
		 */
		out.write("\n\t");
		out.write(getInfoModifiers(cc.getModifiers()).trim() + " "
				+ removeExtension(myFile.getName()).trim() + "(){\n");
		out.write("\n\t}\n\n");
		/*
		 * CONSTRUCTOR 1 - primero se genera la cabecera del constructor y
		 * seguidamente se generan los atributos como parametros de la clase
		 */
		out.write("\t");
		out.write(getInfoModifiers(cc.getModifiers()).trim() + " "
				+ removeExtension(myFile.getName()).trim() + "(");
		String nomEntidad = cc.getSimpleName().substring(0, 1).toLowerCase()
				+ cc.getSimpleName().substring(1, cc.getSimpleName().length());
		out.write(cc.getSimpleName() + " " + nomEntidad);
		out.write("){\n");
		int contador = 0;
		for (int i = 0; i < fields.length; i++) {
			// se extrae el nombre del atributo
			String name = fields[i].getName();
			String simpleName = fields[i].getType().getSimpleName();
			// se filtra el atributo de serial de la clase
			if (!name.toLowerCase().contains("serialversionuid")) {
				if (!isNotTypePrimitiveOrAncestor(simpleName)) {
					out.write("\t\t");
					out.write("this." + name + " = " + nomEntidad + "."
							+ methods[contador].getName() + "();\n");
					contador = contador + 2;
				}
			}
		}
		out.write("\n\t}\n");

		out.write("\n\n\t// Start Methods Declaration \n\n");

		for (int i = 0; i < methods.length; i++) {

			if (!methods[i].getName().toLowerCase().contains("add")
					&& !methods[i].getName().toLowerCase().contains("remove")) {

				int modifiers = methods[i].getModifiers();
				String simpleName = "";
				String name = "";

				if (isNotTypePrimitiveOrAncestor(methods[i].getReturnType()
						.getSimpleName())) {
					if (methods[i].getReturnType().getSimpleName()
							.toLowerCase().contains("list")) {

						simpleName = methods[i].getReturnType().getSimpleName();
						name = methods[i].getName() + "DTO";

						if (existeEntidad(name.substring(3, name.length() - 5))) {
							simpleName = simpleName + "<"
									+ name.substring(3, name.length() - 5)
									+ "DTO>";
						} else if (existeEntidad(name.substring(3,
								name.length() - 4))) {
							simpleName = simpleName + "<"
									+ name.substring(3, name.length() - 4)
									+ "DTO>";
						} else if (existeEntidad(name.substring(3,
								name.length() - 3))) {
							simpleName = simpleName + "<"
									+ name.substring(3, name.length() - 3)
									+ "DTO>";
						}

						name = name.substring(0, 3) + "List"
								+ name.substring(3, name.length());

					} else {
						simpleName = methods[i].getReturnType().getSimpleName()
								+ "DTO";
						if (existeEntidad(methods[i].getName().substring(3, 4)
								.toUpperCase()
								+ methods[i].getName().substring(4,
										methods[i].getName().length()))) {
							name = methods[i].getName() + "DTO";
						} else if (existeEntidad(methods[i].getName()
								.substring(3, 4).toUpperCase()
								+ methods[i].getName().substring(4,
										methods[i].getName().length()) + "s")) {
							name = methods[i].getName() + "sDTO";
						} else {
							name = methods[i].getName() + "DTO";
						}
					}
				} else {
					simpleName = methods[i].getReturnType().getSimpleName();
					name = methods[i].getName();
				}

				if (!simpleName.equals("void")) {
					out.write("\t");
					out.write(getInfoModifiers(modifiers).trim() + " "
							+ simpleName + " " + name);
					out.write("() {\n");
					out.write("\t\t");
					out.write("return " + getReturnParameterUpper(name) + ";");
				} else {

					CtClass[] parametros = methods[i].getParameterTypes();

					for (int j = 0; j < parametros.length; j++) {
						String simpleNameParameter = parametros[j]
								.getSimpleName();
						if (isNotTypePrimitiveOrAncestor(simpleNameParameter)) {
							if (simpleNameParameter.toLowerCase().contains(
									"list")) {

								if (existeEntidad(name.substring(3,
										name.length() - 2))) {
									simpleNameParameter = simpleNameParameter
											+ "<"
											+ name.substring(3,
													name.length() - 2) + "DTO>";
								} else if (existeEntidad(name.substring(3,
										name.length() - 1))) {
									simpleNameParameter = simpleNameParameter
											+ "<"
											+ name.substring(3,
													name.length() - 1) + "DTO>";
								} else if (existeEntidad(name)) {
									simpleNameParameter = simpleNameParameter
											+ "<" + name + "DTO>";
								}

								name = name.substring(0, 3) + "List"
										+ name.substring(3, name.length())
										+ "DTO";

							} else {
								simpleNameParameter = simpleNameParameter
										+ "DTO";
								if (existeEntidad(methods[i].getName()
										.substring(3, 4).toUpperCase()
										+ methods[i].getName().substring(4,
												methods[i].getName().length()))) {
									name = methods[i].getName() + "DTO";
								} else if (existeEntidad(methods[i].getName()
										.substring(3, 4).toUpperCase()
										+ methods[i].getName().substring(4,
												methods[i].getName().length())
										+ "s")) {
									name = methods[i].getName() + "sDTO";
								} else {
									name = methods[i].getName() + "DTO";
								}
							}
						}
						out.write("\t");
						out.write(getInfoModifiers(modifiers) + " "
								+ simpleName + " " + name);
						out.write("(");
						out.write(simpleNameParameter + " "
								+ getReturnParameterUpper(name));
					}
					out.write(") {\n");
					out.write("\t\t");
					out.write("this." + getReturnParameterUpper(name) + "="
							+ getReturnParameterUpper(name) + ";");
				}
				out.write("\n\t}\n\n");
			}
		}
		out.write("\n\n// Finish the class\n }");
		out.close();
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
	 * This method is only for test of package public boolean
	 * equalsPackage(String clase, String paquete) { if
	 * (clase.contains(paquete)) {
	 * System.out.println("Es una clase igual de paquete que el base"); return
	 * true; } return false; }
	 */
}
