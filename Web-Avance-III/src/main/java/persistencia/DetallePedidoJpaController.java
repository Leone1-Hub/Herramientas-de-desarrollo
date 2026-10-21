
package persistencia;
import clases.DetallePedido;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Fabrizio
 */
public class DetallePedidoJpaController implements Serializable {

    public DetallePedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallePedido detallePedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(detallePedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallePedido detallePedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            detallePedido = em.merge(detallePedido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = detallePedido.getId_detalle();
                if (findDetallePedido(id) == null) {
                    throw new NonexistentEntityException("The detallePedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallePedido detallePedido;
            try {
                detallePedido = em.getReference(DetallePedido.class, id);
                detallePedido.getId_detalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallePedido with id " + id + " no longer exists.", enfe);
            }
            em.remove(detallePedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallePedido> findDetallePedidoEntities() {
        return findDetallePedidoEntities(true, -1, -1);
    }

    public List<DetallePedido> findDetallePedidoEntities(int maxResults, int firstResult) {
        return findDetallePedidoEntities(false, maxResults, firstResult);
    }

    private List<DetallePedido> findDetallePedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallePedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetallePedido findDetallePedido(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallePedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallePedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallePedido> rt = cq.from(DetallePedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
