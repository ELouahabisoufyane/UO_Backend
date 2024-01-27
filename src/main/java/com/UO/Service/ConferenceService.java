package com.UO.Service;


import com.UO.DOA.ConferenceRepository;
import com.UO.DOA.ParticipantRepository;
import com.UO.DOA.ParticipationRepesitory;
import com.UO.IService.IConferenceService;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Modele.Participation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor@Transactional
public class ConferenceService implements IConferenceService {



    final ConferenceRepository cr;
    @Autowired
    private final MessageSendingOperations<String> wsTemplate;

    final ParticipantRepository pr;



    final ParticipationRepesitory ptr;
    @Autowired
    private ParticipationService pS;

    @Override
    public Conference getConference(String titre) {
        return this.cr.findByTitre(titre);
    }

    @Override
    public List<Conference> getAllConferences() {
        return cr.findAll();
    }

    @Override
    public void supprimerConference(Long id) {
        ptr.deleteByConferenceId(id);
        this.cr.deleteById(id);

    }

    @Override
    public Conference ajouterConference(Conference c) {

        Conference savedConference = cr.save(c);

        // Ajouter tous les participants à la conférence avec l'état 'absent'
        List<Participant> participants = pr.findAll();
        if(c.getParticipations()!=null){
        for (Participant participant : participants) {

                pS.addParticipation(participant, savedConference, false,false);
        }


        }
        return savedConference;

    }

    @Override
    public Page<Conference> getPageConferences(int page, int size) {
        return cr.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Conference> getConferencesByMotCle(String mc, int page, int size) {
        return cr.findAllByMotCle(mc, PageRequest.of(page, size));
    }

    @Override
    public Page<Conference> findPage(int pageNumber) {
        return cr.findAll(PageRequest.of(pageNumber,9));
    }

    @Override
    public Conference getOne(Long id) {
        return this.cr.findById(id).get();
    }


    public Page<Participant> getParticipantsOfConference(Long conferenceId, int page, int size ) {
        Page<Participant> participations = ptr.findByPageConferenceId(conferenceId,PageRequest.of(page, size));
        List<Participant> ps = new ArrayList<>();
        return participations;
    }
    public Conference updateConference(Conference c) {
        return  this.cr.save(c);
    }

    public void changeEtat(Long conferenceId, Long id,boolean how) {
        Participation p = ptr.findByParticipantIdAndConferenceId(id,conferenceId);

        if(p !=null){

            p.setEtat(true);
            p.setHow(how);
        }


    }

    public Page<Participant> getPresenteParticipantsOfConference(Long conferenceId,int page, int size) {

        Page<Participant> participations= ptr.findByConferenceId(conferenceId,PageRequest.of(page, size));

        return participations;
    }

    public void marquerPresence(String rfid,boolean how) {


        Participant pr =this.pr.findByRfid(rfid);
        Conference c= this.cr.findByDate(LocalDate.now());

        this.changeEtat(c.getId(),pr.getId(),how);


        wsTemplate.convertAndSend("/socket/presenceUpdate", "soufyane");
        System.out.println("Message sent to /topic/presenceUpdate");

    }


    public boolean verfierRfID(String id){
        List<Participant> prs=this.pr.findAll();

        boolean b=false;
        for(Participant pp :prs){
            if(pp.getRfidCardId()!=null){
            if(pp.getRfidCardId().equals(id)){
                b=true;
            }
        }}
        return b;

    }

    public void affecterID(String rfid,Participant p) {

        if(p.getRfidCardId()==null){

                if(this.verfierRfID(rfid)){
                    wsTemplate.convertAndSend("/socket/affected", "Le rfid est déjà affecté à un participant. Veuillez choisir une autre carte et réessayer l'opération. ");
                    System.out.println("Message sent to /topic/affected");
                }
                else{
                    p.setRfidCardId(rfid);
                    System.out.println(p.toString());
                    this.pr.save(p);
                    wsTemplate.convertAndSend("/socket/affected", "bien affecter  😎 ");

                }

        }
        else  if(p.getRfidCardId().isEmpty()) {
            if(this.verfierRfID(rfid)){
                wsTemplate.convertAndSend("/socket/affected", "Le rfid est déjà affecté à un participant. Veuillez choisir une autre carte et réessayer l'opération. ");
                System.out.println("Message sent to /topic/affected");
            }
            else{
                p.setRfidCardId(rfid);
                System.out.println(p.toString());
                this.pr.save(p);
                wsTemplate.convertAndSend("/socket/affected", "bien affecter  😎 ");

            }
        }
        else {
            wsTemplate.convertAndSend("/socket/affected", "wrong");
        }




    }
}
