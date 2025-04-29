package swf.army.mil.p4backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swf.army.mil.p4backend.Entity.Vehicle;
import swf.army.mil.p4backend.service.VehicleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping(value = {"/vehicles", "/vehicles/"})
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping(value = {"/vehicle/{id}", "/vehicle/{id}/"})
    public Vehicle getAll(@PathVariable long id) {
        return vehicleService.getById(id);
    }

    @DeleteMapping(value = {"/vehicle/{id}", "/vehicle/{id}/"})
    public boolean delete(@PathVariable long id) {
        return vehicleService.deleteById(id);
    }

    @PostMapping(value = {"/vehicle", "/vehicle/"})
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }

    @PatchMapping(value = {"/vehicle/{id}", "/vehicle/{id}/"})
    public Optional<Vehicle> update(@RequestBody Vehicle vehicle, @PathVariable long id) {
        return vehicleService.update(vehicle, id);
    }
}
