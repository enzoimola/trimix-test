package trimix.service;

import trimix.dao.TipoDocumentoDao;
import trimix.dto.Response;
import trimix.exception.EntityNotFoundException;
import trimix.model.TipoDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoDocumentoService {

    @Autowired
    private TipoDocumentoDao tipoDocumentoDao;

    public TipoDocumento get(int id) throws EntityNotFoundException, Exception {
        return tipoDocumentoDao.get(id);
    }

    public Response list() throws Exception {
        Response response = new Response();
        List<TipoDocumento> tipoDocumentos = tipoDocumentoDao.list();
        response.setData(tipoDocumentos);
        return response;
    }


}














