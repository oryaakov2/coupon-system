import Coupon from '../Components/Coupon/Coupon'
import '../Components/Card/Card.css'

const CouponList = (props) => {

    return (
        <li className="container-item">
            {props.coupons.map(coupon => <Coupon
                key={coupon.id}
                hidden={props.hidden}
                coupon={coupon} />)
            }
        </li>
    )
}

export default CouponList;