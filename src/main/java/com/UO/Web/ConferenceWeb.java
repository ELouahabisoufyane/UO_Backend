package com.UO.Web;

import com.UO.DOA.ConferenceRepository;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Modele.Statistique;
import com.UO.Service.ConferenceService;
import com.UO.Service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conference")
@CrossOrigin(origins = "*")
public class ConferenceWeb {
    @Autowired
    private ConferenceService cs;
    @Autowired
    private StatistiqueService ss;

    final ConferenceRepository cr;

    public ConferenceWeb(ConferenceRepository cr) {
        this.cr = cr;
    }

    @PostMapping("/addOne")
    public Conference saveConference(@RequestBody Conference c){
        return this.cs.ajouterConference(c);
    }
    @GetMapping("/getAll")
    public List<Conference> getAll(){

        return this.cr.findAll();

    }

    @PutMapping("/update")
    public Conference updateConference(@RequestBody Conference c){
        return this.cs.updateConference(c);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteConference(@PathVariable("id") Long id){
        this.cs.supprimerConference(id);
    }
    @GetMapping("/chercher")
    public Page<Conference> chercherClient(@RequestParam(name = "mc",defaultValue = "i") String mc,
                                            @RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name = "size",defaultValue = "4") int size) {
        Page<Conference> p=cs.getConferencesByMotCle(mc,page,size) ;

        return p;
    }
    @GetMapping("/list/{p}")
    public  Page<Conference> showPage(@PathVariable("p") int currentPage){
        Page<Conference> page = cs.findPage(currentPage);
        return page;
    }
    @GetMapping("/getOne/{id}")
    public  Conference getOne(@PathVariable("id") Long id){
        return this.cs.getOne(id);
    }

    @GetMapping("/{conferenceId}/participants")
    public Page<Participant> getParticipantsOfConference(@PathVariable Long conferenceId,
                                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                                         @RequestParam(name = "size",defaultValue = "4") int size) {
        return cs.getParticipantsOfConference(conferenceId,page,size);
    }

    @PostMapping("/changeEtat/{conferenceId}")
    public void changeEtat(@PathVariable("conferenceId") Long conferenceId,@RequestBody Long id){

       this.cs.changeEtat(conferenceId,id,true);
    }

    @GetMapping("/{conferenceId}/presenteParticipants")
    public Page<Participant> getPresenteParticipantsOfConference(@PathVariable Long conferenceId,
                                                                 @RequestParam(name = "page",defaultValue = "0") int page,
                                                                 @RequestParam(name = "size",defaultValue = "4") int size) {
        return cs.getPresenteParticipantsOfConference(conferenceId,page,size);
    }

    @PostMapping("/marquerPresence")
    public void marquerPresence(@RequestBody Map<String, String> payload){

        String rfid = payload.get("rfid");


        this.cs.marquerPresence(rfid,false);

    }
    @GetMapping("/getByDate/{date}")
    public  Conference getByDate(@PathVariable("date") LocalDate date) {
        return this.cr.findByDate(date);

    }
    @GetMapping("/getConferencePasse")
    public List<Conference> getConferencePasse(){
        return this.cr.findByDateBefore(LocalDate.now());

    }

    @GetMapping("/getStatistiques")
    public List<Statistique> getStatistique(){
       return this.ss.getStatistique();

    }



}
