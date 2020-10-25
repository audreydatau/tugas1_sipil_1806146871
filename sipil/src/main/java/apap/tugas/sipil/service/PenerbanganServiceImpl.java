package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.repository.PenerbanganDb;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public boolean isPenerbanganBulanIni(List<PilotPenerbanganModel> pilotPenerbanganList) {
        Date bulanIni = new Date();
        LocalDate localDate = bulanIni.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int tahun  = localDate.getYear();
        int bulan = localDate.getMonthValue();
        for (PilotPenerbanganModel pp:pilotPenerbanganList){
            Date waktu = pp.getPenerbangan().getWaktu();
            LocalDate localDatePenerbangan = waktu.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int tahunPenerbangan  = localDatePenerbangan.getYear();
            int bulanPenerbangan = localDatePenerbangan.getMonthValue();

            if (bulan==bulanPenerbangan && tahun==tahunPenerbangan){
                return true;
            }
        }
        return false;
    }

}
