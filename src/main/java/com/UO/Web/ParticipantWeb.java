package com.UO.Web;

import com.UO.Modele.Participant;
import com.UO.Service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participant")
@CrossOrigin(origins = "*")
public class ParticipantWeb {
    //ps:le service de participant
    @Autowired
    private ParticipantService ps;

    @GetMapping("/getOne/{nom}")
    public Participant getPrticipant(@PathVariable("nom") String nom){
        return this.ps.getParticipant(nom);
    }

    @GetMapping("/getAll")
    public List<Participant> getAllPrticipant(){

        return this.ps.getAllParticipants();
    }
    @PostMapping("/addOne")
    public Participant saveParticipant(@RequestBody Participant p){
        return this.ps.ajouterParticipant(p);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteParticipant(@PathVariable("id") Long id){
        this.ps.supprimerParticipant(id);
    }
    @GetMapping("/chercher")
    public Page<Participant> chercherClient(@RequestParam(name = "mc",defaultValue = "i") String mc,
                                       @RequestParam(name = "page",defaultValue = "0") int page,
                                       @RequestParam(name = "size",defaultValue = "4") int size) {
        Page<Participant> p=ps.getParticipantsByMotCle(mc,page,size) ;

        return p;
    }
    @GetMapping("/list/{p}")
    public  Page<Participant> showPage(@PathVariable("p") int currentPage){
        Page<Participant> page = ps.findPage(currentPage);
        return page;
    }




}
