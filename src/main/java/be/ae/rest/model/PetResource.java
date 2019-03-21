package be.ae.rest.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class PetResource extends ResourceSupport {

    private String name;
}