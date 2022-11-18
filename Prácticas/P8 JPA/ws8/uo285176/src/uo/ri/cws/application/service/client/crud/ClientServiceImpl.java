/**
 * 
 */
package uo.ri.cws.application.service.client.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.util.exception.NotYetImplementedException;

/**
 * @author UO285176
 *
 */
public class ClientServiceImpl implements ClientCrudService {

    @Override
    public ClientDto addClient(ClientDto client) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void deleteClient(String idClient) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void updateClient(ClientDto client) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public List<ClientDto> findAllClients() throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public Optional<ClientDto> findClientById(String idClient)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

}
