package co.com.datatools.documentos.negocio.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jboss.logging.Logger;


/**
 * Clase que permite la conexion a base de datos
 * de negocio para sincronizar datos
 * @author dixon.alvarez
 *
 */
public class Conexion {

	private Connection conexion;
	private Properties props;
	
	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Conexion.class.getName());

	public Connection getConexion() {
		if (conexion == null){
			try {
				props = new Properties();
//				props.load(new FileInputStream(ConstantesGeneral.PATH_DOCUMENTOS + "/configuracion/conexion.properties"));
				Context initialContext = new InitialContext();
				DataSource datasource = (DataSource)initialContext.lookup(props.getProperty("JNDI"));
				conexion = datasource.getConnection();
			} catch (NamingException ne) {
				logger.error(ne);
			} catch (SQLException se) {
				logger.error(se);
//			} catch (FileNotFoundException fe) {
//				logger.error(fe);
//			} catch (IOException ie) {
//				logger.error(ie);
			}
		}
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	
	public void cerrarConexion(Connection connection){
		if (connection != null){
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

