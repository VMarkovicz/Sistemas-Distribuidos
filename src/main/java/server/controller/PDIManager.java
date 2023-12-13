package server.controller;

import server.dataTransferObject.*;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.PDI;
import server.repository.PDIRepository;
import java.util.List;

public class PDIManager {
    private static PDIManager instance = null;
    private final PDIRepository repository = new PDIRepository();

    public static PDIManager getInstance() {
        if (instance == null) {
            instance = new PDIManager();
        }
        return instance;
    }
    public PDIDTO createPDI(CreatePDIDTO pdi) throws ServerReplyException {
        var model = PDI.of(pdi);
        repository.createPDI(model);
        return PDIDTO.of(model);
    }

    public void deletePDI(DeletePDIDTO pdi) throws ServerReplyException{
        repository.deletePDI(pdi.id());
    }

    public PDIDTO findPDI(long id) throws NotFoundException {
        var entity = repository.findPDI(id)
                .orElseThrow(() -> new NotFoundException("PDI not found"));
        return PDIDTO.of(entity);
    }
    public List<PDIDTO> findPDIs() {
        return repository.findAllPDIs()
                .stream()
                .map(PDIDTO::of)
                .toList();
    }
    public PDIDTO updatePDI(UpdatePDIDTO pdi) throws ServerReplyException {
        var entity = repository.updatePDI(pdi.id(), PDI.of(pdi));
        return PDIDTO.of(entity);
    }
}
