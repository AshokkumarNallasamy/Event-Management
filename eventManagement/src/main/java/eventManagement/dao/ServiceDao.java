package eventManagement.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import eventManagement.dto.Admin;
import eventManagement.dto.Service;

public class ServiceDao {
	public EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("ashok").createEntityManager();
	}
	public Service saveService(Service service) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(service);
		et.commit();
		return service;
	}
	
	public Service findService(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et =em.getTransaction();
		Service exService = em.find(Service.class, id);
		if(exService != null) {
			return exService;
		}
		return null;
	}
	public List<Service> getAllServices(){
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		String jpql = "Select service from Service service";
		
		Query q = em.createQuery(jpql);
		
		List<Service> services = (List) q.getResultList();
		return services;
	}
	public Service deleteServiceById(int id ) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Service exService = em.find(Service.class, id);
		if(exService != null) {
			et.begin();
			em.remove(exService);
			et.commit();
			return exService;
		}
		return null;
	}
	
	public Service updateService(Service service ,int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		if(service != null ) {
			service.setServiceId(service.getServiceId());
			et.begin();
			em.merge(service);
			et.commit();
			return service;
		}
		return null;
	}
}


