package br.udesc.hospedagem.hoteis.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.udesc.hospedagem.hoteis.dao.ClienteDAO;
import br.udesc.hospedagem.hoteis.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ClienteDAOImpl implements ClienteDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void atualizarCliente(Cliente cliente) {
		String query = "UPDATE cliente SET nome = :nome, cpf = :cpf, email = :email, telefone = :telefone, endereco_id = :enderecoId WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(query).setParameter("nome", cliente.getNome())
		.setParameter("cpf", cliente.getCpf()).setParameter("email", cliente.getEmail())
		.setParameter("telefone", cliente.getTelefone()).setParameter("enderecoId", cliente.getEnderecoId())
		.setParameter("clienteId", cliente.getClienteId()).executeUpdate();
	}

	@Override
	@Transactional
	public void cadastrarCliente(Cliente cliente) {
		String query = "INSERT INTO cliente (nome, cpf, email, telefone, endereco_id) "
				+ "VALUES (:nome, :cpf, :email, :telefone, :enderecoId)";

		this.entityManager.createNativeQuery(query).setParameter("nome", cliente.getNome())
		.setParameter("cpf", cliente.getCpf()).setParameter("email", cliente.getEmail())
		.setParameter("telefone", cliente.getTelefone()).setParameter("enderecoId", cliente.getEnderecoId())
		.executeUpdate();
	}

	@Transactional
	@Override
	public void deletarCliente(Integer clienteId) {
		String queryReservaDetalhe = "DELETE FROM reserva_detalhe WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(queryReservaDetalhe).setParameter("clienteId", clienteId).executeUpdate();

		String query = "DELETE FROM cliente WHERE cliente_id = :clienteId";
		this.entityManager.createNativeQuery(query).setParameter("clienteId", clienteId).executeUpdate();
	}

	@Override
	public List<Cliente> listarCliente() {
		String query = "SELECT * FROM cliente order by cliente_id desc";
		return this.entityManager.createNativeQuery(query, Cliente.class).getResultList();
	}
}
