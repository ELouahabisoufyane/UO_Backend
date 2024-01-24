package com.UO.Service;

import com.UO.DOA.ConferenceRepository;
import com.UO.DOA.ParticipationRepesitory;
import com.UO.DOA.StatistiqueRepository;
import com.UO.Modele.Conference;
import com.UO.Modele.Statistique;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatistiqueService {

    final ConferenceRepository cr;
    final ParticipationRepesitory ptr;
    final StatistiqueRepository sr;

    public StatistiqueService(ConferenceRepository cr, ParticipationRepesitory ptr, StatistiqueRepository sr) {
        this.cr = cr;
        this.ptr = ptr;
        this.sr = sr;
    }


    public List<Statistique> getStatistique(){

        List<Conference> cf=this.cr.findByDateBefore(LocalDate.now());

        for(Conference c :cf){

            Statistique s =new Statistique();
            s.setTitre(c.getTitre());
            s.setDate(c.getDate());
            s.setConferencier(c.getConferencier());

            s.setNbAbssence(this.ptr.findParticipantsWithEtatFalse(c.getId()).size());
            s.setNbPrresence(this.ptr.findParticipantsWithEtatTrue(c.getId()).size());
            //setparcarte les participations qui ont how =true
            s.setParCarte(this.ptr.findParticipantsWithHowFlase(c.getId()).size());
            s.setParGest(this.ptr.findParticipantsWithHowTrue(c.getId()).size());
            if(this.sr.findByTitre(s.getTitre())==null)
                this.sr.save(s);

        }

        return this.sr.findAll();
    }

}
