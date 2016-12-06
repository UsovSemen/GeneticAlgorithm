package entity;

import java.io.*;

/**
 * Created by Sam on 18.11.2016.
 */
public class Chromosome implements Cloneable, Serializable {

    int position;
    String binary;
    double value;
    double funcValue;
    double probability;
    String partner;

    public Chromosome(int position, String binary, double value, double funcValue)  {
        this.position = position;
        this.binary = binary;
        this.value = value;
        this.funcValue = funcValue;
    }

    public Chromosome() {
    }

    public Chromosome(Chromosome chromosome) {
        this.position = chromosome.getPosition();
        this.binary = chromosome.getBinary();
        this.value = chromosome.getValue();
        this.funcValue = chromosome.getFuncValue();
        this.probability = chromosome.getProbability();
        this.partner = chromosome.getPartner();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {

        this.binary = Operations.checkLength(binary);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getFuncValue() {
        return funcValue;
    }

    public void setFuncValue(double funcValue) {
        this.funcValue = funcValue;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;

        if (position != that.position) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (Double.compare(that.funcValue, funcValue) != 0) return false;
        if (Double.compare(that.probability, probability) != 0) return false;
        if (binary != null ? !binary.equals(that.binary) : that.binary != null) return false;
        return partner != null ? partner.equals(that.partner) : that.partner == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position;
        result = 31 * result + (binary != null ? binary.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(funcValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(probability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (partner != null ? partner.hashCode() : 0);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "entity.Chromosome{" +
                "position=" + position +
                ", binary='" + binary + '\'' +
                ", value=" + value +
                ", funcValue=" + funcValue +
                ", probability=" + probability +
                ", partner='" + partner + '\'' +
                '}';
    }
}
