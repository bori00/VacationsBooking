package repository.RelationalDBImpl;

import model.VacationPackage;
import repository.VacationPackageRepository;

import javax.persistence.EntityManager;

public class VacationPackageRepositoryImpl extends RepositoryImpl<VacationPackage> implements VacationPackageRepository {
    protected VacationPackageRepositoryImpl(EntityManager entityManager) {
        super(VacationPackage.class, entityManager);
    }
}
