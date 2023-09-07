package com.ap.homebanking_ap;

import com.ap.homebanking_ap.models.*;
import com.ap.homebanking_ap.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args -> {
			Client client1 = new Client();
			client1.setFirstName("Melba");
			client1.setLastName("Morel");
			client1.setEmail("melba@mindhub.com");
			client1.setPassword(passwordEncoder.encode("Melba1234"));

			Client client2 = new Client("Enzo","Fernandez","efernandez@gmail.com", passwordEncoder.encode("Fernandez"));

			clientRepository.save(client1);

			clientRepository.save(client2);

			Account account1 = new Account("VIN001",LocalDate.now(),5000.0);


			Account account2 = new Account("VIN002",LocalDate.now().plusDays(1),7500.0);


			Transaction transaction1 = new Transaction(TransactionType.CREDIT,500.0,"Credit rent", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-200.0,"Debit MacDonall's",LocalDateTime.now());

			client1.addAccount(account1);
			client1.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			accountRepository.save(account1);
			accountRepository.save(account2);

			Loan loan1 = new Loan("Hipotecario",500000.0, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal",100000.0,List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz",300000.0,List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.0,60);
			ClientLoan clientLoan2 = new ClientLoan(50000.0,12);
			ClientLoan clientLoan3 = new ClientLoan(100000.0,24);
			ClientLoan clientLoan4 = new ClientLoan(200000.0,36);



			client1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);

			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan3);
			loan3.addClientLoan(clientLoan4);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(CardType.DEBIT,"4546 5678 4532 4567",
					230,LocalDate.now(),LocalDate.now().plusYears(5),
					"MELBA MOREL", CardColor.GOLD);
			Card card2 = new Card(CardType.CREDIT,"4546 4566 5677 3435",456,LocalDate.now(),LocalDate.now().plusYears(5),
					"MELBA MOREL", CardColor.TITANIUM);
			Card card3 = new Card(CardType.CREDIT,"4546 6754 5677 3456",123,LocalDate.now(),LocalDate.now().plusYears(5),
					"ENZO FERNANDEZ", CardColor.SILVER);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		});
	}




}
