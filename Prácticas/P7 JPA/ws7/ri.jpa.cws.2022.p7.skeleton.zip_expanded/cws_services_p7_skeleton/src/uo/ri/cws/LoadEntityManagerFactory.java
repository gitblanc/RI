package uo.ri.cws;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Fuerza a que se cargen los parámetros de configuración, se analizen todos los
 * mapeos y, si procede, se crea la BDD
 */
public class LoadEntityManagerFactory {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("carworkshop");
		/**
		 * CONDICIONES NECESARIAS de las clases que están en el mapeador
		 * 
		 * Ha de tener la marca @Entity 
		 * Ha de contener [@Transient] (el atributo no
		 * tiene que tener equivalencia en la tabla)
		 * Añadir @Embeddable a un atributo natural de otra clase
		 * Añadir @Id a la clase anterior (identidad artificial, clave primaria)
		 * La clase ha de tener un contructor sin parámetros
		 * 
		 * 
		 * EXTRAS
		 * @Version es un campo de versionado, por lo que cada vez que insertemos una fila, 
		 * empieza en 0 y con un update se sumará 1
		 * @Column da información al motor de la base de datos (hay que especificarle la longitud)
		 * @Basic
		 * 
		 * 
		 * RELACIONES
		 * @OneToMany se pone en la tabla de 1
		 * @ManyToOne se pone en la tabla de 0...*
		 * 
		 * Las combinaciones se especifican en @Table
		 * E.g:
		 * @Table(name = "tworkorders", uniqueConstraints = @UniqueConstraint(columnNames = { "date", "vehicle_id" }))
		 * 
		 * CLASES ASOCIATIVAS
		 * Las solucionaremos con 2 asociaciones @OneToMany
		 * E.g: intervention - substitution - sparepart
		 * 
		 * HERENCIA
		 * @Inheritance(strategy = InheritanceType.JOINED)
		 * 
		 */

		emf.createEntityManager().close();
		emf.close();

		System.out.println("--> Si no hay excepciones todo va bien");
		System.out.println("\n\t (O no hay ninguna clase mapeada)");
	}

}
