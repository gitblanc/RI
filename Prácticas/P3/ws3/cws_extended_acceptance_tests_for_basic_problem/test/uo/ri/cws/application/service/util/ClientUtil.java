package uo.ri.cws.application.service.util;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import uo.ri.cws.application.business.client.ClientService.Client_BLDto;
import uo.ri.cws.application.service.util.sql.AddClientSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindClientByDNISqlUnitOfWork;

public class ClientUtil {

	private Client_BLDto dto = createDefaultClient();

	public ClientUtil randomDni() {
		dto.dni = RandomStringUtils.randomAlphanumeric(9);
		return this;
	}

	public ClientUtil register() {
		new AddClientSqlUnitOfWork(dto).execute();
		return this;
	}

	public Client_BLDto get() {
		return dto;
	}

	private Client_BLDto createDefaultClient() {
		Client_BLDto res = new Client_BLDto();
		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.dni = RandomStringUtils.randomAlphanumeric(9);
		res.name = RandomStringUtils.randomAlphabetic(4) + " name";
		res.surname = RandomStringUtils.randomAlphabetic(6) + " surname";
		res.email = RandomStringUtils.randomAlphabetic(6) + " email";
		res.phone = RandomStringUtils.randomAlphabetic(6) + " phone";		
		res.addressCity = RandomStringUtils.randomAlphabetic(6) + " city";
		res.addressStreet = RandomStringUtils.randomAlphabetic(6) + " street";
		res.addressZipcode = RandomStringUtils.randomAlphabetic(6) + " ZIP CODE";
		return res;
	}

	public ClientUtil withName(String string) {
		dto.name = string;
		return this;
	}

	public ClientUtil withSurname(String string) {
		dto.surname = string;
		return this;
	}

	public ClientUtil withDni(String string) {
		dto.dni = string;
		return this;

	}
	
	public Client_BLDto findByDni(String dni) {
		FindClientByDNISqlUnitOfWork finder = new FindClientByDNISqlUnitOfWork(dni);
		finder.execute();
		return finder.get().get();
	}

	public ClientUtil registerIfNew() {
		FindClientByDNISqlUnitOfWork finder = new FindClientByDNISqlUnitOfWork(dto.dni);
		finder.execute();
		
		Optional<Client_BLDto> op = finder.get(); 
		if ( op.isEmpty() ) {
			register();
		}
		else {
			this.dto = op.get();
		}
		return this;
	}


}
