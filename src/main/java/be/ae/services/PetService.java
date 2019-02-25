package be.ae.services;

import be.ae.rest.model.CreatePetCommand;
import be.ae.rest.model.PetResource;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class PetService {

    public List<PetResource> getPets() {
        return new ArrayList<>();
    }

    public PetResource get(String id) {
        return null;
    }

    public String create(CreatePetCommand command) {
        return "";
    }

    public void delete(String id) {
    }
}