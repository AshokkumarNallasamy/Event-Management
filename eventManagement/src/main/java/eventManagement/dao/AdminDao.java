package eventManagement.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import eventManagement.dto.Admin;

public class AdminDao {

	public EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("ashok").createEntityManager();
	}
	public Admin saveAdmin(Admin admin) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		em.persist(admin);
		et.commit();
		return admin;
	}
	
	public Admin findAdmin(int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et =em.getTransaction();
		Admin exAdmin = em.find(Admin.class, id);
		if(exAdmin != null) {
			return exAdmin;
		}
		return null;
	}
	
	public Admin deleteAdminById(int id ) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		Admin exAdmin = em.find(Admin.class, id);
		if(exAdmin != null) {
			et.begin();
			em.remove(exAdmin);
			et.commit();
			return exAdmin;
		}
		return null;
	}
	
	public Admin updateAdmin(Admin admin , int id) {
		EntityManager em = getEntityManager();
		EntityTransaction et = em.getTransaction();
		if(admin != null ) {
			et.begin();
			Admin updateAdmin = em.merge(admin);
			et.commit();
			return updateAdmin;
		}
		return null;
	}
}
