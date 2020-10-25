package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.MaskapaiModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.service.AkademiService;
import apap.tugas.sipil.service.MaskapaiService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PilotController{
    @Qualifier("pilotServiceImpl")
    @Autowired
    private PilotService pilotService;

    @Qualifier("akademiServiceImpl")
    @Autowired
    private AkademiService akademiService;

    @Qualifier("maskapaiServiceImpl")
    @Autowired
    private MaskapaiService maskapaiService;

    @GetMapping("/")
    private String home(){
        return "home";
    }

    @GetMapping("/pilot/tambah")
    public String tambahPilotFormPage(Model model){
        List<AkademiModel> akademiList = akademiService.getAkademiList();
        List<MaskapaiModel> maskapaiList = maskapaiService.getMaskapaiList();

        model.addAttribute("maskapaiList", maskapaiList);
        model.addAttribute("akademiList", akademiList);
        model.addAttribute("pilot", new PilotModel());
        return "form-tambah-pilot";
    }

    @PostMapping("/pilot/tambah")
    public String tambahPilotSubmit(
            @ModelAttribute PilotModel pilot,
            Model model){
        pilotService.addPilot(pilot);
        model.addAttribute("nip", pilot.getNip());
        return "tambah-pilot";
    }

    @GetMapping("/pilot/{nipPilot}")
    private String viewDetailPilot(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel pilot=pilotService.getPilotByNip(nipPilot);
        model.addAttribute("pilot", pilot);

        return "view-pilot";
    }

    @GetMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormPage(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel pilot=pilotService.getPilotByNip(nipPilot);
        List<AkademiModel> akademiList = akademiService.getAkademiList();
        List<MaskapaiModel> maskapaiList = maskapaiService.getMaskapaiList();

        model.addAttribute("maskapaiList", maskapaiList);
        model.addAttribute("akademiList", akademiList);
        model.addAttribute("pilot",pilot);
        return "form-update-pilot";
    }

    @PostMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormSubmit(
            @PathVariable String nipPilot,
            @ModelAttribute PilotModel pilot,
            Model model
    ){
        PilotModel pilotUpdated= pilotService.updatePilot(pilot);
        model.addAttribute("pilot", pilotUpdated);
        return "update-pilot";
    }

    @RequestMapping("/pilot")
    private String viewAll(Model model){
        List<PilotModel> pilotList = pilotService.getPilotList();
        model.addAttribute("listPilot", pilotList);

        return "view-all-pilot";
    }

    @RequestMapping("/pilot/hapus/{nipPilot}")
    public String deleteKamar(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel deletedPilot = pilotService.getPilotByNip(nipPilot);
        pilotService.deletePilot(deletedPilot);

        model.addAttribute("nip",nipPilot);
        return "delete-pilot";
    }

}