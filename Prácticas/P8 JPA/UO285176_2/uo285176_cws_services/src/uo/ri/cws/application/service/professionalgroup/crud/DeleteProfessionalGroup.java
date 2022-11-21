/**
 * 
 */
package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class DeleteProfessionalGroup implements Command<Void> {

    private String name = null;
    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();
    private ContractRepository crepo = Factory.repository.forContract();

    public DeleteProfessionalGroup(String name) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> p = repo.findByName(name);
	BusinessChecks.isTrue(p.isPresent(), "Must exist");
	List<Contract> c = crepo.findByProfessionalGroupId(p.get().getId());
	BusinessChecks.isTrue(c.isEmpty(), "Still have contracts");
	repo.remove(p.get());
	return null;
    }

}
