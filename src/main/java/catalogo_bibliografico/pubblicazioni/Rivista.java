package catalogo_bibliografico.pubblicazioni;

import catalogo_bibliografico.pubblicazioni.generi_periodicità.Periodicita;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends Pubblicazione {
    
    private Periodicita periodicita;
    
    public Rivista(String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }
    
    public Rivista() { }
}
