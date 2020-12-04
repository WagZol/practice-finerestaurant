package ITCExcercies.ITCRestaurant.Service;

import ITCExcercies.ITCRestaurant.Dao.Coupon;

public interface ICouponService {
    public Coupon findCouponByCode(String couponCode);
}
