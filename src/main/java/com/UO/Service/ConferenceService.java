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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor@Transactional
public class ConferenceService implements IConferenceService {

    final ConferenceRepository cr;

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

                pS.addParticipation(participant, savedConference, false);
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

    public List<Participant> getParticipantsOfConference(Long conferenceId) {

        Set<Participation> participations = ptr.findByConferenceId(conferenceId);
        List<Participant> ps =new ArrayList<>();
        for(Participation p:participations){
            if(!p.isEtat())
                ps.add(p.getParticipant());
        }

        return ps;
    }

    public Conference updateConference(Conference c) {
        return  this.cr.save(c);
    }

    public void changeEtat(Long conferenceId, Long id) {
        Participation p = ptr.findByParticipantIdAndConferenceId(id,conferenceId);
        p.setEtat(true);

    }

    public List<Participant> getPresenteParticipantsOfConference(Long conferenceId) {

        Set<Participation> participations = ptr.findByConferenceId(conferenceId);
        List<Participant> ps =new ArrayList<>();
        for(Participation p:participations){
            if(p.isEtat())
                ps.add(p.getParticipant());
        }

        return ps;
    }

    public void marquerPresence(String rfid) {

        Participant pr =this.pr.findByRfid(rfid);
        Conference c= this.cr.findByTitre("Java");
        this.changeEtat(c.getId(),pr.getId());

    }
}
