package com.UO.IService;

import com.UO.Modele.Participant;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IParticipantService {

    Participant getParticipant(String nom);
    List<Participant> getAllParticipants();
    void supprimerParticipant(Long id);
    Participant ajouterParticipant(Participant p);
    public Page<Participant> getPageParticipants(int page, int size) ;
    public Page<Participant> getParticipantsByMotCle(String mc, int page, int size) ;
    public Page<Participant> findPage(int pageNumber);

}
