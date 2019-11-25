package fr.exalow.main.core;

import fr.exalow.main.entities.DeathEvent;
import fr.exalow.main.entities.Player;
import fr.exalow.main.manager.DeathManager;
import fr.exalow.main.utils.SaveLoader;

public class Simulator {

    private Game game;
    private DeathManager deathManager;
    private int cycle;

    public Simulator() {
        this.game = new Game(new SaveLoader());
        this.deathManager = game.getDeathManager();
    }

    public void start() {
        while (!game.hasWinner()) {
            night();
            day();
            cycle++;
        }
        stop();
    }

    private void day() {

        String day = "[Jour " + cycle + "]";
        System.out.println("\n" + day + "\n");

        for (Player player : deathManager.getLastDeaths("[Nuit " + (cycle - 1) + "]")) {
            System.out.println(player.getName() + " est mort, Il était " + player.getRole() + " !");
        }

        game.update("[Nuit " + (cycle - 1) + "]");

    }

    private void night() {

        String day = "[Nuit " + cycle + "]";
        System.out.println("\n" + day + "\n");

        deathManager.registerDeathEvent(new DeathEvent(game.getRandomPlayer(), day, "Killed by Werewolves"));
    }

    private void stop() {
        System.out.println("\nL'équipe " + game.getWinner() + " a gagnée !");
        this.deathManager.writeElements();
    }
}
