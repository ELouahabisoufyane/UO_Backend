package com.UO.Service;

import com.UO.DOA.ConferenceRepository;
import com.UO.DOA.ParticipantRepository;
import com.UO.DOA.ParticipationRepesitory;
import com.UO.IService.IParticipantService;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

                ptr.addParticipation(p,conference,false,false);

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
        return pr.findAll(PageRequest.of(pageNumber,50));
    }

    public Participant updateParticipant(Participant p) {
        return this.pr.save(p);
    }
    public void saveExcelData(MultipartFile file) throws IOException {
        List<Participant> participantList = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    //skip header row
                    continue;
                }
                Participant p = new Participant();
                if(!row.getCell(0).getStringCellValue().isEmpty() && !row.getCell(1 ).getStringCellValue().isEmpty()){
                p.setCivilite(row.getCell(0).getStringCellValue());
                p.setNom(row.getCell(1 ).getStringCellValue());
                p.setPrenom(row.getCell(2).getStringCellValue());
                p.setEmail(row.getCell(3).getStringCellValue());
                p.setAdresse(row.getCell(4).getStringCellValue());
                p.setCodepos(row.getCell(5).getStringCellValue());
                p.setCommune(row.getCell(6).getStringCellValue());
                p.setTel(row.getCell(7).getStringCellValue());
                p.setNcarte(row.getCell(8).getStringCellValue());
                p.setMontantUFC(row.getCell(9).getNumericCellValue());
                p.setMontantADAUO(row.getCell(10).getNumericCellValue());
                participantList.add(p);}
            }
        }
        this.pr.saveAll(participantList);
    }
}



