package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import apap.tugas.sipil.service.PenerbanganService;
import apap.tugas.sipil.service.PilotPenerbanganService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PenerbanganController {
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

    @Qualifier("pilotServiceImpl")
    @Autowired
    private PilotService pilotService;

    @Qualifier("pilotPenerbanganServiceImpl")
    @Autowired
    private PilotPenerbanganService pilotPenerbanganService;

    @GetMapping("/penerbangan/tambah")
    public String tambahPenerbanganFormPage(Model model){
        model.addAttribute("penerbangan",new PenerbanganModel());
        return "form-tambah-penerbangan";
    }

    @PostMapping("/penerbangan/tambah")
    public String tambahPilotSubmit(
            @ModelAttribute PenerbanganModel penerbangan,
            Model model){
        penerbanganService.addPenerbangan(penerbangan);
        model.addAttribute("id",penerbangan.getId());
        return "tambah-penerbangan";
    }

    @GetMapping("/penerbangan/detail/{idPenerbangan}")
    public String viewDetailPenerbangan(
            @PathVariable Long idPenerbangan,
            Model model
    ){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        List<PilotModel> pilotList = pilotService.getPilotList();

        List<PilotPenerbanganModel> pilotPenerbanganList = penerbangan.getListPilotPenerbangan();
        model.addAttribute("pilotPenerbanganList",pilotPenerbanganList);

        List<PilotModel> tempPilotList = new ArrayList<>();
        if (pilotPenerbanganList!=null){
            for (PilotPenerbanganModel pp:pilotPenerbanganList){
                PilotModel plt = pp.getPilot();
                tempPilotList.add(plt);
            }
            pilotList.removeAll(tempPilotList);

            if (pilotPenerbanganList.size()!=0){
                Date tanggalPenugasan = pilotPenerbanganList.get(0).getTanggalPenugasan();
                model.addAttribute("tanggalPenugasan", tanggalPenugasan);
            }else{
                model.addAttribute("tanggalPenugasan", new Date());
            }
        }else{
            model.addAttribute("tanggalPenugasan", new Date());
        }


        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("pilotList", pilotList);
        return "view-penerbangan";
    }

    @GetMapping("/penerbangan/ubah/{idPenerbangan}")
    public String changePenerbanganFormPage(
            @PathVariable Long idPenerbangan,
            Model model
    ){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);

        model.addAttribute("penerbangan", penerbangan);
        return "form-update-penerbangan";
    }

    @PostMapping("/penerbangan/ubah/{idPenerbangan}")
    public String changePenerbanganFormSubmit(
            @PathVariable Long idPenerbangan,
            @ModelAttribute PenerbanganModel penerbangan,
            Model model
    ){
        PenerbanganModel penerbanganUpdated = penerbanganService.updatePenerbangan(penerbangan);

        model.addAttribute("id", idPenerbangan);
        return "update-penerbangan";
    }

    @RequestMapping("penerbangan/hapus/{idPenerbangan}")
    public String deletePenerbangan(
            @PathVariable Long idPenerbangan,
            Model model
    ){
        PenerbanganModel deletedPenerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        String kodePenerbangan = deletedPenerbangan.getKode();
        penerbanganService.deletePenerbangan(deletedPenerbangan);
        model.addAttribute("kode",kodePenerbangan);
        return "delete-penerbangan";
    }

    @PostMapping("penerbangan/{idPenerbangan}/pilot/tambah")
    public String tambahPilotPenerbangan(
            @PathVariable Long idPenerbangan,
            @ModelAttribute PenerbanganModel penerbangan,
            HttpServletRequest request,
            Model model
    ){
        PilotModel pilot = pilotService.getPilotById(Long.valueOf(request.getParameter("pilot")));

        List<PilotModel> pilotList = pilotService.getPilotList();
        List<PilotPenerbanganModel> pilotPenerbanganList = penerbangan.getListPilotPenerbangan();

        List<PilotModel> tempPilotList = new ArrayList<>();

        if (pilotPenerbanganList!=null){
            for (PilotPenerbanganModel pp:pilotPenerbanganList){
                PilotModel plt = pp.getPilot();
                tempPilotList.add(plt);
            }
        }

        pilotList.removeAll(tempPilotList);
        model.addAttribute("pilotList",pilotList);

        if (tempPilotList.contains(pilot)){
            model.addAttribute("penerbangan",penerbangan);
            model.addAttribute("pilotPenerbanganList",pilotPenerbanganList);
            return "view-penerbangan";
        }else{
            PilotPenerbanganModel pilotPenerbangan = new PilotPenerbanganModel();
            pilotPenerbangan.setPilot(pilot);
            pilotPenerbangan.setPenerbangan(penerbangan);

            pilotPenerbangan.setTanggalPenugasan(new Date());
            pilotPenerbanganService.addPilotPenerbangan(pilotPenerbangan);
            model.addAttribute("penerbanganList",pilotPenerbanganList);
            return "redirect:/penerbangan/detail/" + idPenerbangan;
        }
    }

}
