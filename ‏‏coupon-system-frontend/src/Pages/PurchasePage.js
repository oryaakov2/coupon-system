import { useEffect } from 'react'
import { NotificationManager } from 'react-notifications'
import { useDispatch, useSelector } from 'react-redux'
import CouponList from '../AuxiliaryComponents/CouponList'
import { authActions } from '../Store/authSlice'
import { dataActions } from '../Store/dataSlice'

const PurchasePage = () => {
    const customerUrlPath = 'http://localhost:8080/api/customers/coupons-for-purchase?token='

    const dispatch = useDispatch()

    const token = useSelector(state => state.auth.token)
    const couponsForPurchase = useSelector(state => state.data.couponsForPurchase)

    useEffect(() => {
        fetchCouponsForPurchase()
    }, [couponsForPurchase.length])

    const fetchCouponsForPurchase = async () => {
        try {
            const response = await fetch(customerUrlPath + `${token}`)

            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }
            const couponsForPurchase = await response.json()

            dispatch(dataActions.setCouponsForPurchase(couponsForPurchase))

        } catch (error) {
            dispatch(authActions.logout())
            NotificationManager.error(`${error}`, 'Error', 2000)
        }
    }

    return (
        <>
            <CouponList coupons={couponsForPurchase} />
        </>
    )
}

export default PurchasePage;