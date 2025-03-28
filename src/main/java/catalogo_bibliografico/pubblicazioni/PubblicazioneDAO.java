package catalogo_bibliografico.pubblicazioni;

import catalogo_bibliografico.pubblicazioni.generi_periodicità.Periodicita;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PubblicazioneDAO {
    private final EntityManager em;
    
    public PubblicazioneDAO(EntityManager em) {
        this.em = em;
    }
    
    public void save(Pubblicazione pubblicazione) {
        em.persist(pubblicazione);
    }
    
    public void deleteByIsbn(String isbn) {
        Pubblicazione pubblicazione = em.find(Pubblicazione.class, isbn);
        if (pubblicazione != null) {
            em.remove(pubblicazione);
        }
    }
    
    public Pubblicazione findByIsbn(String isbn) {
        return em.find(Pubblicazione.class, isbn);
    }
    
    public List<Pubblicazione> findAll() {
        return em.createQuery("SELECT p FROM Pubblicazione p", Pubblicazione.class)
                .getResultList();
    }
    
    public List<Libro> findLibriByYear(int year) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.annoPubblicazione = :year", Libro.class)
                .setParameter("year", year)
                .getResultList();
    }
    
    public List<Libro> findLibriByAutore(String autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
    }
    
    public List<Libro> findLibriByTitolo(String titolo) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.titolo LIKE :titolo", Libro.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }
    
    public List<Rivista> findRivisteByTitolo(String titolo) {
        return em.createQuery("SELECT r FROM Rivista r WHERE r.titolo LIKE :titolo", Rivista.class)
                .setParameter("titolo", "%" + titolo + "%")
                .getResultList();
    }
}
