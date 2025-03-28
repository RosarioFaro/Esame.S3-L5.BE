package catalogo_bibliografico.pubblicazioni;

import catalogo_bibliografico.pubblicazioni.generi_periodicità.Genere;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("LIBRO")
public class Libro extends Pubblicazione {
    
    private String autore;
    
    @Enumerated(EnumType.STRING)
    private Genere genere;
    
    public Libro(String titolo, int annoPubblicazione, int numeroPagine, String autore, Genere genere) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }
    
    public Libro() { }
}
