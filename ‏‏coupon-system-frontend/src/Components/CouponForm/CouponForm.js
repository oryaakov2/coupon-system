import { useDispatch, useSelector } from 'react-redux'
import { authActions } from '../../Store/authSlice'
import { useRef } from "react"
import { NotificationManager } from 'react-notifications'
import './CouponForm.css'
import { useHistory } from 'react-router-dom'
import { uiActions } from '../../Store/uiSlice'

const CouponForm = () => {

    const token = useSelector(state => state.auth.token)

    const history = useHistory()
    const dispatch = useDispatch()

    const titleRef = useRef(null)
    const startDateRef = useRef(null)
    const endDateRef = useRef(null)
    const amountRef = useRef(null)
    const categoryRef = useRef(null)
    const priceRef = useRef(null)
    const descRef = useRef(null)
    const imageRef = useRef(null)

    const createCoupon = async (event) => {
        event.preventDefault()

        const coupon = {
            title: titleRef.current.value,
            startDate: startDateRef.current.value,
            endDate: endDateRef.current.value,
            amount: amountRef.current.value,
            category: categoryRef.current.value,
            price: priceRef.current.value,
            description: descRef.current.value,
            imageURL: imageRef.current.value
        }

        try {
            const response = await fetch(`http://localhost:8080/api/companies/add?token=${token}`, {
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

            const newCoupon = await response.json()

            dispatch(uiActions.setToggleModal(`A ${newCoupon.title} coupon has been added!`))

            history.push("/coupons")

        } catch (error) {
            dispatch(authActions.logout())
            NotificationManager.error(`${error}`, 'Error', 2000)
        }
    }

    return (
        <div className="wrapper fadeInDown">
            <div className="coupon-form">
                <div className="fadeIn first">
                    <h2 className="active">Create New Coupon</h2>
                </div>
                <form onSubmit={createCoupon}>
                    <label>
                        <input type="text" className="fadeIn second" placeholder="Title" ref={titleRef} required />
                    </label>
                    <label>
                        <input type="date" className="fadeIn second" placeholder="Start Date" ref={startDateRef} required />
                    </label>
                    <label>
                        <input type="date" className="fadeIn second" placeholder="End Date" ref={endDateRef} required />
                    </label>
                    <label>
                        <input type="number" className="fadeIn second" placeholder="Amount" ref={amountRef} min="1" required />
                    </label>
                    <label>
                        <input type="number" className="fadeIn third" placeholder="Category" ref={categoryRef} min="1" required />
                    </label>
                    <label>
                        <input type="number" className="fadeIn third" placeholder="Price$" ref={priceRef} min="0.00" required />
                    </label>
                    <label>
                        <textarea
                            className="fadeIn third"
                            min="10"
                            cols="40"
                            rows="3"
                            minLength="15"
                            maxLength="20"
                            placeholder="Description"
                            ref={descRef}
                            required />
                    </label>
                    <label>
                        <h2 className="fadeIn fourth">Enter an image url</h2>
                        <input
                            type="url"
                            maxLength="200"
                            className="fadeIn fourth"
                            placeholder="http://example.com"
                            pattern="https://.*"
                            ref={imageRef} />
                    </label>
                    <br />
                    <br />
                    <label>
                        <input type="submit" className="fadeIn fourth" value="Create Coupon" />
                    </label>
                    <label>
                        <input type="reset" className="fadeIn fourth" value="Reset" />
                    </label>
                </form>
            </div>
        </div>
    )
}

export default CouponForm;