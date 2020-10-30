package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;

public interface PilotPenerbanganService {
    void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan);

    PilotPenerbanganModel createPilotPenerbanganModel(PilotModel pilot, PenerbanganModel penerbangan);
}
