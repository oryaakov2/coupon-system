import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import 'react-notifications/lib/notifications.css'
import { NotificationContainer } from 'react-notifications'
import Login from './Components/Login/Login'
import { Redirect, Route, Switch } from 'react-router-dom'
import './App.css'
import { useSelector } from 'react-redux'
import CouponsPage from './Pages/CouponsPage'
import PurchasePage from './Pages/PurchasePage'
import Header from './Components/Header/Header'
import CreateCouponPage from './Pages/CreateCouponPage'
import Modal from './Components/Modal/Modal'
import AccountPage from './Pages/AccountPage'
import PageNotFound from './Pages/PageNotFound'

function App() {

  const token = useSelector(state => state.auth.token)
  const toggleModal = useSelector(state => state.ui.toggleModal)

  return (
    <>
      {toggleModal ? <Modal /> : null}
      {token ? <Header /> : null}

      <Switch>
        <Route exact path='/'>
          <Redirect to='/coupons' />
        </Route>

        <Route path='/login'>
          <Login />
        </Route>

        <Route path='/coupons' render={() => token ? <CouponsPage /> : <Redirect to='/login' />} ></Route>
        <Route path='/purchase-coupons' render={() => token ? <PurchasePage /> : <Redirect to='/login' />} ></Route>
        <Route path='/add' render={() => token ? <CreateCouponPage /> : <Redirect to='/login' />} ></Route>
        <Route path='/account' render={() => token ? <AccountPage /> : <Redirect to='/login' />} ></Route>

        <PageNotFound />
      </Switch>

      <NotificationContainer />
    </>
  )
}

export default App;