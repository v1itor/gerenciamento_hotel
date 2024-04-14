package br.udesc.hospedagem.hoteis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_detalhe")
public class ReservaDetalhe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_detalhe_id")
	private int reservaDetalheId;

	@Column(name = "reserva_id")
	private int reservaId;

	@Column(name = "quarto_id")
	private int quartoId;

	@Column(name = "cliente_id")
	private int clienteId;

	public int getClienteId() {
		return this.clienteId;
	}

	public int getQuartoId() {
		return this.quartoId;
	}

	public int getReservaId() {
		return this.reservaId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public void setQuartoId(int quartoId) {
		this.quartoId = quartoId;
	}

	public void setReservaId(int reservaId) {
		this.reservaId = reservaId;
	}
	
	@Override
	public String toString() {
	    return "Reserva Detalhe:\n" +
	            "  Reserva ID: " + reservaId + "\n" +
	            "  Quarto ID: " + quartoId + "\n" +
	            "  Cliente ID: " + clienteId + "\n" +
	    		"  ID do vínculo da reserva: " + reservaDetalheId + "\n";
	}
}
