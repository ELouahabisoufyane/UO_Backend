package com.UO.Web;

import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/conference")
@CrossOrigin(origins = "*")
public class ConferenceWeb {
    @Autowired
    private ConferenceService cs;

    @PostMapping("/addOne")
    public Conference saveConference(@RequestBody Conference c){
        return this.cs.ajouterConference(c);
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
    public List<Participant> getParticipantsOfConference(@PathVariable Long conferenceId) {
        return cs.getParticipantsOfConference(conferenceId);
    }

    @PostMapping("/changeEtat/{conferenceId}")
    public void changeEtat(@PathVariable("conferenceId") Long conferenceId,@RequestBody Long id){

       this.cs.changeEtat(conferenceId,id);
    }

    @GetMapping("/{conferenceId}/presenteParticipants")
    public List<Participant> getPresenteParticipantsOfConference(@PathVariable Long conferenceId) {
        return cs.getPresenteParticipantsOfConference(conferenceId);
    }

    @PostMapping("/marquerPresence")
    public void marquerPresence(@RequestBody Map<String, String> payload){

        String rfid = payload.get("rfid");


        this.cs.marquerPresence(rfid);

    }



}
