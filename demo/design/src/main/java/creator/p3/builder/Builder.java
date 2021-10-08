package creator.p3.builder;

import creator.p3.entity.*;

/**
 * @Description: 通用生成器接口
 * @author: lwq
 */
public interface Builder {
    void setCarType(CarType type);
    void setSeats(int seats);
    void setEngine(Engine engine);
    void setTransmission(Transmission transmission);
    void setTripComputer(TripComputer tripComputer);
    void setGPSNavigator(GPSNavigator gpsNavigator);
}
