package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.HotelDAO;
import br.udesc.hospedagem.hoteis.model.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class HotelDAOImpl implements HotelDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarHotel(Hotel hotel) {
		String query = "UPDATE hotel SET nome = :nome, endereco_id = :enderecoId, classificacao = :classificacao, data_construcao = :dataConstrucao WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(query).setParameter("nome", hotel.getNome())
		.setParameter("enderecoId", hotel.getEnderecoId())
		.setParameter("classificacao", hotel.getClassificacao())
		.setParameter("dataConstrucao", hotel.getDataConstrucao())
		.setParameter("hotelId", hotel.getHotelId())
		.executeUpdate();
	}

	@Override
	@Transactional
	public void cadastrarHotel(Hotel hotel) {
		String query = "INSERT INTO hotel (nome, endereco_id, classificacao, data_construcao) "
				+ "VALUES (:nome, :enderecoId, :classificacao, :dataConstrucao)";

		this.entityManager.createNativeQuery(query).setParameter("nome", hotel.getNome())
		.setParameter("enderecoId", hotel.getEnderecoId())
		.setParameter("classificacao", hotel.getClassificacao())
		.setParameter("dataConstrucao", hotel.getDataConstrucao()).executeUpdate();
	}

	@Override
	@Transactional
	public void deletarHotel(Integer hotelId) {
		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE quarto_id IN (SELECT quarto_id FROM quarto WHERE hotel_id = :hotelId)";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("hotelId", hotelId).executeUpdate();

		String queryQuarto = "DELETE FROM quarto WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(queryQuarto).setParameter("hotelId", hotelId).executeUpdate();

		String query = "DELETE FROM hotel WHERE hotel_id = :hotelId";
		this.entityManager.createNativeQuery(query).setParameter("hotelId", hotelId).executeUpdate();
	}

	@Override
	public List<Hotel> listarHotel() {
		String query = "SELECT * FROM hotel";
		return this.entityManager.createNativeQuery(query, Hotel.class).getResultList();
	}

	@Override
	public List<Hotel> listarHotelComQuartosDisponiveis() {
		String query = "SELECT h.* FROM hotel h JOIN quarto q ON h.hotel_id = q.hotel_id WHERE q.disponibilidade = true";
		return this.entityManager.createNativeQuery(query, Hotel.class).getResultList();
	}
}
