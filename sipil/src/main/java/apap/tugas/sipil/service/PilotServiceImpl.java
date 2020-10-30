package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.repository.PilotDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class PilotServiceImpl implements PilotService{

    @Autowired
    PilotDb pilotDb;

    @Override
    public void addPilot(PilotModel pilot) {
        String NIP = createNIP(pilot);
        pilot.setNip(NIP);
        pilotDb.save(pilot);
    }

    @Override
    public String createNIP(PilotModel pilot) {
        String jenisKelamin = Integer.toString(pilot.getJenisKelamin());
        String tempatLahir = pilot.getTempatLahir().substring(0,2).toUpperCase();
        int namaLength = pilot.getNama().length();
        String nama = pilot.getNama().substring(namaLength-1,namaLength).toUpperCase();

        Date date = pilot.getTanggalLahir();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day   = localDate.getDayOfMonth();
        String tanggalLahir = Integer.toString(day);
        String bulanLahir;
        if (month<10){
            bulanLahir = "0";
            bulanLahir+=Integer.toString(month);
        }else {
            bulanLahir= Integer.toString(month);
        }
        String tahunLahir = Integer.toString(Math.floorDiv(year,10));

        Random r = new Random();
        String random1= Character.toString((char)(r.nextInt(26) + 'a')).toUpperCase();
        String random2= Character.toString((char)(r.nextInt(26) + 'a')).toUpperCase();

        String NIP = jenisKelamin+tempatLahir+nama+tanggalLahir+bulanLahir+tahunLahir+random1+random2;

        return NIP;
    }

    @Override
    public List<PilotModel> getPilotList() {
        return pilotDb.findAll();
    }

    @Override
    public PilotModel updatePilot(PilotModel pilot) {
        PilotModel targetPilot = pilotDb.findById(pilot.getId()).get();
        targetPilot.setNama(pilot.getNama());
        targetPilot.setNik(pilot.getNik());
        targetPilot.setTempatLahir(pilot.getTempatLahir());
        targetPilot.setTanggalLahir(pilot.getTanggalLahir());
        targetPilot.setJenisKelamin(pilot.getJenisKelamin());
        targetPilot.setAkademi(pilot.getAkademi());
        targetPilot.setMaskapai(pilot.getMaskapai());
        String NIP = createNIP(pilot);
        String NIPLama = targetPilot.getNip();
        if (NIPLama.substring(0,11).equals(NIP.substring(0,11))){
            targetPilot.setNip(NIPLama);
        }else{
            targetPilot.setNip(NIP);
        }
        pilotDb.save(targetPilot);
        return targetPilot;
    }

    @Override
    public PilotModel getPilotByNip(String nip) {
        if (pilotDb.existsByNip(nip)){
            return pilotDb.findAllByNip(nip).get();
        }
        return null;
    }

    @Override
    public PilotModel getPilotByNik(String nik) {
        return pilotDb.findAllByNik(nik).get();
    }

    @Override
    public void deletePilot(PilotModel pilot) {
        pilotDb.delete(pilot);
    }

    @Override
    public PilotModel getPilotById(Long id) {
        return pilotDb.findById(id).get();
    }

    @Override
    public List<PilotModel> getPilotTerbaik(List<PilotModel> pilotList) {
        int n = pilotList.size();
        PilotModel temp = null;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(pilotList.get(j-1).getListPilotPenerbangan().size() < pilotList.get(j).getListPilotPenerbangan().size()){
                    temp = pilotList.get(j-1);
                    pilotList.set(j-1,pilotList.get(j));
                    pilotList.set(j,temp);
                }
            }
        }

        List<PilotModel> result = new ArrayList<>();

        if (pilotList.size()==1){
            result = pilotList;
        }else if (pilotList.size()==2){
            result = pilotList;
        }else if (pilotList.size()>=3){
            for (int i=0;i<3;i++){
                result.add(pilotList.get(i));
            }
        }

        return result;
    }
}

