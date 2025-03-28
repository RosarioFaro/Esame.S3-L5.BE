package catalogo_bibliografico.pubblicazioni;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pubblicazione", discriminatorType = DiscriminatorType.STRING)
@Table(name = "pubblicazioni")
public abstract class Pubblicazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long ISBN;
    
    protected String titolo;
    protected int annoPubblicazione;
    protected int numeroPagine;
    
    public Pubblicazione(String titolo, int annoPubblicazione, int numeroPagine) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }
    
    public Pubblicazione() { }
}
