package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Coupon;
import ITCExcercies.ITCRestaurant.Repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService implements ICouponService{

    @Autowired
    CouponRepository couponRepository;

    @Override
    public Coupon findCouponByCode(String couponCode) {
        return couponRepository.findByCode(couponCode);
    }
}
