package br.udesc.hospedagem.hoteis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.udesc.hospedagem.hoteis.dao.impl.ClienteDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.EnderecoDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.HotelDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.QuartoDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.ReservaDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.ReservaDetalheDAOImpl;
import br.udesc.hospedagem.hoteis.dao.impl.TipoQuartoDAOImpl;
import br.udesc.hospedagem.hoteis.model.Cliente;
import br.udesc.hospedagem.hoteis.model.Endereco;
import br.udesc.hospedagem.hoteis.model.Hotel;
import br.udesc.hospedagem.hoteis.model.Quarto;
import br.udesc.hospedagem.hoteis.model.Reserva;
import br.udesc.hospedagem.hoteis.model.ReservaDetalhe;

@Component
public class Menu {

	Scanner scanner;
	private HotelDAOImpl hotelDAOImpl;
	private QuartoDAOImpl quartoDAOImpl;
	private ClienteDAOImpl clienteDAOImpl;
	private ReservaDAOImpl reservaDAOImpl;
	private EnderecoDAOImpl enderecoDAOImpl;
	private TipoQuartoDAOImpl tipoQuartoDAOImpl;
	private ReservaDetalheDAOImpl reservaDetalheDAOImpl;

	public Menu() {
		super();
		this.scanner = new Scanner(System.in);
	}

	public void alteraHotel() {

	}

	public void atualizarCliente() {
		System.out.println("===== Atualizar Cliente =====");

		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		Cliente cliente = this.pedeDadosCliente();

		this.cadastraEndereco(cliente.getEnderecoId());

		cliente.setClienteId(clienteId);
		this.clienteDAOImpl.atualizarCliente(cliente);

		System.out.println("Cliente atualizado com sucesso!");
	}

	public void atualizarHotel() {
		System.out.println("===== Atualizar Hotel =====");

		System.out.println("Hoteis disponíveis: ");
		this.hotelDAOImpl.listarHotel().forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		Hotel hotel = this.pedeDadosHotel();

		Endereco endereco = this.cadastraEndereco(hotel.getEnderecoId());

		hotel.setEnderecoId(this.enderecoDAOImpl.buscarIdEnderecoPorAributos(endereco));

		hotel.setHotelId(hotelId);
		this.hotelDAOImpl.atualizarHotel(hotel);

		System.out.println("Hotel atualizado com sucesso!");
	}

