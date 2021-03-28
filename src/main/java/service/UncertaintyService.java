package service;

public interface UncertaintyService {
    /**
     * gets uncertainty by measurement data
     * @param data request data
     * @return measurement data
     */
    String getUncertainty(String data);
}
