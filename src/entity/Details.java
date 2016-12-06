package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 05.12.2016.
 */
public class Details {
    public List<List<Chromosome>> ishList = new ArrayList<>();
    public List<List<Chromosome>> afterRep = new ArrayList<>();
    public List<List<Chromosome>> afterCros = new ArrayList<>();
    public List<List<Chromosome>> afterMut = new ArrayList<>();
    public List<List<Chromosome>> afterAll = new ArrayList<>();
    public List<List<Integer>> indexMut = new ArrayList<>();
}
