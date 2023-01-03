package c15.dev.gestioneUtente.controller;

import c15.dev.gestioneUtente.service.GestioneUtenteService;
import c15.dev.model.dao.PazienteDAO;
import c15.dev.model.entity.Paziente;
import c15.dev.model.entity.UtenteRegistrato;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;
import java.util.Optional;

@RestController
@SessionAttributes("utenteLoggato")
public class GestioneUtenteController {
    /**
     * Service per le operazioni di accesso
     */
    @Autowired
    private GestioneUtenteService service;
    /**
     * Sessione
     */
    @Autowired
    private HttpSession session;

    /**
     * Metodo di login.
     * @param email
     * @param password
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestParam String email,
                       @RequestParam String password) {

        Optional<UtenteRegistrato> utente = service.login(email, password);
        utente.ifPresent(utenteRegistrato ->
            {session.setAttribute("utenteLoggato", utente);} );
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        session.invalidate();
    }


}
