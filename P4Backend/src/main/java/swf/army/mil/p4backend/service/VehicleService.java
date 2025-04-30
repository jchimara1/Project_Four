package swf.army.mil.p4backend.service;

import org.springframework.stereotype.Service;
import swf.army.mil.p4backend.Entity.Vehicle;
import swf.army.mil.p4backend.repository.VehicleRepository;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle getById(long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Boolean deleteById(long id) {
        boolean isPresent = vehicleRepository.existsById(id);
        vehicleRepository.deleteById(id);
        return isPresent;
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle, Long id) {
        var current = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No vehicle by that id"));
        vehicle.setId(id);
        vehicle.setMake(vehicle.getMake() != null ? vehicle.getMake() : current.getMake());
        vehicle.setModel(vehicle.getModel() != null ? vehicle.getModel() : current.getModel());
        vehicle.setYear(vehicle.getYear() != null ? vehicle.getYear() : current.getYear());
        vehicle.setPrice(vehicle.getPrice() != null ? vehicle.getPrice() : current.getPrice());
        vehicle.setUsed(vehicle.getUsed() != null ? vehicle.getUsed() : current.getUsed());
        return vehicleRepository.save(vehicle);
    }
}
