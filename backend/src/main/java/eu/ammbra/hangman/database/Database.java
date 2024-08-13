package eu.ammbra.hangman.database;

import eu.ammbra.hangman.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class Database {

	private Map<String, User> data;

	public Database(Map<String, User> data) {
		this.data = data;
	}

	public Map<String, User> getData() {
		return data;
	}
}
