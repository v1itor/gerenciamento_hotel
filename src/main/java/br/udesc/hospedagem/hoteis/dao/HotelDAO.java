package br.udesc.hospedagem.hoteis.dao;

import java.util.List;

import br.udesc.hospedagem.hoteis.model.Hotel;

public interface HotelDAO {

	void atualizarHotel(Hotel hotel);

	public void cadastrarHotel(Hotel hotel);

	public void deletarHotel(Integer hotelId);

	public List<Hotel> listarHotel();

	List<Hotel> listarHotelComQuartosDisponiveis();
}
