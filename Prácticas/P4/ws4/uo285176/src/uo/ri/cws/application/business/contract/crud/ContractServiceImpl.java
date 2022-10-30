/**
 * 
 */
package uo.ri.cws.application.business.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * @author UO285176
 *
 */
public class ContractServiceImpl implements ContractService {

	CommandExecutor executor = new CommandExecutor();

	@Override
	public ContractBLDto add(ContractBLDto contract) throws BusinessException {
		return null;
	}

	@Override
	public void updateContract(ContractBLDto dto) throws BusinessException {

	}

	@Override
	public void deleteContract(String id) throws BusinessException {

	}

	@Override
	public void terminateContract(String contractId) throws BusinessException {

	}

	@Override
	public Optional<ContractBLDto> findContractById(String id) throws BusinessException {

		return null;
	}

	@Override
	public List<ContractSummaryBLDto> findContractsByMechanic(String mechanicDni) throws BusinessException {

		return executor.execute(new FindContractsByMechanicDni(mechanicDni));
	}

	@Override
	public List<ContractSummaryBLDto> findAllContracts() throws BusinessException {

		return null;
	}

}
