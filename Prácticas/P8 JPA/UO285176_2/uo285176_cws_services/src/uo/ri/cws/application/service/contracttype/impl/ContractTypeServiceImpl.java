/**
 * 
 */
package uo.ri.cws.application.service.contracttype.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.util.exception.NotYetImplementedException;

/**
 * @author UO285176
 *
 */
public class ContractTypeServiceImpl implements ContractTypeService {

    @Override
    public ContractTypeDto addContractType(ContractTypeDto dto)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void deleteContractType(String name) throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public void updateContractType(ContractTypeDto dto)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public Optional<ContractTypeDto> findContractTypeByName(String name)
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

    @Override
    public List<ContractTypeDto> findAllContractTypes()
	    throws BusinessException {
	throw new NotYetImplementedException("no me toca");
    }

}
