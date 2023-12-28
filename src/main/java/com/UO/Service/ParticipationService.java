package com.UO.Service;

import com.UO.DOA.ConferenceRepository;
import com.UO.DOA.ParticipantRepository;
import com.UO.DOA.ParticipationRepesitory;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Modele.Participation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepesitory participationRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Transactional
    public Participation addParticipation(Participant p,Conference c, boolean etat) {


        Participation participation = new Participation();
        participation.setParticipant(p);
        participation.setConference(c);
        participation.setEtat(etat);

        return participationRepository.save(participation);
    }
}
