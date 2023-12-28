package com.UO.Service;

import com.UO.DOA.ConferenceRepository;
import com.UO.DOA.ParticipantRepository;
import com.UO.DOA.ParticipationRepesitory;
import com.UO.IService.IParticipantService;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Modele.Participation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service@AllArgsConstructor@Transactional
public class ParticipantService implements IParticipantService {

    final ConferenceRepository cr;

    final ParticipantRepository pr;
    final ParticipationRepesitory ptrr;


    final ParticipationService ptr;



    @Override
    public Participant getParticipant(String nom) {
        return this.pr.findByName(nom) ;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return this.pr.findAll();
    }

    @Override
    public void supprimerParticipant(Long id) {

       ptrr.deleteByParticipantId(id);


        this.pr.deleteById(id);
    }

    @Override
    public Participant ajouterParticipant(Participant p) {
        Participant savedParticipant = pr.save(p);
        List<Conference> allConferences = cr.findAll();

        // Ajouter le participant à chaque conférence
        for (Conference conference : allConferences) {

                ptr.addParticipation(p,conference,false);

        }

        return this.pr.save(p);
    }

    @Override
    public Page<Participant> getPageParticipants(int page, int size) {
        return  pr.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Participant> getParticipantsByMotCle(String mc, int page, int size) {
        return pr.findAllByMotCle(mc, PageRequest.of(page, size));
    }

    @Override
    public Page<Participant> findPage(int pageNumber) {
        return pr.findAll(PageRequest.of(pageNumber,5));
    }
}
