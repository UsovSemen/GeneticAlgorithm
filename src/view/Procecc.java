package view;

import controller.EntryCtrl;
import entity.Chromosome;
import entity.Details;
import entity.Operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sam on 18.11.2016.
 */
public class Procecc {

    public static Details proc(final Integer n, final Integer steps, final Double pc, final Double pm) {

        List<Chromosome> ishListChromosome = Operations.createPopulation(n);

        List<Chromosome> ishList = clone(ishListChromosome);
        List<Chromosome> afterRep;
        List<Chromosome> afterCross;
        List<Chromosome> afterMut;
        List<Chromosome> bufList;
        List<Chromosome> afterAll;
        int loop = 0;
        double max = 18.0;
        double prob = 0.02;
        Details details = new Details();
        boolean f = true;
        while (loop < steps) {
            ishList = sortList(ishList);

            afterRep = Operations.reproduction(ishList, n);
            bufList = Operations.cutting(clone(afterRep), pc);
            afterCross = Operations.crossingover(clone(bufList));
            List<Integer> integers = new ArrayList<>();
            afterMut = Operations.mutation(clone(afterRep), 5, pm, integers);

            bufList.clear();
            bufList = Operations.addAll(ishList, afterRep, afterCross, afterMut);

            bufList = Operations.sortList(bufList);

            afterAll = clone(bufList.subList(0, n));
            // afterAll = Operations.usech(bufList, n);
            // System.out.println(loop + "     " + afterAll.get(0).getFuncValue());

            if(Math.abs(max - afterAll.get(0).getFuncValue()) < prob && f) {EntryCtrl.text="Шаг: " + (1+loop);f = false;}

            Collections.shuffle(afterAll);
           // afterAll = Operations.sortList(afterAll);
            details.ishList.add(clone(ishList));
            details.afterRep.add(clone(afterRep));
            details.afterCros.add(clone(afterCross));
            details.afterMut.add(clone(afterMut));
            details.afterAll.add(clone(afterAll));
            details.indexMut.add(cloneInteger(integers));

            ishList = clone(afterAll);
            loop++;
        }

        return details;
    }

    public static List<Chromosome> clone(List<Chromosome> list) {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            chromosomeList.add(new Chromosome(list.get(i)));
        }
        return chromosomeList;
    }

    private static List<Integer> cloneInteger(List<Integer> list) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            integers.add(list.get(i));
        }
        return integers;
    }

    private static void print(List<Chromosome> list) {
        list.forEach(System.out::println);
    }

    private static List<Chromosome> sortList(List<Chromosome> list) {
        list.sort((o1, o2) -> o1.getFuncValue() < o2.getFuncValue() ? 1 : o1.getFuncValue() > o2.getFuncValue() ? -1 : 0);
        return list;
    }

}
