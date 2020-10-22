package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.PilotPenerbanganModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PilotPenerbanganDb extends JpaRepository<PilotPenerbanganModel, Long> {
    Optional<PilotPenerbanganModel> findAllByPenerbanganId(Long id);
}
