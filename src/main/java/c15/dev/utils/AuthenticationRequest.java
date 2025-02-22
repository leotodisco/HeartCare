package c15.dev.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Leopoldo Todisco, Carlo Venditto.
 * Creato il: 24/01/2023.
 * Classe che indica i dati di una richiesta di login.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /**
     * password dell'utente.
     */
    private String password;
    /**
     * email utente.
     */
    private String email;
}
