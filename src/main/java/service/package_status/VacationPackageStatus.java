package service.package_status;

import model.VacationPackage;

import java.util.function.Predicate;

public enum VacationPackageStatus {
    NOT_BOOKED {
        public boolean hasStatus(VacationPackage vacationPackage) {
            return vacationPackage.getBookings().isEmpty();
        }
    },
    IN_PROGRESS{
        public boolean hasStatus(VacationPackage vacationPackage) {
            return !vacationPackage.getBookings().isEmpty() &&
                    vacationPackage.getBookings().size() < vacationPackage.getNrPlaces();
        }
    },
    BOOKED {
        public boolean hasStatus(VacationPackage vacationPackage) {
            return vacationPackage.getBookings().size() == vacationPackage.getNrPlaces();
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

    abstract boolean hasStatus(VacationPackage vacationPackage);

}
