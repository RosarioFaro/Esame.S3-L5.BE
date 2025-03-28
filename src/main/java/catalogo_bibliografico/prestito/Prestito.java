package catalogo_bibliografico.prestito;

import catalogo_bibliografico.utente.Utente;
import catalogo_bibliografico.pubblicazioni.Pubblicazione;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "prestiti")
public class Prestito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "utente_id", referencedColumnName = "numeroDiTessera")
    private Utente utente;
    
    @ManyToOne
    @JoinColumn(name = "pubblicazione_id", referencedColumnName = "isbn")
    private Pubblicazione pubblicazione;
    
    private LocalDate dataInizioPrestito;
    private LocalDate dataRestituzionePrevista;
    private LocalDate dataRestituzioneEffettiva;
    
    public Prestito(Utente utente, Pubblicazione pubblicazione, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.pubblicazione = pubblicazione;
        this.dataInizioPrestito = dataInizioPrestito;
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }
}
