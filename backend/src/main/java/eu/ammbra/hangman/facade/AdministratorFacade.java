package eu.ammbra.hangman.facade;

import eu.ammbra.hangman.dto.Activity;
import eu.ammbra.hangman.model.Game;
import eu.ammbra.hangman.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorFacade {

	private final GameService gameService;

	public AdministratorFacade(GameService gameService) {
		this.gameService = gameService;
	}

	public List<Activity> retrieveHistory() {
		List<Activity> activities = new ArrayList<>();
		for (Game game : gameService.getAllGames()) {
			Activity activity = new Activity(game.getPlayer().getUsername(),
					game.getSecretWord(), game.getStartDate(),
					game.getEndDate(), game.getAttemptsLeft(),
					game.getStatus());
			activities.add(activity);
		}
		return activities;
	}

}
