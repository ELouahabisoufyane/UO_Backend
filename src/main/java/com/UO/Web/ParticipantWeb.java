package com.UO.Web;

import com.UO.DOA.ParticipantRepository;
import com.UO.Modele.Participant;
import com.UO.Service.ParticipantService;
import com.UO.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/participant")
@CrossOrigin(origins = "*")
public class ParticipantWeb {
    //ps:le service de participant
    @Autowired
    private ParticipantService ps;

    final ParticipantRepository pr;

    public ParticipantWeb(ParticipantRepository pr) {
        this.pr = pr;
    }

    @GetMapping("/getOne/{nom}")
    public Participant getPrticipant(@PathVariable("nom") String nom){
        return this.ps.getParticipant(nom);
    }

    @GetMapping("/getAll")
    public List<Participant> getAllPrticipant(){

        return this.ps.getAllParticipants();
    }
    @PostMapping("/addOne")
    public Participant saveParticipant(@RequestBody Participant p){
        return this.ps.ajouterParticipant(p);
    }
    @PutMapping("/updateOne")
    public Participant updateParticipant(@RequestBody Participant p){
        return this.ps.updateParticipant(p);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteParticipant(@PathVariable("id") Long id){
        this.ps.supprimerParticipant(id);
    }
    @GetMapping("/chercher")
    public Page<Participant> chercherClient(@RequestParam(name = "mc",defaultValue = "i") String mc,
                                       @RequestParam(name = "page",defaultValue = "0") int page,
                                       @RequestParam(name = "size",defaultValue = "4") int size) {
        Page<Participant> p=ps.getParticipantsByMotCle(mc,page,size) ;

        return p;
    }
    @GetMapping("/list/{p}")
    public  Page<Participant> showPage(@PathVariable("p") int currentPage){
        Page<Participant> page = ps.findPage(currentPage);
        return page;
    }
    @GetMapping("/getPartcipantByNom/{k}")
    public  List<Participant> getPartcipantByNom(@PathVariable("k") String nom){
        return this.pr.findAllByName(nom);
    }

    @PostMapping("/Importer")

    public  ResponseEntity<ResponseMessage> addRessource(@RequestParam("file") MultipartFile file ) throws IOException {
        String message = "";

        try {
            this.ps.saveExcelData(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!"+file.getSize();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @DeleteMapping("/deleteTous")
    public void deleteParticipans(){

        this.pr.deleteAll();
    }


}
