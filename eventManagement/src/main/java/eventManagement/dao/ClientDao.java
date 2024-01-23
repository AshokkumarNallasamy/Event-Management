package eventManagement.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import eventManagement.dto.Client;

public class ClientDao {
	public EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("ashok").createEntityManager();
	}
	public EntityTransaction getEntityTransaction() {
		return getEntityManager().getTransaction();
	}
	public Client saveClient(Client client) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(client);
		et.commit();
		return client;
	}
	
	public Client findClient(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		Client exClient = em.find(Client.class, id);
		if(exClient != null) {
			return exClient;
		}
		return null;
	}
	
	public Client deleteClientById(int id ) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		Client exClient = em.find(Client.class, id);
		if(exClient != null) {
			et.begin();
			em.remove(exClient);
			et.commit();
			return exClient;
		}
		return null;
	}
	
	public Client updateClient(Client client ,int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		Client exClient = em.find(Client.class, id);
		if(exClient != null ) {
			exClient.setClientId(exClient.getClientId());
			et.begin();
			em.merge(client);
			et.commit();
			return client;
		}
		return null;
	}
}
