package repository;

import model.Destination;

import java.util.Optional;

public interface DestinationRepository extends IRepository<Destination> {
    Optional<Destination> findByName(String name);
}
