package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;

import java.util.Date;
import java.util.List;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);

    PenerbanganModel getPenerbanganById(Long id);

    List<PenerbanganModel> getPenerbanganList();

    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);

    void deletePenerbangan(PenerbanganModel penerbangan);

    boolean isPenerbanganBulanIni(List<PilotPenerbanganModel> pilotPenerbanganList);

}
