package ITCExcercies.ITCRestaurant.Repository;

import ITCExcercies.ITCRestaurant.Dao.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

    @Query(value = "SELECT * FROM coupon WHERE id=:couponCode",
            nativeQuery = true)
    public Coupon findByCode(@Param("couponCode") String couponCode);
}
