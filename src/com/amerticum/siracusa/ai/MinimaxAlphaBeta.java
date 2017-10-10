package com.amerticum.siracusa.ai;

import com.amerticum.siracusa.Constants;
import com.amerticum.siracusa.model.Doska;
import com.amerticum.siracusa.model.Figura;
import com.amerticum.siracusa.model.Hod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MinimaxAlphaBeta {
    private static final Logger logger = Logger.getLogger(MinimaxAlphaBeta.class.getSimpleName());
    boolean cvet;
    int maxDepth;
    Random rand;
    private volatile Hod bestMove;
    private static final int NTHREADS = Runtime.getRuntime().availableProcessors();
    private final Executor executor = Executors.newFixedThreadPool(NTHREADS);
    private static final double MILLI = 1000.0;

    public MinimaxAlphaBeta(boolean cvet, int maxDepth) {
        this.cvet = cvet;
        this.maxDepth = maxDepth;
        rand = new Random();
    }

    public Hod decision(final Doska doska) {
        // Все возможные ходы
        final ArrayList<Hod> hody = doska.poluchitHody(cvet, true);
        if(hody.size() == 0){
            return null;
        }
        Collections.shuffle(hody);
        long startTime = System.currentTimeMillis();
        CompletionService<Hod> service = new ExecutorCompletionService<Hod>(executor);
        int submitted = 0;
        bestMove = null;
        for (final Hod hod : hody) {
            final Doska callboard = new Doska(doska.polya, doska.figury, doska.istoriaHodov);
            service.submit(new Callable<Hod>() {
                @Override
                public Hod call() throws Exception {
                    callboard.peredvinytFiguru(hod, false);
                    double beta = Double.POSITIVE_INFINITY;
                    if (bestMove != null) {
                        beta = -bestMove.pulochitOcenku();
                    }
                    double ocenka = ocenitHod(callboard, !cvet, Double.NEGATIVE_INFINITY, beta, maxDepth - 1);
                    callboard.anuliruiHod(false);
                    hod.ustanovitOcenku(-ocenka);
                    return hod;
                }
            });
            submitted++;
        }

        // Оценка максимум
        for (int i = 0; i < submitted; i++) {
            try {
                Hod hod = service.take().get();
                if(bestMove == null || hod.pulochitOcenku()>bestMove.pulochitOcenku()){
                    bestMove = hod;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        long time = (System.currentTimeMillis() - startTime);
        logger.info("AI took " + (time / MILLI) + " seconds (" + NTHREADS + " threads, " + maxDepth + " plies)");
        return bestMove;
    }
    private double ocenitHod(final Doska doska, boolean cvet, final double alpha, final double beta, final int depth){
        if (depth == 0) {
            double ocenka = ocenitDosku(doska);
            return (cvet != this.cvet) ? -ocenka : ocenka;
        }
        double best = alpha;
        final ArrayList<Hod> hody = doska.poluchitHody(cvet, true);
        for(Hod hod : hody){
            doska.peredvinytFiguru(hod,false);
            best = Math.max(best, -ocenitHod(doska, !cvet, -beta, -best, depth-1));
            doska.anuliruiHod(false);
            // Альфа-вета отсечение
            if(beta <= best){
                return best;
            }
        }
        return best;
    }
    private double ocenitDosku(final Doska doska) {
        double cenaVsehFigur = cenaVsehFigur(doska);
        double cenaVozmojnosteyImperatora = cenaVozmojnosteyImperatora(doska);
        double cenaVozmojnosteyVsehFigur = cenaVozmojnosteyVsehFigur(doska);
        return cenaVsehFigur * Constants.MATERIAL + cenaVozmojnosteyImperatora * Constants.BESOPASNOST + cenaVozmojnosteyVsehFigur * Constants.MOBILNOST;
    }
    private double cenaVsehFigur(final Doska doska) {
        double value = 0;
        for (Figura[] ryad : doska.figury) {
            for (Figura figuraVRyadu : ryad) {
                if(figuraVRyadu != null){
                    value += figuraVRyadu.cena()*figuraVRyadu.poluchiCvet();
                }
            }
        }
        if(cvet){
            return value;
        }
        return value * -1;
    }
    private double cenaVozmojnosteyImperatora(final Doska doska) {
        return cenaVozmojnosteyImperatora(doska, !cvet) - cenaVozmojnosteyImperatora(doska, cvet);
    }
    private double cenaVozmojnosteyImperatora(final Doska doska, boolean cvet) {
        ArrayList<Hod> hody = doska.poluchitHodyImperatora(cvet,false);
        if(hody==null){
            return 0;
        }else if (hody.size()==1 && hody.get(0).getY2()==-1) {
            // Может случиться :)
            return Double.POSITIVE_INFINITY;
        }
        return hody.size();
    }
    private double cenaVozmojnosteyVsehFigur(final Doska doska) {
        return doska.poluchitHody(cvet, false).size() - doska.poluchitHody(!cvet, false).size();
    }

}
