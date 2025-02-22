package c15.dev.utils;

import c15.dev.model.entity.UtenteRegistrato;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: Leopoldo Todisco, Carlo Venditto.
 * Creato il: 24/01/2023.
 * Questa classe Service si occupa della generazione di un Token
 * e dei controlli applicati su di esso.
 */
@Service
public class JwtService {
    /**
     * key che si usa per criptare i dati.
     */
    private static final String SECRET_KEY =
            "5267556B58703272357538782F413F4428472B4B6250655368566D5971337436";

    /**
     * Metodo che restituisce l'email presente nel token.
     * @param token
     * @return username/password.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Metodo per la generazione di un token.
     * @param userDetails
     * @return token.
     */
    public String generateToken(UtenteRegistrato userDetails) {
        HashMap<String, Object> mappaClaims = new HashMap<>();
        mappaClaims.put("id", userDetails.getId());
        mappaClaims.put("ruolo", userDetails.getRuolo());
        return generateToken(mappaClaims, userDetails);
    }

    /**
     * Metodo per la generazione di un token.
     * Un token dura 24 ore.
     * Al suo interno, il token contiene: email, data di scadenza,
     * id, ruolo.
     * @param extraClaims
     * @param userDetails
     * @return token.
     *
     */
    public String generateToken(final Map<String, Object> extraClaims,
                                final UtenteRegistrato userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 1440))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Metodo che controlla che il token sia valido.
     * @param token
     * @param userDetails
     * @return booleano si o no.
     */
    public boolean isTokenValid(final String token,
                                final UtenteRegistrato userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token);

    }

    /**
     * Metodo che controlla se il token è scaduto.
     * @param token
     * @return true o false.
     */
    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Metodo che estra la data di scadenza del token.
     * @param token
     * @return data di scadenza.
     */
    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Metodo che estrae i componenti del token.
     * @param token
     * @param claimsResolver
     * @return tutte le caratteristiche del token.
     * @param <T>
     */
    public <T> T extractClaim(final String token,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Metodo di ausilio al metodo di estrazione dei componenti del token.
     * @param token
     * @return tutte le caratteristiche del token.
     */
    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Metodo che permette di estrarre la chiave segreta del token.
     * Usa un decoder.
     * @return chiave di cifratura.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
