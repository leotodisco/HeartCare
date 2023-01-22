package c15.dev.registrazione.controller;

import c15.dev.gestioneUtente.service.GestioneUtenteService;
import c15.dev.model.entity.Medico;
import c15.dev.model.entity.Paziente;
import c15.dev.model.entity.UtenteRegistrato;
import c15.dev.registrazione.service.RegistrazioneService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * @author Mario Cicalese
 * Creato il : 03/01/2023
 * Questa classe rappresenta il Service utilizzato per la registrazione
 */
@RestController
@CrossOrigin
public class RegistrazioneController {
    @Autowired
    public RegistrazioneService registrazioneService;

    @Autowired
    public GestioneUtenteService utenteService;

    @Autowired
    public HttpSession session;

    @PostMapping(value = "/registrazione")
        public String registrazione(@Valid @RequestBody Paziente paziente)
                throws Exception {

        registrazioneService.registraPaziente(paziente);

    /*  Paziente pazienteOptional = registrazioneService.findByemail(paziente.getEmail());
      if(pazienteOptional != null)
          return "/registrazione";

      pazienteOptional = registrazioneService.findBycodiceFiscale(paziente.getCodiceFiscale());
      if(pazienteOptional!=null)
          return "/registrazione";

     String password = new String(paziente.getPassword());
      /*if(!(password.equals(confermaPassword)))
          return "/registrazione";

      paziente.setPassword(password);
      Paziente pazienteRegistrato = registrazioneService.registraPaziente(paziente);
      if (pazienteRegistrato == null)
          return("/registrazioneNonAvvenuta");
*/
        return "/registrazioneAvvenuta";

    }

    /**
     * Metodo che permette all'admin di registrare un medico.
     * @param med
     */
    @RequestMapping(value = "/registraMedico", method = RequestMethod.POST)
    public void registraMedico(@Valid @RequestBody Medico med) {
        UtenteRegistrato u = (UtenteRegistrato)
                session.getAttribute("utenteLoggato");
        if(!utenteService.isAdmin(u.getId())){
            return;
        }
        registrazioneService.registraMedico(med);
    }
}
