package service;

import model.Booking;
import model.Destination;
import model.User;
import model.VacationPackage;
import repository.BookingRepository;
import repository.DestinationRepository;
import repository.RelationalDBImpl.BookingRepositoryImpl;
import repository.RelationalDBImpl.DestinationRepositoryImpl;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.RelationalDBImpl.VacationPackageRepositoryImpl;
import repository.UserRepository;
import repository.VacationPackageRepository;
import service.package_status.VacationPackageStatus;
import service.view_models.DestinationViewModel;
import service.view_models.VacationPackageUserViewModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService extends AbstractService<Booking> {
    private static final BookingService instance = new BookingService();

    public static final String CANT_BOOK_FULLY_BOOKED_PACKAGE = "We're sorry, this package is " +
            "already fully booked.";
    public static final String INEXISTENT_VACATION_PACKAGE = "We're sorry, this package can't be " +
            "booked. Please contact an administrator!";

    private BookingService() {}

    public static BookingService getInstance() {
        return instance;
    }

    public OperationStatus add(Long vacationPackageId) {
        User currentUser = ActiveUserStatus.getInstance().getLoggedInUser();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        VacationPackageRepository vacationPackageRepository =
                new VacationPackageRepositoryImpl(entityManager);
        Optional<VacationPackage> optVacationPackage =
                vacationPackageRepository.findById(vacationPackageId);

        if (optVacationPackage.isPresent()) {
            if (!VacationPackageStatus.getVacationPackageStatus(optVacationPackage.get()).equals(VacationPackageStatus.BOOKED)) {
                Booking booking = new Booking(currentUser, optVacationPackage.get());
                entityManager.getTransaction().begin();
                BookingRepository bookingRepository = new BookingRepositoryImpl(entityManager);
                bookingRepository.save(booking);
                entityManager.getTransaction().commit();
                entityManager.close();
                support.firePropertyChange(Events.NEW_ENTITY.toString(), null,
                        new VacationPackageUserViewModel(optVacationPackage.get()));
                return OperationStatus.getSuccessfulOperationStatus();
            } else {
                return OperationStatus.getFailedOperationStatus(CANT_BOOK_FULLY_BOOKED_PACKAGE);
            }
        } else {
            return OperationStatus.getFailedOperationStatus(INEXISTENT_VACATION_PACKAGE);
        }
    }

    public List<VacationPackageUserViewModel> getLoggedInUsersBookedVacations(Long vacationPackageId) {
        // todo: save only the current user's id in the ActiveUserStatus!!!
        User currentUser = ActiveUserStatus.getInstance().getLoggedInUser();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepositoryImpl(entityManager);
        Optional<User> optUser = userRepository.findById(currentUser.getId());
        entityManager.close();

        return optUser
                .get()
                .getBookings()
                .stream()
                .map(Booking::getVacationPackage)
                .map(VacationPackageUserViewModel::new)
                .collect(Collectors.toList());

    }
}
