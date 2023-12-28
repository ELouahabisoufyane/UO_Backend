package com.UO.IService;

import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IConferenceService {


    Conference getConference(String titre);
    List<Conference> getAllConferences();
    void supprimerConference(Long id);
    Conference ajouterConference(Conference c);
    public Page<Conference> getPageConferences(int page, int size) ;
    public Page<Conference> getConferencesByMotCle(String mc, int page, int size) ;
    public Page<Conference> findPage(int pageNumber);

    Conference getOne(Long id);


}
