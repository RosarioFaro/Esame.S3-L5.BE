package catalogo_bibliografico.utente;

import jakarta.persistence.EntityManager;

public class UtenteDAO {
    private EntityManager em;
    
    public UtenteDAO(EntityManager em) {
        this.em = em;
    }
    
    public void save(Utente utente) {
        em.persist(utente);
    }
    
    public Utente findById(Long numeroDiTessera) {
        return em.find(Utente.class, numeroDiTessera);
    }
    
    public void remove(Utente utente) {
        em.remove(utente);
    }
}
