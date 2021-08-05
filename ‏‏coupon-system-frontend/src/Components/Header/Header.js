import { Navbar, Nav } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import { authActions } from '../../Store/authSlice'
import { NavLink } from 'react-router-dom'
import './Header.css'
import { PersonFill, BoxArrowRight } from 'react-bootstrap-icons';

const Header = () => {
    const dispatch = useDispatch()
    const isCompany = useSelector(state => state.auth.isCompany)

    const logoutHandler = () => {
        dispatch(authActions.logout())
    }

    let content = null

    if (isCompany) {
        content = <>
            <NavLink className='nav-link' to="/coupons">My Coupons</NavLink>
            <NavLink className='nav-link' to="/add">Create Coupon</NavLink>
        </>
    } else {
        content = <>
            <NavLink className='nav-link' to="/coupons">My Coupons</NavLink>
            <NavLink className='nav-link' to="/purchase-coupons">Purchase Coupons</NavLink>
        </>
    }

    return (
        <Navbar collapseOnSelect fixed="top" bg="dark" variant="dark" expand='sm'>
            <Navbar.Brand className="brand">Coupons System</Navbar.Brand>
            <Navbar.Toggle style={{ margin: "10px", padding: "5px" }} aria-controls='responsive-navbar-nav' />
            <Navbar.Collapse id='responsive-navbar-nav'>
                <Nav style={{ fontSize: "22px" }} className="mr-auto">
                    {content}
                </Nav>
                <Nav style={{ fontSize: "22px" }} className="ml-auto">
                    <NavLink className='nav-link' to="/account"> Account <PersonFill /></NavLink>
                    <NavLink onClick={logoutHandler} className='nav-link' to="/login">Logout <BoxArrowRight /></NavLink>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default Header;