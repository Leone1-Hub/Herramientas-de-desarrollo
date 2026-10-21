package persistencia;

import clases.Categoria;
import clases.ReglaNegocioException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import persistencia.exceptions.NonexistentEntityException;

/**
 * Acceso a datos de Categoria. Todas las consultas usan parámetros nombrados
 * (nunca se concatena texto del usuario) y las transacciones hacen rollback
 * si algo falla.
 */
public class CategoriaJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private void cerrar(EntityManager em) {
        if (em != null) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public void create(Categoria categoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } finally {
            cerrar(em);
        }
    }

    /**
     * Actualiza nombre y descripción. Si el nombre cambia, actualiza también los
     * productos que usaban el nombre anterior para que no queden huérfanos.
     */
    public void edit(Categoria categoria) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria actual = em.find(Categoria.class, categoria.getIdcategoria());
            if (actual == null) {
                throw new NonexistentEntityException("La categoría con id " + categoria.getIdcategoria() + " ya no existe.");
            }
            String nombreAnterior = actual.getNombre();
            actual.setNombre(categoria.getNombre());
            actual.setDescripcion(categoria.getDescripcion());
            if (!nombreAnterior.equals(categoria.getNombre())) {
                Query q = em.createQuery(
                        "UPDATE Producto p SET p.categoria = :nuevo WHERE LOWER(p.categoria) = LOWER(:anterior)");
                q.setParameter("nuevo", categoria.getNombre());
                q.setParameter("anterior", nombreAnterior);
                q.executeUpdate();
            }
            em.getTransaction().commit();
        } finally {
            cerrar(em);
        }
    }

    /** Elimina la categoría solo si ningún producto la usa. */
    public void destroy(int id) throws NonexistentEntityException, ReglaNegocioException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, id);
            if (categoria == null) {
                throw new NonexistentEntityException("La categoría con id " + id + " ya no existe.");
            }
            Query q = em.createQuery("SELECT COUNT(p) FROM Producto p WHERE LOWER(p.categoria) = LOWER(:nombre)");
            q.setParameter("nombre", categoria.getNombre());
            long enUso = ((Number) q.getSingleResult()).longValue();
            if (enUso > 0) {
                throw new ReglaNegocioException("No se puede eliminar: hay " + enUso
                        + " producto(s) que usan esta categoría.");
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            cerrar(em);
        }
    }

    public List<Categoria> findCategoriaEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Categoria findCategoria(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    /** Indica si ya existe otra categoría con ese nombre (sin distinguir mayúsculas). */
    public boolean existeNombre(String nombre, int idExcluir) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery(
                    "SELECT COUNT(c) FROM Categoria c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.idcategoria <> :id");
            q.setParameter("nombre", nombre);
            q.setParameter("id", idExcluir);
            return ((Number) q.getSingleResult()).longValue() > 0;
        } finally {
            em.close();
        }
    }
}
