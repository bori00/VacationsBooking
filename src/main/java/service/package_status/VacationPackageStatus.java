package service.package_status;

import model.VacationPackage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public enum VacationPackageStatus {
    NOT_BOOKED {
        public boolean hasStatus(VacationPackage vacationPackage) {
            return vacationPackage.getBookings().isEmpty();
        }

        public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
            return cb.equal(cb.size(vacationPackageRoot.get("bookings")), 0);
        }
    },
    IN_PROGRESS{
        public boolean hasStatus(VacationPackage vacationPackage) {
            return !vacationPackage.getBookings().isEmpty() &&
                    vacationPackage.getBookings().size() < vacationPackage.getNrPlaces();
        }

        public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
            return cb.not(cb.and(NOT_BOOKED.getPredicate(cb, vacationPackageRoot),
                    BOOKED.getPredicate(cb, vacationPackageRoot)));
        }
    },
    BOOKED {
        public boolean hasStatus(VacationPackage vacationPackage) {
            return vacationPackage.getBookings().size() == vacationPackage.getNrPlaces();
        }

        public Predicate getPredicate(CriteriaBuilder cb, Root<VacationPackage> vacationPackageRoot) {
            return cb.equal(cb.size(vacationPackageRoot.get("bookings")),
                    vacationPackageRoot.get("nrPlaces"));
        }
    };


    public static VacationPackageStatus getVacationPackageStatus(VacationPackage vacationPackage) {
        for (VacationPackageStatus status : VacationPackageStatus.values()) {
            if (status.hasStatus(vacationPackage)) {
                return status;
            }
        }
        return NOT_BOOKED; // default status. Doesn't occur: the statuses are disjoint and their
        // union covers all cases.
    }

    public abstract boolean hasStatus(VacationPackage vacationPackage);

    public abstract Predicate getPredicate(CriteriaBuilder cb,
                                      Root<VacationPackage> vacationPackageRoot);

}
