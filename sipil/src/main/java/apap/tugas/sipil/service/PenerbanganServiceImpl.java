package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.repository.PenerbanganDb;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class PenerbanganServiceImpl implements PenerbanganService{
    @Autowired
    PenerbanganDb penerbanganDb;

    @Autowired
    PilotPenerbanganDb pilotPenerbanganDb;

    @Override
    public void addPenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.save(penerbangan);
    }

    @Override
    public PenerbanganModel getPenerbanganById(Long id) {
        return penerbanganDb.findById(id).get();
    }

    @Override
    public Date getTanggalPenugasanById(Long id) {
        return pilotPenerbanganDb.findAllByPenerbanganId(id).get().getTanggalPenugasan();
    }

    @Override
    public PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan) {
        PenerbanganModel targetPenerbangan = penerbanganDb.findById(penerbangan.getId()).get();
        targetPenerbangan.setKode(penerbangan.getKode());
        targetPenerbangan.setWaktu(penerbangan.getWaktu());
        targetPenerbangan.setKotaAsal(penerbangan.getKotaAsal());
        targetPenerbangan.setKotaTujuan(penerbangan.getKotaTujuan());

        penerbanganDb.save(targetPenerbangan);
        return targetPenerbangan;
    }

    @Override
    public void deletePenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.delete(penerbangan);
    }


}
