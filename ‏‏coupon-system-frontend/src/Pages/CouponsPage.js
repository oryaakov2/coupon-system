import { useDispatch, useSelector } from 'react-redux'
import { NotificationManager } from 'react-notifications'
import { dataActions } from '../Store/dataSlice'
import { useEffect } from 'react'
import CouponList from '../AuxiliaryComponents/CouponList'
import { authActions } from '../Store/authSlice'
import '../App.css'

const CouponsPage = () => {
    const companyUrlPath = 'http://localhost:8080/api/companies/coupons?token='
    const customerUrlPath = 'http://localhost:8080/api/customers/coupons?token='

    let urlPath = ''

    const dispatch = useDispatch()

    const token = useSelector(state => state.auth.token)
    const isCompany = useSelector(state => state.auth.isCompany)
    const coupons = useSelector(state => state.data.coupons)

    useEffect(() => {
        fetchCoupons()
    }, [coupons.length])

    if (isCompany) {
        urlPath = companyUrlPath

    } else {
        urlPath = customerUrlPath
    }

    const fetchCoupons = async () => {

        try {
            const response = await fetch(urlPath + `${token}`)

            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }

            const coupons = await response.json()

            dispatch(dataActions.setCoupons(coupons))

        } catch (error) {
            dispatch(authActions.logout())
            NotificationManager.error(`${error}`, 'Error', 2000)
        }
    }

    const onChangeHandler = (event) => {
        const sortOrder = event.target.value
        dispatch(dataActions.setSortOrder(sortOrder))
    }

    return (
        <>
            <div className="fadeInDown" style={{ paddingTop: "40px", marginLeft: "130px", marginTop:"40px" }}>
                <label htmlFor="sort-order">
                    <select onChange={onChangeHandler} >
                        <option value="">Sort by</option>
                        <option value="title">Title</option>
                        <option value="price">Price: low to high</option>
                        <option value="endDate">Expired Date</option>
                    </select>
                </label>
            </div>

            <CouponList hidden={true} coupons={coupons}></CouponList>
        </>
    )
}

export default CouponsPage;