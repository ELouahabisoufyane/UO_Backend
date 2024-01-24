package com.UO.Web;


import com.UO.Modele.Participant;
import com.UO.Service.ConferenceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/conference")
@CrossOrigin(origins = "*")
public class MeetingController {

    private Participant dernierParticipant;
    final ConferenceService cs;

    public MeetingController(ConferenceService cs) {
        this.cs = cs;
    }

    @MessageMapping("/socket/someoneJoined")
    @SendTo("/socket/someoneJoined")
    public Participant someoneJoined(Participant message) {

        this.dernierParticipant=message;
        System.out.println("messge"+this.dernierParticipant);
        return message;
    }

    @PostMapping("/affecterID")
    public void affecterID(@RequestBody Map<String, String> payload){

        String rfid = payload.get("rfid");
        System.out.println("messge"+this.dernierParticipant.getNom()+" et "+rfid);

        this.cs.affecterID(rfid,this.dernierParticipant);


    }
}
