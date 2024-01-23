package eventManagement.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import eventManagement.dao.AdminDao;
import eventManagement.dao.ClientDao;
import eventManagement.dao.ClientEventDao;
import eventManagement.dao.ClientServiceDao;
import eventManagement.dao.ServiceDao;
import eventManagement.dto.Admin;
import eventManagement.dto.Client;
import eventManagement.dto.ClientEvent;
import eventManagement.dto.ClientService;
import eventManagement.dto.EventType;
import eventManagement.dto.Service;

public class EventManagement {
	
	AdminDao aDao = new AdminDao();
	ClientDao cDao = new ClientDao();
	ClientEventDao ceDao = new ClientEventDao();
	ClientServiceDao csDao = new ClientServiceDao();
	ServiceDao sDao = new ServiceDao();
	
	
	public static void main(String[] args) {
		
		EventManagement em = new EventManagement();
//		System.out.println(em.saveAdmin());
		
//		System.out.println(em.adminLogin());
		
//		System.out.println(em.saveService());
		
//		System.out.println(em.updateService());
//		System.out.println(em.deleteService());

//		System.out.println(em.saveClient());
//		System.out.println(em.clientLogin());
		
		
	}
	
	public Admin saveAdmin() {
		Admin admin = new Admin();
		System.out.println("\tADMIN SIGNUP");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Admin Name: ");
		admin.setAdminName(s.nextLine());
		System.out.println("Enter Admin Email: ");
		admin.setAdminEmail(s.next());
		System.out.println("Enter Admin Password: ");
		admin.setAdminPassword(s.next());
		System.out.println("Enter Admin Conatct: ");
		admin.setAdminContact(s.nextLong());
		
		return aDao.saveAdmin(admin);
	}
	
	
	public Admin adminLogin() {
		System.err.println("\tADMIN LOGIN");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Email: ");
		String  email = s.next();
		System.out.println("Enter Password: ");
		String password = s.next();
		
		EntityManager em = aDao.getEntityManager();
		
		String jpql = "Select admin from Admin admin where admin.adminEmail = ?1";
		
		Query q = em.createQuery(jpql);
		q.setParameter(1, email);
		
		Admin admin = (Admin) q.getSingleResult();
		if(admin!=null)
		{
			if(admin.getAdminPassword().equals(password)) 
			{
				return admin;
			}
			else
			{
				System.out.println("Invalid Password");
				return null;
			}
		}
		return null;
	}
	
	public Service saveService() {
		Admin a = adminLogin();		 
		if(a!=null) {
					 System.err.println("\tSAVE SERVICE");
					 System.err.println("\t---- -------");
					 
					 Service service = new Service();
					 Scanner s = new Scanner(System.in);
					 System.out.println("Enter Service Name : ");
					 service.setServiceName(s.nextLine());
					 System.out.println("Enter Service Cost Per Day : ");
					 service.setServiceCostPerDay(s.nextDouble());
					 System.out.println("Enter Service Cost Per Person : ");
					 service.setServiceCostPerPerson(s.nextDouble());
					 
					Service savedService = sDao.saveService(service);
					a.getServices().add(savedService);
					Admin x=aDao.updateAdmin(a,a.getAdminId());
					System.out.println(x);
					return service;
				 
		}
		return null;
				 
	}
	public List<Service> findAllServices(Admin admin) {
		List<Service> services = admin.getServices();
		return services;
	}
	public Service updateService() {
		Admin exAdmin = adminLogin();
		if(exAdmin!=null) {
			List<Service> services = findAllServices(exAdmin);
			if(services!=null) {
				System.out.println("\n\nServiceId\t\tServiceName\t\tCostPerDay\t\tCostPerPerson");
				for(Service s : services) {
					System.out.println(s.getServiceId()+"\t\t"+s.getServiceName()+"\t\t"+s.getServiceCostPerDay()+"\t\t"+s.getServiceCostPerPerson());
				}
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter The Id Of the Service You Want to Update: ");
				int id = sc.nextInt();
				sc.nextLine();
				Service exService = sDao.findService(id);
				System.out.println("Enter New Name: ");
				exService.setServiceName(sc.nextLine());
				System.out.println("Enter New Cost Per Day: ");
				exService.setServiceCostPerDay(sc.nextDouble());
				System.out.println("Enter New Cost Per Person: ");
				exService.setServiceCostPerPerson(sc.nextDouble());
				Service upService = sDao.updateService(exService, id);
				return upService;
			}
			return null;
		}
		return null;
	}
	
