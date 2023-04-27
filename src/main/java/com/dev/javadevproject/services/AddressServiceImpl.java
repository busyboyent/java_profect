package com.dev.javadevproject.services;

import com.dev.javadevproject.entities.AddressEntity;
import com.dev.javadevproject.repositories.AddressRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private AddressRepo addressRepo;

    public AddressEntity loadAddressByPostalCode(Integer postalcode) {
        return addressRepo.findByPostalCode(postalcode);
    }

    public void create(String country, String city, String locationAddress, Integer postalCode){
        AddressEntity address = new AddressEntity();
        address.country = country;
        address.city = city;
        address.locationAddress = locationAddress;
        address.postalCode = postalCode;
        addressRepo.save(address);
    }

    private String addressToStr(AddressEntity address){
        var answer = new StringBuilder();
        answer.append(address.country);
        answer.append(" ; ");
        answer.append(address.city);
        answer.append(" ; ");
        answer.append(address.locationAddress);
        answer.append(" ; ");
        answer.append(address.postalCode);
        return answer.toString();
    }

    public Iterable<AddressEntity> returnAddresses(){
        return addressRepo.findAll();
    }

    public List returnStrAddresses(){
        List answer = new ArrayList();
        Iterable<AddressEntity> addresses =  addressRepo.findAll();
        for(AddressEntity temp: addresses)
            answer.add(this.addressToStr(temp));
        return answer;
    }

    public List returnAddressesByCity(String city){
        List answer = new ArrayList();
        Iterable<AddressEntity> addresses =  addressRepo.findAll();
        for(AddressEntity temp: addresses) {
            if(temp.city.equals(city)) {
                answer.add(temp);
            }
        }
        return answer;
    }

    public List returnAddressesByCountry(String country){
        List answer = new ArrayList();
        Iterable<AddressEntity> addressList = addressRepo.findAll();
        for(AddressEntity temp: addressList) {
            if(temp.country.equals(country)) {
                answer.add(temp);
            }
        }
        return answer;
    }
}
