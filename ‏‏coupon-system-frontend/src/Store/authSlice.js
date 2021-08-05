import { createSlice } from "@reduxjs/toolkit"

const authSlice = createSlice({
    name: 'auth',
    initialState: {
        token: JSON.parse(localStorage.getItem('token')),
        isCompany: JSON.parse(localStorage.getItem('isCompany')),
        user: JSON.parse(localStorage.getItem('user'))
    },
    reducers: {
        login(state, action) {
            const details = action.payload

            localStorage.setItem('user', JSON.stringify(details.user))
            localStorage.setItem('token', JSON.stringify(details.token))
            localStorage.setItem('isCompany', JSON.stringify(details.isCompany))

            state.token = details.token
            state.isCompany = details.isCompany
            state.user = details.user
        },
        logout(state) {
            state.token = undefined
            localStorage.removeItem('token')
            localStorage.removeItem('isCompany')
            localStorage.removeItem('user')
        },
        setUser(state, action) {
            state.user = action.payload
            localStorage.setItem('user', JSON.stringify(state.user))
        }
    }
})

export const authActions = authSlice.actions;
export default authSlice.reducer;