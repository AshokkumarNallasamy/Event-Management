package eventManagement.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import eventManagement.dto.ClientEvent;

public class ClientEventDao {
	public EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("ashok").createEntityManager();
	}
	public EntityTransaction getEntityTransaction() {
		return getEntityManager().getTransaction();
	}
	public ClientEvent saveClientEvent(ClientEvent clientEvent) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(clientEvent);
		et.commit();
		return clientEvent;
	}
	
	public ClientEvent findClientEvent(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent != null) {
			return exClientEvent;
		}
		return null;
	}
	
	public ClientEvent deleteClientEventById(int id ) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent != null) {
			et.begin();
			em.remove(exClientEvent);
			et.commit();
			return exClientEvent;
		}
		return null;
	}
	
	public ClientEvent updateClientEvent(ClientEvent clientEvent ,int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = getEntityTransaction();
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent != null ) {
			exClientEvent.setClientEventId(exClientEvent.getClientEventId());
			et.begin();
			em.merge(clientEvent);
			et.commit();
			return clientEvent;
		}
		return null;
	}
}
