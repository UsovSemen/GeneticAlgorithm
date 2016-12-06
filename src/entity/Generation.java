package entity;

import java.util.List;

/**
 * Created by Sam on 05.12.2016.
 */
public class Generation {

    List<Chromosome> listChromosome;
    List<Chromosome> listAfterChromosome;
    Double max;

    public Generation(){}

    public List<Chromosome> getListChromosome() {
        return listChromosome;
    }

    public void setListChromosome(List<Chromosome> listChromosome) {
        this.listChromosome = listChromosome;
    }

    public List<Chromosome> getListAfterChromosome() {
        return listAfterChromosome;
    }

    public void setListAfterChromosome(List<Chromosome> listAfterChromosome) {
        this.listAfterChromosome = listAfterChromosome;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public void formAllData(boolean dir) {
        if (dir) {
            this.max = Double.valueOf(-10000);
            listChromosome.forEach(e -> max = e.getFuncValue() < max ? max : e.getFuncValue());
        } else {
            this.max = Double.valueOf(-10000);
            listChromosome.forEach(e -> max = e.getFuncValue() < max ? max : e.getFuncValue());
        }
    }

}
