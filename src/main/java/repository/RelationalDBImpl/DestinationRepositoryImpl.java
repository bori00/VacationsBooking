package repository.RelationalDBImpl;

import model.Booking;
import model.Destination;
import repository.BookingRepository;
import repository.DestinationRepository;

import javax.persistence.EntityManager;

public class DestinationRepositoryImpl extends RepositoryImpl<Destination> implements DestinationRepository {
    public DestinationRepositoryImpl(EntityManager entityManager) {
        super(Destination.class, entityManager);
    }
}
