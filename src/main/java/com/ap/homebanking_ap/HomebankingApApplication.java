package com.ap.homebanking_ap;

import com.ap.homebanking_ap.models.*;
import com.ap.homebanking_ap.repositories.AccountRepository;
import com.ap.homebanking_ap.repositories.ClientRepository;
import com.ap.homebanking_ap.repositories.LoanRepository;
import com.ap.homebanking_ap.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository){
		return (args -> {
			Client client1 = new Client();
			client1.setFirstName("Melba");
			client1.setLastName("Morel");
			client1.setEmail("melba@mindhub.com");

			Client client2 = new Client("Enzo","Fernandez","efernandez@gmail.com");

			clientRepository.save(client1);

			clientRepository.save(client2);

			Account account1 = new Account("VIN001",LocalDate.now(),5000.0);
			LocalDate today = LocalDate.now();

			Account account2 = new Account("VIN002",LocalDate.now(),7500.0);
			account2.setDate(today.plusDays(1));

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


		});
	}




}
