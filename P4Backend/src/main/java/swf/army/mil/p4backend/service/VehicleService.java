package swf.army.mil.p4backend.service;

import org.springframework.stereotype.Service;
import swf.army.mil.p4backend.Entity.Vehicle;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    public List<Vehicle> getAll() {
        return null;
    }

    public Vehicle getById(long l) {
        return null;
    }

    public Boolean deleteById(long l) {
        return true;
    }

    public Vehicle create(Vehicle vehicle) {
        return null;
    }

    public Optional<Vehicle> update(Vehicle vehicle, Long id) {
        return Optional.empty();
    }
}
