import { NotificationManager } from 'react-notifications'
import { useRef } from 'react'
import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {uiActions} from '../../Store/uiSlice'
import './Account.css'
import {authActions} from '../../Store/authSlice'


const Account = () => {

    const dispatch = useDispatch()

    const token = useSelector(state => state.auth.token)
    const isCompany = useSelector(state => state.auth.isCompany)
    const user = useSelector(state => state.auth.user)
    let isDisabled = useSelector(state => state.ui.isDisabled);

    const fNameRef = useRef(user.firstName)
    const lNameRef = useRef(user.lastName)
    const brandNameRef = useRef(user.name)
    const emailRef = useRef(user.email)
    const passRef = useRef(user.password)
    const confPassRef = useRef(user.password)

    useEffect(() => {
    }, [isDisabled])

    const updateCompany = async () => {

        const updateUser = {
            id: user.id,
            name: brandNameRef.current.value,
            email: emailRef.current.value,
            password: passRef.current.value
        }

        try {
            const response = await fetch(`http://localhost:8080/api/companies/update?token=${token}`, {
                method: 'PATCH',
                body: JSON.stringify(updateUser),
                headers: {
                    'Content-type': 'application/json'
                }
            })

            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }

            const updatedUser = await response.json()

            dispatch(authActions.setUser(updatedUser))

            NotificationManager.success(`Saved changes successfully`, 'Success', 2000)

        } catch (error) {
            dispatch(authActions.logout())
            NotificationManager.error(`${error}`, 'Error', 2000)
        }
    }

    const updateCustomer = async () => {
        const updateUser = {
            id: user.id,
            firstName: fNameRef.current.value,
            lastName: lNameRef.current.value,
            email: emailRef.current.value,
            password: passRef.current.value
        }

        try {
            const response = await fetch(`http://localhost:8080/api/customers/update?token=${token}`, {
                method: 'PATCH',
                body: JSON.stringify(updateUser),
                headers: {
                    'Content-type': 'application/json'
                }
            })

            if (!response.ok) {
                const errorMessage = await response.json()
                throw new Error(errorMessage.message)
            }

            const updatedUser = await response.json()

            dispatch(authActions.setUser(updatedUser))

            NotificationManager.success(`Saved changes successfully`, 'Success', 2000)

        } catch (error) {
            dispatch(authActions.logout())
            NotificationManager.error(`${error}`, 'Error', 2000)
        }
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault()

        if (passRef.current.value === confPassRef.current.value) {
            if (isCompany) {
                await updateCompany()

            } else if (!isCompany) {
                await updateCustomer()
            }
        } else {
            NotificationManager.error('Incorrect Password', 'Error', 2000)
        }
        dispatch(uiActions.setDisabled())
    }

    const onEditHandler = () => {
        dispatch(uiActions.setDisabled())
    }

    const onResetHandler = () => {
        dispatch(uiActions.setDisabled())
    }

    let accountContent = <>
        <h1 style={{ fontWeight: "bold" }}>{user.firstName} {user.lastName}</h1>
        <br />
        <br />
        <label htmlFor="firstName">First Name
            <input ref={fNameRef} type="text" defaultValue={user.firstName} disabled={isDisabled} required></input>
        </label>
        <label htmlFor="lastName">Last Name
            <input ref={lNameRef} type="text" defaultValue={user.lastName} disabled={isDisabled} required></input>
        </label>
        <label htmlFor="email">Email
            <input ref={emailRef} type="email" defaultValue={user.email} disabled={isDisabled} required></input>
        </label>
        <label htmlFor="password" hidden={isDisabled}>Password
            <input ref={passRef} type="password" defaultValue={user.password} required></input>
        </label>
        <label htmlFor="confirmPassword" hidden={isDisabled}>Confirm Password
            <input ref={confPassRef} type="password" defaultValue={user.password} required></input>
        </label>
    </>

    if (isCompany) {
        accountContent = <>
            <h1 style={{ fontWeight: "bold" }}>{user.name}</h1>
            <br />
            <br />
            <label htmlFor="name">Brand Name
                <input ref={brandNameRef} type="text" defaultValue={user.name} disabled={isDisabled} required></input>
            </label>
            <label htmlFor="email">Email
                <input ref={emailRef} type="email" defaultValue={user.email} disabled={isDisabled} required></input>
            </label>
            <label htmlFor="password" hidden={isDisabled}>Password
                <input ref={passRef} type="password" defaultValue={user.password} required></input>
            </label>
            <label htmlFor="confirmPassword" hidden={isDisabled}>Confirm Password
                <input ref={confPassRef} type="password" defaultValue={user.password} required></input>
            </label>
        </>
    }

    return (
        <div className="fadeInDown account">
            <form onReset={onResetHandler} onSubmit={onSubmitHandler}>
                {accountContent}
                {isDisabled ? <button onClick={onEditHandler}>Edit</button> :
                    <div>
                        <input type="reset" value="Cancel" />
                        <input id="save" type="submit" value="Save Changes" />
                    </div>}
            </form>
        </div>
    )
}

export default Account;