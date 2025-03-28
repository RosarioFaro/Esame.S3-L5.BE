package catalogo_bibliografico;

import catalogo_bibliografico.prestito.Prestito;
import catalogo_bibliografico.prestito.PrestitoDAO;
import catalogo_bibliografico.pubblicazioni.Libro;
import catalogo_bibliografico.pubblicazioni.Pubblicazione;
import catalogo_bibliografico.pubblicazioni.PubblicazioneDAO;
import catalogo_bibliografico.pubblicazioni.Rivista;
import catalogo_bibliografico.pubblicazioni.generi_periodicità.Genere;
import catalogo_bibliografico.pubblicazioni.generi_periodicità.Periodicita;
import catalogo_bibliografico.utente.Utente;
import catalogo_bibliografico.utente.UtenteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistUnit");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        
        em.getTransaction().begin();
        
        PubblicazioneDAO pubblicazioneDAO = new PubblicazioneDAO(em);
        
        Libro libro1 = new Libro("Il Signore degli Anelli", 1954, 1200, "J.R.R. Tolkien", Genere.FANTASY);
        Libro libro2 = new Libro("Il richiamo di Cthulhu", 1928, 180, "H.P. Lovecraft", Genere.HORROR);
        Libro libro4 = new Libro("Il Colore Venuto dallo Spazio", 1927, 80, "H.P. Lovecraft", Genere.HORROR);
        Libro libro3 = new Libro("Dune", 1965, 758, "Frank Herbert", Genere.SCI_FI);
        Libro libro5 = new Libro("Messia di Dune", 1968, 450, "Frank Herbert", Genere.SCI_FI);
        
        pubblicazioneDAO.save(libro1);
        pubblicazioneDAO.save(libro2);
        pubblicazioneDAO.save(libro3);
        pubblicazioneDAO.save(libro4);
        pubblicazioneDAO.save(libro5);
        
        Rivista rivista1 = new Rivista("Time", 2023, 40, Periodicita.SETTIMANALE);
        Rivista rivista2 = new Rivista("National Geographic", 2023, 100,  Periodicita.MENSILE);
        Rivista rivista3 = new Rivista("Harvard Business Review", 2023, 60,  Periodicita.SEMESTRALE);
        
        pubblicazioneDAO.save(rivista1);
        pubblicazioneDAO.save(rivista2);
        pubblicazioneDAO.save(rivista3);
        
        UtenteDAO utenteDAO = new UtenteDAO(em);
        
        Utente utente = new Utente("Nino", "Dangerous", LocalDate.of(1985, 5, 15));
        utenteDAO.save(utente);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        
        Prestito prestito1 = new Prestito(utente, libro1, LocalDate.now().minusDays(10));
        Prestito prestito2 = new Prestito(utente, libro2, LocalDate.now().minusDays(20));
        Prestito prestito3 = new Prestito(utente, libro3, LocalDate.now().minusDays(40));
        
        prestitoDAO.save(prestito1);
        prestitoDAO.save(prestito2);
        prestitoDAO.save(prestito3);
        
        System.out.println("Ricerca per ISBN:");
        Libro libroTrovato = (Libro) pubblicazioneDAO.findByIsbn(String.valueOf(libro1.getISBN()));
        System.out.println("Trovato: " + libroTrovato.getTitolo() + " di " + libroTrovato.getAutore());
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Ricerca per anno 1954:");
        List<Libro> libri1954 = pubblicazioneDAO.findLibriByYear(1954);
        libri1954.forEach(libro -> System.out.println(libro.getTitolo() + " di " + libro.getAutore()));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Ricerca per autore ~H.P. Lovecraft~:");
        List<Libro> libriLovecraft = pubblicazioneDAO.findLibriByAutore("H.P. Lovecraft");
        libriLovecraft.forEach(libro -> System.out.println(libro.getTitolo() + " (" + libro.getAnnoPubblicazione() + ")"));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Ricerca per titolo ~Dune~:");
        List<Libro> libriDune = pubblicazioneDAO.findLibriByTitolo("Dune");
        libriDune.forEach(libro -> System.out.println(libro.getTitolo() + " di " + libro.getAutore()));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Ricerca riviste con ~Time~ nel titolo:");
        List<Rivista> rivisteTime = pubblicazioneDAO.findRivisteByTitolo("Time");
        rivisteTime.forEach(rivista -> System.out.println(rivista.getTitolo() + " (" + rivista.getAnnoPubblicazione() + ")"));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Ricerca prestiti per utente con tessera " + utente.getNumeroDiTessera() + ":");
        List<Prestito> prestitiUtente = prestitoDAO.findByUtente(utente.getNumeroDiTessera());
        prestitiUtente.forEach(prestito ->
                System.out.println(prestito.getPubblicazione().getTitolo() + ", prestato il " + prestito.getDataInizioPrestito()));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        System.out.println("Prestiti scaduti non ancora restituiti:");
        List<Prestito> prestitiScaduti = prestitoDAO.findExpiredLoans();
        prestitiScaduti.forEach(prestito ->
                System.out.println(prestito.getPubblicazione().getTitolo() + ", scaduto il " + prestito.getDataRestituzionePrevista()));
        
        System.out.println("~ . ~ . ~ . ~ . ~ . ~ . ~ . ~");
        
        while (true) {
            System.out.println("----- MENU -----");
            System.out.println("1. Visualizza tutte le pubblicazioni");
            System.out.println("2. Elimina pubblicazione per ISBN");
            System.out.println("3. Esci");
            System.out.print("Scegli un'opzione: ");
            
            int scelta = scanner.nextInt();
            scanner.nextLine();
            
            switch (scelta) {
                case 1:
                    System.out.println("Lista delle pubblicazioni:");
                    List<Pubblicazione> pubblicazioni = pubblicazioneDAO.findAll();
                    for (Pubblicazione p : pubblicazioni) {
                        System.out.println("ISBN: " + p.getISBN() + ", Titolo: " + p.getTitolo());
                    }
                    break;
                
                case 2:
                    System.out.print("Inserisci l'ISBN della pubblicazione da eliminare (solo quelle con l'isbn dal 4 in poi): ");
                    String isbn = scanner.nextLine();
                    Pubblicazione pubblicazione = pubblicazioneDAO.findByIsbn(isbn);
                    if (pubblicazione != null) {
                        pubblicazioneDAO.deleteByIsbn(isbn);
                        em.flush();
                        em.clear();
                        System.out.println("Pubblicazione con ISBN " + isbn + " eliminata.");
                    } else {
                        System.out.println("Nessuna pubblicazione trovata con ISBN " + isbn + ".");
                    }
                    break;
                
                case 3:
                    System.out.println("Uscendo dal programma...");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
            
            if (scelta == 3) {
                break;
            }
        }
        
        em.getTransaction().commit();
        
        scanner.close();
        
        em.close();
        emf.close();
    }
}