	public void atualizarQuarto() {
		System.out.println("===== Atualizar Quarto =====");

		System.out.println("Quartos disponíveis: ");
		this.quartoDAOImpl.listarQuarto().forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.print("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		Quarto quarto = this.pedeDadosQuarto();

		quarto.setQuartoId(quartoId);
		this.quartoDAOImpl.atualizarQuarto(quarto);

		System.out.println("Quarto atualizado com sucesso!");
	}

	public void atualizarReserva() {
		System.out.println("===== Atualizar Reserva =====");

		System.out.println("Reservas disponíveis: ");
		this.reservaDAOImpl.listarReserva().forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		Reserva reserva = this.pedeDadosReserva();

		reserva.setReservaId(reservaId);
		this.reservaDAOImpl.atualizarReserva(reserva);

		System.out.println("Reserva atualizada com sucesso!");
	}

	private Endereco cadastraEndereco(Integer enderecoId) {
		Endereco endereco = this.pedeDadosEndereco();

		if (enderecoId != null) {
			endereco.setEnderecoId(enderecoId);
			this.enderecoDAOImpl.atualizarEndereco(endereco);
		} else {
			this.enderecoDAOImpl.cadastrarEndereco(endereco);
		}
		this.enderecoDAOImpl.cadastrarEndereco(endereco);

		return endereco;
	}

	private void cadastrarCliente() {
		System.out.println("===== Cadastro de Cliente =====");

		Cliente cliente = this.pedeDadosCliente();

		// Salvar o cliente no banco de dados
		this.clienteDAOImpl.cadastrarCliente(cliente);

		System.out.println("Agora vamos cadastrar o endereço do cliente: ");
		Endereco endereco = this.cadastraEndereco(null);
		cliente.setEnderecoId(this.enderecoDAOImpl.buscarIdEnderecoPorAributos(endereco));

		System.out.println("Cliente cadastrado com sucesso!");
	}

	private void cadastrarHotel() {
		System.out.println("===== Cadastro de Hotel =====");

		Hotel hotel = this.pedeDadosHotel();

		this.cadastraEndereco(null);

		this.hotelDAOImpl.cadastrarHotel(hotel);

		System.out.println("Hotel cadastrado com sucesso!");
	}

	private void cadastrarQuarto() {
		System.out.println("===== Cadastro de Quarto =====");

		Quarto quarto = this.pedeDadosQuarto();

		if (quarto == null) {
			return;
		}

		this.quartoDAOImpl.cadastrarQuarto(quarto);

		System.out.println("Quarto cadastrado com sucesso!");
	}

	private void cadastrarReserva() {
		System.out.println("===== Cadastro de Reserva =====");



		Reserva reserva = this.pedeDadosReserva();

		System.out.println("Agora vamos criar um vinculo com um cliente e um quarto:");

		ReservaDetalhe reservaDetalhe = this.pedeClienteEQuartoDaReservaDetalhe(reserva);

		if (reservaDetalhe == null) {
			return;
		}

		this.reservaDAOImpl.cadastrarReserva(reserva);

		Integer reservaId = this.reservaDAOImpl.buscaReservaIdPorAtributo(reserva);
		reservaDetalhe.setReservaId(reservaId);

		this.reservaDAOImpl.cadastrarReserva(reserva);

		System.out.println("Reserva cadastrada com sucesso!");
	}

	public void deletarCliente() {

		List<Cliente> listaDeClientes = this.clienteDAOImpl.listarCliente();

		if (listaDeClientes.isEmpty()) {
			System.out.println("\n\nNenhum cliente cadastrado. Por favor, cadastre um cliente antes de continuar.\n\n");
			return;
		}

		listaDeClientes.forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		this.clienteDAOImpl.deletarCliente(clienteId);
		System.out.println("Cliente deletado com sucesso!");
	}

	public void deletarHotel() {

		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();

		if (listaDeHoteis.isEmpty()) {
			System.out.println("\n\nNenhum hotel cadastrado. Por favor, cadastre um hotel antes de continuar.\n\n");
			return;
		}

		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println(
				"Atenção: Ao deletar um hotel, todos os quartos associados a ele serão deletados, assim como todas as partes da reserva associadas àquele quarto.");

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		Hotel hotelSelecionado = listaDeHoteis.stream().filter(hotel -> hotel.getHotelId().equals(hotelId)).findFirst()
				.orElse(null);

		this.hotelDAOImpl.deletarHotel(hotelId);
		this.enderecoDAOImpl.deletarEndereco(hotelSelecionado.getEnderecoId());
		System.out.println("Hotel deletado com sucesso!");
	}

	public void deletarQuarto() {

		List<Quarto> listaDeQuartos = this.quartoDAOImpl.listarQuarto();

		if (listaDeQuartos.isEmpty()) {
			System.out.println("\n\nNenhum quarto cadastrado. Por favor, cadastre um quarto antes de continuar.\n\n");
			return;
		}

		listaDeQuartos.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		this.quartoDAOImpl.deletarQuarto(quartoId);
		System.out.println("Quarto deletado com sucesso!");
	}

	public void deletarReserva() {

		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();

		if (listaDeReservas.isEmpty()) {
			System.out
			.println("\n\nNenhuma reserva cadastrada. Por favor, cadastre uma reserva antes de continuar.\n\n");
			return;
		}

		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		this.reservaDAOImpl.deletarReserva(reservaId);
		System.out.println("Reserva deletada com sucesso!");
	}


	public void deletarReservaDetalhe() {
		System.out.println("===== Deletar Reserva Detalhe =====");

		List<ReservaDetalhe> listaDeReservasDetalhes = this.reservaDetalheDAOImpl.listarReservaDetalhe();

		if (listaDeReservasDetalhes.isEmpty()) {
			System.out.println("\n\nNenhuma reserva detalhe cadastrada. Por favor, cadastre uma reserva detalhe antes de continuar.\n\n");
			return;
		}

		listaDeReservasDetalhes.forEach(reservaDetalhe -> {
			System.out.println(reservaDetalhe);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do vinculo de reserva escolhida: ");
		Integer reservaDetalheId = this.insereNumero();

		this.reservaDetalheDAOImpl.deletarReservaDetalhe(reservaDetalheId);
		System.out.println("Vínculo da reserva deletada com sucesso!");
	}

	public void exibeMenu() {
		boolean sair = false;
		while (!sair) {
			System.out.println("1 - Cadastrar Hotel");
			System.out.println("2 - Cadastrar Quarto");
			System.out.println("3 - Cadastrar Cliente");
			System.out.println("4 - Cadastrar Reserva");
			System.out.println("5 - Listar Hoteis");
			System.out.println("6 - Listar Quartos");
			System.out.println("7 - Listar Clientes");
			System.out.println("8 - Listar Reservas");
			System.out.println("9 - Deletar Hotel");
			System.out.println("10 - Deletar Quarto");
			System.out.println("11 - Deletar Cliente");
			System.out.println("12 - Deletar Reserva");
			System.out.println("13 - Vincular Reserva");
			System.out.println("14 - Atualizar Hotel");
			System.out.println("15 - Atualizar Quarto");
			System.out.println("16 - Atualizar Cliente");
			System.out.println("17 - Atualizar Reserva");
			System.out.println("18 - Deletar Vículo de Reserva");

			System.out.println("0 - Sair");

			System.out.println("Digite a opção desejada: ");

			Integer opcao = this.insereNumero();

			switch (opcao) {
			case 0 -> sair = true;
			case 1 -> this.cadastrarHotel();
			case 2 -> this.cadastrarQuarto();
			case 3 -> this.cadastrarCliente();
			case 4 -> this.cadastrarReserva();
			case 5 -> this.listarHoteis();
			case 6 -> this.listarQuartos();
			case 7 -> this.listarClientes();
			case 8 -> this.listarReservas();
			case 9 -> this.deletarHotel();
			case 10 -> this.deletarQuarto();
			case 11 -> this.deletarCliente();
			case 12 -> this.deletarReserva();
			case 13 -> this.vinculaReserva();
			case 14 -> this.atualizarHotel();
			case 15 -> this.atualizarQuarto();
			case 16 -> this.atualizarCliente();
			case 17 -> this.atualizarReserva();
			case 18 -> this.deletarReservaDetalhe();

			default -> System.out.println("Opção inválida. Por favor, tente novamente.");
			}
		}

		return;
	}

	private BigDecimal insereBigDecimal() {
		boolean numeroValido = false;
		BigDecimal numero = null;
		while (!numeroValido) {
			try {
				numero = this.scanner.nextBigDecimal();
				this.scanner.nextLine(); // Limpar o buffer do scanner
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
			}
		}
		return numero;
	}

	private Integer insereNumero() {
		boolean numeroValido = false;
		Integer numero = null;
		while (!numeroValido) {
			try {
				numero = this.scanner.nextInt();
				this.scanner.nextLine(); // Limpar o buffer do scanner
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
				this.scanner.nextLine(); // Limpar o buffer do scanner
			}
		}
		return numero;
	}

	private String insereString() {
		boolean numeroValido = false;
		String string = null;
		while (!numeroValido) {
			try {
				string = this.scanner.nextLine();
				numeroValido = true;
			} catch (Exception e) {
				System.out.println("Número inválido. Por favor, insira um número válido:");
			}
		}
		return string;
	}

	private Date lerData() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date data = null;
		boolean dataValida = false;

		while (!dataValida) {
			try {
				String dataStr = this.scanner.nextLine();
				data = dateFormat.parse(dataStr);
				dataValida = true;
			} catch (ParseException e) {
				System.out.println("Formato de data inválido. Por favor, insira a data no formato dd/MM/yyyy:");
			}
		}

		return data;
	}

	public void listarClientes() {
		List<Cliente> listaDeClientes = this.clienteDAOImpl.listarCliente();
		if (listaDeClientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}
		listaDeClientes.forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});
	}

