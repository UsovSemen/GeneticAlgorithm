import entity.Chromosome;
import entity.Operations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 18.11.2016.
 */
public class Main_Test {

    public static void main(String[] args) throws Exception {

        int n = 10;
        int steps = 50;
        double pc = 0.5;
        double pm = 0.001;

        List<Chromosome> ishListChromosome = Operations.createPopulation(n);

        List<Chromosome> ishList = clone(ishListChromosome);
        List<Chromosome> afterRep;
        List<Chromosome> afterCross;
        List<Chromosome> afterMut;
        List<Chromosome> bufList;
        List<Chromosome> afterAll;
        int loop = 0;

        while (loop < steps) {

            ishList = sortList(ishList);
            //   print(ishList);
            //   System.out.println("--------1");

            afterRep = Operations.reproduction(clone(ishList), n);
            //   print(afterRep);
            //   System.out.println("--------2");

            bufList = Operations.cutting(clone(afterRep), pc);
            afterCross = Operations.crossingover(clone(bufList));
            //  print(afterCross);
            //  System.out.println("--------3");
            List<Integer> list = new ArrayList<>();
            afterMut = Operations.mutation(clone(afterRep), 5, pm, list);
            //  print(afterMut);
            //  System.out.println("--------4");

            bufList.clear();
            bufList = Operations.addAll(ishList, afterRep, afterCross, afterMut);

            // print(bufList);
            //  System.out.println("--------5");
            //System.out.println(afterRep.size() + afterCross.size() + ishList.size());
            bufList = Operations.sortList(bufList);
            afterAll = /*Operations.reproduction(clone(bufList), n); //*/clone(bufList.subList(0, n));
            //   print(afterAll);
            System.out.println(loop + "     " + afterAll.get(0).getFuncValue());
            ishList = clone(afterAll);
            loop++;
        }
    }

    private static List<Chromosome> clone(List<Chromosome> list) {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            chromosomeList.add(new Chromosome(list.get(i)));
        }
        return chromosomeList;
    }

    private static void print(List<Chromosome> list) {
        list.forEach(System.out::println);
    }

    private static List<Chromosome> sortList(List<Chromosome> list) {
        list.sort((o1, o2) -> o1.getFuncValue() < o2.getFuncValue() ? 1 : o1.getFuncValue() > o2.getFuncValue() ? -1 : 0);
        return list;
    }

}
