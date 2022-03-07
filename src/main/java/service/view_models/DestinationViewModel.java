package service.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.Destination;

@AllArgsConstructor
@Getter
public class DestinationViewModel {
    private final Long id;
    private final String name;

    public DestinationViewModel(Destination destination) {
        this(destination.getId(), destination.getName());
    }
}
