package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 18.11.2016.
 */
public class Operations {

    static double a = 0;
    static double c = 10 * Math.PI;

    static double power = 7;

    static double leftside = 0;
    static double rightside = Math.pow(2, power);

    static Random random = new Random();

    public static double func(double x) {
        return 20 * Math.sin(x / 10) - 2;
    }

    public static List<Chromosome> createPopulation(int n) {
        List<Chromosome> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int pos = getNum();
            String binary = fromNumToBinary(pos);
            double x = fromNumToFloat(pos);
            double val = func(x);
            list.add(new Chromosome(pos, binary, x, val));
        }

        return list;
    }

    public static List<Chromosome> selection(List<Chromosome> chromosomeList, int maxSize) {
        List<Chromosome> list = new ArrayList<>();

        chromosomeList = sortList(chromosomeList);
        double a = 1 + getRand() * (2 - 1);
        double b = 2 - a;
        double N = chromosomeList.size();
        for (int i = 0; i < N; i++) {
            chromosomeList.get(i).setProbability(1 / N * (a - (a - b) * (i - 1 + 1) / (N - 1)));
        }

        int k = 0;
        for (int i = 0; i < maxSize; i++) {
            double rand = getRand();
            double sum = 0;
            for (int j = 0; j < N; j++) {
                sum += chromosomeList.get(j).getProbability();
                if (rand < sum) {
                    int finalJ = j;
                    Chromosome m = new Chromosome(chromosomeList.get(j));
                    if (chromosomeList.stream().filter(e -> e.getPosition() == m.getPosition()).count() != 0 && k < 10) {
                        i--;
                        k++;
                        break;
                    }
                    k=0;
                    list.add(new Chromosome(m));
                    break;
                }
            }
        }
        return list;
    }

    public static List<Chromosome> reproduction(List<Chromosome> chromosomeList, int maxSize) {

        List<Chromosome> list = new ArrayList<>();

        chromosomeList = sortList(chromosomeList);

        double a = 1 + getRand() * (2 - 1);
        double b = 2 - a;

        double N = chromosomeList.size();

        for (int i = 0; i < N; i++) {
            chromosomeList.get(i).setProbability(1 / N * (a - (a - b) * (i - 1 + 1) / (N - 1)));
        }

        boolean pair = false;
        for (int i = 0; i < maxSize; i++) {
            double rand = getRand();
            double sum = 0;
            for (int j = 0; j < N; j++) {
                sum += chromosomeList.get(j).getProbability();
                if (rand < sum) {
                    if (pair) {
                        if (list.get(i - 1).getPosition() == chromosomeList.get(j).getPosition()) {
                            i--;
                            break;
                        } else {
                            list.add(new Chromosome(chromosomeList.get(j)));
                            pair = false;
                        }
                    } else {
                        list.add(new Chromosome(chromosomeList.get(j)));
                        pair = true;
                    }
                    break;
                }
            }
        }
        return list;
    }

    public static List<Chromosome> crossingover(List<Chromosome> chromosomeList) {

        List<Chromosome> list = new ArrayList<>();

        int N = chromosomeList.size();

        for (int i = 0; i < N; i += 2) {

            double pr = getRand();

            list.add(chromosomeList.get(i));
            list.add(chromosomeList.get(i + 1));

            int k = (int) Math.round(getRand() * (power - 1));
            StringBuilder b1 = new StringBuilder();
            StringBuilder b2 = new StringBuilder();

            char[] buf1;
            char[] buf2;

            buf1 = list.get(i).getBinary().toCharArray();
            buf2 = list.get(i + 1).getBinary().toCharArray();
/*            if (buf1.length != buf2.length) {
                System.out.println(list.get(i));
                System.out.println(list.get(i + 1));
                System.out.println(buf1.length);
                System.out.println(buf2.length);
            }*/
            for (int j = 0; j < buf1.length; j++) {
                if (j < k) {
                    b1.append(String.valueOf(buf1[j]));
                    b2.append(String.valueOf(buf2[j]));
                } else {
                    b1.append(String.valueOf(buf2[j]));
                    b2.append(String.valueOf(buf1[j]));
                }
            }

            list.get(i).setBinary(b1.toString());
            list.get(i + 1).setBinary(b2.toString());

            list.get(i).setPartner(i + "");
            list.get(i + 1).setPartner((i + 1) + "");

        }

        for (int i = 0; i < N; i++) {
            list.get(i).setPosition(Integer.parseInt(list.get(i).getBinary(), 2));
            list.get(i).setValue(fromNumToFloat(list.get(i).getPosition()));
            list.get(i).setFuncValue(func(list.get(i).getValue()));

        }


        return list;
    }

    public static List<Chromosome> mutation(List<Chromosome> chromosomeList, int pos, double pm, List<Integer> integerList) {

        List<Chromosome> list = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < chromosomeList.size(); i++) {
            double rand = getRand();
            if (rand < pm) {
                integerList.add(i);
                char[] arr = chromosomeList.get(i).getBinary().toCharArray();
                arr[pos] = (arr[pos] == '1') ? '0' : '1';
                Chromosome chromosome = new Chromosome(new Chromosome(chromosomeList.get(i)));

                chromosome.setBinary(String.valueOf(arr));
                chromosome.setPosition(Integer.parseInt(chromosome.getBinary(), 2));
                chromosome.setValue(fromNumToFloat(chromosome.getPosition()));
                chromosome.setFuncValue(func(chromosome.getValue()));
                //System.out.println(chromosome);
                list.add(chromosome);
            }
        }

        return list;
    }

    public static List<Chromosome> cutting(List<Chromosome> clone, double pc) {
        List<Chromosome> list = new ArrayList<>();

        for (int i = 0; i < clone.size() * pc; i++) {
            list.add(new Chromosome(clone.get(i)));
        }
        if (list.size() % 2 != 0) list.add(new Chromosome(clone.get(list.size())));

        return list;
    }

    public static List<Chromosome> sortList(List<Chromosome> list) {
        list.sort((o1, o2) -> o1.getFuncValue() < o2.getFuncValue() ? 1 : -1);
        return list;
    }

    public static List<Chromosome> addAll(List<Chromosome> ishList, List<Chromosome> rep,
                                          List<Chromosome> cross, List<Chromosome> mut) {
        List<Chromosome> chromosomes = new ArrayList<>();

        ishList.forEach(e -> chromosomes.add(new Chromosome(e)));
/*
        rep.forEach(e -> {
            if (chromosomes.stream().filter(o -> o.getPosition() == e.getPosition()).count() == 0)
                chromosomes.add(new Chromosome(e));
        });*/

        cross.forEach(e -> {
            if (chromosomes.stream().filter(o -> o.getPosition() == e.getPosition()).count() == 0)
                chromosomes.add(new Chromosome(e));
        });

        mut.forEach(e -> {
            if (chromosomes.stream().filter(o -> o.getPosition() == e.getPosition()).count() == 0)
                chromosomes.add(new Chromosome(e));
        });


        return chromosomes;
    }

    private static String fromNumToBinary(int num) {
        String binary = Integer.toBinaryString(num);
        binary = checkLength(binary);
        return binary;
    }

    private static double fromNumToFloat(int x_int) {
        return a + x_int * (c - a) / (Math.pow(2, power) - 1);
    }

    private static int getNum() {
        return (int) (leftside + Math.round(getRand() * (rightside - leftside)));
    }

    private static double getRand() {
        return random.nextDouble();
    }

    public static String checkLength(String binary) {
        if (binary.length() != power) {
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < power - binary.length(); i++) buf.append("0");
            buf.append(binary);
            binary = buf.toString();
        }
        return binary;
    }

    public static List<Chromosome> usech(List<Chromosome> bufList, int n) {

        List<Chromosome> list = new ArrayList<>();

        List<Chromosome> list1 = Operations.selection(bufList, n / 2);

        int l = 0;
        if (n % 2 != 0) l = 1;

        for (int i = 0; i < n / 2 + l; i++) {

            for (Chromosome aBufList : bufList) {
                boolean flag = false;
                for (int k = 0; k < list1.size(); k++) {
                    if (list1.stream().filter(e -> e.getPosition() == aBufList.getPosition()).count() == 0) {
                        list1.add(new Chromosome(aBufList));
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
            }
        }
        return list1;
    }
}
