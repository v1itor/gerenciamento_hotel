package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.QuartoDAO;
import br.udesc.hospedagem.hoteis.model.Quarto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class QuartoDAOImpl implements QuartoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarQuarto(Quarto quarto) {
		boolean possuiTipoQuarto = quarto.getTipoQuarto() != null;
		String queryStr = "UPDATE quarto SET ";
		if (possuiTipoQuarto) {
			queryStr += "tipo_quarto_id = :tipoQuartoId, ";
		}
		queryStr += "hotel_id = :hotelId, numero = :numero, disponibilidade = :disponibilidade, preco = :preco WHERE quarto_id = :quartoId";

		Query query = this.entityManager.createNativeQuery(queryStr);

		if (possuiTipoQuarto) {
			query.setParameter("tipoQuartoId", quarto.getTipoQuarto().getTipoQuartoId());
		}
		query.setParameter("hotelId", quarto.getHotelId());
		query.setParameter("numero", quarto.getNumero());
		query.setParameter("disponibilidade", quarto.getDisponibilidade());
		query.setParameter("preco", quarto.getPreco());
		query.setParameter("quartoId", quarto.getQuartoId());
		query.executeUpdate();
	}

	@Override
	public Quarto buscaQuartoPorId(Integer quartoId) {
		String queryStr = "SELECT * FROM quarto WHERE quarto_id = :quartoId";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		query.setParameter("quartoId", quartoId);
		List<Quarto> listaResultado = query.getResultList();
		if (listaResultado.size() == 0) {
			return null;
		}
		return listaResultado.get(0);
	}

	@Override
	public List<Quarto> buscarQuartoDisponivelPorHotelId(Integer hotelId, Date dataEntrada, Date dataSaida) {
		String queryStr = "SELECT * FROM quarto WHERE hotel_id = :hotelId AND disponibilidade = true and "
				+ "quarto_id NOT IN (SELECT quarto_id FROM reserva WHERE (:dataEntrada between data_inicio and data_fim) or (:dataSaida between data_inicio and data_fim))";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		query.setParameter("hotelId", hotelId);
		query.setParameter("dataEntrada", dataEntrada);
		query.setParameter("dataSaida", dataSaida);
		List<Quarto> listaResultado = query.getResultList();

		return listaResultado;
	}

	@Override
	@Transactional
	public void cadastrarQuarto(Quarto quarto) {
		boolean possuiTipoQuarto = quarto.getTipoQuarto() != null;
		String queryStr = "INSERT INTO quarto (hotel_id, ";
		if (possuiTipoQuarto) {
			queryStr += "tipo_quarto_id, ";
		}
		queryStr += "numero, disponibilidade, preco) VALUES (:hotelId, ";
		if (possuiTipoQuarto) {
			queryStr += ":tipoQuartoId, ";
		}
		queryStr += ":numero, :disponibilidade, :preco)";

		Query query = this.entityManager.createNativeQuery(queryStr);

		if (possuiTipoQuarto) {
			query.setParameter("tipoQuartoId", quarto.getTipoQuarto().getTipoQuartoId());
		}
		query.setParameter("hotelId", quarto.getHotelId());
		query.setParameter("numero", quarto.getNumero());
		query.setParameter("disponibilidade", quarto.getDisponibilidade());
		query.setParameter("preco", quarto.getPreco());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void deletarQuarto(Integer quartoId) {

		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE quarto_id  = :quartoId";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("quartoId", quartoId).executeUpdate();

		String query = "DELETE FROM quarto WHERE quarto_id = :quartoId";
		this.entityManager.createNativeQuery(query).setParameter("quartoId", quartoId).executeUpdate();
	}

	@Override
	public List<Quarto> listarQuarto() {
		String queryStr = "SELECT * FROM quarto";
		Query query = this.entityManager.createNativeQuery(queryStr, Quarto.class);
		List<Quarto> listaResultado = query.getResultList();
		return listaResultado;
	}

}
