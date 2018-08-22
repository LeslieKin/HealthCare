package project.healthcare.phone.measure.ehealth;

public interface ContentFinder<T> {

  void find(byte[] data);

  void reset();

  boolean hasFound();

  T getResult();

}
