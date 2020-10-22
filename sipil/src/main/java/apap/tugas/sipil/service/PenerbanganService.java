package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;

import java.util.Date;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);

    PenerbanganModel getPenerbanganById(Long id);

    Date getTanggalPenugasanById(Long id);

    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);

    void deletePenerbangan(PenerbanganModel penerbangan);
}
