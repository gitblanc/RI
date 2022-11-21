/**
 * 
 */
package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInForceInContractType
	implements Command<List<MechanicDto>> {

    String contractType = null;
    private MechanicRepository repo = Factory.repository.forMechanic();
    private ContractTypeRepository ctrepo = Factory.repository
	    .forContractType();

    public FindMechanicsInForceInContractType(String type) {
	ArgumentChecks.isNotNull(type, "Type can't be null");
	ArgumentChecks.isNotEmpty(type, "Type can't be empty");
	ArgumentChecks.isNotBlank(type, "Type can't be blank");
	this.contractType = type;
    }

    @Override
    public List<MechanicDto> execute() throws BusinessException {
	Optional<ContractType> type = ctrepo.findByName(contractType);
	if (type.isPresent()) {
	    List<Mechanic> mechanics = repo
		    .findInForceInContractType(type.get());
	    return DtoAssembler.toMechanicDtoList(mechanics);
	}
	return new ArrayList<>();
    }

}
