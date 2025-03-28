package catalogo_bibliografico.prestito;

import jakarta.persistence.EntityManager;

import java.util.List;

public class PrestitoDAO {
    private EntityManager em;
    
    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }
    
    public void save(Prestito prestito) {
        em.persist(prestito);
    }
    
    public List<Prestito> findByUtente(Long numeroDiTessera) {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroDiTessera = :tessera", Prestito.class)
                .setParameter("tessera", numeroDiTessera)
                .getResultList();
    }
    
    public List<Prestito> findExpiredLoans() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < CURRENT_DATE", Prestito.class)
                .getResultList();
    }
}
