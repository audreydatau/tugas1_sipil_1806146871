package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.PilotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PilotDb extends JpaRepository<PilotModel, Long> {
    Optional<PilotModel> findAllByAkademi_IdAndMaskapai_Kode(Long idAkademi, String kode);

    Optional<PilotModel> findAllByNip(String nip);

    Optional<PilotModel> findAllByNik(String nik);
}
