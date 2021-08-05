import { useDispatch, useSelector } from 'react-redux'
import { uiActions } from '../../Store/uiSlice'
import './Modal.css'

const Modal = () => {

    const dispatch = useDispatch()
    const text = useSelector(state => state.ui.text)

    const onCloseHandler = () => {
        dispatch(uiActions.setToggleModal())
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <p className="active">{text}</p>
                <br />
                <button className="modal-button" onClick={onCloseHandler}>close</button>
            </div>
        </div>
    )
}

export default Modal;