package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.addresses.AddressRequest;
import com.dev.javadevproject.dto.addresses.CityRequest;
import com.dev.javadevproject.dto.addresses.CountryRequest;
import com.dev.javadevproject.dto.addresses.RemoveAddressRequest;
import com.dev.javadevproject.entities.AddressEntity;
import com.dev.javadevproject.entities.UserEntity;
import com.dev.javadevproject.repositories.AddressRepo;
import com.dev.javadevproject.repositories.UserRepo;
import com.dev.javadevproject.services.AddressServiceImpl;
import com.dev.javadevproject.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/addresses")
public class AddressResource {

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public List getAddresses() throws IOException
    {
        return addressService.returnStrAddresses();
    }

    @PostMapping(value = "/getAddressesByCountry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getAddressesByCountry(@RequestBody CountryRequest countryRequest) throws IOException{
        return addressService.returnAddressesByCountry(countryRequest.country);
    }

    @PostMapping(value = "/getAddressesByCity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getAddressesByCity(@RequestBody CityRequest cityRequest) throws IOException{
        return addressService.returnAddressesByCity(cityRequest.city);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAddress(@RequestBody AddressRequest request) throws IOException{
        HashMap<String, String> response = new HashMap<>();
        AddressEntity addressEntity = request.addressEntity;
        if(request.addressEntity == null || addressEntity.city == null || addressEntity.country == null ||
                addressEntity.locationAddress == null)
        {
            response.put("status", "error");
            response.put("message", "Not enough info");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        addressRepo.save(addressEntity);

        UserEntity user = userService.getCurrentUser();
        List<AddressEntity> addressees = user.getAddresses();
        AddressEntity updatedAddressEntity = addressRepo.save(request.addressEntity);
        addressees.add(updatedAddressEntity);
        user.setAddresss(addressees);
        userRepo.save(user);
        response.put("status","ok");
        response.put("message","address added");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeAddress(@RequestBody RemoveAddressRequest request) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        UserEntity user = userService.getCurrentUser();
        List<AddressEntity> addressees = user.getAddresses();
        addressees.removeIf(c -> c.getId() == request.addressId);
        user.setAddresss(addressees);
        userRepo.save(user);

        if(addressRepo.existsById(request.addressId))
            addressRepo.deleteById(request.addressId);
        response.put("status","ok");
        response.put("message","address removed");
        return ResponseEntity.ok(response);
    }
}

