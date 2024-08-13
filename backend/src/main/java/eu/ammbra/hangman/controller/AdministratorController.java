package eu.ammbra.hangman.controller;

import eu.ammbra.hangman.dto.Activity;
import eu.ammbra.hangman.facade.AdministratorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdministratorController {

    private AdministratorFacade adminFacade;

    @Autowired
    public AdministratorController(AdministratorFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public List<Activity> manage() {
        return adminFacade.retrieveHistory();
    }

}