	public void listarHoteis() {
		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();
		if (listaDeHoteis.isEmpty()) {
			System.out.println("Nenhum hotel cadastrado.");
			return;
		}
		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});
	}

	public void listarQuartos() {
		List<Quarto> listaDeQuartos = this.quartoDAOImpl.listarQuarto();
		if (listaDeQuartos.isEmpty()) {
			System.out.println("Nenhum quarto cadastrado.");
			return;
		}
		listaDeQuartos.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});
	}

	public void listarReservas() {
		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();
		if (listaDeReservas.isEmpty()) {
			System.out.println("Nenhuma reserva cadastrada.");
			return;
		}
		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});
	}

	private ReservaDetalhe pedeClienteEQuartoDaReservaDetalhe(Reserva reserva) {
		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		List<Quarto> quartosDoHotel = new ArrayList<>();
		do {
			System.out.println("Selecione o hotel para a reserva: ");

			List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotelComQuartosDisponiveis();

			if (listaDeHoteis.isEmpty()) {
				System.out.println(
						"\n\nNenhum hotel com quartos disponíveis cadastrado. Por favor, cadastre um hotel ou quarto ou aguarde o fim das reservas antes de continuar.\n\n");
				return null;
			}

			listaDeHoteis.forEach(hotel -> {
				System.out.println(hotel);
				System.out.println("\n----------------------\n");
			});

			System.out.println("Digite o ID do hotel escolhido: ");
			Integer hotelId = this.insereNumero();

			quartosDoHotel = this.quartoDAOImpl.buscarQuartoDisponivelPorHotelId(hotelId, reserva.getDataInicio(), reserva.getDataFim());

			if (quartosDoHotel.isEmpty()) {
				System.out.println("Hotel sem quartos disponíveis. Por favor, selecione outro hotel.");
			}

		} while (quartosDoHotel.isEmpty());

		System.out.println("Quartos disponíveis: ");

		quartosDoHotel.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		ReservaDetalhe reservaDetalhe = new ReservaDetalhe();

		reservaDetalhe.setClienteId(clienteId);
		reservaDetalhe.setQuartoId(quartoId);
		return reservaDetalhe;
	}

	private Cliente pedeDadosCliente() {
		// Coletar dados do usuário
		System.out.println("Nome do Cliente: ");
		String nome = this.insereString();

		System.out.println("Email do Cliente: ");
		String email = this.insereString();

		System.out.println("Telefone do Cliente: ");
		String telefone = this.insereString();

		// Aqui você pode coletar mais dados do cliente, dependendo dos atributos da entidade Cliente

		// Criar o objeto Cliente
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setTelefone(telefone);

		return cliente;
	}

	private Endereco pedeDadosEndereco() {
		System.out.println("Rua: ");
		String rua = this.insereString();

		System.out.println("Número: ");
		Integer numero = this.insereNumero();

		System.out.println("Bairro: ");
		String bairro = this.insereString();

		System.out.println("Cidade: ");
		String cidade = this.insereString();

		System.out.println("Estado: ");
		String estado = this.insereString();

		System.out.println("País: ");
		String pais = this.insereString();

		System.out.println("CEP: ");
		String cep = this.insereString();

		Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, pais, cep);
		return endereco;
	}

	private Hotel pedeDadosHotel() {
		System.out.println("Nome do Hotel: ");
		String nome = this.insereString();

		System.out.println("Classificação do Hotel: ");
		Integer classificacao = this.insereNumero();

		System.out.println("Data de Construção do Hotel (dd/MM/yyyy): ");
		Date dataConstrucao = this.lerData();

		System.out.println("Agora vamos cadastrar o endereço do Hotel ");

		Hotel hotel = new Hotel();

		hotel.setNome(nome);
		hotel.setClassificacao(classificacao);
		hotel.setDataConstrucao(dataConstrucao);
		return hotel;
	}

	private Quarto pedeDadosQuarto() {
		// Coletar dados do usuário
		System.out.println("Número do Quarto: ");
		Integer numero = this.insereNumero();

		System.out.println("Disponibilidade (true/false): ");
		boolean disponibilidade = this.scanner.nextBoolean();
		this.scanner.nextLine(); // Limpar o buffer do scanner

		System.out.println("Preço: ");
		BigDecimal preco = this.insereBigDecimal();

		// Criar o objeto Quarto
		Quarto quarto = new Quarto();
		quarto.setNumero(numero);
		quarto.setDisponibilidade(disponibilidade);
		quarto.setPreco(preco);

		List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotel();

		if (listaDeHoteis.isEmpty()) {
			System.out.println(
					"\n\nNenhum hotel cadastrado. Por favor, cadastre um hotel antes de continuar.\n\n");
			return null;
		}

		listaDeHoteis.forEach(hotel -> {
			System.out.println(hotel);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do hotel escolhido: ");
		Integer hotelId = this.insereNumero();

		quarto.setHotelId(hotelId);
		return quarto;
	}

	private Reserva pedeDadosReserva() {
		System.out.println("Data de Início (dd/MM/yyyy): ");
		Date dataInicio = this.lerData();

		System.out.println("Data de Fim (dd/MM/yyyy): ");
		Date dataFim = this.lerData();

		System.out.println("Forma de Pagamento: ");
		String formaPagamento = this.insereString();

		Reserva reserva = new Reserva();

		reserva.setDataInicio(dataInicio);
		reserva.setDataFim(dataFim);
		reserva.setFormaPagamento(formaPagamento);
		return reserva;
	}

	@Autowired
	public void setClienteDAOImpl(ClienteDAOImpl clienteDAOImpl) {
		this.clienteDAOImpl = clienteDAOImpl;
	}

	@Autowired
	public void setEnderecoDAOImpl(EnderecoDAOImpl enderecoDAOImpl) {
		this.enderecoDAOImpl = enderecoDAOImpl;
	}

	@Autowired
	public void setHotelDAOImpl(HotelDAOImpl hotelDAOImpl) {
		this.hotelDAOImpl = hotelDAOImpl;
	}

	@Autowired
	public void setQuartoDAOImpl(QuartoDAOImpl quartoDAOImpl) {
		this.quartoDAOImpl = quartoDAOImpl;
	}

	@Autowired
	public void setReservaDAOImpl(ReservaDAOImpl reservaDAOImpl) {
		this.reservaDAOImpl = reservaDAOImpl;
	}

	@Autowired
	public void setReservaDetalheDAOImpl(ReservaDetalheDAOImpl reservaDetalheDAOImpl) {
		this.reservaDetalheDAOImpl = reservaDetalheDAOImpl;
	}

	@Autowired
	public void setTipoQuartoDAOImpl(TipoQuartoDAOImpl tipoQuartoDAOImpl) {
		this.tipoQuartoDAOImpl = tipoQuartoDAOImpl;
	}

	public void vinculaReserva() {
		System.out.println("===== Vincular Reserva =====");

		System.out.println("Clientes disponíveis: ");
		this.clienteDAOImpl.listarCliente().forEach(cliente -> {
			System.out.println(cliente);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do cliente escolhido: ");
		Integer clienteId = this.insereNumero();

		System.out.println("Reservas disponíveis: ");
		List<Reserva> listaDeReservas = this.reservaDAOImpl.listarReserva();

		listaDeReservas.forEach(reserva -> {
			System.out.println(reserva);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID da reserva escolhida: ");
		Integer reservaId = this.insereNumero();

		Reserva reservaEscolhida = listaDeReservas.stream().filter(reserva -> reserva.getReservaId().equals(reservaId))
				.findFirst().orElse(null);


		List<Quarto> quartosDoHotel = new ArrayList<>();
		do {
			System.out.println("Selecione o hotel para a reserva: ");

			List<Hotel> listaDeHoteis = this.hotelDAOImpl.listarHotelComQuartosDisponiveis();

			if (listaDeHoteis.isEmpty()) {
				System.out.println(
						"\n\nNenhum hotel com quartos disponíveis cadastrado. Por favor, cadastre um hotel ou quarto ou aguarde o fim das reservas antes de continuar.\n\n");
				return;
			}

			listaDeHoteis.forEach(hotel -> {
				System.out.println(hotel);
				System.out.println("\n----------------------\n");
			});

			System.out.println("Digite o ID do hotel escolhido: ");
			Integer hotelId = this.insereNumero();

			quartosDoHotel = this.quartoDAOImpl.buscarQuartoDisponivelPorHotelId(hotelId,
					reservaEscolhida.getDataInicio(), reservaEscolhida.getDataFim());

			if (quartosDoHotel.isEmpty()) {
				System.out.println("Hotel sem quartos disponíveis. Por favor, selecione outro hotel.");
			}

		} while (quartosDoHotel.isEmpty());

		System.out.println("Quartos disponíveis: ");

		quartosDoHotel.forEach(quarto -> {
			System.out.println(quarto);
			System.out.println("\n----------------------\n");
		});

		System.out.println("Digite o ID do quarto escolhido: ");
		Integer quartoId = this.insereNumero();

		ReservaDetalhe reservaDetalhe = new ReservaDetalhe();

		reservaDetalhe.setClienteId(clienteId);
		reservaDetalhe.setQuartoId(quartoId);
		reservaDetalhe.setReservaId(reservaId);

		this.reservaDetalheDAOImpl.cadastrarReservaDetalhe(reservaDetalhe);

		System.out.println("Reserva vinculada com sucesso!");
	}


}
