package xyz.xfcloud.flight.dubbo.consumer.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import xyz.xfcloud.flight.dubbo.pojo.data.Flight;

import java.util.List;

/**
 * created by Jizhi on 2019/10/17
 */
@Mapper
@Repository
public interface FlightMapper {

    @Select("select * from flight where sms = 0 and sendEmail = 0 and TO_DAYS(departDate) > TO_DAYS(now()) order by id desc")
    List<Flight> getFlights();

    @Insert("INSERT INTO flight VALUES(null,#{departCity},#{departCityCode},#{arriveCity},#{arriveCityCode},#{phone},#{username},#{price},#{departDate},#{departTime},0,NOW(),NOW(),#{email},0);")
    void addFlight(Flight flight);

    @Update("UPDATE flight SET sms = 1 , updateTime = NOW() WHERE id = #{id}")
    void updateFlightPhone(Integer id);

    @Update("UPDATE flight SET sendEmail = 1 , updateTime = NOW() WHERE id = #{id}")
    void updateFlightEmail(Integer id);
}
