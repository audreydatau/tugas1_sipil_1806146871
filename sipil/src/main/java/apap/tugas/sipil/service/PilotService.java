package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;

import java.util.List;

public interface PilotService {

    void addPilot(PilotModel pilot);

    String createNIP(PilotModel pilot);

    List<PilotModel> getPilotList();

    PilotModel updatePilot(PilotModel pilot);

    PilotModel getPilotByNip(String nip);

    PilotModel getPilotByNik(String nik);

    void deletePilot(PilotModel pilot);

    PilotModel getPilotById(Long id);

    List<PilotModel> getPilotTerbaik(List<PilotModel> pilotList);

}
