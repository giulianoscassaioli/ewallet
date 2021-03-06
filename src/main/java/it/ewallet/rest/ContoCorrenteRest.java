package it.ewallet.rest;

import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.ewallet.entity.ContoCorrente;
import it.ewallet.entity.Movimento;



//link generale per eseguire le azioni su post man----->>> http://localhost:8080/ewallet/rest/conto/
//PRELIEVO----->>>   impostando POST su postman poi --->> http://localhost:8080/ewallet/rest/conto/preleva
//DEPOSITO----->>>   impostando POST su postman poi --->> http://localhost:8080/ewallet/rest/conto/deposita
//Per creare un conto bisogna passare i campi iban,intestatario,saldo, al json su postMan
//meno che la dataCreazione che viene generata in automantico
//***********ES CREAZIONE CONTO**********
/*{
"iban":"iban del conto",
"saldo":importo da detrarre o aggiungere,
"intestatario":"nome del intestatario"

}*/
//stessa cosa anche quando si fanno dei prelievi o depositi sui conti
//la data dei singoli movimenti viene generata in automatico
//Quando si passa il movimento su postman va passato solamente l'importo e l'iban 
//senza passare anche il campo operazione che si setta in automatico una volta
//impostato l'url del operazione. esempio per prelievo --->>POST http://localhost:8080/ewallet/rest/conto/preleva
// e poi
////****PER PRELEVARE O DEPOSITARE
/*{
	"iban":"iban da selezionare",
	"importo":importo da detrarre o aggiungere
	
}*/



@Path("/conto")
public class ContoCorrenteRest {

	private static ArrayList<ContoCorrente> conti = new ArrayList<>();
	
	@GET
	@Path("/{iban}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContoCorrente getContoCorrente(@PathParam("iban") String iban) {

		System.out.println(iban);
		for (ContoCorrente con : conti) {

			if (con.getIban().equals(iban)) {

				return con;
			}
		}
		
		ContoCorrente c =new ContoCorrente();
		c.setIban("il conto corrente che hai cercato non esiste");
		c.setDataCreazione(null);
		return c;

	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContoCorrente> getAllConti() {

		return conti;

	}

	@DELETE
	@Path("/{iban}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeContoCorrente(@PathParam("iban") String iban) {

		for (ContoCorrente con : conti) {

			if (con.getIban().equals(iban)) {

				conti.remove(con);

				return Response.status(200).entity("rimozione conto corrente avenuta con successo").build();
			}

		}

		return Response.status(404).entity("conto corrente non trovato").build();

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateContoCorrente(ContoCorrente con) {

		for (ContoCorrente c : conti) {

			if (c.getIban().equals(con.getIban())) {

				int index = conti.lastIndexOf(c);

				conti.set(index, con);

				return Response.status(200).entity("aggiornamento conto corrente avenuto con successo").build();

			}

		}

		return Response.status(404).entity("conto corrente non trovato").build();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertContoCorrente(ContoCorrente con) {
		for (ContoCorrente c: conti) {
			if(c.getIban().equals(con.getIban())) {
				
				return Response.status(404).entity("il conto gia esiste").build();
				
			}
			
		}
		conti.add(con);
		return Response.status(201).entity("creazione conto avvenuto con successo").build();
		
	}
	
	

	@POST
	@Path("/preleva")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response preleva(Movimento mov) {

		if(mov.getImporto()==0) {
			return Response.status(400).entity("devi inserire un importo superiore a 0").build();
		}
		
		if(mov.getImporto()<0) {
			return Response.status(400).entity("inserire un importo non negativo").build();
		}
		
		Movimento m = null;

		for (ContoCorrente c : conti) {
			if (c.getIban().equals(mov.getIban())) {
				if (c.getSaldo() >= mov.getImporto()) {

					m = new Movimento();
					m.setDataMov(mov.getDataMov());
					m.setImporto(mov.getImporto());
                    m.setTipoMov("prelievo");
                    m.setIban(c.getIban());
					c.setSaldo(c.getSaldo() - mov.getImporto());
					c.getMovimenti().add(m);

					return Response.status(200).entity("prelievo effettuato! saldo attuale: "+c.getSaldo()).build();
				} else {

					return Response.status(400).entity("mancanza di fondi sufficenti sul conto").build();

				}

			}

		}

		return Response.status(404).entity("conto corrente non esistente").build();
	}

	@POST
	@Path("/deposita")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response depostita(Movimento mov) {
		if(mov.getImporto()==0) {
			return Response.status(400).entity("devi inserire un importo superiore a 0").build();
		}
		
		if(mov.getImporto()<0) {
			return Response.status(400).entity("inserire un importo non negativo").build();
		}
		
		Movimento m=null;
		for (ContoCorrente c : conti) {
			if (c.getIban().equals(mov.getIban())) {

				m = new Movimento();
				m.setDataMov(mov.getDataMov());
				m.setImporto(mov.getImporto());
                m.setTipoMov("deposito");
                m.setIban(c.getIban());
				c.setSaldo(c.getSaldo() + m.getImporto());
				c.getMovimenti().add(m);

				return Response.status(200).entity("deposito effettuato! saldo attuale: "+c.getSaldo()).build();
			}

		}

		return Response.status(404).entity("conto corrente non esistente").build();
	}

}
