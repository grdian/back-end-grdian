package grdian.backendgrdian;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import grdian.backendgrdian.controllers.MailMan;
import grdian.backendgrdian.entities.AppMessage;
import grdian.backendgrdian.entities.User;
import grdian.backendgrdian.repos.AppMessageRepository;
import grdian.backendgrdian.repos.UserRepository;

@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AppMessageRepository messageRepo;

	@Autowired
	MailMan mailMan;

	// Logging and Feedback
	private Logger logger = LoggerFactory.getLogger(Initializer.class);
	private Instant startTimestamp;
	private boolean errorsDuringInitialization = false;

	@Override
	public void run(String... args) throws Exception {
		logStartOfInitializer();

		try {
			populateRepositories();
		} catch (Exception e) {
			errorsDuringInitialization = true;
			logErrorAndException(e);
		}
		logCompletionOfInitializer();
	}

	private void populateRepositories() {
		User user01 = new User("Lawrence", "Mboya",
				"https://images.pexels.com/photos/555790/pexels-photo-555790.png?auto=compress&cs=tinysrgb&dpr=1&w=500",
				"1234567890", "mboya@gmail.com", "password");
		User user02 = new User("Nazik", "Elhag",
				"https://i.pinimg.com/originals/97/2c/58/972c58ce25d1e0bc96f2a787d254c26c.jpg", "0987654321",
				"elhag@gmail.com", "password");
		User user03 = new User("Tyler", "Conner", "https://cdn7.dissolve.com/p/D237_88_779/D237_88_779_1200.jpg",
				"0987654321", "conner@gmail.com", "password");

		user01 = userRepo.save(user01);
		user02 = userRepo.save(user02);
		user03 = userRepo.save(user03);

//		AppMessage message01 = new AppMessage("1 this is a message", user01);
//		messageRepo.save(message01);
//		mailMan.sendMessageToUsers(message01);
//
//		AppMessage message02 = new AppMessage("2 this is another message", user02);
//		messageRepo.save(message02);
//		mailMan.sendMessageToUsers(message02);
	}

	private void logStartOfInitializer() {
		logger.info("Starting Initializer");
		startTimestamp = Instant.now();
	}

	private void logErrorAndException(Exception ex) {
		logger.error("There was a problem in the execution of the Initializer.", ex);
	}

	private void logCompletionOfInitializer() {
		logger.info(
				"Initializer done " + checkForErrors() + ", it took " + timeToCompleteInitializer() + " ms to finish.");
	}

	private String checkForErrors() {
		if (errorsDuringInitialization) {
			return "WITH ERRORS";
		}
		return "";
	}

	private int timeToCompleteInitializer() {
		return Instant.now().compareTo(startTimestamp) / 1000000;
	}
}
