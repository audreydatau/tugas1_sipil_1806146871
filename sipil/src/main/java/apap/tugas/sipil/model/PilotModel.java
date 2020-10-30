package apap.tugas.sipil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pilot")
public class PilotModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 13)
    @Column(name = "nip",nullable = false, unique = true)
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nik", nullable = false)
    private String nik;

    @NotNull
    @Column(name="tanggalLahir", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempatLahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name = "jenisKelamin", nullable = false)
    private Integer jenisKelamin;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "akademiId", referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private AkademiModel akademi;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "maskapaiId", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MaskapaiModel maskapai;

    @OneToMany(mappedBy = "pilot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PilotPenerbanganModel> listPilotPenerbangan;

    public List<PilotPenerbanganModel> getListPilotPenerbangan() {
        return listPilotPenerbangan;
    }

    public void setListPilotPenerbangan(List<PilotPenerbanganModel> listPilotPenerbangan) {
        this.listPilotPenerbangan = listPilotPenerbangan;
    }


    public MaskapaiModel getMaskapai() {
        return maskapai;
    }

    public void setMaskapai(MaskapaiModel maskapai) {
        this.maskapai = maskapai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tampatLahir) {
        this.tempatLahir = tampatLahir;
    }

    public Integer getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(Integer jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public AkademiModel getAkademi() {
        return akademi;
    }

    public void setAkademi(AkademiModel akademi) {
        this.akademi = akademi;
    }
}
