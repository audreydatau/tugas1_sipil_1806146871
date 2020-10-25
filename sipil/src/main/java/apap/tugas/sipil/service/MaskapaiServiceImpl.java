package apap.tugas.sipil.service;

import apap.tugas.sipil.model.MaskapaiModel;
import apap.tugas.sipil.repository.MaskapaiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MaskapaiServiceImpl implements MaskapaiService {
    @Autowired
    MaskapaiDb maskapaiDb;

    @Override
    public List<MaskapaiModel> getMaskapaiList() {
        return maskapaiDb.findAll();
    }

    @Override
    public MaskapaiModel getMaskapaiByKode(String kode) {
        return maskapaiDb.findAllByKode(kode).get();
    }
}
