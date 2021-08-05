import './Login.css'
import '../../App.css'
import { useRef } from 'react'
import { useDispatch } from 'react-redux'
import { useHistory } from 'react-router'
import { authActions } from '../../Store/authSlice'
import couponImage from '../../Images/coupon.jpg'
import { NotificationManager } from 'react-notifications'

const Login = () => {
    const customerUrlPath = 'http://localhost:8080/api/login-customer?'
    const companyUrlPath = 'http://localhost:8080/api/login-company?'

    const emailRef = useRef(null)
    const passwordRef = useRef(null)

    const dispatch = useDispatch()
    const history = useHistory()

    let isCompany = false
    let urlPath = customerUrlPath

    const onChangeHandler = () => {
        isCompany = !isCompany

        if (isCompany) {
            urlPath = companyUrlPath

        } else {
            urlPath = customerUrlPath
        }
    }

    const loginHandler = async (email, password, urlPath) => {
        const response = await fetch(urlPath + `email=${email}&password=${password}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })

        if (!response.ok) {
            const errorMessage = await response.json()
            throw new Error(errorMessage.message)
        }

        const token = await response.text()

        if (isCompany) {
            const response = await fetch(`http://localhost:8080/api/companies?token=${token}`)
            const user = await response.json()

            dispatch(authActions.login({ token, isCompany, user }))

            NotificationManager.success(`Welcome Back ${user.name} `, 'Success', 2000)

        } else {
            const response = await fetch(`http://localhost:8080/api/customers?token=${token}`)
            const user = await response.json()

            dispatch(authActions.login({ token, isCompany, user }))

            NotificationManager.success(`Welcome Back ${user.firstName} ${user.lastName} `, 'Success', 2000)
        }
    }

    const onLoginHandler = async (event) => {
        event.preventDefault()

        const email = emailRef.current.value
        const password = passwordRef.current.value

        await loginHandler(email, password, urlPath)
            .catch(error => NotificationManager.error(`${error}`, 'Login failed!'))

        history.replace("/coupons")
    }

    return (
        <div className="wrapper fadeInDown">
            <div id="formContent">
                <div className="fadeIn first">
                    <img src={couponImage} id="logo" alt="Coupon Logo" />
                    <h1>Sign In</h1>
                </div>
                <form onSubmit={onLoginHandler}>
                    <input type="email" className="fadeIn second" ref={emailRef} placeholder="Enter email" />
                    <input type="password" className="fadeIn third" ref={passwordRef} placeholder="Enter password" />
                    <br />
                    <label className="fadeIn fourth form-check-label" >
                        <input onChange={onChangeHandler} type="checkbox" />
                        <h2>As Company</h2>
                    </label>
                    <br />
                    <input type="submit" className="fadeIn fourth" value="Login" />
                </form>
            </div>
        </div>
    )
}

export default Login;