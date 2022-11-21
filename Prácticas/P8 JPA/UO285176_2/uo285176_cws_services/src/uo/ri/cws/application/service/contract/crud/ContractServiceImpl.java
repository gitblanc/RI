/**
 * 
 */
package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.util.exception.NotYetImplementedException;

/**
 * @author UO285176
 *
 */
public class ContractServiceImpl implements ContractService {

    @Override
    public ContractDto addContract(ContractDto c) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void updateContract(ContractDto dto) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void deleteContract(String id) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void terminateContract(String contractId) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public Optional<ContractDto> findContractById(String id)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public List<ContractSummaryDto> findContractsByMechanic(String mechanicDni)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public List<ContractSummaryDto> findAllContracts()
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

}
