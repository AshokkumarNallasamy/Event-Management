package eventManagement.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import eventManagement.dto.ClientService;

public class ClientServiceDao {

	public EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("ashok").createEntityManager();
	}
	public EntityTransaction getEntityTransaction() {
		return getEntityManager().getTransaction();
	}
	public ClientService saveClientService(ClientService clientService) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(clientService);
		et.commit();
		return clientService;
	}
	
	public ClientService findClientService(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null) {
			return exClientService;
		}
		return null;
	}
	
	public ClientService deleteClientServiceById(int id ) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null) {
			et.begin();
			em.remove(exClientService);
			et.commit();
			return exClientService;
		}
		return null;
	}
	
	public ClientService updateClientService(ClientService clientService ,int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null ) {
			exClientService.setClientServiceId(exClientService.getClientServiceId());
			et.begin();
			em.merge(clientService);
			et.commit();
			return clientService;
		}
		return null;
	}
}
