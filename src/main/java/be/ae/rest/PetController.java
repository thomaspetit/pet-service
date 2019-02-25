package be.ae.rest;

import be.ae.rest.model.CreatePetCommand;
import be.ae.rest.model.PetResource;
import be.ae.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/pets")
@ExposesResourceFor(PetResource.class)
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetResource>> list() {
        final List<PetResource> pets = petService.getPets();
        return ResponseEntity.ok(pets);
    }

    @RequestMapping(value = "/{id}")
    public ResponseEntity<PetResource> get(@PathVariable String id) {
        final PetResource petResource = petService.get(id);
        return ResponseEntity.ok(petResource);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreatePetCommand createPetCommand) throws URISyntaxException {
        final String id = petService.create(createPetCommand);
        return ResponseEntity.created(entityLinks.linkForSingleResource(PetResource.class, id).toUri()).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}