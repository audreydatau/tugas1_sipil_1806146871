package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.service.PenerbanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class PenerbanganController {
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

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
//        Date tanggalPenugasan = penerbanganService.getTanggalPenugasanById(id);

//        model.addAttribute("tanggalPenugasan", tanggalPenugasan);
        model.addAttribute("penerbangan", penerbangan);
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

}
