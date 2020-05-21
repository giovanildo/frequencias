package com.giovanildo.dao;

public class PopulaBanco {

	public static void buscaEmCascata() {
//		EntityManager em = JpaUtils.getEntityManager();
//
//		String jpql = "select t from Torneio t join t.competidores c where c = :pCompetidor";
//		
//		Competidor competidor = new Competidor();
//		competidor.setId(10);
//		
//		TypedQuery<Torneio> query = em.createQuery(jpql, Torneio.class);
//		query.setParameter("pCompetidor", competidor);
//		
//		List<Torneio> lista = query.getResultList();
//		
//		for (Torneio daVez : lista) {
//			System.out.println(daVez.getNome());
//			System.out.println(daVez.getId());
//			for (Competidor competidor1 : daVez.getCompetidores()) {
//				System.out.println(competidor1.getClube());
//			}
//		}
//		
//	}
//	
//	public static void listandoDados() {
//		for (Clube daVez : new DAO<Clube>(Clube.class).listaTodos()) {
//			System.out.println(daVez.toString());
//		}
//
//		for (EAtleta daVez : new DAO<EAtleta>(EAtleta.class).listaTodos()) {
//			System.out.println(daVez.toString());
//		}
//
//		for (Torneio daVez : new DAO<Torneio>(Torneio.class).listaTodos()) {
//			System.out.println(daVez.toString());
//			System.out.println(daVez.getId());
//		}
//	}

//	public static List<Competidor> adicionarTorneios() {
//		// adicionando Torneio/Competidores
//
//		Torneio torneio = new Torneio();
//		torneio.setNome("Cachorro na rua");
//		torneio.setPorqueDoNome("correndo ");
//
//		List<Competidor> competidores = new ArrayList<>();
//
//		competidores.add(new Competidor(torneio, eaService.byString("Giovanildo"), clubeService.byString("Porto")));
//		competidores.add(new Competidor(torneio, eaService.byString("Genilson"), clubeService.byString("Manchester")));
//		competidores.add(new Competidor(torneio, eaService.byString("Fabiano"), clubeService.byString("Real")));
//		competidores.add(new Competidor(torneio, eaService.byString("Zaldir"), clubeService.byString("Borrusia")));
//
//		torneio.setCompetidores(competidores);
//
//		torneioService.insert(torneio);
//
//		return competidores;
//	}
//
//	public static void adicionarEatletas() {
//		eaService.insert(new EAtleta("Giovanildo"));
//		eaService.insert(new EAtleta("Genilson"));
//		eaService.insert(new EAtleta("Fabiano"));
//		eaService.insert(new EAtleta("Zaldir"));
//
//	}
//
//	public static void adicionarClubes() {
//		clubeService.insert(new Clube("Borrusia", "Alemanha"));
//		clubeService.insert(new Clube("Real", "Espanha"));
//		clubeService.insert(new Clube("Manchester", "Inglaterra"));
//		clubeService.insert(new Clube("Porto", "Portugal"));
//	}
	}
}
