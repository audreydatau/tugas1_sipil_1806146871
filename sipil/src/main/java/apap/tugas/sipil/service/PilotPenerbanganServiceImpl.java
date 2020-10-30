package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class PilotPenerbanganServiceImpl implements PilotPenerbanganService {
    @Autowired
    PilotPenerbanganDb pilotPenerbanganDb;

    @Override
    public void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan) {
        pilotPenerbanganDb.save(pilotPenerbangan);
    }

    @Override
    public PilotPenerbanganModel createPilotPenerbanganModel(PilotModel pilot, PenerbanganModel penerbangan) {
        PilotPenerbanganModel pilotPenerbangan = new PilotPenerbanganModel();
        pilotPenerbangan.setPilot(pilot);
        pilotPenerbangan.setPenerbangan(penerbangan);

        pilotPenerbangan.setTanggalPenugasan(new Date());
        return pilotPenerbangan;
    }
}
