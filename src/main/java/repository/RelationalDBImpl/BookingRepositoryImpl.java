package repository.RelationalDBImpl;

import model.Booking;
import repository.BookingRepository;

import javax.persistence.EntityManager;

public class BookingRepositoryImpl extends RepositoryImpl<Booking> implements BookingRepository {
    public BookingRepositoryImpl(EntityManager entityManager) {
        super(Booking.class, entityManager);
    }
}