	public Service  deleteService() {
		Admin exAdmin = adminLogin();
		if(exAdmin!=null) {
			List<Service> services = findAllServices(exAdmin);
			if(services!=null) {
				System.out.println("\n\nServiceId\t\tServiceName\t\tCostPerDay\t\tCostPerPerson");
				for(Service s : services) {
					System.out.println(s.getServiceId()+"\t\t"+s.getServiceName()+"\t\t"+s.getServiceCostPerDay()+"\t\t"+s.getServiceCostPerPerson());
				}
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter The Id Of the Service You Want to Delete: ");
				int sId = sc.nextInt();
				
				Service delService = sDao.findService(sId);
				services.remove(delService);
				
				exAdmin.setServices(services);
				aDao.updateAdmin(exAdmin, exAdmin.getAdminId());
				
				return sDao.deleteServiceById(sId);
				
			}
			return null;
		}
		return null;
	}
	
	public Client saveClient() {
		
		Client client = new Client();
		System.out.println("\tCLIENT SIGNUP");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Client Name: ");
		client.setClientName(s.nextLine());
		System.out.println("Enter Client Email: ");
		client.setClientEmail(s.next());
		System.out.println("Enter Client Conatct: ");
		client.setClientContact(s.nextLong());
		System.out.println("Enter Client Password: ");
		client.setClientPassword(s.next());
		
		return cDao.saveClient(client);
	}
	
	public Client clientLogin() {
		System.err.println("\tCLIEN LOGIN");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Email: ");
		String  email = s.next();
		System.out.println("Enter Password: ");
		String password = s.next();
		
		EntityManager em = aDao.getEntityManager();
		
		String jpql = "Select client from Client client where client.clientEmail = ?1";
		
		Query q = em.createQuery(jpql);
		q.setParameter(1, email);
		
		Client client = (Client) q.getSingleResult();
		if(client!=null)
		{
			if(client.getClientPassword().equals(password)) 
			{
				return client;
			}
			else
			{
				System.out.println("Invalid Password");
				return null;
			}
		}
		return null;
	}
	
	public EventType getEventType() {
		System.out.println("*".repeat(20)+"Types Of Events"+"*".repeat(20));
		
		System.out.println("\t1.Marriage");
		System.out.println("\t2.Engagement");
		System.out.println("\t3.BirthDay");
		System.out.println("\t4.Anniversary");
		System.out.println("\t5.BabyShower");
		System.out.println("\t6.Reunion");
		System.out.println("\t7.NamingCeremony");
		System.out.println("\t8.BachelorParty");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\t\tSelect the Event Number You Want ");
		int n = sc.nextInt();
		switch (n) {
		case 1 : return EventType.Marriage;
		case 2 : return EventType.Engagement;
		case 3 : return EventType.BirthDay;
		case 4 : return EventType.Anniversary;
		case 5 : return EventType.BabyShower;
		case 6 : return EventType.Reunion;
		case 7 : return EventType.NamingCeremony;
		case 8 : return EventType.BachelorParty;
		default: return null;
		}
	}
	
