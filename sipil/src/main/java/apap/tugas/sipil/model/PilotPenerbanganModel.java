package apap.tugas.sipil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "pilotPenerbangan")
public class PilotPenerbanganModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pilotId",referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PilotModel pilot;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "penerbanganId",referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PenerbanganModel penerbangan;

    public Date getTanggalPenugasan() {
        return tanggalPenugasan;
    }

    public void setTanggalPenugasan(Date tanggalPenugasan) {
        this.tanggalPenugasan = tanggalPenugasan;
    }

    @NotNull
    @Column(name="tanggalPenugasan", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date tanggalPenugasan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PilotModel getPilot() {
        return pilot;
    }

    public void setPilot(PilotModel pilot) {
        this.pilot = pilot;
    }

    public PenerbanganModel getPenerbangan() {
        return penerbangan;
    }

    public void setPenerbangan(PenerbanganModel penerbangan) {
        this.penerbangan = penerbangan;
    }
}
