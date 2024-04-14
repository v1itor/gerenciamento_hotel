package br.udesc.hospedagem.hoteis.dao;

import java.util.List;

import br.udesc.hospedagem.hoteis.model.Cliente;

public interface ClienteDAO {

	void atualizarCliente(Cliente cliente);

	public void cadastrarCliente(Cliente cliente);

	void deletarCliente(Integer clienteId);

	public List<Cliente> listarCliente();

}
