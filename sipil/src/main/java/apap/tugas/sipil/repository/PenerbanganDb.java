package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.PenerbanganModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenerbanganDb extends JpaRepository<PenerbanganModel, Long> {
}
