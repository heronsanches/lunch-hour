package lunch.hour.model.sb.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import lunch.hour.constants.PersistenceConstants;
import lunch.hour.model.entity.Professional;
import lunch.hour.model.entity.Restaurant;
import lunch.hour.model.entity.Voting;

/**
 *
 * @author Heron Sanches
 */
@Stateless
public class VotingDAO implements VotingDAOI {
   
   @PersistenceContext(unitName = PersistenceConstants.PERSISTENCE_UNIT_NAME)
   private EntityManager em;
   

   @Override
   public boolean insert(Voting vote) {
      
      try {
         em.persist(vote);
      } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e){
         return false;
      }
      
      return true;
      
   }


   @Override
   public boolean verifyIfVoted(String idProfessional, Date date){
      
      TypedQuery<Professional> tq = em.createNamedQuery("Voting.findByIdProfessionalTxtCpf_date", Professional.class);
      
      try{
         
         tq.setParameter("idProfessionalTxtCpf", idProfessional);
         tq.setParameter("idVotingDt", date);
         tq.getSingleResult();
         return true;
         
      }catch(NoResultException nre){
         
      }
      
      return false;
      
   }

   
   @Override
   public List<Restaurant> listRestaurantByDate(Date day) {
      
      try{
         
         TypedQuery<Restaurant> tq = em.createNamedQuery("Voting.findRestaurantsByDate", Restaurant.class);
         tq.setParameter("date", day);
         return (List<Restaurant>) tq.getResultList();
     
      }catch(NoResultException nre){
         
      }
      
      return null;

   }

   
}