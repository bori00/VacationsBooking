package service.service_impl;

import model.Booking;
import model.User;
import model.VacationPackage;
import repository.BookingRepository;
import repository.RelationalDBImpl.BookingRepositoryImpl;
import repository.RelationalDBImpl.UserRepositoryImpl;
import repository.RelationalDBImpl.VacationPackageRepositoryImpl;
import repository.UserRepository;
import repository.VacationPackageRepository;
import service.IBookingService;
import service.IOperationStatus;
import service.package_status.VacationPackageStatus;
import service.view_models.VacationPackageUserViewModel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService extends AbstractService<Booking> implements IBookingService {
    private static final BookingService instance = new BookingService();

    public static final String CANT_BOOK_FULLY_BOOKED_PACKAGE = "We're sorry, this package is " +
            "already fully booked.";
    public static final String INEXISTENT_VACATION_PACKAGE = "We're sorry, this package can't be " +
            "booked. Please contact an administrator!";

    private BookingService() {}

    public static BookingService getInstance() {
        return instance;
    }

    public IOperationStatus add(Long vacationPackageId) {
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
                support.firePropertyChange(Events.EDITED_ENTITY.toString(), null,
                        new VacationPackageUserViewModel(optVacationPackage.get()));
                //adding a new booking may change the data of other booked vacations as well
                VacationPackageService.getInstance().fireNewBookingEvent();
                return OperationStatus.getSuccessfulOperationStatus();
            } else {
                return OperationStatus.getFailedOperationStatus(CANT_BOOK_FULLY_BOOKED_PACKAGE);
            }
        } else {
            return OperationStatus.getFailedOperationStatus(INEXISTENT_VACATION_PACKAGE);
        }
    }

    public List<VacationPackageUserViewModel> getLoggedInUsersBookedVacations() {
        User currentUser = ActiveUserStatus.getInstance().getLoggedInUser();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepositoryImpl(entityManager);
        Optional<User> optUser = userRepository.findById(currentUser.getId());

        List<VacationPackageUserViewModel> result = optUser
                .get()
                .getBookings()
                .stream()
                .map(Booking::getVacationPackage)
                .map(VacationPackageUserViewModel::new)
                .collect(Collectors.toList());
        entityManager.close();

        return result;

    }
}
