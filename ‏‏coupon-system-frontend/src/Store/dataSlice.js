import { createSlice } from "@reduxjs/toolkit"

const dataSlice = createSlice({
    name: 'data',
    initialState: { coupons: [], couponsForPurchase: [], sortOrder: '' },
    reducers: {
        setCoupons(state, action) {
            state.coupons = action.payload
        },
        setCouponsForPurchase(state, action) {
            state.couponsForPurchase = action.payload
        },
        removeCoupon(state, action) {
            state.coupons.pop(action.payload)
        },
        removeCouponForPurchase(state, action) {
            state.couponsForPurchase.pop(action.payload)
        },
        setSortOrder(state, action) {
            state.sortOrder = action.payload

            if (state.sortOrder === 'title') {
                state.coupons.sort((a, b) => (a.title.toUpperCase() > b.title.toUpperCase() ? 1 : -1))

            } else if (state.sortOrder === 'price') {
                state.coupons.sort((a, b) => (a.price > b.price ? 1 : -1))

            } else if (state.sortOrder === 'endDate') {
                state.coupons.sort((a, b) => (a.endDate > b.endDate ? 1 : -1))

            } else {
                state.coupons.sort(() => (Math.random() - 0.5))
            }
        }
    }
})

export const dataActions = dataSlice.actions;
export default dataSlice.reducer;