	public LocalDate getLocalDate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Date of Service to Start: ");
		int date = sc.nextInt();
		System.out.println("Enter the month of Service");
		int month = sc.nextInt();
		return LocalDate.of(2024, month, date);
	}
	public ClientEvent createClientEvent() {
		Client client = clientLogin();
		ClientEvent ce = new ClientEvent();
		if(client!=null) {
			EventType e = getEventType();
			if(e!=null) {
				List<Service> services = sDao.getAllServices();
				if(services!=null) {
					System.out.println("\n\nServiceId\t\tServiceName\t\tCostPerDay\t\tCostPerPerson");
					for(Service s : services) {
						System.out.println(s.getServiceId()+"\t\t"+s.getServiceName()+"\t\t"+s.getServiceCostPerDay()+"\t\t"+s.getServiceCostPerPerson());
					}
					Scanner sc = new Scanner(System.in);
					System.out.println("Select the Service Id to add to the Event:");
					int  sId= sc.nextInt();
					Service s = sDao.findService(sId);
					if(s!=null) {
						System.out.println("Enter the Number of People for the Service:"+s.getServiceName());
						int noOfPeople = sc.nextInt();
						System.out.println("Enter the Number of Days for the Service:"+s.getServiceName());
						int noOfDays = sc.nextInt();
						LocalDate ld = getLocalDate();
						System.out.println("Enter the Location: ");
						String location = sc.next();
						ce.setEventType(e);
						ce.setClientEventNoOfDays(noOfDays);
						ce.setClientEventNoOfPeople(noOfPeople);
						ce.setClientEventLocation(location);
						ce.setStartDate(ld);
						ce.setClient(client);
						
						List<ClientService> cServices = new ArrayList<ClientService>();
						ClientService cs = new ClientService();
						cs.setClientServiceCostPerPerson(s.getServiceCostPerPerson());
						cs.setClientServiceNoOfDays(noOfDays);
						cs.setClientServiceName(s.getServiceName());
						cServices.add(cs);
						ce.setClientServices(cServices);
						
						List<ClientEvent> cEvents = new ArrayList<ClientEvent>();
						cEvents.add(ce);
						client.setEvents(cEvents);
						cDao.updateClient(client, client.getClientId());
						return ce;
					}
				}
		    }
	   }
		return null;
    }
	
	public ClientService addClientService() {
		Client client = clientLogin();
		Scanner sc = new Scanner(System.in);
		EventType e = null;
		int ceType = -1;
		ClientEvent ce = null;
		if(client != null) {
		
		System.out.println("The Available events present in client "+client.getClientName()+" is shown below");
		System.out.println("-".repeat(10)+"ID"+"-".repeat(10)+"EventType"+"-".repeat(10));
		
		for(int i=0;i<client.getEvents().size();i++) {
		int a=i+1;
			System.out.println("-".repeat(10)+client.getEvents().get(i).getClientEventId()+"-".repeat(7)+client.getEvents().get(i).getEventType()+"-".repeat(10));
		}
		 ceType=sc.nextInt();
		 ce = ceDao.findClientEvent(ceType);
		if(ce!=null) {
			System.out.println("-".repeat(15)+"AVAILABLE SERVICES"+"-".repeat(15));
			List<Service> services = sDao.getAllServices();
			if(services!=null) {
				System.out.println("\n\nServiceId\t\tServiceName\t\tCostPerDay\t\tCostPerPerson");
				for(Service s : services) {
					System.out.println(s.getServiceId()+"\t\t"+s.getServiceName()+"\t\t"+s.getServiceCostPerDay()+"\t\t"+s.getServiceCostPerPerson());
				}
			
			
			System.out.println("select the id to Add the Service");
			int id=sc.nextInt();
			
			Service s=sDao.findService(id);
			if(s!=null) {
			
			System.out.println("Enter number of days to provide service for Service :"+s.getServiceName());
			int noOfDays=sc.nextInt();
			ClientService cs=new ClientService();
			cs.setClientServiceNoOfDays(noOfDays);
			cs.setClientServiceCostPerPerson(s.getServiceCostPerPerson());
			cs.setClientServiceName(s.getServiceName());
			List<ClientService> cServices = new ArrayList<ClientService>();
			cServices.add(cs);
 			ce.setClientServices(cServices);
			ceDao.updateClientEvent(ce, ce.getClientEventId());
			return cs;
		}
		
		}

	
}
	}
		return null;
	}
public ClientService removeClientService() {
	Client c = clientLogin();
	ClientEvent ce = null;
	EventType e = null;
	int ceType;
	Scanner sc = new Scanner(System.in);
	if(c!=null) {
		
		System.out.println("The Available events present in client "+c.getClientName()+" is shown below");
		System.out.println("-".repeat(10)+"ID"+"-".repeat(10)+"EventType"+"-".repeat(10));
		
		for(int i=0;i<c.getEvents().size();i++) {
		int a=i+1;
			System.out.println("-".repeat(10)+c.getEvents().get(i).getClientEventId()+"-".repeat(7)+c.getEvents().get(i).getEventType()+"-".repeat(10));
		}
		 ceType=sc.nextInt();
		ce=ceDao.findClientEvent(ceType);
		if(ce!=null) {
			System.out.println("The Services Present in the selected Events are ");
			System.out.println("-".repeat(15)+"ID"+"-".repeat(15)+"Service"+"-".repeat(15));
			List<ClientService>services=ce.getClientServices();
			for(ClientService cli:ce.getClientServices()) {
				System.out.println("-".repeat(15)+cli.getClientServiceId()+"-".repeat(15)+cli.getClientServiceName()+"-".repeat(15));
			}
			System.out.println("enter the ID to delete the Service");
			int u=sc.nextInt();
			ClientService clientService=csDao.findClientService(u);
			if(clientService != null) {
				services.remove(clientService);
				ce.setClientServices(services);
				ceDao.updateClientEvent(ce, ce.getClientEventId());
				return clientService;
			}
			
		}

	}
	return null;
}
}























