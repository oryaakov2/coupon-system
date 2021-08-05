import { createSlice } from "@reduxjs/toolkit";

const uiSlice = createSlice({
    name: 'ui',
    initialState: { toggleModal: false, text: '', isDisabled: true },
    reducers: {
        setToggleModal(state, action) {
            state.toggleModal = !state.toggleModal

            if (state.toggleModal === true) {
                state.text = action.payload

            } else {
                state.text = ''
            }
        },
        setDisabled(state) {
            state.isDisabled = !state.isDisabled
        }
    }
})

export const uiActions = uiSlice.actions;
export default uiSlice.reducer;