package uo.ri.cws.application.persistence.mechanic;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;

public interface MechanicGateway extends Gateway<MechanicDALDto> {

	/*
	 * Finds a row in the table
	 * 
	 * @param record's field
	 * 
	 * @return dto from that record, probably null
	 */
	Optional<MechanicDALDto> findByDni(String dni);

	/**
	 * Finds a row in the table
	 * @return dto from that record, probably null
	 */
	Optional<MechanicDALDto> findById(String id);

	/**
	 * Finds a row in the table
	 * @param id
	 * @return List<dto> from that record, probably null
	 */
	List<MechanicDALDto> findAllMechanicWorkOrders(String id);

	public class MechanicDALDto {

		public String id;
		public Long version;

		public String dni;
		public String name;
		public String surname;

	}
}
