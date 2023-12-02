package aoc.year2023.day02;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CubeConundrum {
    public void sumIdsOfGames(List<String> fileContent) {
        BagContent bagContent = new BagContent(12, 13, 14);

        Set<Game> games = new HashSet<>();

        for (String line: fileContent) {
            Pattern pattern = Pattern.compile("Game (?<id>(\\d+)): (?<cubes>(.*))");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int id = Integer.parseInt(matcher.group("id"));
                String cubes = matcher.group("cubes");
                Game game = this.initializeGame(id, cubes);
                games.add(game);
            }
        }

        int idsSum = games.stream()
                .filter(game -> game.isPossibleWithBagContent(bagContent))
                .map(Game::getId)
                .mapToInt(Integer::intValue)
                .sum();

        log.info("{}", idsSum);

        int power = games.stream()
                .map(Game::power)
                .mapToInt(Integer::intValue)
                .sum();

        log.info("{}", power);
    }

    private Game initializeGame(int id, String cubes) {
        Game game = new Game();
        game.setId(id);
        game.setGameSets(new ArrayList<>());

        // Treating cubes
        List<String> cubeSets = Arrays.stream(cubes.split(";")).toList();

        cubeSets.forEach(cubeSet -> {
            GameSet gameSet = new GameSet();
            Pattern pattern = Pattern.compile("(?<number>\\d+) (?<type>[a-z]+)");
            Matcher matcher = pattern.matcher(cubeSet);
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group("number"));
                CubeType type = CubeType.valueOf(matcher.group("type").toUpperCase());
                gameSet.increaseMatchingCubes(number, type);
            }
            game.getGameSets().add(gameSet);
        });

        return game;
    }
}
