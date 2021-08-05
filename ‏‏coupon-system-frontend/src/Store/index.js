import { configureStore } from "@reduxjs/toolkit"
import authSlice from './authSlice'
import dataSlice from './dataSlice'
import uiSlice from "./uiSlice";

const store = configureStore({
    reducer: {
        auth: authSlice,
        data: dataSlice,
        ui: uiSlice
    }
})

export default store;