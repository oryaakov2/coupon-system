import { NotificationManager } from 'react-notifications'
import { useDispatch, useSelector } from 'react-redux'
import { Card } from 'react-bootstrap'
import { dataActions } from '../../Store/dataSlice'
import couponImage from '../../Images/coupon.jpg'
import '../../App.css'

const Coupon = (props) => {

    const token = useSelector(state => state.auth.token)
    const isCompany = useSelector(state => state.auth.isCompany)

    const dispatch = useDispatch()

    const coupon = props.coupon

    let couponDate = <h2>Expired date: {coupon.endDate}</h2>

    const calculateDiffrenceInDays = () => {
        const currentDate = new Date()
        const couponEndDate = new Date(coupon.endDate)

        const diffrenceInTime = couponEndDate.getTime() - currentDate.getTime()

        const diffrenceInDays = diffrenceInTime / (1000 * 3600 * 24)

        if (diffrenceInDays <= 7) {
            return true
        }
        return false
    }

    if (calculateDiffrenceInDays()) {
        couponDate = <h2 className="date">Expired Date Soon! {coupon.endDate}</h2>
    }

    const companyUrlPath = 'http://localhost:8080/api/companies/delete?token='
    const customerUrlPath = 'http://localhost:8080/api/customers/purchase-coupons?token='

    const deleteCoupon = async () => {
        const coupon = props.coupon

        try {
            const response = await fetch(companyUrlPath + `${token}`, {
                method: 'POST',
                body: JSON.stringify(coupon),
                headers: {
                    'Content-type': 'application/json'
                }
            })
            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }

            const deletedCoupon = await response.json()

            dispatch(dataActions.removeCoupon(deletedCoupon))

            NotificationManager.success(`${deletedCoupon.title} coupon removed!`, 'Removed Success', 2000)

        } catch (error) {
            NotificationManager.error(`${error}`, 'Removed Failed', 2000)
        }
    }

    const purchaseCoupon = async () => {

        try {
            const response = await fetch(customerUrlPath + `${token}`, {
                method: 'POST',
                body: JSON.stringify(coupon),
                headers: {
                    'Content-type': 'application/json'
                }
            })

            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }

            const purchasedCoupon = await response.json()

            dispatch(dataActions.removeCouponForPurchase(purchasedCoupon))

            NotificationManager.success(`${purchasedCoupon.title} purchased`, 'Purchase Successfully', 2000)

        } catch (error) {
            NotificationManager.error(`${error.message}`, 'Purchase Failed', 2000)
        }
    }

    return (
        <div>
            <Card>
                <img className="logo" src={coupon.imageURL ? coupon.imageURL : couponImage} alt='Coupon' />
                <h3>{coupon.title}</h3>
                <p>Price: {coupon.price.toFixed(2)}$</p>
                <p hidden={props.hidden}>Amount: {coupon.amount}</p>
                <p>Description: {coupon.description}</p>
                <div>
                    {couponDate}
                </div>
                <br />
                {!isCompany ?
                    <button id="purchase" hidden={props.hidden} onClick={purchaseCoupon}>Purchase</button> :
                    <button id="remove" onClick={deleteCoupon}>Remove Coupon</button>}
            </Card>
        </div>
    )
}

export default Coupon;