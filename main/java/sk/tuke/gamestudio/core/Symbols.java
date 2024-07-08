package sk.tuke.gamestudio.core;

import java.util.Random;
import sk.tuke.gamestudio.consoleUI.*;

public class Symbols {

    private final char[] SymbolsArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    private final double[] symbolProbabilities = {0.3, 0.23, 0.17, 0.1, 0.08, 0.07, 0.05};
    private boolean currentSymbolGenerated = false;
    private char CurrentSymbol, NextSymbol;

    public char generateSymbol(Random random) {
        double rand = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < SymbolsArray.length; i++) {
            cumulativeProbability += symbolProbabilities[i];
            if (rand < cumulativeProbability) {
                return SymbolsArray[i];
            }
        }

        return SymbolsArray[SymbolsArray.length - 1];
    }

    public char getCurrentSymbol() {
        return CurrentSymbol;
    }

    public char getNextSymbol() {
        return NextSymbol;
    }

    public void symbolsUpdate(Random random) {
        if (!currentSymbolGenerated) {
            CurrentSymbol = generateSymbol(random);
            NextSymbol = generateSymbol(random);
            currentSymbolGenerated = true;
        } else {
            CurrentSymbol = NextSymbol;
            NextSymbol = generateSymbol(random);
        }
    }

    public void symbolsPrint() {
        if (!currentSymbolGenerated)
            symbolsUpdate(new Random());

        System.out.println();
        System.out.println("Current symbol   -->" + CurrentSymbol + "<--");
        System.out.println("Next symbol      -->" + NextSymbol + "<--");
    }
}