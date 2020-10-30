package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.AkademiModel;
import apap.tugas.sipil.model.MaskapaiModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.service.AkademiService;
import apap.tugas.sipil.service.MaskapaiService;
import apap.tugas.sipil.service.PenerbanganService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class PilotController{
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

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

        if (pilot!=null){
            List<PilotPenerbanganModel> pilotPenerbanganList = pilot.getListPilotPenerbangan();

            model.addAttribute("pilotPenerbanganList",pilotPenerbanganList);
            model.addAttribute("pilot", pilot);
        }
        model.addAttribute("nip",nipPilot);
        return "view-pilot";
    }

    @GetMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormPage(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel pilot=pilotService.getPilotByNip(nipPilot);

        if (pilot!=null){
            List<AkademiModel> akademiList = akademiService.getAkademiList();
            List<MaskapaiModel> maskapaiList = maskapaiService.getMaskapaiList();

            model.addAttribute("maskapaiList", maskapaiList);
            model.addAttribute("akademiList", akademiList);
            model.addAttribute("pilot",pilot);
        }

        model.addAttribute("nip",nipPilot);
        return "form-update-pilot";
    }

    @PostMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormSubmit(
            @PathVariable String nipPilot,
            @ModelAttribute PilotModel pilot,
            Model model
    ){
        PilotModel pilotUpdated= pilotService.updatePilot(pilot);

        model.addAttribute("nipPilot",nipPilot);
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
    public String deletePilot(
            @PathVariable String nipPilot,
            Model model
    ){
        PilotModel deletedPilot = pilotService.getPilotByNip(nipPilot);
        if (deletedPilot!=null){
            pilotService.deletePilot(deletedPilot);
            model.addAttribute("nip",nipPilot);
        }else {
            model.addAttribute("msg","Pilot dengan NIP "+nipPilot+" tidak ditemukan");
        }

        return "delete-pilot";
    }

    @GetMapping("/cari/pilot")
    public String cariPilot(
            @RequestParam(value ="kodeMaskapai",required = false)String kodeMaskapai,
            @RequestParam(value = "idSekolah", required = false)Long idSekolah,
            Model model
    ){
        List<PilotModel> pilotList = new ArrayList<>();
        List<MaskapaiModel> maskapaiList = maskapaiService.getMaskapaiList();
        List<AkademiModel> akademiList = akademiService.getAkademiList();

        if (kodeMaskapai!=null && kodeMaskapai!=""){
            pilotList = pilotService.getPilotList();
            MaskapaiModel maskapai = maskapaiService.getMaskapaiByKode(kodeMaskapai);
            List<PilotModel> tempList = new ArrayList<>(pilotList);
            pilotList = new ArrayList<>();
            for (PilotModel pilotModel:tempList){
                if (maskapai == pilotModel.getMaskapai()){
                    pilotList.add(pilotModel);
                }
            }
        }
        if (idSekolah!=null){
            pilotList = pilotService.getPilotList();
            AkademiModel akademi = akademiService.getAkademiById(idSekolah);
            List<PilotModel> tempList = new ArrayList<>(pilotList);
            pilotList = new ArrayList<>();
            for (PilotModel pilotModel:tempList){
                if (akademi==pilotModel.getAkademi()){
                    pilotList.add(pilotModel);
                }
            }
        }

        model.addAttribute("pilotList",pilotList);
        model.addAttribute("maskapaiList", maskapaiList);
        model.addAttribute("akademiList", akademiList);

        return "cari-pilot";
    }

    @GetMapping("/cari/pilot/penerbangan-terbanyak")
    public String tigaTerbanyak(
            @RequestParam(value = "kodeMaskapai", required = false) String kodeMaskapai,
            Model model
    ){
        List<PilotModel> pilotList = new ArrayList<>();
        List<MaskapaiModel> maskapaiList = maskapaiService.getMaskapaiList();
        List<PilotModel> terbanyakList = new ArrayList<>();
        HashMap<PilotModel,Integer> terbanyakMap = new HashMap<>();

        if (kodeMaskapai!=null && kodeMaskapai!=""){
            pilotList = pilotService.getPilotList();
            MaskapaiModel maskapai = maskapaiService.getMaskapaiByKode(kodeMaskapai);
            List<PilotModel> tempList = new ArrayList<>(pilotList);
            pilotList = new ArrayList<>();
            for (PilotModel pilotModel:tempList){
                if (maskapai == pilotModel.getMaskapai()){
                    pilotList.add(pilotModel);
                }
            }

            terbanyakList = pilotService.getPilotTerbaik(pilotList);

            for (PilotModel pilotModel:terbanyakList){
                int total = pilotModel.getListPilotPenerbangan().size();
                terbanyakMap.put(pilotModel,total);
            }
        }

        model.addAttribute("maskapaiList", maskapaiList);
        model.addAttribute("terbanyakList", terbanyakList);
        model.addAttribute("terbanyakMap", terbanyakMap);

        return "pilot-terbaik";
    }

    @GetMapping("/cari/pilot/bulan-ini")
    public String pilotBulanIni(Model model){
        List<PilotModel> pilotList = pilotService.getPilotList();
        List<PilotModel> result = new ArrayList<>();

        for (PilotModel pilot:pilotList){
            if(penerbanganService.isPenerbanganBulanIni(pilot.getListPilotPenerbangan())){
                result.add(pilot);
            }
        }

        model.addAttribute("pilotList",result);
        return "pilot-bulan-ini";
    }


}